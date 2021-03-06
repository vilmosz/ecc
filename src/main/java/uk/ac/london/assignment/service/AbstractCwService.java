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
    public static final String REFERENCE = "csv";
    
    protected final AssessmentRepository assessmentRepository;  
    protected List<String> header;    

    public AbstractCwService(AssessmentRepository assessmentRepository) {
        this.assessmentRepository = assessmentRepository;
    }
    
    public abstract List<String> getHeader();

    public void assess(final Assessment assessment, final String prefix) {
    	assessment.resetError(prefix);
    	getHeader()
    		.stream()
    		.forEach(header -> match(
    							assessment, 
    							prefix, 
    							o -> assessment.getInput(REFERENCE, header), 
    							o -> assessment.getInput(prefix, header), 
    							header));
        assessmentRepository.save(assessment);
    }
    
    public void match(final Assessment assessment, final String prefix, Function<Assessment, Object> expected, Function<Assessment, Object> actual, String key) {
        try {
        	LOG.debug("match: {}", key);
            boolean match = Objects.equal(expected.apply(assessment), actual.apply(assessment));
            assessment.setResult(prefix, key,  match ? 1 : 0);
            assessment.appendError(prefix, match ? null : MessageFormat.format(COMMENT, key, expected.apply(assessment), actual.apply(assessment)));
        } catch (NullPointerException e) {
            LOG.trace("{}", e);
            assessment.setResult(prefix, key, 0);
            assessment.appendError(prefix, MessageFormat.format(COMMENT, key, expected.apply(assessment), null));
        }
    }

    public void check(final Assessment assessment, final String prefix, Function<Assessment, Boolean> check, String key) {
        try {
        	LOG.debug("check: {}", key);
            boolean res = check.apply(assessment);
            assessment.setResult(prefix, key,  res ? 1 : 0);
            assessment.appendError(prefix, res ? null : MessageFormat.format("{0} [out of expected range]", key));
        } catch (NullPointerException e) {
            LOG.trace("{}", e);
            assessment.setResult(prefix, key, 0);
            assessment.appendError(prefix, MessageFormat.format("{0} [out of expected range]", key));
        }
    }
    
}
