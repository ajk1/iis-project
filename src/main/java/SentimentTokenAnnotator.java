import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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

  public HashMap<String, Double> sentiDictionary = new HashMap<>();
	
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    System.out.println(">> Sentiment Token Annotator Processing");
    
    //read library TODO: use libraries by configurations
    // Open the file
    FileInputStream fstream;
	try {
	    System.out.println("... Reading Sentiment Library");
		fstream = new FileInputStream("src/main/resources/libraries/vader_sentiment_lexicon.txt");
	    BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

	    String strLine;

	    //Read File Line By Line
	    while ((strLine = br.readLine()) != null)   {
	    	String[] parts = strLine.split("\t");
	    	sentiDictionary.put(parts[0], Double.valueOf(parts[1]));
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
    			  if(sentiDictionary.containsKey(unigram.getRawText())) {
    				  System.out.println("... sentiment score of " + unigram.getRawText()+ ": " + sentiDictionary.get(unigram.getRawText()));
    				  unigram.setSentimentScore(sentiDictionary.get(unigram.getRawText()));
    			  } else {
    				  System.out.println("... sentiment score of " + unigram.getRawText()+ ": 0");
    				  unigram.setSentimentScore(0.0);    				  
    			  }
    		  }
    	  }
    	  
    	  
      }
    }
    
  }

}
