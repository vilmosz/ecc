package uk.ac.london.assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import uk.ac.london.assignment.repository.StudentRepository;

@Component
public class DummyDataLoader implements CommandLineRunner {

	private final StudentRepository repository;

	@Autowired
	public DummyDataLoader(StudentRepository repository) {
		this.repository = repository;
	}

	@Override
	public void run(String... strings) throws Exception {
		repository.deleteAll();
	}
}
