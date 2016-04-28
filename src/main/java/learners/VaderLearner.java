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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import type.Review;
import type.Sentence;
import util.Utils;

public class VaderLearner extends ClassificationLearner{
	
	private List<Record> data;
	private HashMap<String, Float> dictionary;
	private double[] cutoffScores = new double[4];
	
	@Override
	public void train() {
		int goldIndex;
		int[] labelCount = new int[5];
		
		List<Double> scores = new ArrayList<Double>();
		
		for (Record record : data) {
	    	Review r = record.review;
			goldIndex = r.getGoldLabel()-1;
	    	labelCount[goldIndex]++;
	    	
	    	int numMatches = 0;
	    	double scoreSum = 0;
	    	//get the value of each word in the review
	    	//HashMap<String, Integer> wordValue = new HashMap<String, Integer>();
			for(Sentence sentence : Utils.fromFSListToLinkedList(r.getSentences(), Sentence.class)) {
				
				//tokens are already clean, remove stopwords
				for(String token : Utils.fromStringListToArrayList(sentence.getUnigramList())) {
					if (dictionary.containsKey(token)) {
						numMatches++;
						scoreSum += dictionary.get(token);
					}
				}
			}
			scores.add(numMatches==0 ? 0 : scoreSum/numMatches);
		}
		Collections.sort(scores);
		cutoffScores[0] = scores.get(labelCount[0]);
		cutoffScores[1] = scores.get(labelCount[0]+labelCount[1]);
		cutoffScores[2] = scores.get(labelCount[0]+labelCount[1]+labelCount[2]);
		cutoffScores[3] = scores.get(labelCount[0]+labelCount[1]+labelCount[2]+labelCount[3]);
		
	}
	
	@Override
	public int predict(Record review) {
		Review r = review.review;
    	
    	int numMatches = 0;
    	double score = 0;
    	//get the value of each word in the review
    	//HashMap<String, Integer> wordValue = new HashMap<String, Integer>();
		for(Sentence sentence : Utils.fromFSListToLinkedList(r.getSentences(), Sentence.class)) {
			
			//tokens are already clean, remove stopwords
			for(String token : Utils.fromStringListToArrayList(sentence.getUnigramList())) {
				if (dictionary.containsKey(token)) {
					numMatches++;
					score += dictionary.get(token);
				}
			}
		}
		score = score/numMatches;
		for (int i=0; i<4; i++) {
			if (score < cutoffScores[i]) return i+1;
		}
		return 5;
	}
	
	@Override
	public void initialize(String mode, String modelPath, List<Record> data) {
		this.data = data;
		FileInputStream fstream;
	    
	    //first file: vader TODO: automatically loop through library files in sentiment libraries folder
		try {
		    System.out.println("... Reading Vader Sentiment Libraries");
			fstream = new FileInputStream("src/main/resources/libraries/sentiment_libraries/vader.txt");
		    BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		    String strLine;
		    dictionary = new HashMap<String, Float>();
		    
		    //Read File Line By Line
		    while ((strLine = br.readLine()) != null)   {
		    	String[] parts = strLine.split("\t");
		    	dictionary.put(parts[0], Float.valueOf(parts[1]));
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
	public void initTest(String modelPath) {

	    // Open the file
	    FileInputStream fstream;
		try {
			System.out.println("... LEARNER INFO: opening NaiveBayes model descripter");
			fstream = new FileInputStream(modelPath + inputFileName + "_" + this.getClass().getSimpleName() + ".txt");
		    BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		    String strLine;
		    
		    //Read File Line By Line
		    for (int i=0; i<4; i++)   {
		    	cutoffScores[i] = Double.parseDouble(br.readLine().trim());
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
	}

	@Override
	public void writeModel() {
		System.out.println("... LEARNER INFO: writing " + modelName + " to " + modelPath);
		
		//write csv document with scores for analysis
		File outputFile = null;
	    PrintWriter writer = null;
	    try {	    	
	        outputFile = new File(Paths.get(modelPath, inputFileName + "_" + this.getClass().getSimpleName() + ".txt").toString());
	        outputFile.getParentFile().mkdirs();
	        writer = new PrintWriter(outputFile);
	    } catch (FileNotFoundException e) {
	        System.out.printf("Output file could not be written: %s\n",
	                Paths.get(modelPath, "topWords.txt").toString());
	        return;
	    }
	    for(double score : cutoffScores) {
	    	System.out.println(score);
	        writer.println(score);
	    }
		writer.close();
		
	}
}
