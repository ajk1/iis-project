package learners;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class Record {
	public SortedMap<String, Integer> tokenFreq = new TreeMap<String, Integer>();
	public int goldLabel;
	
	public void setAttr(List<String> tokens, Set<String> topWords) {
		for(String token: topWords) {
			tokenFreq.put(token, 0);
		}
		
		for(String token: tokens) {
			if(tokenFreq.containsKey(token)) {
				tokenFreq.put(token, tokenFreq.get(token) + 1);				
			}
		}
	}
	
	public void addNeg(Map<String, Integer> negatedWords) {
		for(Entry<String, Integer> e: negatedWords.entrySet()) {
			String key = "n_"+e.getKey();
			tokenFreq.put(key, e.getValue());
		}
	}
	
	public void setGoldLabel(int goldLabel) {
		this.goldLabel = goldLabel;
	}
}
