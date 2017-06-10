package uk.ac.london.assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import uk.ac.london.assignment.repository.AssessmentRepository;

@Component
public class DummyDataLoader implements CommandLineRunner {

	private final AssessmentRepository repository;

	@Autowired
	public DummyDataLoader(AssessmentRepository repository) {
		this.repository = repository;
	}

	@Override
	public void run(String... strings) throws Exception {
		repository.deleteAll();
	}
	
}
