package util;

import java.util.LinkedList;
import java.util.List;

public class NGramUtils {
	public static String[] getNgramArray(String s, int len) {
	    String[] parts = s.split(" ");
	    String[] result = new String[parts.length - len + 1];
	    for(int i = 0; i < parts.length - len + 1; i++) {
	       StringBuilder sb = new StringBuilder();
	       for(int k = 0; k < len; k++) {
	           if(k > 0) sb.append(' ');
	           sb.append(parts[i+k]);
	       }
	       result[i] = sb.toString();
	    }
	    return result;
	}

	public static List<String> getNgramList(String s, int len) {
		List<String> result = new LinkedList<String>();
	    String[] parts = s.split(" ");
	    for(int i = 0; i < parts.length - len + 1; i++) {
	       StringBuilder sb = new StringBuilder();
	       for(int k = 0; k < len; k++) {
	           if(k > 0) sb.append(' ');
	           sb.append(parts[i+k]);
	       }
	       result.add(sb.toString());
	    }
	    return result;
	}

}
