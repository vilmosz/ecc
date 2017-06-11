package uk.ac.london.assignment.service;

import java.awt.Point;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.jayway.jsonpath.InvalidJsonException;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

import uk.ac.london.assignment.QueryConfiguration;
import uk.ac.london.assignment.model.Assessment;
import uk.ac.london.assignment.repository.AssessmentRepository;
import uk.ac.london.ecc.Ecc;

@Service
public class UploadService {

	private static final Logger logger = LoggerFactory.getLogger(UploadService.class);

	private static final Pattern pattern = Pattern.compile("^([a-zA-Z ]+)_([0-9]+)_.*_([0-9]+)_(?:CO|cw).*");

	private final AssessmentRepository assessmentRepository;
	private final ApplicationEventPublisher eventPublisher;

	@Autowired
	private QueryConfiguration queryConfiguration;

	@Autowired
	public UploadService(AssessmentRepository assessmentRepository, ApplicationEventPublisher eventPublisher) {
		this.assessmentRepository = assessmentRepository;
		this.eventPublisher = eventPublisher;
	}

	public Assessment loadJson(final String content, final String filename, final String prefix) throws IOException {
		String error = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			logger.info("Load {} / {}", prefix, filename);
			try {
				final String id = parseString(content, "$.srn");
				final Assessment assessment = load(id);
				assessment.setName(JsonPath.read(content, "$.name"));
				queryConfiguration.getQueries()
					.get(prefix)
					.entrySet()
					.stream()
					.forEach(e -> {
						logger.info("{}.{} = {}", prefix, e.getKey(), parseInt(content, e.getValue()));
						assessment.setInput(prefix, e.getKey(), parseInt(content, e.getValue()));
					});
				eventPublisher.publishEvent(new AssessmentEvent(assessment, prefix, error));
				return assessmentRepository.save(assessment);
			} catch (InvalidJsonException je) {
				logger.trace("{}", je);
				mapper.readValue(content, Object.class);
				throw new RuntimeException("Should never get here: " + filename);
			}
		} catch (JsonParseException | JsonMappingException | PathNotFoundException e) {
			Assessment assessment = parseFilename(filename, prefix);
			assessment.setError(prefix, e.getMessage());
			logger.info("{} / {}", filename, error);
			return assessmentRepository.save(assessment);
		}
	}

	private Assessment load(final String id) {
		Assessment assessment = assessmentRepository.findOne(id.trim());
		if (assessment != null)
			return assessment;
		throw new IllegalArgumentException(String.format("Unrecognized SRN: [%s]", id));
	}

	private final Integer parseInt(final String content, final String jsonPath) {
		try {
			return JsonPath.read(content, jsonPath);
		} catch (ClassCastException e) {
			logger.trace("{}", e);
			return Integer.valueOf(JsonPath.read(content, jsonPath));
		}
	}

	private String parseString(final String content, final String jsonPath) {
		try {
			return JsonPath.read(content, jsonPath);
		} catch (ClassCastException e) {
			logger.trace("{}", e);
			return parseInt(content, jsonPath).toString();
		}
	}

	private Assessment parseFilename(final String filename, final String prefix) {
		logger.info("Parse filename [{}]", filename);
		Matcher m = pattern.matcher(filename);
		if (m.find()) {
			String id = m.group(3);
			return load(id);
		}
		throw new IllegalArgumentException(String.format("Unexpected filname format: [%s]", filename));
	}

	public List<Assessment> loadCsv(final InputStream inputStream, final String prefix) throws IOException {
		CsvSchema schema = CsvSchema.emptySchema().withHeader();
		CsvMapper mapper = new CsvMapper();
		MappingIterator<uk.ac.london.assignment.model.csv.Student> reader = mapper
				.readerFor(uk.ac.london.assignment.model.csv.Student.class).with(schema).readValues(inputStream);
		List<Assessment> assesssments = new ArrayList<>();
		while (reader.hasNextValue()) {
			uk.ac.london.assignment.model.csv.Student csv = reader.nextValue();
			logger.debug("{}", csv);
			Assessment assessment = createAssessment(csv, prefix);
			logger.info("{}: {}", prefix, assessment.getInput());
			assesssments.add(assessment);
		}
		return assesssments;
	}

	private Assessment createAssessment(uk.ac.london.assignment.model.csv.Student csv, final String prefix) {
		Assessment assessment = assessmentRepository.findOne(csv.getStudentCode());
		if (assessment == null) {
			assessment = new Assessment();
			assessment.setId(csv.getStudentCode());
		}
		assessment.setName(csv.getName().trim().replaceAll("\\s+", " "));
		assessment.setInput(prefix, "a", csv.getA());
		assessment.setInput(prefix, "b", csv.getB());
		assessment.setInput(prefix, "k", csv.getK());
		assessment.setInput(prefix, "order", csv.getOrder());
		assessment.setInput(prefix, "px", csv.getPx());
		assessment.setInput(prefix, "py", csv.getPy());
		assessment.setInput(prefix, "qx", csv.getQx());
		assessment.setInput(prefix, "qy", csv.getQy());
		assessment.setInput(prefix, "rx", csv.getRx());
		assessment.setInput(prefix, "ry", csv.getRy());
		assessment.setInput(prefix, "n", 3);
		// solve the multiplication
		Point p = new Point(csv.getPx(), csv.getPy());
		Ecc ecc = new Ecc(csv.getA().longValue(), csv.getB().longValue(), csv.getK().longValue(),
				csv.getOrder().longValue());
		Point s = Ecc.multiplyPoint(p, 3, ecc);
		assessment.setInput(prefix, "sx", s.x);
		assessment.setInput(prefix, "sy", s.y);
		return assessmentRepository.save(assessment);
	}

}
