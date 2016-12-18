package uk.ac.london.assignment.service;

import java.awt.Point;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import uk.ac.london.assignment.model.ModkAdd;
import uk.ac.london.assignment.model.ModkMul;
import uk.ac.london.assignment.model.Student;
import uk.ac.london.assignment.repository.StudentRepository;
import uk.ac.london.ecc.Ecc;

@Service
public class UploadService {

	private static final Log logger = LogFactory.getLog(UploadService.class);

	@Autowired
	private StudentRepository repository;

	public List<Student> loadStudents(InputStream inputStream) throws JsonProcessingException, IOException {
		CsvSchema schema = CsvSchema.emptySchema().withHeader();
		CsvMapper mapper = new CsvMapper();
		MappingIterator<uk.ac.london.assignment.model.csv.Student> reader = mapper
				.readerFor(uk.ac.london.assignment.model.csv.Student.class).with(schema).readValues(inputStream);
		List<Student> students = new ArrayList<>();
		while (reader.hasNextValue()) {
			uk.ac.london.assignment.model.csv.Student csv = reader.nextValue();
			logger.debug(csv);
			Student student = createStudent(csv);
			if (student != null) {
				students.add(student);
			}
		}
		return students;
	}

	public Student createStudent(uk.ac.london.assignment.model.csv.Student csv) {
		Student student = new Student();
		student.setId(csv.getStudentCode());
		student.setName(csv.getName().trim().replaceAll("\\s+", " "));
		student.setEcc(new Ecc(csv.getA().longValue(), csv.getB().longValue(), csv.getK().longValue()));
		ModkAdd ex1 = new ModkAdd();
		ex1.setP(new Point(csv.getPx(), csv.getPy()));
		ex1.setQ(new Point(csv.getQx(), csv.getQy()));		
		ex1.setR(new Point(csv.getRx(), csv.getRy()));		
		student.addAssignment(ex1);
		ModkMul ex2 = new ModkMul();
		ex2.setP(new Point(csv.getPx(), csv.getPy()));
		ex2.setN(3);		
		student.addAssignment(ex2);
		repository.save(student);
		return student;
	}

}
