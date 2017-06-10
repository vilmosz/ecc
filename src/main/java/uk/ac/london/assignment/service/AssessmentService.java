package uk.ac.london.assignment.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;

import uk.ac.london.assignment.model.Assessment;
import uk.ac.london.assignment.repository.AssessmentRepository;

@Service
@ConfigurationProperties(prefix = "uolia.cw.queries")
public class AssessmentService {

    private static final Logger LOG = LoggerFactory.getLogger(AssessmentService.class);
    private static final String COMMENT = "{0} [expected={1},actual={2}]";
    
    public static final String PREFIX = "cw1";

    private final AssessmentRepository assessmentRepository;    
    private Map<String, String> cw1;
    private List<String> header;

    @Autowired
    public AssessmentService(AssessmentRepository assessmentRepository) {
        this.assessmentRepository = assessmentRepository;
    }

    /**
     * 0: "a", 1: "b", 2: "k", 3: "order", 4: "px", 5: "py", 6: "qx", 7: "qy", 8: "rx", 9: "ry", 10: "sx", 11: "sy"
     * 
     * @param assessment
     */
    public void assess(final Assessment assessment) {
    	assessment.setError(PREFIX, null);
        match(assessment, PREFIX, o -> assessment.getInput("csv", "a"), o -> assessment.getInput("cw1", "a"), getHeader(0));
        match(assessment, PREFIX, o -> assessment.getInput("csv", "b"), o -> assessment.getInput("cw1", "b"), getHeader(1));
        match(assessment, PREFIX, o -> assessment.getInput("csv", "k"), o -> assessment.getInput("cw1", "k"), getHeader(2));
        match(assessment, PREFIX, o -> assessment.getInput("csv", "order"), o -> assessment.getInput("cw1", "order"), getHeader(3));
        match(assessment, PREFIX, o -> assessment.getInput("csv", "px"), o -> assessment.getInput("cw1", "px"), getHeader(4));
        match(assessment, PREFIX, o -> assessment.getInput("csv", "py"), o -> assessment.getInput("cw1", "py"), getHeader(5));
        match(assessment, PREFIX, o -> assessment.getInput("csv", "qx"), o -> assessment.getInput("cw1", "qx"), getHeader(6));
        match(assessment, PREFIX, o -> assessment.getInput("csv", "qy"), o -> assessment.getInput("cw1", "qy"), getHeader(7));
        match(assessment, PREFIX, o -> assessment.getInput("csv", "rx"), o -> assessment.getInput("cw1", "rx"), getHeader(8));
        match(assessment, PREFIX, o -> assessment.getInput("csv", "ry"), o -> assessment.getInput("cw1", "ry"), getHeader(9));
        match(assessment, PREFIX, o -> assessment.getInput("csv", "sx"), o -> assessment.getInput("cw1", "sx"), getHeader(10));
        match(assessment, PREFIX, o -> assessment.getInput("csv", "sy"), o -> assessment.getInput("cw1", "sy"), getHeader(11));
        assessmentRepository.save(assessment);
    }

    public String getHeader(final int i) {
    	if (header == null)
    		header = cw1.keySet().stream().collect(Collectors.toList()); 
        return header.get(i);
    }

    public Map<String, String> getCw1() {
    	return cw1;
    }

    public void setCw1(Map<String, String> cw1) {
    	this.cw1 = cw1;
    }
    
    private void match(final Assessment assessment, final String prefix, Function<Assessment, Object> expected, Function<Assessment, Object> actual, String key) {
        try {
        	LOG.info("match: {}", key);
            boolean match = Objects.equal(expected.apply(assessment), actual.apply(assessment));
            assessment.setResult(prefix, key,  match ? 1 : 0);
            assessment.appendError(prefix, match ? null : MessageFormat.format(COMMENT, key, expected.apply(assessment), actual.apply(assessment)));
        } catch (NullPointerException e) {
            LOG.trace("{}", e);
            assessment.setResult(prefix, key, 0);
            assessment.appendError(prefix, MessageFormat.format(COMMENT, key, expected.apply(assessment), null));
        }
    }

    @EventListener
    void handleStudentEvent(AssessmentEvent event) {
    	if (!Objects.equal(PREFIX, event.getPrefix()));
        Assessment assessment = (Assessment) event.getSource();
        LOG.info("[{}] : {}", event.getPrefix(), assessment.getId());
    	assess(assessment);
    }

}
