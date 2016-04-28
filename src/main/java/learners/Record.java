package learners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import type.Review;
import type.Sentence;
import util.CoreNLPUtils;
import util.Utils;

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
	
	public static List<Record> reviewsToRecordsWithNeg(List<Review> reviews) {
		ArrayList<Record> data = new ArrayList<Record>();
		int ctr = 0;
		for (Review review : reviews) {
			System.out.println("... Learner Annotator: annotating " + (ctr++) + " review to record ... ");	
			Record r = Record.reviewToRecordWithNeg(review);
			data.add(r);
		}		
		return data;
	}
	
	public static Record reviewToRecordWithNeg(Review review) {
		Record r = Record.reviewToRecord(review);
		Map<String, Integer> negatedWords = new HashMap<String, Integer>();
		
		//for each sentence in review
		for(Sentence sentence : Utils.fromFSListToLinkedList(review.getSentences(), Sentence.class)) {

			//negation detection
			Map<String, Integer> negatedWordsInSentence = CoreNLPUtils.getNegatedWordsWithParseTree(sentence.getRawText());
			negatedWordsInSentence.forEach((k, v) -> negatedWords.merge(k, v, (v1, v2) -> v1 + v2));
		}
		r.addNeg(negatedWords);
		r.addNegSubstract(negatedWords);
		return r;
	}	

	public static Record reviewToRecord(Review review) {
		Record r = new Record();
		List<String> allTokens = new ArrayList<String>();
		
		//for each sentence in review
		for(Sentence sentence : Utils.fromFSListToLinkedList(review.getSentences(), Sentence.class)) {

			//token detection
			List<String> tokenList = Utils.fromStringListToArrayList(sentence.getUnigramList());

			for(String token: tokenList) {
				token = token.replaceAll("[^a-zA-Z ]", "");
				if (!token.equals(token.toUpperCase())) token = token.toLowerCase();
				allTokens.add(token);					
			}
		}
		r.setReview(review);
		r.setGoldLabel(review.getGoldLabel());
		r.setAttr(allTokens);
		return r;
	}		
	
	public static List<Record> reviewsToRecordsWithNegOneSentence(List<Review> reviews, String firstOrLast) {
		ArrayList<Record> data = new ArrayList<Record>();
		int ctr = 0;
		for (Review review : reviews) {
			System.out.println("... Learner Annotator: annotating " + firstOrLast +
					" sentence of " + (ctr++) + " review to record ... ");	
			Record r = new Record();
			List<String> allTokens = new ArrayList<String>();
			Map<String, Integer> negatedWords = new HashMap<String, Integer>();
			
			//for each sentence in review
			Sentence sentence;
			List<Sentence> sentences = Utils.fromFSListToLinkedList(review.getSentences(), Sentence.class);
			if (firstOrLast.toLowerCase().equals("first")) {
				sentence = sentences.get(0); 
			} else {
				sentence = sentences.get(sentences.size()-1); 
			}
			
			//token detection
			List<String> tokenList = Utils.fromStringListToArrayList(sentence.getUnigramList());

			for(String token: tokenList) {
				token = token.replaceAll("[^a-zA-Z ]", "");
				if (!token.equals(token.toUpperCase())) token = token.toLowerCase();
				allTokens.add(token);					
			}
			//negation detection
			Map<String, Integer> negatedWordsInSentence = CoreNLPUtils.getNegatedWordsWithParseTree(sentence.getRawText());
			negatedWordsInSentence.forEach((k, v) -> negatedWords.merge(k, v, (v1, v2) -> v1 + v2));
			
			r.setReview(review);
			r.setGoldLabel(review.getGoldLabel());
			r.setAttr(allTokens);
			r.addNeg(negatedWords);
			r.addNegSubstract(negatedWords);
			data.add(r);
		}		
		return data;
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
			//also subtract from frequency of non-negated word
			if (tokenFreqNeg.containsKey(e.getKey()))
				tokenFreqNeg.put(e.getKey(), tokenFreqNeg.get(e.getKey()) - e.getValue());
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
