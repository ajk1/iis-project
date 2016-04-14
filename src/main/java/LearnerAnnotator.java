import java.io.File;
import java.io.FileNotFoundException;
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
	private int sizeLimit;
	private String mode;
	private String modelPath;
	private int topWordLimit = 1000;
	
	Set<String> topWords = new HashSet<String>();

	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);		
		
		//set params
		sizeLimit = Integer.valueOf((String) aContext.getConfigParameterValue(PARAM_SIZELIMIT));
		mode = (String) aContext.getConfigParameterValue(PARAM_MODE);
		modelPath = (String) aContext.getConfigParameterValue(PARAM_MODEL_PATH);
	}

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		System.out.println(">> Learner Annotator Processing");
		System.out.println("... sizeLimit: " + sizeLimit);
		System.out.println("... mode: " + mode);
		System.out.println("... modelPath: " + modelPath);

		ClassificationLearner nbLearner = new NaiveBayesLearner();
		ClassificationLearner nnLearner = new NeuralNetLearner();
		Learner svmLearner = new SVMLearner();
		Learner regLearner = new LinearRegressionLearner();
		
		List<Record> data = new ArrayList<Record>();
		
		
		// get reviews from the CAS
		Collection<Review> reviews = JCasUtil.select(aJCas, Review.class);      
		System.out.println("... review size: " + reviews.size());
		System.out.println("... data size: " + data.size());
		int ctr = 0;
		
		Map<String, Integer> sortedWordFreq = new LinkedHashMap<String, Integer>();	
		Set<String> vocabulary = new HashSet<String>();
		
		if(mode.equals("train")) {
			
			// write top words to file
			HashMap<String, Integer> wordFreq = new HashMap<String, Integer>();
			for (Review review : reviews) {
		    	if(ctr++ > sizeLimit && sizeLimit != 0) break;
				System.out.println("... review id: " + review.getProductId());

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
			sortedWordFreq = MapUtil.sortByValue(wordFreq);	
			writeTopWords(sortedWordFreq, topWordLimit);
			
			int i=0;
			for (String v : sortedWordFreq.keySet()) {
				if (i<1000) vocabulary.add(v);
				else break;
				i++;
			}
			
			//writeTopWords(sortedWordFreq, topWordLimit);
			
			ctr = 0;
			//init unified data format
			for (Review review : reviews) {
		    	if(ctr++ > sizeLimit && sizeLimit != 0) break;
				
				Record r = new Record();
				List<String> allTokens = new ArrayList<String>();
				Map<String, Integer> negatedWords = new HashMap<String, Integer>();
				
				//for each sentence in review
				for(Sentence sentence : Utils.fromFSListToLinkedList(review.getSentences(), Sentence.class)) {
					System.out.println("... sentence text: " + sentence.getRawText());

					//negation detection
					Map<String, Integer> negatedWordsInSentence = CoreNLPUtils.getNegatedWordsWithParseTree(sentence.getRawText());
//					System.out.println(negatedWordsInSentence);
					negatedWordsInSentence.forEach((k, v) -> negatedWords.merge(k, v, (v1, v2) -> v1 + v2));

					//token detection
					List<String> tokenList = Utils.fromStringListToArrayList(sentence.getUnigramList());

					for(String token: tokenList) {
						token = token.toLowerCase();
						allTokens.add(token);					
					}
				}
				System.out.println("review: " + negatedWords);
				
				r.setGoldLabel(review.getGoldLabel());
				r.setAttr(allTokens, vocabulary);
				r.addNeg(negatedWords);
				data.add(r);
			}
			
		}
		
		//0-order classification
		System.out.println("... 0-order classification processing... ");	
		for (Review review : reviews) {
	    	if(ctr++ > sizeLimit && sizeLimit != 0) break;
			List<Integer> cScores = Utils.fromIntegerListToArrayList(review.getClassificationScores());
			cScores.add(5);
			review.setClassificationScores(Utils.fromCollectionToIntegerList(aJCas, cScores));
		}
		
		//naive bayes init
		nbLearner.setModelPath(modelPath);
		
		ctr = 0;
		
		if(mode.equals("train")) {
			nbLearner.initTrain(modelPath, data, vocabulary);
			nbLearner.train();
			nbLearner.writeModel();			
		} else if(mode.equals("test")) {
			System.out.println("... naive bayes classification processing... ");	
			nbLearner.initTest(modelPath);
			for (Review review : reviews) {
		    	if(ctr++ > sizeLimit && sizeLimit != 0) break;
		    	int predictScore = nbLearner.predict(review);
				
//		    	System.out.println("... predicting review: " + (ctr-1) + ": " + predictScore);
				
				List<Integer> cScores = Utils.fromIntegerListToArrayList(review.getClassificationScores());
				cScores.add(predictScore);
				review.setClassificationScores(Utils.fromCollectionToIntegerList(aJCas, cScores));
//				System.out.println("size: " + Utils.fromIntegerListToArrayList(review.getClassificationScores()).size());
			}
		}
		
		//neural network init
		nnLearner.setModelPath(modelPath);
		
		ctr = 0;
		
		if(mode.equals("train")) {
			nnLearner.initTrain(modelPath, data, vocabulary);
			nnLearner.train();
			nnLearner.writeModel();			
		} else if(mode.equals("test")) {
			System.out.println("... neural network classification processing... ");	
			nnLearner.initTest(modelPath);
			for (Review review : reviews) {
		    	if(ctr++ > sizeLimit && sizeLimit != 0) break;
		    	int predictScore = nnLearner.predict(review);
				
//				System.out.println("... predicting review: " + (ctr-1) + ": " + predictScore);
				
				List<Integer> cScores = Utils.fromIntegerListToArrayList(review.getClassificationScores());
				cScores.add(predictScore);
				review.setClassificationScores(Utils.fromCollectionToIntegerList(aJCas, cScores));
//				System.out.println("size: " + Utils.fromIntegerListToArrayList(review.getClassificationScores()).size());
			}
		}
		
		svmLearner.initialize(mode, modelPath, data);
		regLearner.initialize(mode, modelPath, data);
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
