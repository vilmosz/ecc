package uk.ac.london.assignment.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;

import uk.ac.london.assignment.model.Assessment;
import uk.ac.london.assignment.model.Cw1;
import uk.ac.london.assignment.model.Exercise;
import uk.ac.london.assignment.model.Exercise.Type;
import uk.ac.london.assignment.model.Student;
import uk.ac.london.assignment.repository.Cw1Repository;
import uk.ac.london.assignment.repository.Cw2Repository;

@Service
public class AssessmentService {

    private static final Logger LOG = LoggerFactory.getLogger(AssessmentService.class);
    private static final String COMMENT = "{0} [expected={1},actual={2}]";

    private final Cw1Repository cw1Repository;
    private final Cw2Repository cw2Repository;

    @Autowired
    public AssessmentService(
            Cw1Repository cw1Repository,
            Cw2Repository cw2Repository) {
        this.cw1Repository = cw1Repository;
        this.cw2Repository = cw2Repository;
    }

    public void assess(final Assessment assessment) {
        assessment.resetResults();
        if (assessment.hasError())
            return;
        match(assessment, o -> assessment.getExpected().getEcc().getA(), o -> assessment.getActual().getEcc().getA(), Assessment.A_KEY);
        match(assessment, o -> assessment.getExpected().getEcc().getB(), o -> assessment.getActual().getEcc().getB(), Assessment.B_KEY);
        match(assessment, o -> assessment.getExpected().getEcc().getK(), o -> assessment.getActual().getEcc().getK(), Assessment.K_KEY);
        match(assessment, o -> assessment.getExpected().getEcc().getOrder(), o -> assessment.getActual().getEcc().getOrder(), Assessment.ORDER_KEY);
        match(assessment, o -> ((Exercise)assessment.getExpected().getAssignment().get(Type.MODK_ADD.toString())).getR(), o -> ((Exercise)assessment.getExpected().getAssignment().get(Type.MODK_ADD.toString())).getR(), Assessment.R_KEY);
        match(assessment, o -> ((Exercise)assessment.getExpected().getAssignment().get(Type.MODK_MUL.toString())).getR(), o -> ((Exercise)assessment.getExpected().getAssignment().get(Type.MODK_MUL.toString())).getR(), Assessment.S_KEY);
    }

    public List<String> getHeaders() {
        return Assessment.getHeaders().stream().map(k -> k.replaceAll("^[0-9]*", "")).collect(Collectors.toList());
    }

    private void match(final Assessment assessment, Function<Assessment, Object> expected, Function<Assessment, Object> actual, String key) {
        try {
            boolean match = Objects.equal(expected.apply(assessment), actual.apply(assessment));
            assessment.addResult(key,  match ? 1 : 0);
            assessment.addComment(match ? null : MessageFormat.format(COMMENT, key, expected.apply(assessment), actual.apply(assessment)));
        } catch (NullPointerException e) {
            LOG.trace("{}", e);
            assessment.addResult(key, 0);
            assessment.addComment(MessageFormat.format(COMMENT, key, expected.apply(assessment), null));
        }
    }

    @EventListener
    void handleStudentEvent(StudentEvent event) {
        final Student student = (Student) event.getSource();
        switch (event.getType()) {
        case ACTUAL:
            LOG.info("Actual submission: {}", student);
            // update assessment
            if (cw1Repository.exists(student.getId())) {
                Cw1 assessment = cw1Repository.findOne(student.getId());
                assessment.setActual(student);
                assessment.setError(event.getError());
                assess(assessment);
                cw1Repository.save(assessment);
            } else {
                LOG.warn("{} is unrecognized", student);
            }
            break;
        case EXPECTED:
            LOG.info("Expected submission: {}", student);
            // create assessment
            Cw1 assessment = new Cw1();
            assessment.setExpected(student);
            assessment.setId(student.getId());
            assessment.setName(student.getName());
            assessment.resetResults();
            assessment.addComment("No submission");
            cw1Repository.save(assessment);
            break;
        default:
            break;
        }
    }

}
