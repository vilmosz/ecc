package uk.ac.london.assignment.controllers;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;

import uk.ac.london.assignment.model.Assessment;
import uk.ac.london.assignment.repository.AssessmentRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({ "test" })
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class FileUploadControllerTest {

    private static final String ZIP_FILE = "cw.zip";
    private static final String UPLOAD_ENDPOINT = "/api/upload/cw1";

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    AssessmentRepository repo;

    @Before
    public void setUp() {
        repo.save(new Assessment("000000001"));
        repo.save(new Assessment("000000002"));
        repo.save(new Assessment("012345678"));
    }

    @After
    public void tearDown() {
        repo.delete("000000001");
        repo.delete("000000002");
        repo.delete("012345678");
    }

    @Test
    public void uploadTest_zip() {
        final LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", new ClassPathResource(ZIP_FILE));
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        final HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
        final ResponseEntity<String> response = restTemplate.exchange(UPLOAD_ENDPOINT, HttpMethod.POST, requestEntity, String.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

}
