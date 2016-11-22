package uk.ac.london.assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;

import uk.ac.london.assignment.model.Exercise;
import uk.ac.london.assignment.model.ModkAdd;
import uk.ac.london.assignment.model.Student;
import uk.ac.london.assignment.repository.StudentRepository;
import uk.ac.london.ecc.Ecc;

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
		Student s1 = new Student("Vilmos", "Zsombori");
		s1.setEcc(new Ecc(2L, 3L, 101L));
		s1.setAssignment(ImmutableList.<Exercise>builder().add(new ModkAdd()).build());
		repository.save(s1);
	}
}
