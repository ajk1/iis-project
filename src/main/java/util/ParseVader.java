package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;

public class ParseVader {
	public static void main(String[] args) {
	    FileInputStream fstream;

		try {
			fstream = new FileInputStream("src/main/resources/libraries/sentiment_libraries/vader_sentiment_lexicon.txt");
		    BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		    String strLine;
		    
		    //Read File Line By Line
	        PrintWriter writer = new PrintWriter("src/main/resources/libraries/sentiment_libraries/vader.txt", "UTF-8");

		    while ((strLine = br.readLine()) != null)   {
		    	String[] parts = strLine.split("\t");
		        writer.println(parts[0] + "\t" + (Float.valueOf(parts[1]) / 4));
		    }

		    //Close the input stream
		    br.close();
		    writer.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
