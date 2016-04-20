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
	private String recordsFile = "src/main/resources/records/records.txt";
	
	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);		
		
		//set params
		sizeLimit = Integer.valueOf((String) aContext.getConfigParameterValue(PARAM_SIZELIMIT));
		mode = (String) aContext.getConfigParameterValue(PARAM_MODE);
		modelPath = (String) aContext.getConfigParameterValue(PARAM_MODEL_PATH);
		System.out.println(aContext.getConfigParameterValue(PARAM_READ_RECORDS));
//		for (String name : aContext.getConfigParameterNames())
//			System.out.println(name);
//		System.out.println(aContext.getConfigParameterValue(PARAM_READ_RECORDS));
		readRecords = false;//aContext.getConfigParameterValue(PARAM_READ_RECORDS).toString().toLowerCase().equals("true");
	}

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		System.out.println(">> Learner Annotator Processing");
		System.out.println("... sizeLimit: " + sizeLimit);
		System.out.println("... mode: " + mode);
		System.out.println("... modelPath: " + modelPath);

		// 1. annotate Records from Reviews (Class <Review> is POJO)
		
		// get reviews from the CAS
		Collection<Review> reviews = JCasUtil.select(aJCas, Review.class);
		System.out.println("... review size: " + reviews.size());

		// 1.1 init vocab and write vocab (CV and Train) / Read vocal (Test)
		// PARAMS: vocab size limit

		Map<String, Integer> sortedWordFreq = new LinkedHashMap<String, Integer>();	
		Set<String> vocabulary = new HashSet<String>();

		// DataSet, in the format of Record
		List<Record> data = new ArrayList<Record>();

		int ctr = 0;
		
		if(mode.equals("train") || mode.equals("cv")) {	
			
			// write vocab to file
			sortedWordFreq = MapUtil.sortByValue(getVocabInTrainSet(reviews));		
			writeTopWords(sortedWordFreq, topWordLimit);
			vocabulary = getTopVocab(sortedWordFreq, topWordLimit);	
			
		} else if(mode.equals("test")) {
			
			// read vocab from file			
			sortedWordFreq = getTopWordsFromFile(topWordLimit);
			vocabulary = getTopVocab(sortedWordFreq, topWordLimit);					
		}
		Record.setVocab(vocabulary);

		
		// 1.2 create Record List for unified learner input data format
		if (!readRecords) {
			for (Review review : reviews) {
		    	if(ctr++ > sizeLimit && sizeLimit != 0) break;
				System.out.println("... Learner Annotator: annotating " + ctr + " review to record ... ");	
	
				Record r = reviewToRecord(review, sortedWordFreq);
				data.add(r);
			}
			writeRecords(data);
			
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
				System.out.println(trainingData.size());
				System.out.println(testingData.size());
				
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
			    	System.out.println("... predicting review: " + (ctr) + ": " + nbPredictScore + "," + nnPredictScore);
			    	
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
		    	
				
				
				//##############################
				//# 0-order classification     #
				//##############################
				

				//##############################
				//# NB classification          #
				//##############################

				
				//##############################
				//# NN classification          #
				//##############################


		    	
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

	
	private Record reviewToRecord(Review review, Map<String, Integer> sortedWordFreq) {
		Record r = new Record();
		List<String> allTokens = new ArrayList<String>();
		Map<String, Integer> negatedWords = new HashMap<String, Integer>();
		
		//for each sentence in review
		for(Sentence sentence : Utils.fromFSListToLinkedList(review.getSentences(), Sentence.class)) {

			//negation detection
			Map<String, Integer> negatedWordsInSentence = CoreNLPUtils.getNegatedWordsWithParseTree(sentence.getRawText());
//			System.out.println(negatedWordsInSentence);
			negatedWordsInSentence.forEach((k, v) -> negatedWords.merge(k, v, (v1, v2) -> v1 + v2));

			//token detection
			List<String> tokenList = Utils.fromStringListToArrayList(sentence.getUnigramList());

			for(String token: tokenList) {
				token = token.toLowerCase();
				allTokens.add(token);					
			}
		}
//		System.out.println("review: " + negatedWords);
		r.setReview(review);
		r.setGoldLabel(review.getGoldLabel());
		r.setAttr(allTokens);
		r.addNeg(negatedWords);
		r.addNegSubstract(negatedWords);
		return r;
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
		Set<String> vocabulary = new HashSet<String>();
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
						token = token.toLowerCase();
						allTokens.add(token);					
					}
				}
				String[] values = line.split("\\s");
				for(int i=0; i<values.length/2; i++) {
					negatedWords.put(values[i*2], Integer.parseInt(values[i*2+1]));
					System.out.println(values[i*2]);
					System.out.println(review.getRawText());
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

	private HashMap<String, Integer> getVocabInTrainSet(Collection<Review> reviews) {
		HashMap<String, Integer> wordFreq = new HashMap<String, Integer>();
		int ctr = 0;
		for (Review review : reviews) {
	    	if(ctr++ > sizeLimit && sizeLimit != 0) break;

			for(Sentence sentence : Utils.fromFSListToLinkedList(review.getSentences(), Sentence.class)) {
				
				//generating top words vocabs
				//TODO: stopwords removal
				for(String token : Utils.fromStringListToArrayList(sentence.getUnigramList())) {
					token = token.toLowerCase();

					if(!wordFreq.containsKey(token)) {
						wordFreq.put(token, 1);
					} else {
						wordFreq.put(token, wordFreq.get(token) + 1);
					}
				}
			}
		}
		return wordFreq;
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
	
	public List<String> getCompoundNouns(CoreMap sentence) {
	    List<String> nounPhraseCandidates = new LinkedList<String>();
	    List<String> nounPhrases = new LinkedList<String>();
	    List<CoreLabel> tokens = sentence.get(CoreAnnotations.TokensAnnotation.class);

		for (int j = 0; j < tokens.size(); ++j) {
	        CoreLabel token = tokens.get(j);
	        String lemma = token.get(CoreAnnotations.LemmaAnnotation.class);
	        String word = token.get(CoreAnnotations.TextAnnotation.class);
	        String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
	        System.out.println("word: "+word+", lemma: "+lemma+", pos: "+pos);
	        
	        //extracting noun phrase
	        if(isNoun(pos)) {
	      	  nounPhraseCandidates.add(word);
	        } else {
	      	  if(nounPhraseCandidates.size() > 1) {
	      		  nounPhrases.add(String.join(" ",nounPhraseCandidates));
	      	  }
	      	  nounPhraseCandidates.clear();
	        }
	    }
	    return nounPhrases;
	}
	
	public boolean isNoun(String posTag) {
		if(posTag.equals("NN")) return true;
		if(posTag.equals("NNS")) return true;
		if(posTag.equals("NNP")) return true;
		if(posTag.equals("NNPS")) return true;
		return false;
	}
	

	
}
