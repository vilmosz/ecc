package uk.ac.london.assignment.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import uk.ac.london.assignment.model.Assessment;
import uk.ac.london.assignment.model.Student;
import uk.ac.london.assignment.repository.AssessmentRepository;
import uk.ac.london.assignment.repository.StudentRepository;

@Controller
public class HomeController {
	
	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private AssessmentRepository assessmentRepository;
	
	@RequestMapping(value = "/")
	public String index(Model model) {
    	List<Assessment> assessments = assessmentRepository.findAll();
        model.addAttribute("assessments", assessments);
		return "home";
	}

	@RequestMapping(value = "/actual")
	public String actual(Model model) {
    	List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
		return "students";
	}

	@RequestMapping(value = "/expected")
	public String expected(Model model) {
    	List<Assessment> assessments = assessmentRepository.findAll();    	
    	List<Student> students = assessments.stream().map(Assessment::getExpected).collect(Collectors.toList());
        model.addAttribute("students", students);
		return "students";
	}
	
}
