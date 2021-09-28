package com.spl.safetyNet;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import com.spl.safetyNet.service.JsonFileData3;

@SpringBootApplication
public class SafetyNetApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(SafetyNetApplication.class, args);
		/*
		 * JsonFileData2 jsonFileData = new JsonFileData2(); jsonFileData.jsonLoad();
		 */
		JsonFileData3 jsonFileData = new JsonFileData3(); 
		jsonFileData.loadStationsWithOutListPerson();
		jsonFileData.loadPersons();
	}

}
