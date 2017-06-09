package uk.ac.london.assignment.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import uk.ac.london.assignment.model.Assessment;
import uk.ac.london.assignment.model.Cw1;
import uk.ac.london.assignment.model.Student;
import uk.ac.london.assignment.repository.Cw1Repository;
import uk.ac.london.assignment.repository.StudentRepository;

@Controller
public class HomeController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private Cw1Repository cw1Repository;

    @RequestMapping(value = "/")
    public String index(Model model) {
        List<Cw1> assessments = cw1Repository.findAll();
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
        List<Cw1> assessments = cw1Repository.findAll();
        List<Student> students = assessments.stream().map(Assessment::getExpected).collect(Collectors.toList());
        model.addAttribute("students", students);
        return "students";
    }

}
