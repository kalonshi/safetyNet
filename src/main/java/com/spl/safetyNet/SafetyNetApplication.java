package com.spl.safetyNet;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import com.spl.safetyNet.service.JsonFileData;

@SpringBootApplication
public class SafetyNetApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(SafetyNetApplication.class, args);
		
		JsonFileData jsonFileData = new JsonFileData();
		jsonFileData.loadJsonPersons();
		jsonFileData.loadJsonMedicalRecords();
		jsonFileData.loadStationsWithOutListPerson();
		jsonFileData.loadMedicalRecords();
		jsonFileData.loadPersons();
		jsonFileData.loadStations();
		
	}

}
