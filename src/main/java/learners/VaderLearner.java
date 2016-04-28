package learners;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class VaderLearner extends ClassificationLearner{
	
	private List<Record> data;
	private HashMap<String, Float> dictionary;
	
	
	@Override
	public void train() {
		// TODO Auto-generated method stub
		
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
		
		
	}
	
	@Override
	public void initTrain(String modelPath, List<Record> data, Set<String> vocabulary) {
		System.out.println("... LEARNER INFO: " + this.getClass().getSimpleName() + " initializing training");
		
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
//	    for(String key: wordPOfStars.get(0).keySet()) {
//	    	String line = key + " ";
//	    	for(int i=0; i<5; i++) {
//	    		line = line + wordPOfStars.get(i).get(key) + " ";
//	    	}
//	        writer.println(line);
//	    }
		writer.close();
		
	}
}
