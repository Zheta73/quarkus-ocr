package com.zheta.quarkus.ocr.service;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.itextpdf.pdfocr.TextInfo;
import com.itextpdf.pdfocr.tesseract4.Tesseract4LibOcrEngine;
import com.itextpdf.pdfocr.tesseract4.Tesseract4OcrEngineProperties;


@ApplicationScoped
public class TesseractService {
	
	@ConfigProperty(name="tesseract.data.path")
	String tesseractDataPath;
	
	private Tesseract4OcrEngineProperties tesseractProperties;
	
	
	@PostConstruct
	void onConstruct() {
		tesseractProperties = new Tesseract4OcrEngineProperties();
		tesseractProperties.setPathToTessData(new File(tesseractDataPath));
		List<String> requiredLanguages = Arrays.asList("spa");
		tesseractProperties.setLanguages(requiredLanguages);
	}
	
	public Tesseract4LibOcrEngine createEngine() {
		return new Tesseract4LibOcrEngine(tesseractProperties);
	}
	
	public String extratTextFromJPG(File fileJPG) {
		StringBuilder builder = new StringBuilder();
		Tesseract4LibOcrEngine tesseractEngine = createEngine();
		Map<Integer, List<TextInfo>> data = tesseractEngine.doImageOcr(fileJPG);
		if( data != null && data.containsKey(1) ) {
			for(TextInfo ti : data.get(1)) {
				builder.append(ti.getText());
			}
		}
		
		return builder.toString();
	}
	
}
