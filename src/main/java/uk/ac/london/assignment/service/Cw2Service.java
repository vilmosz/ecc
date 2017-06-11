package uk.ac.london.assignment.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;

import uk.ac.london.assignment.model.Assessment;
import uk.ac.london.assignment.repository.AssessmentRepository;

@Service
@ConfigurationProperties(prefix = "uolia.cw.queries")
public class Cw2Service extends AbstractCwService {
    
    private static final Logger LOG = LoggerFactory.getLogger(Cw2Service.class);

    public static final String PREFIX = "cw2";

    private Map<String, String> cw2;
    private List<String> header;

    public Cw2Service(AssessmentRepository assessmentRepository) {
    	super(assessmentRepository);
    }

    /**
     * 0: "a", 1: "b", 2: "k", 3: "gx", 4: "gy", 5: "order", 6: "da", 
     * 7: "qax", 8: "qay", 9: "db", 10: "qbx", 11: "qby", 12: "keyx", 13: "keyy"
     * 
     * @param assessment
     */
    public void assess(final Assessment assessment) {
    	assessment.setError(PREFIX, null);
        match(assessment, PREFIX, o -> assessment.getInput("csv", "a"), o -> assessment.getInput(PREFIX, "a"), getHeader(0));
        match(assessment, PREFIX, o -> assessment.getInput("csv", "b"), o -> assessment.getInput(PREFIX, "b"), getHeader(1));
        match(assessment, PREFIX, o -> assessment.getInput("csv", "k"), o -> assessment.getInput(PREFIX, "k"), getHeader(2));
        match(assessment, PREFIX, o -> assessment.getInput("csv", "order"), o -> assessment.getInput(PREFIX, "order"), getHeader(5));
        match(assessment, PREFIX, o -> assessment.getInput("csv", "px"), o -> assessment.getInput(PREFIX, "gx"), getHeader(3));
        match(assessment, PREFIX, o -> assessment.getInput("csv", "py"), o -> assessment.getInput(PREFIX, "gy"), getHeader(4));
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
    		header = cw2.keySet().stream().collect(Collectors.toList()); 
        return header.get(i);
    }

    public Map<String, String> getCw2() {
    	return cw2;
    }

    public void setCw2(Map<String, String> cw2) {
    	this.cw2 = cw2;
    }
    
    @EventListener
    void handleStudentEvent(AssessmentEvent event) {
    	if (!Objects.equal(PREFIX, event.getPrefix()))
    		return;
        Assessment assessment = (Assessment) event.getSource();
        LOG.info("[{}] : {}", event.getPrefix(), assessment.getId());
    	assess(assessment);
    }

}
