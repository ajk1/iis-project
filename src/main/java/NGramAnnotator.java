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
import type.InputDocument;
import type.Ngram;
import type.Review;
import type.Sentence;
import util.StopWordUtils;
import util.NGramUtils;
import util.Utils;

public class NGramAnnotator extends JCasAnnotator_ImplBase {
	final String PARAM_SIZELIMIT = "SizeLimit";
	private int sizeLimit;

	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException {
		super.initialize(aContext);		
		sizeLimit = Integer.valueOf((String) aContext.getConfigParameterValue(PARAM_SIZELIMIT));
	}

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		System.out.println(">> NGram Annotator Processing");
		System.out.println("... sizeLimit: " + sizeLimit);

		// get reviews from the CAS
		Collection<Review> reviews = JCasUtil.select(aJCas, Review.class);      
		System.out.println("... review size: " + reviews.size());

		TokenizerFactory factory = PTBTokenizerFactory.newTokenizerFactory();

		int ctr = 0;
		for (Review review : reviews) {
	    	if(ctr++ > sizeLimit && sizeLimit != 0) break;
    	    int uniGramCount = 0;  	  
    	    int biGramCount = 0;  	  
    	    int triGramCount = 0;  	  
			
			//for each sentence in review
			for(Sentence sentence : Utils.fromFSListToLinkedList(review.getSentences(), Sentence.class)) {
				String cleanSentenceText = sentence.getRawText();

				//annotate tokens
				Tokenizer tokenizer = factory.getTokenizer(new StringReader(cleanSentenceText));
              
				List<String> tokensInSentence = tokenizer.tokenize();
				
				//unigram
				List<String> uniGramsTextInSentence = (List<String>) StringUtils.getNgrams(tokensInSentence, 1, 1);
				sentence.setUnigramList(Utils.fromCollectionToStringList(aJCas, uniGramsTextInSentence));

				//bigram
				List<String> biGramsTextInSentence = (List<String>) StringUtils.getNgrams(tokensInSentence, 2, 2);				
				sentence.setBigramList(Utils.fromCollectionToStringList(aJCas, biGramsTextInSentence));

				//trigram
				List<String> triGramsTextInSentence = (List<String>) StringUtils.getNgrams(tokensInSentence, 3, 3);
				sentence.setTrigramList(Utils.fromCollectionToStringList(aJCas, triGramsTextInSentence));
				
				uniGramCount += uniGramsTextInSentence.size();
				biGramCount += biGramsTextInSentence.size();
				triGramCount += triGramsTextInSentence.size();
			}
			System.out.println("... review: " + ctr + " added "+ uniGramCount +" unigram, " + biGramCount +" bigrams, " + triGramCount +" trigrams" );
    	  
		}
	}

}
