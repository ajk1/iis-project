package util;

//Adapted from https://github.com/harryaskham/Twitter-L-LDA/blob/master/util/Stopwords.java

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class StopWordUtils {
	
	public static String[] stopwordsLimited = {",", ".", "''", "``", "\"\"", "\\", "/"};
	public static String[] stopwords = {"a", "as", "able", "about", "above", "according", "accordingly", "across", "actually", "after", "afterwards", "again", "against", "ain't", "aint", "all", "allows", "almost", "along", "already", "also", "although", "always", "am", "among", "amongst", "an", "and", "another", "any", "anybody", "anyhow", "anyone", "anything", "anyway", "anyways", "anywhere", "apart", "appear", "appropriate", "are", "aren't", "arent", "around", "as", "aside", "ask", "asking", "associated", "at", "available", "away", "awfully", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "believe", "below", "beside", "besides", "between", "beyond", "both", "brief", "but", "by", "cmon", "cs", "came", "can't", "can", "cannot", "cant", "cause", "causes", "changes", "co", "com", "come", "comes", "concerning", "consequently", "consider", "considering", "contain", "containing", "contains", "corresponding", "could", "couldn't", "couldnt", "course", "currently", "described", "despite", "did", "didn't", "didnt", "different", "do", "does", "doesn't", "doesnt", "doing", "don't", "dont", "done", "down", "downwards", "during", "each", "edu", "eg", "eight", "either", "else", "elsewhere", "enough", "entirely", "especially", "et", "etc", "even", "ever", "every", "everybody", "everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "far", "few", "fifth", "first", "five", "followed", "following", "follows", "for", "former", "formerly", "forth", "four", "from", "further", "furthermore", "get", "gets", "getting", "given", "gives", "go", "goes", "going", "gone", "got", "gotten", "had", "hadn't", "hadnt", "happens", "hardly", "has", "hasn't", "hasnt", "have", "haven't", "havent", "having", "he", "hes", "hello", "hence", "her", "here", "heres", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "how", "howbeit", "however", "i", "id", "im", "ive", "ie", "if", "immediate", "in", "inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", "into", "inward", "is", "isn't", "isnt", "it", "itd", "itll", "its", "its", "itself", "just", "keep", "keeps", "kept", "know", "knows", "known", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let", "lets", "likely", "little", "look", "looking", "looks", "ltd", "mainly", "many", "may", "maybe", "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", "myself", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", "nevertheless", "new", "next", "nine", "nobody", "non", "none", "noone", "nor", "normally", "not", "nothing", "now", "nowhere", "obviously", "of", "off", "often", "oh", "old", "on", "once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise", "ought", "our", "ours", "ourselves", "out", "outside", "over", "overall", "own", "particular", "particularly", "per", "perhaps", "placed", "plus", "possible", "presumably", "probably", "provides", "que", "quite", "qv", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively", "right", "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible", "sent", "seven", "several", "shall", "she", "should", "shouldn't", "shouldnt", "since", "six", "so", "some", "somebody", "somehow", "someone", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "specified", "specify", "specifying", "still", "sub", "such", "sup", "ts", "take", "taken", "tell", "tends", "th", "than", "thanx", "that", "thats", "thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "theres", "thereafter", "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "theyd", "theyll", "theyre", "theyve", "think", "third", "this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried", "tries", "try", "trying", "twice", "two", "un", "under", "unless", "unlikely", "until", "unto", "up", "upon", "us", "use", "used", "uses", "using", "usually", "various", "very", "via", "viz", "vs", "wants", "was", "wasn't", "wasnt", "way", "we", "wed", "were", "weve", "went", "were", "weren't", "werent", "what", "whats", "whatever", "when", "whence", "whenever", "where", "wheres", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whos", "whoever", "whole", "whom", "whose", "why", "will", "willing", "with", "within", "without", "won't", "wont", "wonder", "would", "would", "wouldn't", "wouldnt", "yet", "you", "youd", "youll", "youre", "youve", "your", "yours", "yourself", "yourselves", "zero"};
	
	public Set<String> stopWordSet = new HashSet<String>(Arrays.asList(stopwords));
	
	public StopWordUtils(String mode) {
		if(mode.equals("limit")) {
			stopWordSet = new HashSet<String>(Arrays.asList(stopwordsLimited));
		} else if(mode.equals("full")) {
			stopWordSet = new HashSet<String>(Arrays.asList(stopwords));			
		}
	}
	
	public boolean isStopword(String word) {
//		if(word.length() < 2) return true;
//		if(word.charAt(0) >= '0' && word.charAt(0) <= '9') return true; //remove numbers, "25th", etc
		if(stopWordSet.contains(word)) return true;
		else return false;
	}
	
	public String removeStopword(String sentence) {
		StringBuilder sb = new StringBuilder();
		for(String s: sentence.split(" ")) {
			if(!isStopword(s)) {
				sb.append(s+" ");
			}
		}
		return sb.toString();  
	}
	
	public List<String> removeStopwordReturnList(String sentence) {
		  List<String> list = new LinkedList<String>();
		  for(String s: sentence.split(" ")) {
			if(!isStopword(s)) {
				list.add(s);
			}
		  }
		  return list;
	}


}

