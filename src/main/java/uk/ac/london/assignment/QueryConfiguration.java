package uk.ac.london.assignment;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Maps;

@Configuration
@ConfigurationProperties(prefix = "uolia.cw")
public class QueryConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(QueryConfiguration.class);

    private Map<String, Map<String, String>> queries;

    public QueryConfiguration() {
        LOG.debug("Loding query properties");
    }

    public Map<String, Map<String, String>> getQueries() {
        return queries;
    }

    public void setQueries(Map<String, Map<String, String>> queries) {
        this.queries = queries;
    }

    public Map<String, String> getQueries(final String prefix) {
        if (queries != null && queries.containsKey(prefix))
            return queries.get(prefix);
        return Maps.newTreeMap();
    }

}
