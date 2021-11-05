package com.spl.safetyNet.serviceTest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jsoniter.any.Any;
import com.spl.safetyNet.service.JsonFileData;

@SpringBootTest

@ExtendWith(MockitoExtension.class)

@MockitoSettings(strictness = Strictness.LENIENT)
class JsonFileDataTest {
	@Autowired
	private JsonFileData jSonFileData;

	@Test
	public void testFile() throws IOException {
		String filePath = "src/main/resources/data.json";

		byte[] bytesFile = Files.readAllBytes(new File(filePath).toPath());

		assertTrue(!bytesFile.toString().isEmpty());
	}

	@Test
	public void testLoadStationsWithOutListPerson() throws IOException {
		String filePath = "src/main/resources/data.json";

		int nbOfStations = jSonFileData.readFileJson(filePath).get("firestations").size();
		int nb = 13;
		assertEquals(nb, nbOfStations);

	}

	@Test
	public void testLoadJsonPersons() throws IOException {
		String filePath = "src/main/resources/data.json";

		int nbOfPersons = jSonFileData.readFileJson(filePath).get("persons").size();
		int nbP = 23;
		String lastName = "Boyd";
		String firstName = "John";
		String lastNameJson = jSonFileData.readFileJson(filePath).get("persons").get(0).get(1).toString();
		String firstNameJson = jSonFileData.readFileJson(filePath).get("persons").get(0).get(0).toString();
		
		/*
		 * String firstNameJsonTest =
		 * jSonFileData.readFileJson(filePath).get("persons").;
		 */
		assertEquals(nbP, nbOfPersons);
		/*
		 * assertEquals(lastName, lastNameJson ); assertEquals(firstName,
		 * firstNameJson);
		 */

	}

	@Test
	public void testLoadJsonMedicalRecords() throws IOException {
		String filePath = "src/main/resources/data.json";

		int nbOfMedicalRecords = jSonFileData.readFileJson(filePath).get("medicalrecords").size();
		int nbM = 23;
		assertEquals(nbM, nbOfMedicalRecords);

	}

	@Test
	public void TestReadFileJson() {
		String filePath = "src/main/resources/data.json";
		try {
			jSonFileData.readFileJson(filePath).size();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
