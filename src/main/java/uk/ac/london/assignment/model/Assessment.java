package uk.ac.london.assignment.model;

import java.text.MessageFormat;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Assessment")
public class Assessment {

	private static final String COMMENT_KEY = "Comment";
	
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
		if (results.containsKey(COMMENT_KEY) && comment != null && !comment.isEmpty()) {
			results.put(COMMENT_KEY, MessageFormat.format("{0}; {1}", results.get(COMMENT_KEY), comment));			
		} else {
			results.put(COMMENT_KEY, comment);
		}
	}
	
	public void resetComment() {
		results.remove(COMMENT_KEY);
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
	
}
