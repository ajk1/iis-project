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
			if(ctr++ > sizeLimit) break;
    	  
			List<Ngram> uniGrams = new ArrayList<Ngram>();
			List<Ngram> biGrams = new ArrayList<Ngram>();
    	  
			//for each sentence in review
			for(Sentence sentence : Utils.fromFSListToLinkedList(review.getSentences(), Sentence.class)) {
				String cleanSentenceText = sentence.getRawText();

				//annotate tokens
				Tokenizer tokenizer = factory.getTokenizer(new StringReader(cleanSentenceText));
              
				List<String> tokensInSentence = tokenizer.tokenize();

				//unigram
				List<String> uniGramsTextInSentence = (List<String>) StringUtils.getNgrams(tokensInSentence, 1, 1);
				List<Ngram> uniGramsInSentence = new ArrayList<Ngram>();
				for(String uniGramText: uniGramsTextInSentence) {
//            	  	System.out.println("... Unigram: "+uniGramText);
					Ngram uniGram = new Ngram(aJCas);
					uniGram.setN(1);
					uniGram.setRawText(uniGramText);
					uniGram.addToIndexes();
            	  
					uniGramsInSentence.add(uniGram);	//add to sentence scope list
					uniGrams.add(uniGram);			//add to review scope list
				}              
				sentence.setUnigrams(Utils.fromCollectionToFSList(aJCas, uniGramsInSentence));

				//bigram
				List<String> biGramsTextInSentence = (List<String>) StringUtils.getNgrams(tokensInSentence, 2, 2);
				List<Ngram> biGramsInSentence = new ArrayList<Ngram>();
				for(String biGramText: biGramsTextInSentence) {
//            	  	System.out.println("... Bigram: "+biGramText);
            	  	Ngram biGram = new Ngram(aJCas);
            	  	biGram.setN(2);
            	  	biGram.setRawText(biGramText);
            	  	biGram.addToIndexes();
            	  
            	  	biGramsInSentence.add(biGram);	//add to sentence scope list
            	  	biGrams.add(biGram);				//add to review scope list
				}
              
				sentence.setBigrams(Utils.fromCollectionToFSList(aJCas, biGramsInSentence));

			}
			review.setUnigrams(Utils.fromCollectionToFSList(aJCas, uniGrams));	//add to review scope unigram list
			review.setBigrams(Utils.fromCollectionToFSList(aJCas, biGrams));		//add to review scope bigram list

			System.out.println("... review: " + ctr + " added "+ uniGrams.size() +" unigram, " + biGrams.size() +" bigram");
    	  
		}
	}

}
