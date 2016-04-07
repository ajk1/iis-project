/**
 * 
 */
package learners;

import java.util.List;
import java.util.Set;

/**
 * @author Terry
 *
 */
public interface Learner {
	void initialize(String mode, String modelPath, List<Record> data);
	
	//produce a model and save the model to file system
	void train();
	
	void initTest(String modelPath);

	void writeModel();

	void setModelPath(String modelPath);

	void initTrain(String modelPath, List<Record> data, Set<String> vocabulary);
}
