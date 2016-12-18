package uk.ac.london.assignment.controllers;

import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import uk.ac.london.assignment.model.Student;
import uk.ac.london.assignment.service.UploadService;

@RestController
public class FileUploadController {

	private static final Log logger = LogFactory.getLog(FileUploadController.class);

	@Autowired
	private UploadService uploadService;

	@PostMapping(value = "/api/upload-students", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Student> uploadStudents(@RequestParam("file") MultipartFile file) throws IOException {
		logger.info(String.format("File [%s] has been uploaded", file.getOriginalFilename()));
		try {
			return uploadService.loadStudents(file.getInputStream());
		} catch (Exception e) {
			logger.error(String.format("Error processing [%s]", file.getOriginalFilename()), e);
			throw e;
		}
	}

}
