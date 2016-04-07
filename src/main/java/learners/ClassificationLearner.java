package learners;

import java.util.List;

public class ClassificationLearner implements Learner{

	protected String modelPath;
	protected String modelName = this.getClass().getSimpleName();
	
	@Override
	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
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

	@Override
	public void predict() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void initTrain(String modelPath, List<Record> data) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void initTest(String modelPath, List<Record> data) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void writeModel() {
		// TODO Auto-generated method stub
		
	}


}
