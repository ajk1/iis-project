import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;

import edu.stanford.nlp.util.CoreMap;
import type.Review;
import type.Sentence;
import util.Utils;
import util.MapUtil;
import util.StopWordUtils;
import util.CoreNLPUtils;

import learners.*;

public class LearnerAnnotator extends JCasAnnotator_ImplBase {
	final String PARAM_SIZELIMIT = "SizeLimit";
	final String PARAM_MODE = "Mode";
	final String PARAM_MODEL_PATH = "ModelDir";
	final String PARAM_READ_RECORDS = "ReadRecords";
	private int sizeLimit;
	private String mode;
	private String modelPath;
	private boolean readRecords;
	private int topWordLimit = 1000;
	private int numOfFolds = 10;
	private boolean removeStopWords = true;
	private boolean useInfoGain = false;
	private String recordsFile = "src/main/resources/records/records.txt";
	
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

		// 1. annotate Records from Reviews (Class <Review> is POJO)
		
		// get reviews from the CAS
		Collection<Review> reviews = JCasUtil.select(aJCas, Review.class);
		System.out.println("... review size: " + reviews.size());

		// 1.1 init vocab and write vocab (CV and Train) / Read vocal (Test)
		// PARAMS: vocab size limit

		Map<String, Integer> sortedWordFreq = new LinkedHashMap<String, Integer>();	
		Map<String, Double> weightedWordFreq = new LinkedHashMap<String, Double>();
		Set<String> vocabulary = new HashSet<String>();

		// DataSet, in the format of Record
		List<Record> data;

		int ctr = 0;
		
		if(mode.equals("train") || mode.equals("cv")) {	
			
			// write vocab to file
			sortedWordFreq = MapUtil.sortByValue(getVocabInTrainSet(reviews));
			vocabulary = getTopVocab(sortedWordFreq, topWordLimit);	
			Record.setVocab(vocabulary);
			
			if (useInfoGain) {
				if (!readRecords) {
					data = reviewsToRecords(reviews);
		//			writeRecords(data);
				}
				else { 
					data = readRecords(reviews);
				}
				//get infoGain
				Map<String, Double> infoGain = MapUtil.sortByValue(getInfoGain(data)); 
				for (String word : infoGain.keySet()) {
					if (sortedWordFreq.containsKey(word)){
//						System.out.println(word + " " + infoGain.get(word));
						weightedWordFreq.put(word, Math.pow(1, infoGain.get(word))*sortedWordFreq.get(word));
					}
				}
				vocabulary = new HashSet<String>();
				int i=0;
				for (String v : weightedWordFreq.keySet()) {
					if (i<topWordLimit) vocabulary.add(v);
					else break;
					i++;
				}	
				Record.setVocab(vocabulary);
			}

			writeTopWords(sortedWordFreq, topWordLimit);
		} else if(mode.equals("test")) {
			
			// read vocab from file			
			sortedWordFreq = getTopWordsFromFile(topWordLimit);
			vocabulary = getTopVocab(sortedWordFreq, topWordLimit);					
		}
		
		// 1.2 create Record List for unified learner input data format
		if (!readRecords) {
			data = reviewsToRecords(reviews);
			
//			writeRecords(data);
		}
		else { 
			data = readRecords(reviews);
		}
//		
//		for (Record r : data) {
//			for(String s : r.tokenFreq.keySet()) {
//				if(r.tokenFreq.get(s)>0) System.out.print(s+" "+r.tokenFreq.get(s)+", ");
//			}
//			System.out.println();
//		}
		
		// 2. Learners start working here
		ClassificationLearner nbLearner = new NaiveBayesLearner();
		ClassificationLearner nnLearner = new NeuralNetLearner();
		Learner svmLearner = new SVMLearner();
		Learner regLearner = new LinearRegressionLearner();
		
		//naive bayes init
		nbLearner.setModelPath(modelPath);
		
		//neural network init
		nnLearner.setModelPath(modelPath);
				
