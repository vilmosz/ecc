package uk.ac.london.assignment.controllers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.ImmutableSet;

import uk.ac.london.assignment.model.Student;
import uk.ac.london.assignment.service.UploadService;

@RestController
public class FileUploadController {

	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
    private final Set<String> compressedMimeTypes = ImmutableSet.<String>builder()
            .add("application/zip")
            .add("application/octet-stream")
            .add("application/x-zip-compressed")
            .build();	

	@Autowired
	private UploadService uploadService;

	@PostMapping(value = "/api/upload-students", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Student> uploadStudents(@RequestParam("file") MultipartFile file) throws IOException {
		logger.info(String.format("File [%s] has been uploaded", file.getOriginalFilename()));
        final String mimeType = file.getContentType();
		try {
	        if (isCompressed(mimeType)) {
	        	logger.info("Storing compressed file [{}] of type [{}]", file.getOriginalFilename(), file.getContentType());
	        	List<Student> students = new ArrayList<>();
	            try (final ZipInputStream zis = new ZipInputStream(file.getInputStream())) {
	                ZipEntry entry;
	                while ((entry = zis.getNextEntry()) != null) {
	                    final String entryName = entry.getName();
	                    String text = IOUtils.toString(zis, StandardCharsets.UTF_8.name());
	                    Student student = uploadService.loadJson(text, entryName);
	                    logger.info("Student: {}", student);
	                    if (student != null)
	                    	students.add(student);
	                    zis.closeEntry();
	                }
	            }	        	
	            return students;
	        } else {
	            logger.info("Storing simple file [{}] of type [{}]", file.getOriginalFilename(), file.getContentType());
				return uploadService.loadCsv(file.getInputStream());
	        }
		} catch (Exception e) {
			logger.error(String.format("Error processing [%s]", file.getOriginalFilename()), e);
			throw e;
		}
	}
	
    private final Boolean isCompressed(final String mimeType) {
        return compressedMimeTypes.contains(mimeType);
    }
	

}
