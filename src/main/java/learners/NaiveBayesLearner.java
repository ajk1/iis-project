package learners;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import type.Review;
import type.Sentence;
import util.Utils;

public class NaiveBayesLearner extends ClassificationLearner{
	
	private List<Record> data;
	private List<Map<String, Float>> wordPOfStars  = new ArrayList<Map<String, Float>>();
	private Set<String> vocabulary = new HashSet<String>();

	@Override
	public void train() {

		float[] priorP = new float[5];
		int[] totalLengthOfAClass = new int[5];
		List<ArrayList<Record>> reviewWithStars = new ArrayList<ArrayList<Record>>();
		
		System.out.println("... LEARNER INFO: NaiveBayes training");
		System.out.println("... LEARNER INFO: NaiveBayes training, tokenFreqNeg size: " + data.get(0).tokenFreqNeg.size());
		
		// init
		// find union set for all reviews
		for(Record r: data) {
			vocabulary.addAll(r.tokenFreqNeg.keySet());
		}
		
		// put different ratings training record to 5 buckets
		// init 5 vocabularies
		for(int i=1; i<=5; i++) {
			reviewWithStars.add(new ArrayList<Record>());
			wordPOfStars.add(new HashMap<String, Float>());
		}
		
		//calculate prior Pi
		for(Record r: data) {
			reviewWithStars.get(r.goldLabel - 1).add(r);
		}
		
		for(int i=1; i<=5; i++) {
			priorP[i-1] = (float)reviewWithStars.get(i-1).size() / data.size();
			
			int totalLength = 0;
			for(Record r: reviewWithStars.get(i-1)) {
				for(int j: r.tokenFreqNeg.values()) {
					totalLength += j;
				}
			}
			totalLengthOfAClass[i-1] = totalLength;

			System.out.println("... LEARNER INFO: size of " + i + ": " + reviewWithStars.get(i-1).size());
		}
				
		for(int i=0; i<5; i++) {			
			for(String word: vocabulary) {
				int nk = 0;
				for(Record r: reviewWithStars.get(i)) {
//					System.out.println("... LEARNER INFO: " + word);
					if(r.tokenFreqNeg.containsKey(word)) {
						nk += r.tokenFreqNeg.get(word);						
					}
				}
				wordPOfStars.get(i).put(word, (float)(nk+1) / ( totalLengthOfAClass[i] + data.get(0).tokenFreqNeg.size()));
			}
		}
				
		
	}

	@Override
	public int predict(Record review) {
		float[] nbScore = new float[5];
		for(Entry<String, Integer> e: review.tokenFreqNeg.entrySet()) {
			if(wordPOfStars.get(0).keySet().contains(e.getKey())) {
				for(int i=0; i<5; i++) {
					nbScore[i] += e.getValue() * Math.log(wordPOfStars.get(i).get(e.getKey()));
				}					
			}
		}

		float max = 0 - Float.MAX_VALUE;
		int maxId = 0;
		for(int i=0; i<5; i++) {
			if(nbScore[i] > max) {
				max = nbScore[i];
				maxId = i;
			}
		}
		
		return maxId + 1;		
	}
	
	@Override
	public int predict(Review review) {
		
		float[] nbScore = new float[5];
		
		for(Sentence sentence : Utils.fromFSListToLinkedList(review.getSentences(), Sentence.class)) {
			List<String> tokenList = Utils.fromStringListToArrayList(sentence.getUnigramList());
			for(String token: tokenList) {
				if(wordPOfStars.get(0).keySet().contains(token)) {
					for(int i=0; i<5; i++) {
						nbScore[i] += Math.log(wordPOfStars.get(i).get(token));
					}					
				}
			}
		}
		
		float max = 0 - Float.MAX_VALUE;
		int maxId = 0;
		for(int i=0; i<5; i++) {
			if(nbScore[i] > max) {
				max = nbScore[i];
				maxId = i;
			}
		}
		
		return maxId + 1;
	}

	@Override
	public void initTest(String modelPath) {
		
		//init
		for(int i=1; i<=5; i++) {
			wordPOfStars.add(new HashMap<String, Float>());
		}

	    // Open the file
	    FileInputStream fstream;
		try {
			System.out.println("... LEARNER INFO: opening NaiveBayes model descripter");
			fstream = new FileInputStream(modelPath + this.getClass().getSimpleName() + ".txt");
		    BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		    String strLine;
		    
		    //Read File Line By Line
		    while ((strLine = br.readLine()) != null)   {
		    	String[] parts = strLine.split(" ");
		    	for(int i=0; i<5; i++) {
		    		wordPOfStars.get(i).put(parts[0], Float.valueOf(parts[i+1]));
		    	}
		    }

		    //Close the input stream
		    br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
	}
	
	@Override
	public void initTrain(String modelPath, List<Record> data, Set<String> vocabulary) {
		System.out.println("... LEARNER INFO: " + this.getClass().getSimpleName() + " initializing training");
		this.data = data;
		this.vocabulary = vocabulary;
	}

	@Override
	public void writeModel() {
		System.out.println("... LEARNER INFO: writing " + modelName + " to " + modelPath);
		
		//write csv document with scores for analysis
		File outputFile = null;
	    PrintWriter writer = null;
	    try {	    	
	        outputFile = new File(Paths.get(modelPath, this.getClass().getSimpleName() + ".txt").toString());
	        outputFile.getParentFile().mkdirs();
	        writer = new PrintWriter(outputFile);
	    } catch (FileNotFoundException e) {
	        System.out.printf("Output file could not be written: %s\n",
	                Paths.get(modelPath, "topWords.txt").toString());
	        return;
	    }
	    for(String key: wordPOfStars.get(0).keySet()) {
	    	String line = key + " ";
	    	for(int i=0; i<5; i++) {
	    		line = line + wordPOfStars.get(i).get(key) + " ";
	    	}
	        writer.println(line);
	    }
		writer.close();
		
	}
	
}
