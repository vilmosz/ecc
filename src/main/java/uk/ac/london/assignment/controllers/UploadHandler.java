package uk.ac.london.assignment.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import uk.ac.london.assignment.model.Assessment;

@FunctionalInterface
public interface UploadHandler {

	List<Assessment> handleUpload(MultipartFile file, String prefix) throws IOException;

}
