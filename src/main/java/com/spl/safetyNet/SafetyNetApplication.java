package com.spl.safetyNet;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import com.spl.safetyNet.service.JsonFileData3;
import com.spl.safetyNet.service.JsonFileData4;

@SpringBootApplication
public class SafetyNetApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(SafetyNetApplication.class, args);
		/*
		 * JsonFileData2 jsonFileData = new JsonFileData2(); jsonFileData.jsonLoad();
		 */
		/* JsonFileData3 jsonFileData = new JsonFileData3(); */
		JsonFileData4 jsonFileData = new JsonFileData4(); 
		jsonFileData.loadStationsWithOutListPerson();
		jsonFileData.loadPersons();
	}

}
