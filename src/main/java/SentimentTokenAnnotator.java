import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;

import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.process.PTBTokenizer.PTBTokenizerFactory;
import type.InputDocument;
import type.Ngram;
import type.Review;
import type.Sentence;
import util.Utils;

public class SentimentTokenAnnotator extends JCasAnnotator_ImplBase {

  public List<HashMap<String, Float>> sentiDictionaries = new ArrayList<HashMap<String, Float>>();
	
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    System.out.println(">> Sentiment Token Annotator Processing");
    
    // Read library
    // Open the file
    FileInputStream fstream;
    
    //first file: vader TODO: automatically loop through librariy files in sentiment libraries folder
	try {
	    System.out.println("... Reading Vader Sentiment Libraries");
		fstream = new FileInputStream("src/main/resources/libraries/sentiment_libraries/vader_sentiment_lexicon.txt");
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
	
    
    // get document text from the CAS
    String docText = aJCas.getDocumentText();

    FSIterator it = aJCas.getAnnotationIndex(InputDocument.type).iterator();
    if (it.hasNext()) {
      InputDocument doc = (InputDocument) it.next();
      
      int ctr = 0;
      for (Review review : Utils.fromFSListToLinkedList(doc.getReviews(), Review.class)) {
    	  if(ctr++ > 10) break;
    	  
    	  //annotate unigram sentiment score
    	  List<Sentence> sentences = Utils.fromFSListToLinkedList(review.getSentences(), Sentence.class);
    	  for(Sentence s: sentences) {
        	  List<Ngram> unigrams = Utils.fromFSListToLinkedList(s.getUnigrams(), Ngram.class);
    		  for(Ngram unigram: unigrams) {
    			  
    			  for(int i=0; i<sentiDictionaries.size(); i++) {
        			  if(sentiDictionaries.get(i).containsKey(unigram.getRawText())) {
        				  List<Float> fl = Utils.fromFloatListToArrayList(unigram.getSentimentScore());
        				  fl.add(sentiDictionaries.get(i).get(unigram.getRawText()));
        				  unigram.setSentimentScore(Utils.fromCollectionToFloatList(aJCas, fl));
        			  } else {
        				  List<Float> fl = Utils.fromFloatListToArrayList(unigram.getSentimentScore());
        				  fl.add((float)0);
        				  unigram.setSentimentScore(Utils.fromCollectionToFloatList(aJCas, fl));
        			  }
    				  
    			  }
				  List<Float> fl = Utils.fromFloatListToArrayList(unigram.getSentimentScore());
//				  System.out.println("... sentiment score of " + unigram.getRawText()+ ": " + fl.get(0) + ", " + fl.get(1)); 
						  
    		  }
    	  }
    	  
    	  
      }
    }
    
  }

}
