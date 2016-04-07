package learners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NaiveBayesLeaner extends ClassificationLearner{
	
	private List<Record> data;
	private List<Map<String, Float>> wordPOfStars  = new ArrayList<Map<String, Float>>();

	@Override
	public void train() {

		float[] priorP = new float[5];
		int[] totalLengthOfAClass = new int[5];
		
		System.out.println("... LEARNER INFO: NaiveBayes training");
		
		List<ArrayList<Record>> reviewWithStars = new ArrayList<ArrayList<Record>>();
		//calculate prior Pi
		
		for(int i=1; i<=5; i++) {
			reviewWithStars.add(new ArrayList<Record>());
			wordPOfStars.add(new HashMap<String, Float>());
		}
		
		for(Record r: data) {
			reviewWithStars.get(r.goldLabel - 1).add(r);
		}
		
		for(int i=1; i<=5; i++) {
			priorP[i-1] = (float)reviewWithStars.get(i-1).size() / data.size();
			
			int totalLength = 0;
			for(Record r: reviewWithStars.get(i-1)) {
				for(int j: r.tokenFreq.values()) {
					totalLength += j;
				}
			}
			totalLengthOfAClass[i-1] = totalLength;
		}
		
		for(int i=0; i<5; i++) {
//			System.out.println(priorP[i]);			
//			System.out.println(totalLengthOfAClass[i]);			
		}
		
		for(int i=0; i<5; i++) {			
			for(String word: data.get(0).tokenFreq.keySet()) {
				int nk = 0;
				for(Record r: reviewWithStars.get(i)) {
					nk += r.tokenFreq.get(word);
				}
				wordPOfStars.get(i).put(word, (float)(nk+1) / ( totalLengthOfAClass[i] + data.get(0).tokenFreq.size()));
			}
		}
		
		for(int i=0; i<5; i++) {
//			System.out.println(wordPOfStars.get(i).get("brilliant"));			
//			System.out.println(totalLengthOfAClass[i]);			
		}
		
		
	}

	@Override
	public void predict() {
		// TODO Auto-generated method stub
		System.out.println("... LEARNER INFO: NaiveBayes predicting");
		
	}

	@Override
	public void initTest(String modelPath, List<Record> data ) {
		
		
	}
	
	@Override
	public void initTrain(String modelPath, List<Record> data) {
		System.out.println("... LEARNER INFO: " + this.getClass().getSimpleName() + " initializing training");
		this.data = data;
	}

	@Override
	public void writeModel() {
		System.out.println("... LEARNER INFO: writing " + modelName + " to " + modelPath);
	}
	
}
