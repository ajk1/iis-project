import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.util.FSCollectionFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceProcessException;

//import rank.CompositeRanker;
//import rank.IRanker;
import type.Measurement;
import type.Review;
import type.Score;
import util.Utils;

/**
 * This CAS Consumer generates the report file with the method metrics
 */
public class ReviewScoreWriter extends CasConsumer_ImplBase {
	final String PARAM_OUTPUTDIR = "OutputDir";

	final String OUTPUT_FILENAME = "ErrorAnalysis.csv";

	File mOutputDir;

//  IRanker ngramRanker, otherRanker;
//
//  CompositeRanker compositeRanker;

	@Override
	public void initialize() throws ResourceInitializationException {
	    String mOutputDirStr = (String) getConfigParameterValue(PARAM_OUTPUTDIR);
	    if (mOutputDirStr != null) {
	    	mOutputDir = new File(mOutputDirStr);
	    	if (!mOutputDir.exists()) {
	    		mOutputDir.mkdirs();
	    	}
	    }
//
//    // Initialize rankers
//    compositeRanker = new CompositeRanker();
//    ngramRanker = new NgramRanker();
//    otherRanker = new OtherRanker();
//    compositeRanker.addRanker(ngramRanker);
//    compositeRanker.addRanker(otherRanker);
  }

  @Override
  public void processCas(CAS arg0) throws ResourceProcessException {
	  System.out.println(">> Review Score Writer Processing");
	  // Import the CAS as a aJCas
	  JCas aJCas = null;
	  File outputFile = null;
	  PrintWriter writer = null;
	  try {
      aJCas = arg0.getJCas();
//      try {
//        outputFile = new File(Paths.get(mOutputDir.getAbsolutePath(), OUTPUT_FILENAME).toString());
//        outputFile.getParentFile().mkdirs();
//        writer = new PrintWriter(outputFile);
//      } catch (FileNotFoundException e) {
//        System.out.printf("Output file could not be written: %s\n",
//                Paths.get(mOutputDir.getAbsolutePath(), OUTPUT_FILENAME).toString());
//        return;
//      }
//
//      writer.println("question_id,tp,fn,fp,precision,recall,f1");
      
      // Retrieve all the reviews for printout
      List<Review> allReviews = UimaUtils.getAnnotations(aJCas, Review.class);
      
      double sumErrorSquare = 0;
      double[] tp = new double[5];
      double[] fp = new double[5];
      double[] tn = new double[5];
      double[] fn = new double[5];
      
      for (Review review : allReviews) {
      
    	  Score sc = review.getScore();
			List<Integer> cScores = Utils.fromIntegerListToLinkedList(sc.getClassificationScores());
			List<Float> rScores = Utils.fromFloatListToLinkedList(sc.getRegressionScores());
    	  
    	  //regression-like evaluation
    	  sumErrorSquare += Math.pow(review.getScore().getGoldLabel() - rScores.get(0) ,2);
    	  
    	  //classifier evaulation
    	  for(int i=1; i<=5; i++) {
    		  if(review.getScore().getGoldLabel() == i && cScores.get(0) == i) {
    			  tp[i-1]++;
    		  } else if(review.getScore().getGoldLabel() == i && cScores.get(0) != i) {
    			  fn[i-1]++;
    		  } else if(review.getScore().getGoldLabel() != i && cScores.get(0) != i) {
    			  tn[i-1]++;
    		  } else {
    			  fp[i-1]++;
    		  }
    	  }
      }
      
      System.out.print("tp: ");
      for(double d: tp) { System.out.printf("%-10.0f",d);}
      System.out.println("");
      System.out.print("fp: ");
      for(double d: fp) { System.out.printf("%-10.0f",d);}
      System.out.println("");
      System.out.print("tn: ");
      for(double d: tn) { System.out.printf("%-10.0f",d);}
      System.out.println("");
      System.out.print("fn: ");
      for(double d: fn) { System.out.printf("%-10.0f",d);}
      System.out.println("");
      
      
	  System.out.println("... MSE: " + sumErrorSquare / allReviews.size());
	  double f1agg = 0;
      for(int i=0; i<5; i++) {
    	  double precision = (tp[i] + fp[i] == 0) ? 0 : tp[i] / (tp[i] + fp[i]);
    	  double recall = (tp[i] + fn[i] == 0) ? 0 : tp[i] / (tp[i] + fn[i]);
    	  double accuracy = (tp[i] + tn[i])/(tp[i]+fp[i]+tn[i]+fn[i]);
    	  double f1 = (precision == 0 || recall == 0) ? 0.0 : 2 * precision * recall / (precision + recall);
    	  System.out.println("... accuracy of rating " + (i+1) +" : " + accuracy);
    	  System.out.println("... precision of rating " + (i+1) +" : " + precision);    	  
    	  System.out.println("... recall of rating " + (i+1) +" : " + recall);    	  
    	  System.out.println("... f1 of rating " + (i+1) +" : " + f1);  
    	  f1agg += f1*(tp[i]+fn[i]);
      }
      System.out.println("... weighted f1 (aggregate) : " + f1agg);
      
    } catch (CASException e) {
      try {
        throw new CollectionException(e);
      } catch (CollectionException e1) {
        e1.printStackTrace();
      }
    } finally {
      if (writer != null)
        writer.close();
    }
    
  }
}

