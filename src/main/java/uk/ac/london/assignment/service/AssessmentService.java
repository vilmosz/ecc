package uk.ac.london.assignment.service;

import java.text.MessageFormat;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;

import uk.ac.london.assignment.model.Assessment;
import uk.ac.london.assignment.model.Student;
import uk.ac.london.assignment.repository.AssessmentRepository;

@Service
public class AssessmentService {

    private static final Logger LOG = LoggerFactory.getLogger(AssessmentService.class);
    private static final String COMMENT = "{0} [expected={1},actual={2}]";

    private final AssessmentRepository repository;

    @Autowired
    public AssessmentService(AssessmentRepository repository) {
    	this.repository = repository;    	
    }
    
    public void assess(final Assessment assessment) {
    	assessment.resetComment();
    	match(assessment, o -> assessment.getExpected().getEcc().getA(), o -> assessment.getActual().getEcc().getA(), "a");
    	match(assessment, o -> assessment.getExpected().getEcc().getB(), o -> assessment.getActual().getEcc().getB(), "b");
    	match(assessment, o -> assessment.getExpected().getEcc().getK(), o -> assessment.getActual().getEcc().getK(), "k");
    	match(assessment, o -> assessment.getExpected().getEcc().getOrder(), o -> assessment.getActual().getEcc().getOrder(), "Order");
    }

    private void match(final Assessment assessment, Function<Assessment, Object> expected, Function<Assessment, Object> actual, String key) {
    	try {
    		boolean match = Objects.equal(expected.apply(assessment), actual.apply(assessment));
    		assessment.addResult(key,  match ? 1 : 0);
    		assessment.addComment(match ? null : MessageFormat.format(COMMENT, key, expected.apply(assessment), actual.apply(assessment)));    		
    	} catch (NullPointerException e) {    		
    		assessment.addResult(key, 0);    		
    		assessment.addComment(MessageFormat.format(COMMENT, key, expected.apply(assessment), null));
    	}
    	assessment.addResult("Fla", 5);    	
    }
    
    @EventListener
    void handleStudentEvent(StudentEvent event) {
		final Student student = (Student) event.getSource();
    	switch (event.getType()) {
		case ACTUAL:
	        LOG.info("Actual submission: {}", student);
			// update assessment
			if (repository.exists(student.getId())) {
				Assessment assessment = repository.findOne(student.getId());
				assessment.setActual(student);
				assess(assessment);
				repository.save(assessment);			
			} else {
				LOG.warn("{} is unrecognized", student);
			}			
			break;
		case EXPECTED:
	        LOG.info("Expected submission: {}", student);
			// create assessment
			Assessment assessment = new Assessment();
			assessment.setExpected(student);
			assessment.setId(student.getId());
			assessment.setName(student.getName());
			repository.save(assessment);
			break;
		default:
			break;
		}
    }
    
}
