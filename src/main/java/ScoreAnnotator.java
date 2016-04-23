import java.util.Collection;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import type.Ngram;
import type.Review;
import type.Score;
import util.Utils;

public class ScoreAnnotator extends JCasAnnotator_ImplBase {
	final String PARAM_SIZELIMIT = "SizeLimit";
	private int sizeLimit;


	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);		
		sizeLimit = Integer.valueOf((String) aContext.getConfigParameterValue(PARAM_SIZELIMIT));
	}

	
	@Override
	  	public void process(JCas aJCas) throws AnalysisEngineProcessException {
	    System.out.println(">> Score Annotator Processing");
//	    System.out.println("... sizeLimit: " + sizeLimit);
	    
		// get reviews from the CAS
		Collection<Review> reviews = JCasUtil.select(aJCas, Review.class);      
//		System.out.println("... review size: " + reviews.size());
      
		
		//zero-order regression
		double reviewCount = 0;
		double sumScore = 0;  
		for (Review review : reviews) {
			reviewCount++;
			sumScore += review.getGoldLabel();
		}
      
		float avgScore = (float) (sumScore / reviewCount);
		System.out.println("... Average Score of reviews is "+avgScore);
      
		//annotating different scores
		for (Review review : reviews) {
			List<Integer> cScores = Utils.fromIntegerListToLinkedList(review.getClassificationScores());
			List<Float> rScores = Utils.fromFloatListToLinkedList(review.getRegressionScores());

			//[0] zero-order regression
			cScores.add((int) Math.floor(avgScore + 1.0));
			rScores.add(avgScore);
			
			//add back to type Score
			review.setRegressionScores(Utils.fromCollectionToFloatList(aJCas, rScores));
//			review.setClassificationScores(Utils.fromCollectionToIntegerList(aJCas, cScores));
		}

      
      
  	}

}
