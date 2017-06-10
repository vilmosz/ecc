package uk.ac.london.assignment.model;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.collect.ImmutableList;

@Document(collection = "Assessment")
public class Assessment {

    public static final String A_KEY = "1a";
    public static final String B_KEY = "2b";
    public static final String K_KEY = "3k";
    public static final String ORDER_KEY = "4order";
    public static final String R_KEY = "5R";
    public static final String S_KEY = "6S";
    public static final String COMMENT_KEY = "7Comment";

    private @Id String id;
    private String name;
    private Map<String, Map<String, Integer>> input;
    private Map<String, Map<String, Object>> result;
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

    public String getName() {
        return name;
    }

    public Map<String, Object> getResult(final String prefix) {
    	if (result != null && result.containsKey(prefix))
    		return result.get(prefix);
    	return null;
    }

    public Object getResult(final String prefix, final String key) {
    	if (getResult(prefix) != null)
    		return getResult(prefix).get(key);
    	return null;
    }
    
    public void addResult(final String prefix, final String key, Object value) {
		if (result == null)
			result = new TreeMap<>();
		if (!result.containsKey(prefix))
			result.put(prefix,  new TreeMap<>());
		Map<String, Object> map = result.get(prefix);
		map.put(key, value);
    }

    public void appendResult(final String prefix, final String key, Object value) {
        if (value == null || value.toString() == null|| value.toString().isEmpty())
            return;
        if (Character.isDigit(value.toString().charAt(0)))
            value = value.toString().substring(1);
        addResult(prefix, key, getResult(prefix, key) == null ? value
                : MessageFormat.format("{0}; {1}", getResult(prefix, key), value));
    }
    
    public void addComment(final String prefix, final String comment) {
    	appendResult(prefix, COMMENT_KEY, comment);
    }

    public void resetResult(final String prefix) {
    	if (getResult(prefix) != null)
    		getResult(prefix).clear();
        getHeaders().stream().forEach(key -> result.put(key, null));
        if (hasError(prefix)) {
            addComment(prefix, getError(prefix));
        }
    }
    
    public String getError(final String prefix) {
    	if (error != null && error.containsKey(prefix))
    		return error.get(prefix);
    	return null;
    }

    public boolean hasError(final String prefix) {
        return error != null && error.containsKey(prefix) && !error.get(prefix).isEmpty();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Map<String, Map<String, Object>> getResult() {
		return result;
	}

	public void setResult(Map<String, Map<String, Object>> result) {
		this.result = result;
	}

	public Map<String, String> getError() {
		return error;
	}

	public void setError(Map<String, String> error) {
		this.error = error;
	}

	public void setError(final String prefix, final String value) {
		if (error == null)
			error = new TreeMap<>();
		error.put(prefix,  value);
	}

	public static List<String> getHeaders() {
        return ImmutableList.<String> builder()
                .add(A_KEY)
                .add(B_KEY)
                .add(K_KEY)
                .add(ORDER_KEY)
                .add(R_KEY)
                .add(S_KEY)
                .add(COMMENT_KEY)
                .build();
    }

	public Map<String, Map<String, Integer>> getInput() {
		return input;
	}

	public void setInput(Map<String, Map<String, Integer>> input) {
		this.input = input;
	}

	public void addInput(final String prefix, final String key, final Integer value) {
		if (input == null)
			input = new TreeMap<>();
		if (!input.containsKey(prefix))
			input.put(prefix,  new TreeMap<>());
		Map<String, Integer> map = input.get(prefix);
		map.put(key, value);
	}

	public Object getInput(final String prefix, final String key) {
		if (input != null && input.containsKey(prefix) && input.get(prefix).containsKey(key))
			return input.get(prefix).get(key);
		return null;
	}
	
}