		ctr = 0;
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
				// TODO: k-fold modify here
				
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

				
				// k-fold test
				System.out.println("... Cross-Validation: Testing fold " + (i+1));
				for(Record r : testingData) {
					ctr++;
			    	int zeroRegScore = 5;
			    	int nbPredictScore = nbLearner.predict(r);			    	
			    	int nnPredictScore = nnLearner.predict(r);
//			    	System.out.println("... predicting review: " + (ctr) + ": " + nbPredictScore + "," + nnPredictScore);
			    	
					List<Integer> cScores = Utils.fromIntegerListToArrayList(r.review.getClassificationScores());
					cScores.add(zeroRegScore);
					cScores.add(nnPredictScore);
					cScores.add(nbPredictScore);
					r.review.setClassificationScores(Utils.fromCollectionToIntegerList(aJCas, cScores));

				}
				
			}
	
		} else if(mode.equals("test")) {
			System.out.println("... Learner Annotator: TESTING MODE ... ");	
			nbLearner.initTest(modelPath);
			nnLearner.initTest(modelPath);

			for (Record r : data) {
		    	if(ctr++ > sizeLimit && sizeLimit != 0) break;
		    	int zeroRegScore = 5;
		    	int nbPredictScore = nbLearner.predict(r);			    	
		    	int nnPredictScore = nnLearner.predict(r);
		    	System.out.println("... predicting review: " + (ctr-1) + ": " + nbPredictScore + "," + nbPredictScore);
		    			    	
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

	
	private ArrayList<Record> reviewsToRecords(Collection<Review> reviews) {
		ArrayList<Record> data = new ArrayList<Record>();
		int ctr = 0;
		for (Review review : reviews) {
	    	if(ctr++ > sizeLimit && sizeLimit != 0) break;
			System.out.println("... Learner Annotator: annotating " + ctr + " review to record ... ");	
			
			Record r = new Record();
			List<String> allTokens = new ArrayList<String>();
			Map<String, Integer> negatedWords = new HashMap<String, Integer>();
			
			//for each sentence in review
			for(Sentence sentence : Utils.fromFSListToLinkedList(review.getSentences(), Sentence.class)) {
	
				//negation detection - all negated words are cleaned (punctuation removed)
				Map<String, Integer> negatedWordsInSentence = CoreNLPUtils.getNegatedWordsWithParseTree(sentence.getRawText());

				negatedWordsInSentence.forEach((k, v) -> negatedWords.merge(k, v, (v1, v2) -> v1 + v2));
				
				//token detection
				List<String> tokenList = Utils.fromStringListToArrayList(sentence.getUnigramList());
	
				for(String token: tokenList) {
					//remove punctuation
					token = token.replaceAll("[^a-zA-Z ]", "");
					if (!token.equals(token.toUpperCase())) token = token.toLowerCase();
					allTokens.add(token);					
				}
			}
			r.setReview(review);
			r.setGoldLabel(review.getGoldLabel());
			r.setAttr(allTokens);
			r.addNeg(negatedWords);
			r.addNegSubstract(negatedWords);
			
			data.add(r);
		}
		return data;
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
				
				Record r = new Record();
				List<String> allTokens = new ArrayList<String>();
				Map<String, Integer> negatedWords = new HashMap<String, Integer>();
				
				//for each sentence in review
				for(Sentence sentence : Utils.fromFSListToLinkedList(review.getSentences(), Sentence.class)) {
					//token detection
					List<String> tokenList = Utils.fromStringListToArrayList(sentence.getUnigramList());

					for(String token: tokenList) {
						//remove punctuation
						token = token.replaceAll("[^a-zA-Z ]", "");
						if (!token.equals(token.toUpperCase())) token = token.toLowerCase();
						allTokens.add(token);		
					}
				}
				String[] values = line.split("\\s");
				for(int i=0; i<values.length/2; i++) {
					negatedWords.put(values[i*2], Integer.parseInt(values[i*2+1]));
				}
				
				r.setReview(review);
				r.setGoldLabel(review.getGoldLabel());
				r.setAttr(allTokens);
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

	private Set<String> getTopVocab(Map<String, Integer> sortedWordFreq, int topWordLimit) {
		Set<String> vocabulary = new HashSet<String>();
		int i=0;
		for (String v : sortedWordFreq.keySet()) {
			if (i<topWordLimit) vocabulary.add(v);
			else break;
			i++;
		}	
		return vocabulary;
	}
	
	private HashMap<String, Integer> getVocabInTrainSet(Collection<Review> reviews) {
		HashMap<String, Integer> wordFreq = new HashMap<String, Integer>();
		StopWordUtils swu = new StopWordUtils("full");
		int ctr = 0;
		for (Review review : reviews) {
	    	if(ctr++ > sizeLimit && sizeLimit != 0) break;

			for(Sentence sentence : Utils.fromFSListToLinkedList(review.getSentences(), Sentence.class)) {
				
				//generating top words vocabs
				for(String token : Utils.fromStringListToArrayList(sentence.getUnigramList())) {
					//remove punctuation
					token = token.replaceAll("[^a-zA-Z ]", "");
					if (!token.equals(token.toUpperCase())) token = token.toLowerCase();
					if(!(removeStopWords && swu.isStopword(token))) {
						if(!wordFreq.containsKey(token)) {
							wordFreq.put(token, 1);
						} else {
							wordFreq.put(token, wordFreq.get(token) + 1);
						}
					}
				}
			}
		}
		return wordFreq;
	}
	
	private HashMap<String, Double> getInfoGain(List<Record> data) {
		//word -> values (..., -1, 0, 1, ...) -> labels [1,2,3,4,5]
		HashMap<String, HashMap<Integer, int[]>> wordValueLabels = new HashMap<String, HashMap<Integer, int[]>>();;
		StopWordUtils swu = new StopWordUtils("full");
		HashMap<Integer, int[]> valueLabels;
		double[] pLabel = {0,0,0,0,0};
		int numRecords = 0;
		//int[] labels = new int[5];
		int goldIndex;
		
		int ctr = 0;
		for (Record r : data) {
	    	if(ctr++ > sizeLimit && sizeLimit != 0) break;
	    	goldIndex = r.goldLabel-1;
	    	pLabel[goldIndex]++;
	    	numRecords++;
	    	//get the value of each word in the review
	    	HashMap<String, Integer> wordValue = new HashMap<String, Integer>();
			for(Sentence sentence : Utils.fromFSListToLinkedList(r.review.getSentences(), Sentence.class)) {
				
				//tokens are already clean, remove stopwords
				for(String token : Utils.fromStringListToArrayList(sentence.getUnigramList())) {
					if (!(removeStopWords && swu.isStopword(token))) {
						if(wordValue.containsKey(token)){
							wordValue.put(token, wordValue.get(token)+1);
						}
						else {
							wordValue.put(token, 1);
						}
					}
				}
			}
			//subtract negated words
			for(String token : r.negatedWords.keySet()) {
				if (wordValue.containsKey(token))
					wordValue.put(token, wordValue.get(token) - r.negatedWords.get(token));
				else 
					wordValue.put(token, - r.negatedWords.get(token));
			}
			//add word-value pair to word-value-labels
			for(String token : wordValue.keySet()) {
				//if the word hasn't been encountered yet
				int value = wordValue.get(token);
				if(!wordValueLabels.containsKey(token)) {
					valueLabels = new HashMap<Integer, int[]>();
					int[] labels = new int[5];
					labels[goldIndex]++;
					valueLabels.put(value, labels);
					wordValueLabels.put(token, valueLabels);
				//if this value of the word hasn't been encountered yet
				} else if (!wordValueLabels.get(token).containsKey(value)) {
					valueLabels = wordValueLabels.get(token);
					int[] labels = new int[5];
					labels[goldIndex]++;
					valueLabels.put(value, labels);
					wordValueLabels.put(token, valueLabels);
				//word and value has been encountered, just increment label;
				} else {
					valueLabels = wordValueLabels.get(token);
					int[] labels = valueLabels.get(value);
					labels[goldIndex]++;
					valueLabels.put(value, labels);
					wordValueLabels.put(token, valueLabels);
				}
				
			}
		}
//		ctr = 0;
//		for (Record r : data) {
//	    	if(ctr++ > sizeLimit && sizeLimit != 0) break;
//	    	goldIndex = r.goldLabel-1;
//	    	pLabel[goldIndex]++;
//	    	numRecords++;
//	    	//get the value of each word in the review
//	    	HashMap<String, Integer> wordValue = new HashMap<String, Integer>();
//			for(Sentence sentence : Utils.fromFSListToLinkedList(r.review.getSentences(), Sentence.class)) {
//				
//				Set<String> sentenceTokens = new HashSet<String>(Utils.fromStringListToArrayList(sentence.getUnigramList()));
//				for(String token : wordValueLabels.keySet()) {
//					if (!sentenceTokens.contains(token)) {
//						if (!wordValueLabels.get(token).containsKey(0)) {
//							valueLabels = wordValueLabels.get(token);
//							int[] labels = new int[5];
//							labels[goldIndex]++;
//							valueLabels.put(0, labels);
//							wordValueLabels.put(token, valueLabels);
//						//word and value has been encountered, just increment label;
//						} else {
//							valueLabels = wordValueLabels.get(token);
//							int[] labels = valueLabels.get(0);
//							labels[goldIndex]++;
//							valueLabels.put(0, labels);
//							wordValueLabels.put(token, valueLabels);
//						}
//					}
//				}
//			}
//		}
		//get label probabilities
		for (int i=0; i<5; i++) {
			pLabel[i] = pLabel[i]/numRecords;
		}
		HashMap<String, Double> wordInfoGain = new HashMap<String, Double>();
		//IG(word) = H(labels) - sum_values(P(value=v)*H(labels|value=v))
		//H(labels) = sum(P(label)*log(1/P(label)))
		double hLabels = 0;
		for (int i=0; i<5; i++) {
			hLabels += pLabel[i]*Math.log(pLabel[i]==0 ? 1 : 1/pLabel[i]);
		}
		for (String token : wordValueLabels.keySet()) {
			valueLabels = wordValueLabels.get(token);
			HashMap<Integer, Integer> valueFreq = new HashMap<Integer, Integer>();
			int totalValues = 0;
			for (int value : valueLabels.keySet()) {
				int[] labels = valueLabels.get(value);
				int sum = 0;
				for (int i=0; i<5; i++) {
					sum += labels[i];
				}
				valueFreq.put(value, sum);
				totalValues += sum;
			}

			double hLabelsGivenValue = 0.0;
			for (int value : valueLabels.keySet()) {
				int[] labels = valueLabels.get(value);
				//calculate H(labels|value=v)
				double h = 0;
				for (int i=0; i<5; i++) {
					double p = (double) labels[i]/valueFreq.get(value);
					h += p*Math.log(p==0 ? 1.0 : 1.0/p);
				}
				hLabelsGivenValue += h*(double)valueFreq.get(value)/totalValues;
			}
//			if (hLabelsGivenValue < 1) {
//				System.out.println(token + " " + hLabelsGivenValue + " " + totalValues);
//				for (int value : valueLabels.keySet()) {
//					System.out.println(value + " " + valueFreq.get(value));
//				}
//			}
			wordInfoGain.put(token, hLabels - hLabelsGivenValue);
		}
		//System.out.println(hLabels);
		return wordInfoGain;
	}

	private void writeTopWords(Map<String, Integer> map, int limit) {
		//write csv document with scores for analysis
		File outputFile = null;
	    PrintWriter writer = null;
	    try {
	        outputFile = new File(Paths.get(modelPath, "topWords.txt").toString());
	        outputFile.getParentFile().mkdirs();
	        writer = new PrintWriter(outputFile);
	    } catch (FileNotFoundException e) {
	        System.out.printf("Output file could not be written: %s\n",
	                Paths.get(modelPath, "topWords.txt").toString());
	        return;
	    }
	    for(String key: map.keySet()) {
	        writer.println(key);
	    }
		writer.close();
	}
	
	private Map<String, Integer> getTopWordsFromFile(int limit) {
		Map<String, Integer> topWords = new LinkedHashMap<String, Integer>();
		
		// Construct BufferedReader from FileReader
		File file = new File(Paths.get(modelPath, "topWords.txt").toString());
		FileReader fileReader;
		try {
			fileReader = new FileReader(file);
			//Construct BufferedReader from InputStreamReader
			BufferedReader br = new BufferedReader(fileReader);
		 
			String line = null;
			while ((line = br.readLine()) != null) {
				topWords.put(line, 0);
			}
			br.close();
		 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
		return topWords;	
	}

	
}
