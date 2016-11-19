package uk.ac.london.assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import uk.ac.london.assignment.model.Student;
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
		Student s1 = new Student("Frodo", "Baggins");
		this.repository.save(s1);
	}
}
