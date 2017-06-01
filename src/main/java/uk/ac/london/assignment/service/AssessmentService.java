package uk.ac.london.assignment.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import uk.ac.london.assignment.model.Assessment;
import uk.ac.london.assignment.model.Student;
import uk.ac.london.assignment.repository.AssessmentRepository;

@Service
public class AssessmentService {

    private static final Logger logger = LoggerFactory.getLogger(AssessmentService.class);

    @Autowired
    private AssessmentRepository repository;

    public Assessment assess(final InputStream input) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        Student student = mapper.readValue(input, Student.class);

        Assessment assessment = new Assessment(student);
        assessment = repository.save(assessment);

        logger.debug("Assess: {}", assessment);
        return assessment;
    }

    public Assessment assess(final String filename) throws IOException {
        File file = new File(filename);
        Resource resource = null;
        if (file.exists() && file.canRead())
        	resource = new PathResource(file.toURI());
        else        	
            resource = new ClassPathResource(filename);
        if (resource.exists() && resource.isReadable()) {
            return assess(resource.getInputStream());
        } else {
            logger.warn("Can't open [{}]", filename);
            throw new IOException("Can't open file: " + filename);
        }
    }

}
