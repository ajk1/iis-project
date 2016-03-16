import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.util.StringUtils;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer.PTBTokenizerFactory;
import type.InputDocument;
import type.Ngram;
import type.Review;
import type.Token;
import util.StopWordUtils;
import util.Utils;

public class TokenAnnotator extends JCasAnnotator_ImplBase {

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    System.out.println(">> Token Annotator Processing");
    StopWordUtils swu = new StopWordUtils("limit");

    // get document text from the CAS
    String docText = aJCas.getDocumentText();

    FSIterator it = aJCas.getAnnotationIndex(InputDocument.type).iterator();
    if (it.hasNext()) {
      InputDocument doc = (InputDocument) it.next();
      

      int ctr = 0;
      for (Review review : Utils.fromFSListToLinkedList(doc.getReviews(), Review.class)) {
    	  if(ctr++ > 10) break;
    	  
          String body = review.getRawText();
          
          //component implementation
          
          List<type.Sentence> sentences = new ArrayList<type.Sentence>();
          
          //annotate sentences
          Reader reader = new StringReader(body);
          DocumentPreprocessor dp = new DocumentPreprocessor(reader);
          for(List<HasWord> hw: dp) {
        	  String rawSentenceText = Sentence.listToString(hw);
        	  String cleanSentenceText = swu.removeStopword(rawSentenceText);
        	  
              type.Sentence sentence = new type.Sentence(aJCas);
              sentence.setRawText(cleanSentenceText);
              sentence.addToIndexes();              
              sentences.add(sentence);
          }
          review.setSentences(Utils.fromCollectionToFSList(aJCas, sentences));	//add sentences to review scope list
    	  System.out.println("... review: " + ctr + " added "+ sentences.size() +" sentences");
          
      }
    }
    
  }

}
