package learners;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import type.Review;

public class Record {
	public static Set<String> vocabulary;
	
	public SortedMap<String, Integer> tokenFreq = new TreeMap<String, Integer>();
	public SortedMap<String, Integer> tokenFreqNeg = new TreeMap<String, Integer>();	//treat negation word as new attr. E.g. great <-> n_great
	public SortedMap<String, Integer> tokenFreqSubNeg = new TreeMap<String, Integer>();	//treat negation word as substration of the origin word
	public int goldLabel;
	public Review review;
	
	public static void setVocab(Set<String> vocab) {
		vocabulary = vocab;
	}
	
	public void  setReview(Review r) {
		this.review = r;
	}
	
	public void setAttr(List<String> tokens) {
		for(String token: vocabulary) {
			tokenFreq.put(token, 0);
		}
		
		for(String token: tokens) {
			if(tokenFreq.containsKey(token)) {
				tokenFreq.put(token, tokenFreq.get(token) + 1);				
			}
		}
	}
	
	public void addNeg(Map<String, Integer> negatedWords) {
		for(Entry<String, Integer> e: negatedWords.entrySet()) {
			String key = "n_"+e.getKey();
			tokenFreq.put(key, e.getValue());
		}
	}
	
	public void addNegSubstract(Map<String, Integer> negatedWords) {
		for(Entry<String, Integer> e: negatedWords.entrySet()) {
			if(tokenFreq.containsKey(e.getKey())) {
				tokenFreq.put(e.getKey(), e.getValue() - 1);				
			} else {
				tokenFreq.put(e.getKey(), -1);								
			}
		}
		
	}
	
	public void setGoldLabel(int goldLabel) {
		this.goldLabel = goldLabel;
	}
}
