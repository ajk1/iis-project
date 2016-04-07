import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Paths;
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

import type.Review;
import type.Sentence;
import util.Utils;
import util.MapUtil;

import learners.*;

public class LearnerAnnotator extends JCasAnnotator_ImplBase {
	final String PARAM_SIZELIMIT = "SizeLimit";
	final String PARAM_MODE = "Mode";
	final String PARAM_MODEL_PATH = "ModelDir";
	private int sizeLimit;
	private String mode;
	private String modelPath;
	private int topWordLimit;
	
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

		Learner nbLearner = new NaiveBayesLeaner();
		Learner svmLearner = new SVMLearner();
		Learner regLearner = new LinearRegressionLearner();
		
		List<Record> data = new ArrayList<Record>();
		
		
		// get reviews from the CAS
		Collection<Review> reviews = JCasUtil.select(aJCas, Review.class);      
		System.out.println("... review size: " + reviews.size());

		// write top words to file
		HashMap<String, Integer> wordFreq = new HashMap<String, Integer>();
		for (Review review : reviews) {
			for(Sentence sentence : Utils.fromFSListToLinkedList(review.getSentences(), Sentence.class)) {
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
		Map<String, Integer> sortedWordFreq = MapUtil.sortByValue(wordFreq);		
		writeTopWords(sortedWordFreq, topWordLimit);
		
		int ctr = 0;
		for (Review review : reviews) {
	    	if(ctr++ > sizeLimit && sizeLimit != 0) break;
			
			Record r = new Record();
			List<String> allTokens = new ArrayList<String>();
			
			//for each sentence in review
			for(Sentence sentence : Utils.fromFSListToLinkedList(review.getSentences(), Sentence.class)) {
				List<String> tokenList = Utils.fromStringListToArrayList(sentence.getUnigramList());
				for(String token: tokenList) {
					token = token.toLowerCase();
					allTokens.add(token);					
				}
			}
			
			r.setGoldLabel(review.getGoldLabel());
			r.setAttr(allTokens, sortedWordFreq.keySet());
			data.add(r);
		}
		
		System.out.println("... data size: " + data.size());
		
		//naive bayes init
		Set<String> vocabulary = sortedWordFreq.keySet();
		nbLearner.initTrain(modelPath, data, vocabulary);
		nbLearner.train();
		nbLearner.setModelPath(modelPath);
		nbLearner.writeModel();
		
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
	
}
