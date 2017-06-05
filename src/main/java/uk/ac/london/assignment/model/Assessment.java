package uk.ac.london.assignment.model;

import java.text.MessageFormat;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.collect.ImmutableSortedMap;

@Document(collection = "Assessment")
public class Assessment {
	
	public static final String A_KEY 		= "1a";
	public static final String B_KEY 		= "2b";
	public static final String K_KEY 		= "3k";
	public static final String ORDER_KEY	= "4order";
	public static final String R_KEY 		= "5R";
	public static final String S_KEY 		= "6S";
	public static final String COMMENT_KEY	= "7Comment";
	public static final String TOTAL_KEY	= "0Total";

	private static final SortedMap<String, Integer> weights = ImmutableSortedMap.<String, Integer>naturalOrder()
    		.put(A_KEY, 1)
    		.put(B_KEY, 1)
    		.put(K_KEY, 1)
    		.put(ORDER_KEY, 1)
    		.put(R_KEY, 1)
    		.put(S_KEY, 1)
    		.put(TOTAL_KEY, 0)
    		.put(COMMENT_KEY, 0)
    		.build();
		
	private @Id String id;
	private String name;
	private Student expected;
	private Student actual;	
	private Map<String, Object> results = new TreeMap<>();
	private String error;
	
    public Assessment() {
    	// empty constructor
    }

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Student getExpected() {
		return expected;
	}

	public Student getActual() {
		return actual;
	}
	
	public Map<String, Object> getResults() {
		return results;
	}

	public void addResult(String key, Object value) {
		results.put(key, value);		
	}

	public void addComment(String comment) {
		if (comment == null || comment.isEmpty())
			return;
		if (Character.isDigit(comment.charAt(0)))
			comment = comment.substring(1);
		results.put(COMMENT_KEY, results.get(COMMENT_KEY) == null ? comment : MessageFormat.format("{0}; {1}", results.get(COMMENT_KEY), comment));			
	}
	
	public void addTotal(Integer total) {
		results.put(TOTAL_KEY, total);
	}
	
	public void resetResults() {
		results.clear();
		weights.keySet().stream().forEach(key -> results.put(key, null));
		if (hasError()) {
			results.put(COMMENT_KEY, error);
			results.put(TOTAL_KEY, 0);
		}
	}
	
	public boolean hasError() {
		return error != null && !error.isEmpty();
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setExpected(Student expected) {
		this.expected = expected;
	}

	public void setActual(Student actual) {
		this.actual = actual;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	public static SortedMap<String, Integer> getWeights() {
		return weights;		
	}
	
}
