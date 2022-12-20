package com.zheta.quarkus.ocr.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;

import com.zheta.quarkus.ocr.util.GeneratorUNID;


@ApplicationScoped
public class OCRService {
	
	@Inject
	TesseractService tesseractService;
	
	
	public String extractTextFromPDF(byte[] bytes) {
		try {
			PDDocument document = PDDocument.load(bytes);
			PDFTextStripper textStripper = new PDFTextStripper();
			String strippedText = textStripper.getText(document);
			String text = strippedText.trim();
			if( text.isEmpty() ) {
				File fileJPG = saveAsJPG(document);
				text = tesseractService.extratTextFromJPG(fileJPG);
				fileJPG.delete();
			}
			
			return text;
			
		} catch(IOException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
	// Private...
	
	private File saveAsJPG(PDDocument document) throws IOException {
		PDFRenderer pdfRenderer = new PDFRenderer(document);
		BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 300, ImageType.RGB);
		String tempFilePreffix = "tempfile_" + GeneratorUNID.generate() + "_PG_1";
		File tempFileJPG = File.createTempFile(tempFilePreffix, ".jpg");
		ImageIO.write(bim, "jpg", tempFileJPG);		
		return tempFileJPG;
	}
	
}
