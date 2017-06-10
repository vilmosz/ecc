package uk.ac.london.assignment.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import uk.ac.london.assignment.model.Assessment;

@RepositoryRestResource
public interface AssessmentRepository extends MongoRepository<Assessment, String> {

    public List<Assessment> findByName(@Param("s") String s);

    @Query(value="{'ecc.a': ?0, '$and' : [{'ecc.b' : ?1}]}")
    public List<Assessment> findByCurve(@Param("a") Long a, @Param("b") Long b);

}
