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
public class Cw2Service extends AbstractCwService {
    
    private static final Logger LOG = LoggerFactory.getLogger(Cw2Service.class);

    public static final String PREFIX = "cw2";

    private Map<String, String> cw2;

    public Cw2Service(AssessmentRepository assessmentRepository) {
    	super(assessmentRepository);
    }

    @Override
    public void assess(final Assessment assessment, final String prefix) {
    	super.assess(assessment, prefix);
    	check(assessment, prefix, o -> assessment.getInput(prefix, "da") > 10 && assessment.getInput(prefix, "da") < assessment.getInput(REFERENCE, "order") , "da");
    	check(assessment, prefix, o -> assessment.getInput(prefix, "db") > 10 && assessment.getInput(prefix, "db") < assessment.getInput(REFERENCE, "order") , "db");
        assessmentRepository.save(assessment);
    }
    
    @Override
    public List<String> getHeader() {
    	if (header == null)
    		header = cw2.keySet().stream().collect(Collectors.toList()); 
        return header;
    }

    public Map<String, String> getCw2() {
    	return cw2;
    }

    public void setCw2(Map<String, String> cw2) {
    	this.cw2 = cw2;
    }
    
    @EventListener
    void handleStudentEvent(AssessmentEvent event) {
    	if (!Objects.equal(PREFIX, event.getPrefix()))
    		return;
        Assessment assessment = (Assessment) event.getSource();
        LOG.info("[{}] : {}", event.getPrefix(), assessment.getId());

        // get parameters
		Long a = assessment.getInput(REFERENCE, "a").longValue();
		Long b = assessment.getInput(REFERENCE, "b").longValue();
		Long k = assessment.getInput(REFERENCE, "k").longValue();
		
		Ecc ecc = new Ecc(a, b, k);
		
		// compute order
		assessment.setInput(REFERENCE, "order", ecc.getOrder().intValue());

		Integer gx = assessment.getInput(REFERENCE, "px");
		Integer gy = assessment.getInput(REFERENCE, "py");
		assessment.setInput(REFERENCE, "gx", gx);
		assessment.setInput(REFERENCE, "gy", gy);

		// private keys
		Integer da = assessment.getInput(PREFIX, "da");
		Integer db = assessment.getInput(PREFIX, "db");
		assessment.setInput(REFERENCE, "da", da);
		assessment.setInput(REFERENCE, "db", db);
		
		Point g = new Point(gx, gy);
		
		// Alice's key
		Point qa = Ecc.multiplyPoint(g, da, ecc);
		assessment.setInput(REFERENCE, "qax", qa.x);
		assessment.setInput(REFERENCE, "qay", qa.y);

		// Bob's key
		Point qb = Ecc.multiplyPoint(g, db, ecc);
		assessment.setInput(REFERENCE, "qbx", qb.x);
		assessment.setInput(REFERENCE, "qby", qb.y);
		
		// Shared key
		Point alice	= Ecc.multiplyPoint(qb, da, ecc);
		Point bob	= Ecc.multiplyPoint(qa, db, ecc);

		if (!Objects.equal(alice, bob))
			throw new IllegalArgumentException("Calculations are wrong");
		
		assessment.setInput(REFERENCE, "keyx", bob.x);
		assessment.setInput(REFERENCE, "keyy", bob.y);
		
    	assess(assessment, PREFIX);
    }

}
