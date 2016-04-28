package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
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
			if(opt.equals("weighted")) {	
				System.out.println("... VOCAB INFO: generating vocab using info gain");
				Map<String, Double> sortedWordFreq = MapUtil.sortByValue(getWeightedWordFreq(reviews));
				
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
	
	
	static private HashMap<String, Double> getWeightedWordFreq(List<Review> reviews) {
		HashMap<String, Double> weightedWordFreq = new HashMap<String, Double>();
//		List<Record>[] labeledData = (ArrayList<Record>[]) new ArrayList[5];
//		for (int i=0; i<5; i++) {
//			labeledData[i] = new ArrayList<Record>();
//		}
//		for (Record r : data) {
//			labeledData[r.goldLabel-1].add(r);
//		}
		//word -> values (..., -1, 0, 1, ...) -> labels [1,2,3,4,5]
		HashMap<String, int[]> wordFreqByLabel = new HashMap<String, int[]>();
		
		StopWordUtils swu = new StopWordUtils("full");
		double[] pLabel = {0,0,0,0,0};
		int numRecords = 0;
		int goldIndex;
		
		for (Review r : reviews) {
	    	goldIndex = r.getGoldLabel()-1;
	    	pLabel[goldIndex]++;
	    	numRecords++;
	    	//get the value of each word in the review
	    	//HashMap<String, Integer> wordValue = new HashMap<String, Integer>();
			for(Sentence sentence : Utils.fromFSListToLinkedList(r.getSentences(), Sentence.class)) {
				
				//tokens are already clean, remove stopwords
				for(String token : Utils.fromStringListToArrayList(sentence.getUnigramList())) {
					if (!(swu.isStopword(token))) {
						int[] freq = {0,0,0,0,0};
						if(wordFreqByLabel.containsKey(token)){
							freq = wordFreqByLabel.get(token);
						}
						freq[goldIndex]++;
						wordFreqByLabel.put(token, freq);
					}
				}
			}
//			//subtract negated words
//			for(String token : r.negatedWords.keySet()) {
//				if (wordFreqByLabel.containsKey(token))
//					wordValue.put(token, wordValue.get(token) - r.negatedWords.get(token));
//				else 
//					wordValue.put(token, - r.negatedWords.get(token));
//			}
		}
		
		//get distribution of labels
		for (int i=0; i<5; i++) {
			pLabel[i] = pLabel[i]/numRecords;
		}
		
		//get distribution of word frequencies (sum to 1)
		for (String token : wordFreqByLabel.keySet()) {

			int[] freq = wordFreqByLabel.get(token);
			int total = 0;
			for (int i=0; i<5; i++) {
				total += freq[i];
			}
			//measures the deviance of the distribution of word frequency over labels from the average label frequency
			double sumSqResidual = 0;
			for (int i=0; i<5; i++) {
				sumSqResidual += Math.pow(pLabel[i] - (double)freq[i]/total, 2);
			}
			weightedWordFreq.put(token, sumSqResidual*total);
		}
		//System.out.println(hLabels);
		return weightedWordFreq;
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
