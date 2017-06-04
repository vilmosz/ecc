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
	
	private static final String COMMENT_KEY	= "Comment";
	private static final String ERROR_KEY	= "Error";
	private static final String TOTAL_KEY	= "Total";

	private static final SortedMap<String, Integer> weights = ImmutableSortedMap.<String, Integer>naturalOrder()
    		.put("a", 1)
    		.put("b", 1)
    		.put("k", 1)
    		.put("Order", 1)
    		.put("R", 1)
    		.put("S", 1)
    		.put(TOTAL_KEY, 0)
    		.put(COMMENT_KEY, 0)
    		.put(ERROR_KEY, 0)
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
		results.put(COMMENT_KEY, results.get(COMMENT_KEY) == null ? comment : MessageFormat.format("{0}; {1}", results.get(COMMENT_KEY), comment));			
	}
	
	public void addTotal(Integer total) {
		results.put(TOTAL_KEY, total);
	}
	
	public void resetResults() {
		results.clear();
		weights.keySet().stream().forEach(key -> results.put(key, null));
		if (hasError()) {
			results.put(ERROR_KEY, error);
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
