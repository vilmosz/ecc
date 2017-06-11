package uk.ac.london.assignment.model;

import java.text.MessageFormat;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Assessment")
public class Assessment {

    public static final String COMMENT_KEY = "7Comment";

    private @Id String id;
    private String name;
    private Map<String, Map<String, Integer>> input = new TreeMap<>();
    private Map<String, Map<String, Integer>> result = new TreeMap<>();
    private Map<String, String> error;

    public Assessment() {
        // empty constructor
    }

    public Assessment(String id) {
    	this.id = id;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
	
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Map<String, Map<String, Integer>> getResult() {
		return result;
	}

	public void setResult(Map<String, Map<String, Integer>> result) {
		this.result = result;
	}
        
    public Map<String, Integer> getResult(final String prefix) {
    	if (result != null && result.containsKey(prefix))
    		return result.get(prefix);
    	return null;
    }

    public Object getResult(final String prefix, final String key) {
    	if (getResult(prefix) != null)
    		return getResult(prefix).get(key);
    	return null;
    }
    
    public void setResult(final String prefix, final String key, Integer value) {
		if (result == null)
			result = new TreeMap<>();
		if (!result.containsKey(prefix))
			result.put(prefix,  new TreeMap<>());
		Map<String, Integer> map = result.get(prefix);
		map.put(key, value);
    }
        
	public Map<String, String> getError() {
		return error;
	}

	public void setError(Map<String, String> error) {
		this.error = error;
	}    
   
    public boolean hasError(final String prefix) {
        return error != null && error.containsKey(prefix) && !error.get(prefix).isEmpty();
    }

	public String getError(final String prefix) {
    	if (error != null && error.containsKey(prefix))
    		return error.get(prefix);
    	return null;
    }

	public void setError(final String prefix, final String value) {
		if (error == null)
			error = new TreeMap<>();
		error.put(prefix,  value);
	}

	public void resetError(final String prefix) {
		if (error != null) {
			if (error.containsKey(prefix))
				error.remove(prefix);
			if (error.isEmpty())
				error = null;
		}
	}
	
    public void appendError(final String prefix, String message) {
        if (message == null || message.isEmpty())
            return;
        if (Character.isDigit(message.charAt(0)))
            message = message.toString().substring(1);
        setError(prefix, getError(prefix) == null ? message
                : MessageFormat.format("{0}; {1}", getError(prefix), message));
    }
	
	public Map<String, Map<String, Integer>> getInput() {
		return input;
	}

	public void setInput(Map<String, Map<String, Integer>> input) {
		this.input = input;
	}

	public void setInput(final String prefix, final String key, final Integer value) {
		if (input == null)
			input = new TreeMap<>();
		if (!input.containsKey(prefix))
			input.put(prefix,  new TreeMap<>());
		Map<String, Integer> map = input.get(prefix);
		map.put(key, value);
	}

	public Integer getInput(final String prefix, final String key) {
		if (input != null && input.containsKey(prefix) && input.get(prefix).containsKey(key))
			return input.get(prefix).get(key);
		return null;
	}
	
}
