package uk.ac.london.assignment.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import uk.ac.london.assignment.model.Student;
import uk.ac.london.assignment.repository.StudentRepository;

@Controller
public class HomeController {
	
	@Autowired
	private StudentRepository repository;

	@RequestMapping(value = "/")
	public String index(Model model) {
    	List<Student> students = repository.findAll();
        model.addAttribute("students", students);
		return "home";
	}

}
