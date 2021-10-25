package com.spl.safetyNet.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.spl.safetyNet.models.MedicalRecord;

import com.spl.safetyNet.service.IMedicalRecordImpl;
import com.spl.safetyNet.service.JsonFileData;
@SpringBootTest

@ExtendWith(MockitoExtension.class)

@MockitoSettings(strictness = Strictness.LENIENT)


public class IMedicalRecordImplTest {
	@Autowired
	private  IMedicalRecordImpl iMedicalRecordImpl;

	@Autowired
	private JsonFileData jSonFile;

	private static Logger logger = LogManager.getLogger(IMedicalRecordImpl.class);
	
	@Test
	public void addMedicalRecordTest() {
		String lastName = "Boyd";
		String firstName = "John";
		List<String> medicationTest = new ArrayList<>();
		List<String> allergiesTest = new ArrayList<>();
		medicationTest.add("aznol:350mg");
		medicationTest.add("hydrapermazol:100mg");
		allergiesTest.add("nillacilan");
	}
	@Test
	public void addMedicalRecordAllergieTest() {
		String lastName = "Boyd";
		String firstName = "John";
		String allergy="shell";
	 boolean addOk=iMedicalRecordImpl.addMedicalRecordAllergie(firstName, lastName, allergy);
	 assertEquals(true,addOk);
	 List<String> allergies=iMedicalRecordImpl.getMedicalRecord(firstName, lastName).getAllergies();
	 assertEquals(true,allergies.contains(allergy));
		
	 }
	
	@Test
	public void addMedicalRecordMedicationTest() {
		
	}
	@Test
	public void deleteMedicalRecordTest() {
		String lastName = "Boyd";
		String firstName = "John";
		
	}
	@Test
	public void deleteMedicalRecordAllergyTest() {
		String lastName = "Boyd";
		String firstName = "John";	
	}
	@Test
	public void deleteMedicalRecordMedicationTest() {
		String lastName = "Boyd";
		String firstName = "John";	
	}
	@Test
	public void getMedicalRecordTest() {
		String lastName = "Boyd";
		String firstName = "John";
		List<String> medicationTest = new ArrayList<>();
		List<String> allergiesTest = new ArrayList<>();
		medicationTest.add("aznol:350mg");
		medicationTest.add("hydrapermazol:100mg");
		allergiesTest.add("nillacilan");
	}
	@Test
	public void updateMedicalRecordMedicationTest() {
		String lastName = "Boyd";
		String firstName = "John";	
		List<String> medicationTest = new ArrayList<>();
		List<String> allergiesTest = new ArrayList<>();
		medicationTest.add("aznol:350mg");
		medicationTest.add("hydrapermazol:100mg");
		allergiesTest.add("nillacilan");
	}
	@Test
	public void updateMedicalRecordAllergyTest() {
		String lastName = "Boyd";
		String firstName = "John";
		List<String> medicationTest = new ArrayList<>();
		List<String> allergiesTest = new ArrayList<>();
		medicationTest.add("aznol:350mg");
		medicationTest.add("hydrapermazol:100mg");
		allergiesTest.add("nillacilan");
	}
	
	
	
}
