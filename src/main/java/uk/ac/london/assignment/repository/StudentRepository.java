package uk.ac.london.assignment.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import uk.ac.london.assignment.model.Student;

@RepositoryRestResource
public interface StudentRepository extends MongoRepository<Student, String> {

	public List<Student> findByFirstName(@Param("s") String s);

	public List<Student> findByLastName(@Param("s") String lastName);
	
	@Query(value="{'ecc.a': ?0, '$and' : [{'ecc.b' : ?1}]}")
	public List<Student> findByCurve(@Param("a") Long a, @Param("b") Long b);

}

