package uk.ac.london.assignment.service;

import java.awt.Point;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;

import uk.ac.london.assignment.model.Assessment;
import uk.ac.london.assignment.repository.AssessmentRepository;
import uk.ac.london.ecc.Ecc;

@Service
@ConfigurationProperties(prefix = "uolia.cw.queries")
public class Cw1Service extends AbstractCwService {

    private static final Logger LOG = LoggerFactory.getLogger(Cw1Service.class);

    public static final String PREFIX = "cw1";

    private Map<String, String> cw1;

    public Cw1Service(AssessmentRepository assessmentRepository) {
        super(assessmentRepository);
    }

    @Override
    public List<String> getHeader() {
        if (header == null)
            header = cw1.keySet().stream().collect(Collectors.toList());
        return this.header;
    }

    public Map<String, String> getCw1() {
        return cw1;
    }

    public void setCw1(Map<String, String> cw1) {
        this.cw1 = cw1;
    }

    @EventListener
    void handleAssessmentEvent(AssessmentEvent event) {
        if (!Objects.equal(PREFIX, event.getPrefix()))
            return;
        Assessment assessment = (Assessment) event.getSource();
        LOG.info("[{}] : {}", event.getPrefix(), assessment.getId());

        try {
            // get parameters
            Long a = assessment.getInput(REFERENCE, "a").longValue();
            Long b = assessment.getInput(REFERENCE, "b").longValue();
            Long k = assessment.getInput(REFERENCE, "k").longValue();
            Number n = assessment.getInput(REFERENCE, "n");

            Ecc ecc = new Ecc(a, b, k);

            // compute order
            assessment.setInput(REFERENCE, "order", ecc.getOrder().intValue());

            Integer px = assessment.getInput(REFERENCE, "px");
            Integer py = assessment.getInput(REFERENCE, "py");
            Integer qx = assessment.getInput(REFERENCE, "qx");
            Integer qy = assessment.getInput(REFERENCE, "qy");

            Point p = new Point(px, py);
            Point q = new Point(qx, qy);

            // solve the addition
            Point r = Ecc.addPoint(p, q, ecc);
            assessment.setInput(REFERENCE, "rx", r.x);
            assessment.setInput(REFERENCE, "ry", r.y);

            // solve the multiplication
            Point s = Ecc.multiplyPoint(p, n, ecc);
            assessment.setInput(REFERENCE, "sx", s.x);
            assessment.setInput(REFERENCE, "sy", s.y);

            assess(assessment, PREFIX);
        } catch (NullPointerException e) {
            LOG.warn("No reference created for [{}]", assessment.getId());
        }
    }

}
