package uk.ac.london.assignment;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "uolia.cw")
public class QueryConfiguration {
	
    private static final Logger LOG = LoggerFactory.getLogger(QueryConfiguration.class);
	
	private Map<String, Map<String, String>> queries;
	private String test;
	
	public QueryConfiguration() {
		LOG.info("Loding query properties... {}", test);
	}
		
	public Map<String, Map<String, String>> getQueries() {
		return queries;
	}

	public void setQueries(Map<String, Map<String, String>> queries) {
		this.queries = queries;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}
		
}
