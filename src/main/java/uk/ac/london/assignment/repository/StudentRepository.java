package uk.ac.london.assignment.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import uk.ac.london.assignment.model.Student;

@RepositoryRestResource
public interface StudentRepository extends MongoRepository<Student, String> {

	public Student findByFirstName(String firstName);

	public List<Student> findByLastName(String lastName);

}

