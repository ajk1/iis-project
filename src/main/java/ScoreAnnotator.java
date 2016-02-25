import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;

import type.InputDocument;
import type.Review;
import type.Score;
import util.Utils;

public class ScoreAnnotator extends JCasAnnotator_ImplBase {


  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    System.out.println(">> Score Annotator Processing");
    // get document text from the CAS
    String docText = aJCas.getDocumentText();

    FSIterator it = aJCas.getAnnotationIndex(InputDocument.type).iterator();
    if (it.hasNext()) {
      InputDocument doc = (InputDocument) it.next();
      
      //zero-order regression
      double reviewCount = 0;
      double sumScore = 0;  
      for (Review review : Utils.fromFSListToLinkedList(doc.getReviews(), Review.class)) {
          reviewCount++;
          sumScore += review.getScore().getGoldLabel();
          //component implementation
      }
      
      double avgScore = sumScore / reviewCount;
      System.out.println("... Avaerage Score of reviews is "+avgScore);
      
      for (Review review : Utils.fromFSListToLinkedList(doc.getReviews(), Review.class)) {
    	  Score sc = review.getScore();
    	  sc.setRegressionScore(avgScore);
    	  sc.setClassificationScore((int) Math.floor(avgScore + 0.5));
      }

      
      
    }
  }

}
