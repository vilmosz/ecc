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

import uk.ac.london.assignment.model.Exercise;
import uk.ac.london.assignment.model.Exercise.Type;
import uk.ac.london.assignment.model.Student;
import uk.ac.london.assignment.repository.StudentRepository;
import uk.ac.london.ecc.Ecc;

@Service
public class UploadService {

	private static final Logger logger = LoggerFactory.getLogger(UploadService.class);

	private static final Pattern pattern = Pattern.compile("^([a-zA-Z ]+)_([0-9]+)_.*_([0-9]+)_CO.*");

	private final StudentRepository studentRepository;
	private final ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public UploadService(StudentRepository studentRepository, ApplicationEventPublisher eventPublisher) {
		this.studentRepository = studentRepository;
		this.eventPublisher = eventPublisher;
	}
	
	public Student loadJson(final String content, final String filename) {
		Student student = null;
        String error = null;
        ObjectMapper mapper = new ObjectMapper();
		try {
			logger.info("Load {}", filename);
			student = mapper.readValue(content, Student.class);
			student.setFile(filename);
		} catch (JsonParseException | JsonMappingException e) {
		    logger.trace("{}", e);
			student = parseFilename(filename);
			error = e.getMessage();
			logger.trace("{} / {}", filename, e.getMessage());
		} catch (IOException e) {
            logger.error("{} / {}", filename, e);
		}
		studentRepository.save(student);
		eventPublisher.publishEvent(new StudentEvent(student, error, StudentEvent.Type.ACTUAL));
		return student;
	}

	private Student parseFilename(final String filename) {
		logger.info("Parse filename [{}]", filename);
		Student student = new Student();
		student.setFile(filename);
	    Matcher m = pattern.matcher(filename);
	    if (m.find()) {
	    	student.setName(m.group(1));
	    	student.setSrn(m.group(3));
	    }
	    return student;
	}

	public List<Student> loadCsv(final InputStream inputStream) throws IOException {
		CsvSchema schema = CsvSchema.emptySchema().withHeader();
		CsvMapper mapper = new CsvMapper();
		MappingIterator<uk.ac.london.assignment.model.csv.Student> reader = mapper
				.readerFor(uk.ac.london.assignment.model.csv.Student.class).with(schema).readValues(inputStream);
		List<Student> students = new ArrayList<>();
		while (reader.hasNextValue()) {
			uk.ac.london.assignment.model.csv.Student csv = reader.nextValue();
			logger.debug("{}", csv);
			Student student = createStudent(csv);
			students.add(student);
			eventPublisher.publishEvent(new StudentEvent(student, null, StudentEvent.Type.EXPECTED));			
		}
		return students;
	}

	private Student createStudent(uk.ac.london.assignment.model.csv.Student csv) {
		Student student = new Student();
		student.setId(csv.getStudentCode());
		student.setName(csv.getName().trim().replaceAll("\\s+", " "));
		student.setEcc(new Ecc(csv.getA().longValue(), csv.getB().longValue(), csv.getK().longValue(), csv.getOrder().longValue()));
		Exercise ex1 = new Exercise();
		ex1.setP(new Point(csv.getPx(), csv.getPy()));
		ex1.setQ(new Point(csv.getQx(), csv.getQy()));
		ex1.setR(new Point(csv.getRx(), csv.getRy()));
		ex1.setType(Type.MODK_ADD);
		student.addAssignment(ex1);
		Exercise ex2 = new Exercise();
		ex2.setP(new Point(csv.getPx(), csv.getPy()));
		ex2.setN(3);
		ex2.setType(Type.MODK_MUL);
		student.addAssignment(ex2);
		return student;
	}

}
