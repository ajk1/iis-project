import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.process.PTBTokenizer.PTBTokenizerFactory;
import type.InputDocument;
import type.Ngram;
import type.Review;
import type.Score;
import type.Sentence;
import util.Utils;

public class SentimentTokenAnnotator extends JCasAnnotator_ImplBase {
	final String PARAM_SIZELIMIT = "SizeLimit";
	final String PARAM_MODE = "Mode";
	private int sizeLimit;
	private String mode;

	public List<HashMap<String, Float>> sentiDictionaries = new ArrayList<HashMap<String, Float>>();
	
	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);		
		sizeLimit = Integer.valueOf((String) aContext.getConfigParameterValue(PARAM_SIZELIMIT));
		mode = (String) aContext.getConfigParameterValue(PARAM_MODE);

	    // Read library
	    // Open the file
	    FileInputStream fstream;
	    
	    //first file: vader TODO: automatically loop through library files in sentiment libraries folder
		try {
		    System.out.println("... Reading Vader Sentiment Libraries");
			fstream = new FileInputStream("src/main/resources/libraries/sentiment_libraries/vader.txt");
		    BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		    String strLine;
		    sentiDictionaries.add(new HashMap<String, Float>());
		    
		    //Read File Line By Line
		    while ((strLine = br.readLine()) != null)   {
		    	String[] parts = strLine.split("\t");
		    	sentiDictionaries.get(0).put(parts[0], Float.valueOf(parts[1]));
		    }

		    //Close the input stream
		    br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
		    System.out.println("... Reading SenticNet Sentiment Libraries");
			fstream = new FileInputStream("src/main/resources/libraries/sentiment_libraries/senticnet.txt");
		    BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		    String strLine;
		    sentiDictionaries.add(new HashMap<String, Float>());
		    
		    //Read File Line By Line
		    while ((strLine = br.readLine()) != null)   {
		    	String[] parts = strLine.split("\t");
		    	sentiDictionaries.get(1).put(parts[0], Float.valueOf(parts[1]));
		    }

		    //Close the input stream
		    br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
  
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
    System.out.println(">> Sentiment Token Annotator Processing");
    System.out.println("... sizeLimit: " + sizeLimit);
    System.out.println("... mode: " + mode);
    
	// get reviews from the CAS
	Collection<Review> reviews = JCasUtil.select(aJCas, Review.class);      
	System.out.println("... review size: " + reviews.size());
	
	//write csv document with scores for analysis
	File outputFile = null;
    PrintWriter writer = null;
    try {
      try {
        outputFile = new File(Paths.get("src/main/resources/outputData/", "ScoringAnalysis.csv").toString());
        outputFile.getParentFile().mkdirs();
        writer = new PrintWriter(outputFile);
      } catch (FileNotFoundException e) {
        System.out.printf("Output file could not be written: %s\n",
                Paths.get("src/main/resources/outputData/", "ScoringAnalysis.csv").toString());
        return;
      }
      writer.println("s1,s2,s_agg,s_agg1,s1_match,s2_match,s1s2_match,s_agg_match,s_agg1_match,label");
      
        int ctr = 0;
        for (Review review : reviews) {
        	if(ctr++ > sizeLimit && sizeLimit != 0) break;
		  //score of review from each library
		  float[] scores = new float[sentiDictionaries.size()];
		  //number of tokens with a match in each library
		  int[] matches = new int[sentiDictionaries.size()];
		  for(int i=0; i<scores.length; i++){
			  scores[i] = 0;
			  matches[i] = 0;
		  }
		  float aggScore = 0; //aggregate score from both libraries
		  float aggScore1 = 0; //aggregate score that prioritizes VADER
		  int size = 0; //total number of tokens in review
		  int aggMatches = 0; //number of tokens matched in either library
		  
    	  //annotate unigram sentiment score
    	  List<Sentence> sentences = Utils.fromFSListToLinkedList(review.getSentences(), Sentence.class);
    	  for(Sentence s: sentences) {
    		  //scores for sentence-averaging (not yet implemented)
    		  //float[] sentenceScores = new float[sentiDictionaries.size()];
        	  List<String> unigrams = Utils.fromStringListToLinkedList(s.getUnigramList());
//    		  List<Ngram> unigrams = Utils.fromFSListToLinkedList(s.getUnigrams(), Ngram.class);
    		  for(String unigram: unigrams) {
    			  float uniScore = 0;
				  List<Float> fl = new ArrayList<Float>();

    			  for(int i=0; i<sentiDictionaries.size(); i++) {
        			  if(sentiDictionaries.get(i).containsKey(unigram)) {
        				          				  
        				  float p = sentiDictionaries.get(i).get(unigram);
        				  fl.add(p);
        				  scores[i] += p;
        				  matches[i]++;
        				  if (uniScore == 0) {
            				  uniScore = p;
            				  aggScore1 += p;
            				  aggMatches++;
        				  }
        				  else {
        					  uniScore = (uniScore + p)/2;
        				  }
        			  } else {
        				  fl.add((float)0);
        			  }
    			  }
    			  size++;
    			  aggScore += uniScore;
				  
//				  System.out.println("... sentiment score of " + unigram + ": " + fl.get(0) + ", " + fl.get(1)); 
    		  }
//    		  System.out.println(Math.round(1000*sentenceScore1)/1000.0 + "\t" + 
//    				  	Math.round(1000*sentenceScore2)/1000.0 + "\t" + s.getRawText());
    	  }
    	  writer.println(scores[0]/size + "," + scores[1]/size + "," + (aggScore/size) + "," +
    			  (aggScore1/size) + "," + scores[0]/matches[0] + "," + scores[1]/matches[1] + "," + 
    			  (scores[0]+scores[1])/(matches[0]+matches[1]) + "," + 
    			  (aggScore/aggMatches) + "," + (aggScore1/aggMatches) + 
    			  review.getScore().getGoldLabel());
//    	  Score sc = review.getScore();
//    	  sc.setRegressionScore(score1);
//    	  review.setScore(sc);
    	  
        }
    }finally {
        if (writer != null)
            writer.close();
    }
  }

}
