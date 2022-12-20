package com.zheta.quarkus.ocr.model;

import io.quarkus.runtime.annotations.RegisterForReflection;


@RegisterForReflection
public class OCR {
	private String fileName;
	private String text;
	
	
	public OCR() {
		// NOOP
	}
	
	public OCR(String fileName, String text) {
		this.fileName = fileName;
		this.text = text;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
}
