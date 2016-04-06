import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);		
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
		
		nbLearner.initialize(mode, modelPath);
		svmLearner.initialize(mode, modelPath);
		regLearner.initialize(mode, modelPath);
		
		System.out.println("... mode: " + mode);
	}

}
