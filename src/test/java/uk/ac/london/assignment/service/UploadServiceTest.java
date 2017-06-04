package uk.ac.london.assignment.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.ClassPathResource;

import uk.ac.london.assignment.model.Student;
import uk.ac.london.assignment.repository.StudentRepository;

public class UploadServiceTest {

    public static final String GOOD_JSON	= "MARK ZUCKERBERG_000000001_assignsubmission_file_MarkZuckerberg_123456789_CO3326_cw1.json";
    public static final String BAD_JSON		= "MARK ZUCKERBERG_000000002_assignsubmission_file_MarkZuckerberg_012345678_CO3326_cw2.json";

    private UploadService uploadService;

    @Before
    public void setUp() {
    	final StudentRepository studentRepository = mock(StudentRepository.class);
    	final ApplicationEventPublisher eventPublisher = mock(ApplicationEventPublisher.class);
    	uploadService = new UploadService(studentRepository, eventPublisher);
    }
    
    @Test
    public void loadJsonTest_GoodJson() throws IOException {
    	File file = (new ClassPathResource(GOOD_JSON)).getFile();
    	String content = FileUtils.readFileToString(file, "UTF-8");
    	Student student = uploadService.loadJson(content, GOOD_JSON);
        assertNotNull(student);
    }

    @Test
    public void loadJsonTest_BadJson() throws IOException {
    	File file = (new ClassPathResource(BAD_JSON)).getFile();
    	String content = FileUtils.readFileToString(file, "UTF-8");
    	Student student = uploadService.loadJson(content, BAD_JSON);
        assertNotNull(student);
    }
    
}