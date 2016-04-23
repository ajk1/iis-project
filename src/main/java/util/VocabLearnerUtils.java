package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import learners.Record;
import type.Review;
import type.Sentence;
import util.MapUtil;

public class VocabLearnerUtils {

	static final String vocabPath = "src/main/resources/vocabularies/";
	static int vocabSize;
	
	static public void setVocabSize(int sizeLimit) {
		vocabSize = sizeLimit;
	}
	
	static public Set<String> getVocab(String mode, String fileName, int limit, String opt, List<Review> reviews) {
		fileName = "vocab_" + fileName + "_" + opt + "_" + limit + ".txt"; 
		
		Set<String> vocabulary = new LinkedHashSet<String>();
		//READ or WRITE MODE
		if(mode.equals("read")) {
			System.out.println("... VOCAB INFO: reading vocab from file: " + vocabPath + fileName);

			vocabulary = getVocabFromLinkedSet(getVocabFromFile(fileName), limit);
			
		} else if(mode.equals("write")){
			
			//OPTIONS include [sort by infoGain, sort by pure frequency]
			if(opt.equals("infoGain")) {	
				System.out.println("... VOCAB INFO: generating vocab using info gain");
				Map<String, Double> sortedWordFreq = MapUtil.sortByValue(getInfoGain(reviews));
				
				//LEGACY code: 
//				for (String word : infoGain.keySet()) {
//					if (sortedWordFreq.containsKey(word)){
////						System.out.println(word + " " + infoGain.get(word));
//						weightedWordFreq.put(word, Math.pow(1, infoGain.get(word))*sortedWordFreq.get(word));
//					}
//				}
				
				vocabulary = getVocabFromLinkedSet(sortedWordFreq.keySet(), limit);
				
			} else if(opt.equals("freq")){
				System.out.println("... VOCAB INFO: generating vocab using word freqencies");
				Map<String, Integer> sortedWordFreq = MapUtil.sortByValue(getWordFreqInTrainSet(reviews));
				vocabulary = getVocabFromLinkedSet(sortedWordFreq.keySet(), limit);
			}			
			
			System.out.println("... VOCAB INFO: writing vocab to file: " + vocabPath + fileName);
			writeVocabToFile(vocabulary, fileName);
		}
		
		return vocabulary;
	}
	
	private static void writeVocabToFile(Set<String> vocabulary, String fileName) {
		File outputFile = null;
	    PrintWriter writer = null;
	    try {
	        outputFile = new File(Paths.get(vocabPath, fileName).toString());
	        outputFile.getParentFile().mkdirs();
	        writer = new PrintWriter(outputFile);
	    } catch (FileNotFoundException e) {
	        System.out.printf("Output file could not be written: %s\n",
	                Paths.get(vocabPath, fileName).toString());
	        return;
	    }
	    for(String key: vocabulary) {
	        writer.println(key);
	    }
		writer.close();		
	}

