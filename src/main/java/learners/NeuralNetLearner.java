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
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import type.Review;
import type.Sentence;
import util.Utils;

public class NeuralNetLearner extends ClassificationLearner{
	
	private List<Record> data;
	private Set<String> vocabulary = new HashSet<String>();
	private int numInput;
	private int numHidden = 4;
	private int numOutput = 5;
	double eta = 5; //step size
	double[][] w;
	double[][] q = new double[numHidden][numOutput];

	@Override
	public void train() {
		
		List<double[]> inputs = new ArrayList<double[]>();
		for (Record r: data) {
			int sum = 0;
			for (int v : r.tokenFreq.values()) {
				sum += v;
			}
			double[] x = new double[numInput];
			x[0] = 1;
			int j = 1;
			for (int v : r.tokenFreq.values()) {
				x[j] = (double)v/sum;
				j++;
			}
			inputs.add(x);
		}
		//randomize weights
		for (int h=0; h<numHidden; h++) {
			for (int i=0; i<numInput; i++) {
				w[i][h] = Math.random()*0.2 - 0.1;
			}
			for (int o=0; o<numOutput; o++) {
				q[h][o] = Math.random()*0.2 - 0.1;
			}
		}
		
		double prevSqError = 100000;
		for (int iter=0; iter<1000; iter++) {
			double sqError = 0;
			
			for (int n=0; n<data.size(); n++) {
				double[] x = inputs.get(n);
				double[] output = {0,0,0,0,0};
				int[] t = {0,0,0,0,0}; //true output
				t[data.get(n).goldLabel - 1] = 1;
				
				double[] oH = new double[numHidden];
				
				for (int h=0; h<numHidden; h++) {
					double weightedSum = 0;
					for (int i=0; i<numInput; i++) {
						weightedSum += x[i]*w[i][h];
					}
					oH[h] = sigmoid(weightedSum);
				}
				
				double[] dO = new double[numOutput];
				double[] dH = new double[numHidden];
				
				for (int o=0; o<numOutput; o++) {
					for (int h=0; h<numHidden; h++) {
						output[o] += oH[h]*q[h][o];
					}
					output[o] = sigmoid(output[o]);
					dO[o] = output[o]*(1-output[o])*(t[o] - output[o]);
				}
				for (int h=0; h<numHidden; h++) {
					double sum = 0;
					for (int o=0; o<numOutput; o++) {
						sum += q[h][o]*dO[o];
					}
					dH[h] = oH[h]*(1-oH[h])*sum;
				}
				
				for (int h=0; h<numHidden; h++) {
					for (int i=0; i<numInput; i++) {
						w[i][h] += eta*dH[h]*x[i];
					}
					for (int o=0; o<numOutput; o++) {
						q[h][o] += eta*dO[o]*oH[h];
					}
				}
				
				for (int o=0; o<numOutput; o++) {
					sqError += Math.pow(output[o] - t[o], 2)/2;
				}
			}
			System.out.println(sqError);
			if (sqError/prevSqError > 1.01) {
				System.out.println("reduce step");
				if (eta < 1) break;
				eta = eta/2;
			}
			prevSqError = sqError;
		}
		
	}

	@Override
	public int predict(Review review) {
		
		float[] nnScore = new float[5];
		
		List<String> tokens = new ArrayList<String>();
		for(Sentence sentence : Utils.fromFSListToLinkedList(review.getSentences(), Sentence.class)) {
			List<String> tokenList = Utils.fromStringListToArrayList(sentence.getUnigramList());
			for(String token: tokenList) {
				tokens.add(token);
			}
		}
		Record r = new Record();
		r.setAttr(tokens, vocabulary);
		
		int sum = 0;
		for (int v : r.tokenFreq.values()) {
			sum += v;
		}
		double[] x = new double[numInput];
		x[0] = 1;
		int j = 1;
		for (int v : r.tokenFreq.values()) {
			x[j] = (double)v/sum;
			j++;
		}
		
		double[] output = {0,0,0,0,0};
		double[] oH = new double[numHidden];
		
		for (int h=0; h<numHidden; h++) {
			double weightedSum = 0;
			for (int i=0; i<numInput; i++) {
				weightedSum += x[i]*w[i][h];
			}
			oH[h] = sigmoid(weightedSum);
		}
		
		double max = -1;
		int maxId = -1;
		for (int o=0; o<numOutput; o++) {
			for (int h=0; h<numHidden; h++) {
				output[o] += oH[h]*q[h][o];
			}
			output[o] = sigmoid(output[o]);
			if (output[o] > max) {
				max = output[o];
				maxId = o;
			}
		}
		
		return maxId + 1;
	}

	@Override
	public void initTest(String modelPath) {
		
		// Open the file
	    FileInputStream fstream;
		try {
			System.out.println("... LEARNER INFO: opening NeuralNet model descripter");
			fstream = new FileInputStream(modelPath + this.getClass().getSimpleName() + ".txt");
		    BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

			//first line: vocabulary in alphabetical order
		    String line = br.readLine();
		    for(String s : line.split("\\s+")) {
		    	vocabulary.add(s);
		    }
		    numInput = vocabulary.size() + 1;
		    //next h lines: weights from hidden to output units
		    for(int h=0; h<numHidden; h++) {
		    	line = br.readLine();
		    	String[] s = line.split("\\s+");
		    	for(int o=0; o<numOutput; o++) {
		    		q[h][o] = Double.parseDouble(s[o]);
		    	}
		    }
		    //next i lines: weights from input to hidden units
		    w = new double[numInput][numHidden];
		    for(int i=0; i<numInput; i++) {
		    	line = br.readLine();
		    	String[] s = line.split("\\s+");
		    	for(int h=0; h<numHidden; h++) {
		    		w[i][h] = Double.parseDouble(s[h]);
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
		this.numInput = vocabulary.size() + 1; //extra unit for bias unit (which always has input of 1)
		this.w = new double[numInput][numHidden]; 
	}

	@Override
	public void writeModel() {
		System.out.println("... LEARNER INFO: writing " + modelName + " to " + modelPath);
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
	  //first line: vocabulary in alphabetical order
	    String line = "";
	    for(String s : data.get(0).tokenFreq.keySet()) {
	    	line = line + s + " ";
	    }
	    writer.println(line);
	    //next h lines: weights from hidden to output units
	    for(int h=0; h<numHidden; h++) {
	    	line = "";
	    	for(int o=0; o<numOutput; o++) {
	    		line = line + q[h][o] + " ";
	    	}
	    	writer.println(line);
	    }
	    //next i lines: weights from input to hidden units
	    for(int i=0; i<numInput; i++) {
	    	line = "";
	    	for(int h=0; h<numHidden; h++) {
	    		line = line + w[i][h] + " ";
	    	}
	    	writer.println(line);
	    }
		writer.close();
		
	}
	
	private double sigmoid(double x) {
		return 1/(1 + Math.exp(-x));
	}
}
