import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.process.PTBTokenizer.PTBTokenizerFactory;
import edu.stanford.nlp.util.StringUtils;
import type.Review;
import type.Sentence;
import util.StopWordUtils;
import util.NGramUtils;
import util.Utils;

import learners.*;

public class LearnerAnnotator extends JCasAnnotator_ImplBase {
	final String PARAM_SIZELIMIT = "SizeLimit";
	final String PARAM_MODE = "Mode";
	final String PARAM_MODEL_PATH = "ModelDir";
	private int sizeLimit;
	private String mode;
	private String modelPath;
	
	Set<String> topWords = new HashSet<String>();

	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);		
		
		//set params
		sizeLimit = Integer.valueOf((String) aContext.getConfigParameterValue(PARAM_SIZELIMIT));
		mode = (String) aContext.getConfigParameterValue(PARAM_MODE);
		modelPath = (String) aContext.getConfigParameterValue(PARAM_MODEL_PATH);
		
		
		// get top words 
	    // Read library
	    // Open the file
	    FileInputStream fstream;
		
	    System.out.println("... Reading Vader Sentiment Libraries");
	    
	    //Read File Line By Line
	    try {
			fstream = new FileInputStream("src/main/resources/libraries/sentiment_libraries/vader.txt");
		    BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		    String strLine;
			while ((strLine = br.readLine()) != null)   {
				String[] parts = strLine.split("\t");
				topWords.add(parts[0]);
			    //Close the input stream
			}
		    br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

		int ctr = 0;
		for (Review review : reviews) {
	    	if(ctr++ > sizeLimit && sizeLimit != 0) break;
			
			Record r = new Record();
			List<String> allTokens = new ArrayList<String>();
			
			//for each sentence in review
			for(Sentence sentence : Utils.fromFSListToLinkedList(review.getSentences(), Sentence.class)) {
				List<String> tokenList = Utils.fromStringListToArrayList(sentence.getUnigramList());
				allTokens.addAll(tokenList);
			}
			
			r.setGoldLabel(review.getGoldLabel());
			r.setAttr(allTokens, topWords);
			data.add(r);
		}
		
		System.out.println("... data size: " + data.size());
		
		//naive bayes init
		nbLearner.initTrain(modelPath, data);
		nbLearner.train();
		nbLearner.setModelPath(modelPath);
		nbLearner.writeModel();
		
		svmLearner.initialize(mode, modelPath, data);
		regLearner.initialize(mode, modelPath, data);
	}

}
