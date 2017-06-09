package uk.ac.london.assignment.model;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.data.annotation.Id;

import com.google.common.collect.ImmutableList;

public abstract class Assessment {

    public static final String A_KEY = "1a";
    public static final String B_KEY = "2b";
    public static final String K_KEY = "3k";
    public static final String ORDER_KEY = "4order";
    public static final String R_KEY = "5R";
    public static final String S_KEY = "6S";
    public static final String COMMENT_KEY = "7Comment";

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
        results.put(COMMENT_KEY, results.get(COMMENT_KEY) == null ? comment
                : MessageFormat.format("{0}; {1}", results.get(COMMENT_KEY), comment));
    }

    public void resetResults() {
        results.clear();
        getHeaders().stream().forEach(key -> results.put(key, null));
        if (hasError()) {
            results.put(COMMENT_KEY, error);
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

}
