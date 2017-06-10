package uk.ac.london.assignment.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;

import uk.ac.london.assignment.model.Assessment;
import uk.ac.london.assignment.repository.AssessmentRepository;

@Service
public class AssessmentService {

    private static final Logger LOG = LoggerFactory.getLogger(AssessmentService.class);
    private static final String COMMENT = "{0} [expected={1},actual={2}]";

    private final AssessmentRepository assessmentRepository;

    @Autowired
    public AssessmentService(AssessmentRepository assessmentRepository) {
        this.assessmentRepository = assessmentRepository;
    }

    public void assess(final Assessment assessment) {
    	final String prefix = "cw1";
        assessment.resetResult(prefix);
        if (assessment.hasError(prefix))
            return;
        match(assessment, prefix, o -> assessment.getInput("csv", "a"), o -> assessment.getInput("cw1", "a"), Assessment.A_KEY);
        match(assessment, prefix, o -> assessment.getInput("csv", "b"), o -> assessment.getInput("cw1", "b"), Assessment.B_KEY);
        match(assessment, prefix, o -> assessment.getInput("csv", "k"), o -> assessment.getInput("cw1", "k"), Assessment.K_KEY);
        match(assessment, prefix, o -> assessment.getInput("csv", "order"), o -> assessment.getInput("cw1", "order"), Assessment.ORDER_KEY);
        match(assessment, prefix, o -> assessment.getInput("csv", "rx"), o -> assessment.getInput("cw1", "rx"), Assessment.R_KEY);
        match(assessment, prefix, o -> assessment.getInput("csv", "ry"), o -> assessment.getInput("cw1", "ry"), Assessment.R_KEY);
        match(assessment, prefix, o -> assessment.getInput("csv", "sx"), o -> assessment.getInput("cw1", "sx"), Assessment.S_KEY);
        match(assessment, prefix, o -> assessment.getInput("csv", "sy"), o -> assessment.getInput("cw1", "sy"), Assessment.S_KEY);
        assessmentRepository.save(assessment);
    }

    public List<String> getHeaders() {
        return Assessment.getHeaders().stream().map(k -> k.replaceAll("^[0-9]*", "")).collect(Collectors.toList());
    }

    private void match(final Assessment assessment, final String prefix, Function<Assessment, Object> expected, Function<Assessment, Object> actual, String key) {
        try {
            boolean match = Objects.equal(expected.apply(assessment), actual.apply(assessment));
            assessment.addResult(prefix,key,  match ? 1 : 0);
            assessment.addComment(prefix, match ? null : MessageFormat.format(COMMENT, key, expected.apply(assessment), actual.apply(assessment)));
        } catch (NullPointerException e) {
            LOG.trace("{}", e);
            assessment.addResult(prefix, key, 0);
            assessment.addComment(prefix, MessageFormat.format(COMMENT, key, expected.apply(assessment), null));
        }
    }

    @EventListener
    void handleStudentEvent(AssessmentEvent event) {
        Assessment assessment = (Assessment) event.getSource();
        LOG.debug("[{}] : {}", event.getPrefix(), assessment.getId());
        if (event.getError() != null)
        	LOG.debug("Error [{}]: {}", event.getPrefix(), event.getError());
    }

}
