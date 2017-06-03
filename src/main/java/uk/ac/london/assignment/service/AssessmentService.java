package uk.ac.london.assignment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import uk.ac.london.assignment.model.Assessment;
import uk.ac.london.assignment.model.Student;
import uk.ac.london.assignment.repository.AssessmentRepository;

@Service
public class AssessmentService {

    private static final Logger LOG = LoggerFactory.getLogger(AssessmentService.class);

    @Autowired
    private AssessmentRepository repository;
    
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
