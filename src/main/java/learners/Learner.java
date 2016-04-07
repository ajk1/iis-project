/**
 * 
 */
package learners;

import java.util.List;

/**
 * @author Terry
 *
 */
public interface Learner {
	void initialize(String mode, String modelPath, List<Record> data);
	
	//produce a model and save the model to file system
	void train();
	
	//take
	void predict();
	
	//
	void initTrain(String modelPath, List<Record> data);

	void initTest(String modelPath, List<Record> data);

	void writeModel();

	void setModelPath(String modelPath);
}
