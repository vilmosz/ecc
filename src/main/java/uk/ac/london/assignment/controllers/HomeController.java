package uk.ac.london.assignment.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import uk.ac.london.assignment.model.Assessment;
import uk.ac.london.assignment.repository.AssessmentRepository;

@Controller
public class HomeController {

    @Autowired
    private AssessmentRepository assessmentRepository;

    @RequestMapping(value = "/")
    public String index(Model model) {
        List<Assessment> assessments = assessmentRepository.findAll();
        model.addAttribute("assessments", assessments);
        return "home";
    }

    @RequestMapping(value = "/cw1")
    public String actual(Model model) {
        List<Assessment> assessments = assessmentRepository.findAll();
        model.addAttribute("assessments", assessments);
        return "cw1";
    }

    @RequestMapping(value = "/cw2")
    public String expected(Model model) {
        List<Assessment> assessments = assessmentRepository.findAll();
        model.addAttribute("assessments", assessments);
        return "cw2";
    }

}
