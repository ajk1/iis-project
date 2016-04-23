import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import type.Config;
import type.Review;
import util.Utils;
import util.VocabLearnerUtils;

import learners.*;

public class LearnerAnnotator extends JCasAnnotator_ImplBase {
	final String PARAM_SIZELIMIT = "SizeLimit";
	final String PARAM_MODE = "Mode";
	final String PARAM_MODEL_PATH = "ModelDir";
	final String PARAM_READ_RECORDS = "ReadRecords";
	private int sizeLimit;
	private String mode;
	private int numOfFolds = 10;
	private String modelPath;
	private String inputFileName;
	
	// for reading pre-train review instances
	private boolean readRecords;
	private String recordsFile = "src/main/resources/records/records.txt";
	
	// for reading vocabs
	private String vocabMode  = "write";
	private String vocabFileName  = "freq_instruments";
	private String vocabOpt  = "freq";
	private int vocabLimit  = 1000;
	
	private boolean removeStopWords = true;
	private boolean useInfoGain = false;
	
	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);		
		
		//set params
		sizeLimit = Integer.valueOf((String) aContext.getConfigParameterValue(PARAM_SIZELIMIT));
		mode = (String) aContext.getConfigParameterValue(PARAM_MODE);
		modelPath = (String) aContext.getConfigParameterValue(PARAM_MODEL_PATH);
		readRecords = Boolean.valueOf((String)aContext.getConfigParameterValue(PARAM_READ_RECORDS));
	}

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		System.out.println(">> Learner Annotator Processing");
		System.out.println("... sizeLimit: " + sizeLimit);
		System.out.println("... mode: " + mode);
		System.out.println("... modelPath: " + modelPath);
		System.out.println("... readRecords: " + readRecords);

		Config config = JCasUtil.selectSingle(aJCas, Config.class);
		inputFileName = config.getInputFileName().split("\\.")[0];
		System.out.println("... inputFileName " + inputFileName);

		
		// 1. annotate Records from Reviews (Class <Review> is POJO)
		// get reviews from the CAS
		Collection<Review> allReviews = JCasUtil.select(aJCas, Review.class);
		
		//subset reviews to working reviews by sizeLimit
		List<Review> reviews = subsetReviewsByLimit(allReviews, sizeLimit);
		
		System.out.println("... allReview size: " + reviews.size());
		System.out.println("... review size: " + reviews.size());

		// 1.1 init vocab and write vocab (CV and Train) / Read vocal (Test)
		// PARAMS: vocab size limit

		Set<String> vocabulary = new HashSet<String>();

		// DataSet, in the format of Record
		List<Record> data = new ArrayList<Record>();
		
		// Read vocab or build vocab using the current 
		vocabulary = VocabLearnerUtils.getVocab(vocabMode, inputFileName, vocabLimit, vocabOpt, reviews);
		Record.setVocab(vocabulary);
		
		// 1.2 create Record List for unified learner input data format
		if (!readRecords) {
			data = Record.reviewsToRecordsWithNeg(reviews);
//			writeRecords(data);
		}
		else { 
			data = readRecords(reviews);
		}
		
		// 2. Learners start working here
		ClassificationLearner nbLearner = new NaiveBayesLearner();
		ClassificationLearner nnLearner = new NeuralNetLearner();
		Learner svmLearner = new SVMLearner();
		Learner regLearner = new LinearRegressionLearner();
		
		//naive bayes init
		nbLearner.setModelPath(modelPath);
		
		//neural network init
		nnLearner.setModelPath(modelPath);
				
		if(mode.equals("train")) {
			System.out.println("... Learner Annotator: TRAIN MODE ... ");	
			
			nbLearner.initTrain(modelPath, data, vocabulary);
			nbLearner.train();
			nbLearner.writeModel();		
			
			nnLearner.initTrain(modelPath, data, vocabulary);
			nnLearner.train();
			nnLearner.writeModel();			
			
		} else if(mode.equals("cv")) {
			System.out.println("... Learner Annotator: CROSS VALIDATION MODE ... ");	

			for(int i = 0; i < numOfFolds; i++) {
				System.out.println("... Cross-Validation: " + (i*sizeLimit/numOfFolds+1) 
						+ " through " + Math.round((double)(i+1)*sizeLimit/numOfFolds));
				
				// get training set 
				List<Record> trainingData = new ArrayList<Record>();
				//get testing set 
				List<Record> testingData = new ArrayList<Record>();
				for(int j=0; j<sizeLimit; j++) {
					if (j >= i*sizeLimit/numOfFolds && j < Math.round((double)(i+1)*sizeLimit/numOfFolds))
						testingData.add(data.get(j));
					else
						trainingData.add(data.get(j));
				}
				
				// k-fold train
				System.out.println("... Cross-Validation: Training fold " + (i+1));
				nbLearner.initTrain(modelPath, trainingData, vocabulary);
				nbLearner.train();
				nbLearner.writeModel();			

				nnLearner.initTrain(modelPath, trainingData, vocabulary);
				nnLearner.train();
				nnLearner.writeModel();			
				
				nbLearner.initTest(modelPath);
				nnLearner.initTest(modelPath);

				// k-fold test
				System.out.println("... Cross-Validation: Testing fold " + (i+1));
				for(Record r : testingData) {

			    	int zeroRegScore = 5;
			    	int nbPredictScore = nbLearner.predict(r);		    	
			    	int nnPredictScore = nnLearner.predict(r);
//			    	System.out.println("... predicting review: " + (ctr) + ": " + nbPredictScore + "," + nnPredictScore);
			    	
					List<Integer> cScores = Utils.fromIntegerListToArrayList(r.review.getClassificationScores());
					cScores.add(zeroRegScore);
					cScores.add(nbPredictScore);
					cScores.add(nnPredictScore);
					r.review.setClassificationScores(Utils.fromCollectionToIntegerList(aJCas, cScores));

				}
				
			}
	
		} else if(mode.equals("test")) {
			System.out.println("... Learner Annotator: TESTING MODE ... ");	
			nbLearner.initTest(modelPath);
			nnLearner.initTest(modelPath);

			for (Record r : data) {
		    	int zeroRegScore = 5;
		    	int nbPredictScore = nbLearner.predict(r);			    	
		    	int nnPredictScore = nnLearner.predict(r);
//		    	System.out.println("... predicting review: " + (ctr-1) + ": " + nbPredictScore + "," + nbPredictScore);
		    			    	
				List<Integer> cScores = Utils.fromIntegerListToArrayList(r.review.getClassificationScores());
				cScores.add(zeroRegScore);
				cScores.add(nnPredictScore);
				cScores.add(nbPredictScore);
				r.review.setClassificationScores(Utils.fromCollectionToIntegerList(aJCas, cScores));	
			}
			System.out.println("... neural network classification processing... ");	
			
		}
		
		svmLearner.initialize(mode, modelPath, data);
		regLearner.initialize(mode, modelPath, data);
	}

	//##############################
	//# helper functions           #
	//##############################
	private List<Review> subsetReviewsByLimit(Collection<Review> allReviews, int sizeLimit) {
		List<Review> reviews = new ArrayList<Review>();
		int ctr = 0;
		if(sizeLimit != 0) {
			for(Review r: allReviews) {
				if(ctr++ > sizeLimit) break;
				else reviews.add(r);
			}
		} else {
			reviews = (List<Review>) allReviews;
		}
		return reviews;
	}
	
	private void writeRecords(List<Record> data) {
		System.out.println("... Writing Records to " + recordsFile);
		//write txt document with scores for analysis
		File outputFile = null;
	    PrintWriter writer = null;
	    try {	    	
	        outputFile = new File(recordsFile);
	        outputFile.getParentFile().mkdirs();
	        writer = new PrintWriter(outputFile);
	    } catch (FileNotFoundException e) {
	        System.out.printf("Output file could not be written: %s\n", recordsFile);
	        return;
	    }
	    //each record in data
	    for(Record r : data) {
	    	String line = "";
	    	for (String s : r.negatedWords.keySet()) {
	    		line = line + s + " " + r.negatedWords.get(s) + " ";
	    	}
	    	writer.println(line);
	    }
	    
	    writer.close();
	}
	
	private ArrayList<Record> readRecords(Collection<Review> reviews) {
		ArrayList<Record> data = new ArrayList<Record>();
		int ctr = 0;

		// Open the file
	    FileInputStream fstream;
		try {
			System.out.println("... Reading Records from " + recordsFile);
			fstream = new FileInputStream(recordsFile);
		    BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		    
			for (Review review : reviews) {
				if(ctr++ > sizeLimit && sizeLimit != 0) break;
				String line = br.readLine();
				if(line == null) break;
				
				Record r = Record.reviewToRecord(review);
				Map<String, Integer> negatedWords = new HashMap<String, Integer>();
				

				String[] values = line.split("\\s");
				for(int i=0; i<values.length/2; i++) {
					negatedWords.put(values[i*2], Integer.parseInt(values[i*2+1]));
				}
				
				r.addNeg(negatedWords);
				r.addNegSubstract(negatedWords);
				data.add(r);
			}
			
			System.out.println("... Finished reading " + ctr + " Records");
		    //Close the input stream
		    br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}


	


	
}
