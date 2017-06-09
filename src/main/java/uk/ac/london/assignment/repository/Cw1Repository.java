package uk.ac.london.assignment.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import uk.ac.london.assignment.model.Cw1;

@RepositoryRestResource
public interface Cw1Repository extends MongoRepository<Cw1, String> {

    public List<Cw1> findByName(@Param("s") String s);

    @Query(value="{'ecc.a': ?0, '$and' : [{'ecc.b' : ?1}]}")
    public List<Cw1> findByCurve(@Param("a") Long a, @Param("b") Long b);

}
