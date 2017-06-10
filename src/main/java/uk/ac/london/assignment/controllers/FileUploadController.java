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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

import uk.ac.london.assignment.model.Assessment;
import uk.ac.london.assignment.service.UploadService;

@RestController
@RequestMapping(value = "api/upload")
public class FileUploadController {

	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

	private final static Set<String> compressedMimeTypes = ImmutableSet.<String>builder().add("application/zip")
			.add("application/octet-stream").add("application/x-zip-compressed").build();

	@Autowired
	private UploadService uploadService;

	@PostMapping(value = "/{prefix}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Assessment> uploadStudents(
			@PathVariable("prefix") final String prefix,
			@RequestParam("file") MultipartFile file) throws IOException {
		final String mimeType = file.getContentType();
		logger.info("Storing file [{}] of type [{}]", file.getOriginalFilename(), mimeType);
		try {
			if (isCompressed(mimeType)) {
				return getCompressedFileHandler().handleUpload(file, prefix);
			} else if (isJson(mimeType)) {
				return getJsonFileHandler().handleUpload(file, prefix);
			} else if (isCsv(mimeType)) {
				return getCsvFileHandler().handleUpload(file, prefix);
			} else {
				throw new IllegalArgumentException(String.format("Unrecognized mime type [%s]", mimeType));
			}
		} catch (Exception e) {
			logger.error(String.format("Error processing [%s]", file.getOriginalFilename()), e);
			throw e;
		}
	}	
	
	private final Boolean isCompressed(final String mimeType) {
		return compressedMimeTypes.contains(mimeType);
	}

	private final Boolean isJson(final String mimeType) {
		return Objects.equal("application/json", mimeType);
	}

	private final Boolean isCsv(final String mimeType) {
		return Objects.equal("text/csv", mimeType);
	}

	private UploadHandler getJsonFileHandler() {
		return (file, prefix) -> {
			String text = IOUtils.toString(file.getInputStream(), StandardCharsets.UTF_8.name());
			Assessment assessment = uploadService.loadJson(text, file.getOriginalFilename(), prefix);
			logger.debug("Assessment: {}", assessment);
			return Lists.newArrayList(assessment);
		};
	}

	private UploadHandler getCompressedFileHandler() {
		return (file, prefix) -> {
			List<Assessment> assessments = new ArrayList<>();
			try (final ZipInputStream zis = new ZipInputStream(file.getInputStream())) {
				ZipEntry entry;
				while ((entry = zis.getNextEntry()) != null) {
					final String entryName = entry.getName();
					String text = IOUtils.toString(zis, StandardCharsets.UTF_8.name());
					Assessment assessment = uploadService.loadJson(text, entryName, prefix);
					logger.debug("Assessment: {}", assessment);
					if (assessment != null)
						assessments.add(assessment);
					zis.closeEntry();
				}
			}
			return assessments;
		};
	}

	private UploadHandler getCsvFileHandler() {
		return (file, prefix) -> {
			return uploadService.loadCsv(file.getInputStream(), prefix);
		};
	}

}
