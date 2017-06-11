package uk.ac.london.assignment.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Objects;

import uk.ac.london.assignment.model.Assessment;
import uk.ac.london.assignment.repository.AssessmentRepository;

public abstract class AbstractCwService {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractCwService.class);
    public static final String COMMENT = "{0} [expected={1},actual={2}]";
    
    protected final AssessmentRepository assessmentRepository;  
    protected List<String> header;    

    public AbstractCwService(AssessmentRepository assessmentRepository) {
        this.assessmentRepository = assessmentRepository;
    }
    
    public void match(final Assessment assessment, final String prefix, Function<Assessment, Object> expected, Function<Assessment, Object> actual, String key) {
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
    
}
