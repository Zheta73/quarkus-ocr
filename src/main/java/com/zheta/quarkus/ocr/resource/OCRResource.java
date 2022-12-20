package com.zheta.quarkus.ocr.resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.RestQuery;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import com.zheta.quarkus.ocr.model.OCR;
import com.zheta.quarkus.ocr.service.OCRService;


@Path("/ocr")
public class OCRResource {
	
	@Inject
	OCRService ocrService;
	
	@Inject
	Logger log;
	
	
	@POST
	@Path("/pdf")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public OCR extractTextFromPDF(@RestForm("file") FileUpload fileUpload, @RestQuery boolean debug, @RestQuery int maxText) throws IOException {
		log.infof("Extracting text from PDF: %s (%s bytes)", fileUpload.fileName(), fileUpload.size());
		long start = System.currentTimeMillis();
		
		String path = fileUpload.filePath().toString();
		byte[] bytes = Files.readAllBytes(Paths.get(path));
		String text = ocrService.extractTextFromPDF(bytes);
		OCR ocr = new OCR(fileUpload.fileName(), text);
		
		long end = System.currentTimeMillis();
		long elapsed = end - start;
		
		if( debug ) {
			String debugText = ( maxText == 0 ) ? text : text.substring(0, maxText);
			log.infof("Extracted text\n%s", debugText);
		}
		
		log.infof("Extracted text from PDF: %s (%s bytes) finished in %s ms", fileUpload.fileName(), fileUpload.size(), elapsed);
		return ocr;
	}
	
}
