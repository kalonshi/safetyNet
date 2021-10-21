package com.spl.safetyNet.serviceTest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.Test;

class JsonFileDataTest {

	@Test   
	public void testFile() throws IOException {
		String filePath = "src/main/resources/data.json";
	    	    
		byte[] bytesFile = Files.readAllBytes(new File(filePath).toPath());
	   
		assertTrue(!bytesFile.toString().isEmpty());
	}

}
