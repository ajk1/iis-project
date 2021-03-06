package learners;

import java.util.List;
import java.util.Set;

import type.Review;

public class ClassificationLearner implements Learner{

	protected String modelPath;
	protected String inputFileName;
	protected String modelName = this.getClass().getSimpleName();
	
	@Override
	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}
	
	public void setInputFileName(String fileName) {
		inputFileName = fileName;
	}
	
	@Override
	public void initialize(String mode, String modelPath, List<Record> data) {
		// TODO Auto-generated method stub
		System.out.println("... LEARNER INFO: " + this.getClass().getSimpleName() + " initializing");
		
	}


	//for classification trainer, we are actually training five different boolean classifiers
	@Override
	public void train() {
		
		//replicate the data set so 
		
		// TODO Auto-generated method stub
		
	}

	public int predict(Record r) {
		return 0;
	}


	@Override
	public void initTest(String modelPath) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void writeModel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initTrain(String modelPath, List<Record> data, Set<String> vocabulary) {
		// TODO Auto-generated method stub
		
	}

	public int predict(Review r) {
		// TODO Auto-generated method stub
		return 0;
	}


}