	static private Set<String> getVocabFromFile(String fileName) {
		Set<String> vocab = new LinkedHashSet<String>();
		
		// Construct BufferedReader from FileReader
		File file = new File(Paths.get(vocabPath, fileName).toString());
		FileReader fileReader;
		try {
			fileReader = new FileReader(file);
			BufferedReader br = new BufferedReader(fileReader);
		 
			String line = null;
			while ((line = br.readLine()) != null) {
				vocab.add(line);
			}
			br.close();
		 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
		return vocab;			
	}
	
	static private Set<String> getVocabFromLinkedSet(Set<String> keySet, int limit) {
		Set<String> vocabulary = new HashSet<String>();
		int i=0;
		for (String v : keySet) {
			if (i<limit) vocabulary.add(v);
			else break;
			i++;
		}	
		return vocabulary;
	}

	static private HashMap<String, Integer> getWordFreqInTrainSet(Collection<Review> reviews) {
		HashMap<String, Integer> wordFreq = new HashMap<String, Integer>();
		StopWordUtils swu = new StopWordUtils("full");
		for (Review review : reviews) {

			for(Sentence sentence : Utils.fromFSListToLinkedList(review.getSentences(), Sentence.class)) {
				
				//generating top words vocabs
				for(String token : Utils.fromStringListToArrayList(sentence.getUnigramList())) {
					//remove punctuation
					token = token.replaceAll("[^a-zA-Z ]", "");
					
					//keep all capalized words
					if (!token.equals(token.toUpperCase())) token = token.toLowerCase();
					
					//remove stopwords, rely on the stopword util
					if(!swu.isStopword(token)) {
						if(!wordFreq.containsKey(token)) {
							wordFreq.put(token, 1);
						} else {
							wordFreq.put(token, wordFreq.get(token) + 1);
						}
					}
				}
			}
		}
		return wordFreq;
	}
	
	
	static private HashMap<String, Double> getInfoGain(List<Review> reviews) {
		List<Record> data = Record.reviewsToRecordsWithNeg(reviews);
		
		//word -> values (..., -1, 0, 1, ...) -> labels [1,2,3,4,5]
		HashMap<String, HashMap<Integer, int[]>> wordValueLabels = new HashMap<String, HashMap<Integer, int[]>>();;
		StopWordUtils swu = new StopWordUtils("full");
		HashMap<Integer, int[]> valueLabels;
		double[] pLabel = {0,0,0,0,0};
		int numRecords = 0;
		//int[] labels = new int[5];
		int goldIndex;
		
		for (Record r : data) {
	    	goldIndex = r.goldLabel-1;
	    	pLabel[goldIndex]++;
	    	numRecords++;
	    	//get the value of each word in the review
	    	HashMap<String, Integer> wordValue = new HashMap<String, Integer>();
			for(Sentence sentence : Utils.fromFSListToLinkedList(r.review.getSentences(), Sentence.class)) {
				
				//tokens are already clean, remove stopwords
				for(String token : Utils.fromStringListToArrayList(sentence.getUnigramList())) {
					if (!(swu.isStopword(token))) {
						if(wordValue.containsKey(token)){
							wordValue.put(token, wordValue.get(token)+1);
						}
						else {
							wordValue.put(token, 1);
						}
					}
				}
			}
			//subtract negated words
			for(String token : r.negatedWords.keySet()) {
				if (wordValue.containsKey(token))
					wordValue.put(token, wordValue.get(token) - r.negatedWords.get(token));
				else 
					wordValue.put(token, - r.negatedWords.get(token));
			}
			//add word-value pair to word-value-labels
			for(String token : wordValue.keySet()) {
				//if the word hasn't been encountered yet
				int value = wordValue.get(token);
				if(!wordValueLabels.containsKey(token)) {
					valueLabels = new HashMap<Integer, int[]>();
					int[] labels = new int[5];
					labels[goldIndex]++;
					valueLabels.put(value, labels);
					wordValueLabels.put(token, valueLabels);
				//if this value of the word hasn't been encountered yet
				} else if (!wordValueLabels.get(token).containsKey(value)) {
					valueLabels = wordValueLabels.get(token);
					int[] labels = new int[5];
					labels[goldIndex]++;
					valueLabels.put(value, labels);
					wordValueLabels.put(token, valueLabels);
				//word and value has been encountered, just increment label;
				} else {
					valueLabels = wordValueLabels.get(token);
					int[] labels = valueLabels.get(value);
					labels[goldIndex]++;
					valueLabels.put(value, labels);
					wordValueLabels.put(token, valueLabels);
				}
				
			}
		}
//		ctr = 0;
//		for (Record r : data) {
//	    	if(ctr++ > sizeLimit && sizeLimit != 0) break;
//	    	goldIndex = r.goldLabel-1;
//	    	pLabel[goldIndex]++;
//	    	numRecords++;
//	    	//get the value of each word in the review
//	    	HashMap<String, Integer> wordValue = new HashMap<String, Integer>();
//			for(Sentence sentence : Utils.fromFSListToLinkedList(r.review.getSentences(), Sentence.class)) {
//				
//				Set<String> sentenceTokens = new HashSet<String>(Utils.fromStringListToArrayList(sentence.getUnigramList()));
//				for(String token : wordValueLabels.keySet()) {
//					if (!sentenceTokens.contains(token)) {
//						if (!wordValueLabels.get(token).containsKey(0)) {
//							valueLabels = wordValueLabels.get(token);
//							int[] labels = new int[5];
//							labels[goldIndex]++;
//							valueLabels.put(0, labels);
//							wordValueLabels.put(token, valueLabels);
//						//word and value has been encountered, just increment label;
//						} else {
//							valueLabels = wordValueLabels.get(token);
//							int[] labels = valueLabels.get(0);
//							labels[goldIndex]++;
//							valueLabels.put(0, labels);
//							wordValueLabels.put(token, valueLabels);
//						}
//					}
//				}
//			}
//		}
		//get label probabilities
		for (int i=0; i<5; i++) {
			pLabel[i] = pLabel[i]/numRecords;
		}
		HashMap<String, Double> wordInfoGain = new HashMap<String, Double>();
		//IG(word) = H(labels) - sum_values(P(value=v)*H(labels|value=v))
		//H(labels) = sum(P(label)*log(1/P(label)))
		double hLabels = 0;
		for (int i=0; i<5; i++) {
			hLabels += pLabel[i]*Math.log(pLabel[i]==0 ? 1 : 1/pLabel[i]);
		}
		for (String token : wordValueLabels.keySet()) {
			valueLabels = wordValueLabels.get(token);
			HashMap<Integer, Integer> valueFreq = new HashMap<Integer, Integer>();
			int totalValues = 0;
			for (int value : valueLabels.keySet()) {
				int[] labels = valueLabels.get(value);
				int sum = 0;
				for (int i=0; i<5; i++) {
					sum += labels[i];
				}
				valueFreq.put(value, sum);
				totalValues += sum;
			}

			double hLabelsGivenValue = 0.0;
			for (int value : valueLabels.keySet()) {
				int[] labels = valueLabels.get(value);
				//calculate H(labels|value=v)
				double h = 0;
				for (int i=0; i<5; i++) {
					double p = (double) labels[i]/valueFreq.get(value);
					h += p*Math.log(p==0 ? 1.0 : 1.0/p);
				}
				hLabelsGivenValue += h*(double)valueFreq.get(value)/totalValues;
			}
//			if (hLabelsGivenValue < 1) {
//				System.out.println(token + " " + hLabelsGivenValue + " " + totalValues);
//				for (int value : valueLabels.keySet()) {
//					System.out.println(value + " " + valueFreq.get(value));
//				}
//			}
			wordInfoGain.put(token, hLabels - hLabelsGivenValue);
		}
		//System.out.println(hLabels);
		return wordInfoGain;
	}	
	
	
	static public void main(String[] args) {
		Map<String, Integer> mp = new LinkedHashMap<String, Integer>();
		mp.put("a", 1);
		mp.put("b", 6);
		mp.put("c", 4);
		mp.put("d", 3);
		mp.put("e", 7);
		mp.put("f", 2);
		mp.put("g", 8);
		Map<String, Integer> mp2 = MapUtil.sortByValue(mp);
		System.out.println(mp2);
		System.out.println(mp2.keySet());
		for(String s : mp2.keySet()) {
			System.out.println(s);
		}
	}
}
