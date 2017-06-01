package uk.ac.london.assignment.service;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import uk.ac.london.assignment.model.Assessment;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({ "test" })
public class AssessmentServiceTest {

    public static final String TEST_JSON = "MARK%20ZUCKERBERG_000000001_assignsubmission_file_MarkZuckerberg_123456789_CO3326_cw1.json";

    @Autowired
    AssessmentService assessmentService;

    @Test
    public void assessTest() throws IOException {
        Assessment assesment = assessmentService.assess(TEST_JSON);
        assertNotNull(assesment);
    }

}
