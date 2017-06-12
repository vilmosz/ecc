package uk.ac.london.assignment.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.ClassPathResource;

import com.google.common.collect.ImmutableMap;

import uk.ac.london.assignment.QueryConfiguration;
import uk.ac.london.assignment.model.Assessment;
import uk.ac.london.assignment.repository.AssessmentRepository;

public class UploadServiceTest {

    public static final String GOOD_JSON	= "MARK ZUCKERBERG_000000001_assignsubmission_file_MarkZuckerberg_123456789_CO3326_cw1.json";
    public static final String BAD_JSON		= "MARK ZUCKERBERG_000000002_assignsubmission_file_MarkZuckerberg_012345678_CO3326_cw2.json";

    private UploadService uploadService;

    @Before
    public void setUp() {
        final AssessmentRepository assessmentRepository = mock(AssessmentRepository.class);
        final QueryConfiguration queryConfiguration = mock(QueryConfiguration.class);
        final ApplicationEventPublisher eventPublisher = mock(ApplicationEventPublisher.class);
        when(queryConfiguration.getQueries(any(String.class))).thenReturn(createQueries());
        when(assessmentRepository.findOne(any(String.class))).thenReturn(new Assessment());
        when(assessmentRepository.save(any(Assessment.class))).thenAnswer(new Answer<Assessment>() {
            @Override
            public Assessment answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return (Assessment) args[0];
            }
        });
        uploadService = new UploadService(assessmentRepository, queryConfiguration, eventPublisher);
    }

    @Test
    public void loadJsonTest_GoodJson() throws IOException {
        File file = (new ClassPathResource(GOOD_JSON)).getFile();
        String content = FileUtils.readFileToString(file, "UTF-8");
        Assessment assessment = uploadService.loadJson(content, GOOD_JSON, "cw1");
        assertNotNull(assessment);
    }

    @Test
    public void loadJsonTest_BadJson() throws IOException {
        File file = (new ClassPathResource(BAD_JSON)).getFile();
        String content = FileUtils.readFileToString(file, "UTF-8");
        Assessment assessment = uploadService.loadJson(content, BAD_JSON, "cw1");
        assertNotNull(assessment);
    }

    static Map<String, String> createQueries() {
        return ImmutableMap.<String, String>builder()
                .put("a", "$.ecc.a")
                .put("b", "$.ecc.b")
                .put("k", "$.ecc.k")
                .put("order", "$.ecc.order")
                .put("px", "$.assignment.modk-add.p.x")
                .put("py", "$.assignment.modk-add.p.y")
                .put("qx", "$.assignment.modk-add.q.x")
                .put("qy", "$.assignment.modk-add.q.y")
                .put("rx", "$.assignment.modk-add.r.x")
                .put("ry", "$.assignment.modk-add.r.y")
                .put("sx", "$.assignment.modk-mul.s.x")
                .put("sy", "$.assignment.modk-mul.s.y")
                .build();
    }

}
