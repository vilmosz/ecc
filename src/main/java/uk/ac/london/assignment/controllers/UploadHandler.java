package uk.ac.london.assignment.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import uk.ac.london.assignment.model.Student;

@FunctionalInterface
public interface UploadHandler {

	List<Student> handleUpload(MultipartFile file) throws IOException;

}
