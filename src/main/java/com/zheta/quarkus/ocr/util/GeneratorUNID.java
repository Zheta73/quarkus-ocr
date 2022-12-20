package com.zheta.quarkus.ocr.util;

public class GeneratorUNID {
	
	public static String generate() {
        return Integer.toHexString(new Object().hashCode());
    }
	
}
