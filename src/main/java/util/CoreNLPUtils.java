package util;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;

public class CoreNLPUtils {
	
	public static Map<String, Integer> getNegatedWordsWithParseTree(String text){

	    // create an empty Annotation just with the given text
	    Annotation document = new Annotation(text);
	    
	    // run all Annotators on this text
	    CoreNLP.annotate(document);

	    List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        Map<String, Integer> negatedWords = new HashMap<String, Integer>();
	    for (CoreMap sentence : sentences) {

	        Tree sentenceTree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);

	        // Output dependency tree
	        TreebankLanguagePack tlp = new PennTreebankLanguagePack();
	        GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
	        GrammaticalStructure gs = gsf.newGrammaticalStructure(sentenceTree);
	        Collection<TypedDependency> tdl = gs.typedDependenciesCollapsed();

			String pattern = "(?<type>.+)\\((?<aWord>.+)-(?<aIndex>.+), (?<bWord>.+)-(?<bIndex>.+)\\)";

	        // Extract 
	        for(TypedDependency td: tdl) {
//		        System.out.println("typedDependencies: "+td); 	        	
		        Matcher matcher = Pattern.compile(pattern).matcher(td.toString());
		        if (matcher.find()) {
		        	String type = matcher.group("type");
		      		String aWord = matcher.group("aWord");
		      		String aIndex = matcher.group("aIndex");
		      		String bWord = matcher.group("bWord");
		      		String bIndex = matcher.group("bIndex");
		      		
		      		if(type.equals("neg")) {
//			      		System.out.println(type + ", "+aWord+", "+aIndex+", "+bWord+", "+bIndex);
			      		String negatedWord = Integer.valueOf(aIndex) > Integer.valueOf(bIndex) ? aWord : bWord;
			      		if(negatedWords.containsKey(negatedWord)) {
			      			negatedWords.put(negatedWord, negatedWords.get(negatedWord) + 1);
			      		} else {
			      			negatedWords.put(negatedWord, 1);
			      		}
		      		}
		        }

	        }
	    }
	    return negatedWords;
	    
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
