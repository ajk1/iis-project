/**
 * 
 */
package learners;

/**
 * @author Terry
 *
 */
public interface Learner {
	void initialize(String mode, String modelPath);
	
	//produce a model and save the model to file system
	void train();
	
	//take
	void predict();
}
