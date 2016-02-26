import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.Word;
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
import util.StopWordUtils;
import util.Utils;



public class TokenAnnotator extends JCasAnnotator_ImplBase {


  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    System.out.println(">> Token Annotator Processing");
    // get document text from the CAS
    String docText = aJCas.getDocumentText();

    FSIterator it = aJCas.getAnnotationIndex(InputDocument.type).iterator();
    if (it.hasNext()) {
      InputDocument doc = (InputDocument) it.next();
      
      StopWordUtils swu = new StopWordUtils("limit");
      TokenizerFactory factory = PTBTokenizerFactory.newTokenizerFactory();

      int ctr = 0;
      for (Review review : Utils.fromFSListToLinkedList(doc.getReviews(), Review.class)) {
    	  if(ctr > 30) break;
    	  
    	  System.out.println("... processing review: " + (ctr++));
          String body = review.getRawText();
          
          //component implementation
          
          List<type.Sentence> sentences = new ArrayList<type.Sentence>();
          List<Ngram> uniGrams = new ArrayList<Ngram>();
          List<Ngram> biGrams = new ArrayList<Ngram>();
          List<Token> tokens = new ArrayList<Token>();
          
          //annotate sentences
          Reader reader = new StringReader(body);
          DocumentPreprocessor dp = new DocumentPreprocessor(reader);
          for(List<HasWord> hw: dp) {
        	  String rawSentenceText = Sentence.listToString(hw);
        	  String cleanSentenceText = swu.removeStopword(rawSentenceText);
        	  System.out.println("... Clean sentence: "+cleanSentenceText);
        	  
              type.Sentence sentence = new type.Sentence(aJCas);
              sentence.setRawText(cleanSentenceText);
              sentence.addToIndexes();

              //annotate tokens
              Tokenizer tokenizer = factory.getTokenizer(new StringReader(cleanSentenceText));
              
              List<String> tokensInSentence = tokenizer.tokenize();

              //unigram
              List<String> uniGramsTextInSentence = (List<String>) StringUtils.getNgrams(tokensInSentence, 1, 1);
              List<Ngram> uniGramsInSentence = new ArrayList<Ngram>();
              for(String uniGramText: uniGramsTextInSentence) {
            	  System.out.println("... Unigram: "+uniGramText);
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
            	  System.out.println("... Bigram: "+biGramText);
            	  Ngram biGram = new Ngram(aJCas);
            	  biGram.setN(2);
            	  biGram.setRawText(biGramText);
            	  biGram.addToIndexes();
            	  
            	  biGramsInSentence.add(biGram);	//add to sentence scope list
            	  biGrams.add(biGram);				//add to review scope list
              }
              
              sentence.setBigrams(Utils.fromCollectionToFSList(aJCas, biGramsInSentence));
              sentences.add(sentence);
          }

          review.setUnigrams(Utils.fromCollectionToFSList(aJCas, uniGrams));	//add to review scope unigram list
          review.setBigrams(Utils.fromCollectionToFSList(aJCas, biGrams));		//add to review scope bigram list
          review.setSentences(Utils.fromCollectionToFSList(aJCas, sentences));	//add sentences to review scope list

                    
          
      }
    }
    
  }

}
