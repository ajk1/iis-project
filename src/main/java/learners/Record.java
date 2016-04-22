package learners;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import type.Review;

public class Record {
	public static Set<String> vocabulary;
	//public static Set<String> negatedVocabulary;
	
	public SortedMap<String, Integer> tokenFreq = new TreeMap<String, Integer>();
	public SortedMap<String, Integer> tokenFreqNeg = new TreeMap<String, Integer>();	//treat negation word as new attr. E.g. great <-> n_great
	public SortedMap<String, Integer> tokenFreqSubNeg = new TreeMap<String, Integer>();	//treat negation word as substration of the origin word
	public Map<String, Integer> negatedWords = new HashMap<String, Integer>();
	public int goldLabel;
	public Review review;
	
	public static void setVocab(Set<String> vocab) {
		vocabulary = vocab;
	}
	
//	public static void setNegatedVocab(Set<String> negatedVocab) {
//		negatedVocabulary = negatedVocab;
//	}
	
	public void  setReview(Review r) {
		this.review = r;
		//this.goldLabel = r.getGoldLabel();
	}
	
	public void setAttr(List<String> tokens) {
		for(String token: vocabulary) {
			tokenFreq.put(token, 0);
		}
		
		for(String token: tokens) {
			if(tokenFreq.containsKey(token)) {
				tokenFreq.put(token, tokenFreq.get(token) + 1);				
				//System.out.println(token + " " + tokenFreq.get(token));
			}
		}
	}
	
	public void addNeg(Map<String, Integer> negatedWords) {
		this.negatedWords = negatedWords;
		tokenFreqNeg = new TreeMap<String, Integer>(tokenFreq);
		for(Entry<String, Integer> e: negatedWords.entrySet()) {
			String key = "n_"+e.getKey();
			tokenFreqNeg.put(key, e.getValue());
		}
	}
	
	public void addNegSubstract(Map<String, Integer> negatedWords) {
		//this.negatedWords = negatedWords;
		tokenFreqSubNeg = new TreeMap<String, Integer>(tokenFreq);
		for(Entry<String, Integer> e: negatedWords.entrySet()) {
			if(tokenFreqSubNeg.containsKey(e.getKey())) {
				tokenFreqSubNeg.put(e.getKey(), tokenFreqSubNeg.get(e.getKey()) - e.getValue());				
			} //don't need else block because if it's not in vocab then it's not important
			/*else {
				tokenFreqSubNeg.put(e.getKey(), -1);								
			}*/
		}
		
	}
	
	public void setGoldLabel(int goldLabel) {
		this.goldLabel = goldLabel;
	}
}
