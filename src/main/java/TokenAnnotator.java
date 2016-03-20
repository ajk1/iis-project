import java.io.Reader;
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

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.util.StringUtils;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer.PTBTokenizerFactory;
import type.InputDocument;
import type.Ngram;
import type.Review;
import type.Token;
import util.Utils;
import util.StopWordUtils;

public class TokenAnnotator extends JCasAnnotator_ImplBase {
	final String PARAM_SIZELIMIT = "SizeLimit";
	private int sizeLimit;
	
	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);		
		sizeLimit = Integer.valueOf((String) aContext.getConfigParameterValue(PARAM_SIZELIMIT));
	}

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		System.out.println(">> Token Annotator Processing");
		System.out.println("... sizeLimit : " + sizeLimit);
    
	    //component implementation	          
	    StopWordUtils swu = new StopWordUtils("limit");
	
	    // get reviews from the CAS
	    Collection<Review> reviews = JCasUtil.select(aJCas, Review.class);      
	    System.out.println("... review size: " + reviews.size());
	
	    int ctr = 0;
	    for (Review review : reviews) {
	    	if(ctr++ > sizeLimit) break;
	    	  
	        String body = review.getRawText();
	          
	        List<type.Sentence> sentences = new ArrayList<type.Sentence>();
	          
	        //annotate sentences
	        Reader reader = new StringReader(body);
	        DocumentPreprocessor dp = new DocumentPreprocessor(reader);
	        for(List<HasWord> hw: dp) {
	        	String rawSentenceText = Sentence.listToString(hw);
	        	String cleanSentenceText = swu.removeStopword(rawSentenceText);
	        	  
	            type.Sentence sentence = new type.Sentence(aJCas);
	            sentence.setRawText(cleanSentenceText);
	            sentence.addToIndexes();              
	            sentences.add(sentence);
	        }
	        review.setSentences(Utils.fromCollectionToFSList(aJCas, sentences));	//add sentences to review scope list
	    	System.out.println("... review: " + ctr + " added "+ sentences.size() +" sentences");
	    }
	}
    
 
}
