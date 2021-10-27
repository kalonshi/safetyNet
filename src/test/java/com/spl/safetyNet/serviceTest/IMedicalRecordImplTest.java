package com.spl.safetyNet.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
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
	private IMedicalRecordImpl iMedicalRecordImpl;

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
		String allergy = "shell";
		boolean addAllergyOk = iMedicalRecordImpl.addMedicalRecordAllergie(firstName, lastName, allergy);
		System.out.println("ajout Status : " + addAllergyOk);
		assertEquals(true, addAllergyOk);

		

	}

	@Test
	public void addMedicalRecordMedicationTest() {
		String lastName = "Boyd";
		String firstName = "John";
		String medication = "aspirin 500mg";
		boolean addOk = iMedicalRecordImpl.addMedicalRecordMedication(firstName, lastName, medication);
		assertEquals(true, addOk);
		
	}

	@Test
	public void deleteMedicalRecordTest() {
		String lastName = "Boyd";
		String firstName = "John";

		boolean medicalrecordIsDeleted = iMedicalRecordImpl.deleteMedicalRecord(firstName, lastName);
		assertEquals(true, medicalrecordIsDeleted);
	}

	@Test
	public void deleteUnknownMedicalRecordTest() {
		String lastName = "";
		String firstName = "";

		boolean medicalrecordIsDeleted = iMedicalRecordImpl.deleteMedicalRecord(firstName, lastName);
		assertEquals(false, medicalrecordIsDeleted);
	}

	@Test
	public void deleteMedicalRecordAllergyTest() {
		String lastName = "Boyd";
		String firstName = "John";
		String allergy = "nillacilan";
		boolean deleteAllgergyFromMedicalRecord = iMedicalRecordImpl.deleteMedicalRecordAllergy(firstName, lastName,
				allergy);
		assertEquals(true, deleteAllgergyFromMedicalRecord);
	}

	@Test
	public void deleteUnknownMedicalRecordAllergyTest() {
		String lastName = "";
		String firstName = "";
		String allergy = "nillacilan";
		boolean deleteAllgergyFromMedicalRecord = iMedicalRecordImpl.deleteMedicalRecordAllergy(firstName, lastName,
				allergy);
		assertEquals(false, deleteAllgergyFromMedicalRecord);
	}

	@Test
	public void deleteMedicalRecordwithUnknownAllergyTest() {
		String lastName = "Boyd";
		String firstName = "John";
		String allergy = "";
		boolean deleteAllgergyFromMedicalRecord = iMedicalRecordImpl.deleteMedicalRecordAllergy(firstName, lastName,
				allergy);
		assertEquals(false, deleteAllgergyFromMedicalRecord);
	}

	@Test
	public void deleteMedicalRecordMedicationTest() {
		String lastName = "Boyd";
		String firstName = "John";
		String medication = "aznol:350mg";
		boolean medicalRecordMedicationIsDeleted = iMedicalRecordImpl.deleteMedicalRecordMedication(firstName, lastName,
				medication);
		assertEquals(true, medicalRecordMedicationIsDeleted);
	}

	@Test
	public void deleteUnknownMedicalRecordMedicationTest() {
		String lastName = "";
		String firstName = "";
		String medication = "aznol:350mg";
		boolean medicalRecordMedicationIsDeleted = iMedicalRecordImpl.deleteMedicalRecordMedication(firstName, lastName,
				medication);
		assertEquals(false, medicalRecordMedicationIsDeleted);
	}

	@Test
	public void deleteMedicalRecordWithUnknownMedicationTest() {
		String lastName = "Boyd";
		String firstName = "John";
		String medication = "";
		boolean medicalRecordMedicationIsDeleted = iMedicalRecordImpl.deleteMedicalRecordMedication(firstName, lastName,
				medication);
		assertEquals(false, medicalRecordMedicationIsDeleted);
	}

	@Test
	public void getMedicalRecordTest() {
		String lastName = "Boyd";
		String firstName = "John";
		MedicalRecord medicalRecordSelect = iMedicalRecordImpl.getMedicalRecord(firstName, lastName);

		String medication = "aznol:350mg";
		String medication2 = "hydrapermazol:100mg";
		String allergie = "nillacilan";

		assertEquals(lastName, medicalRecordSelect.getPerson().getLastName());
		assertEquals(firstName, medicalRecordSelect.getPerson().getFirstName());
		assertEquals(true, medicalRecordSelect.getMedications().contains(medication));
		assertEquals(true, medicalRecordSelect.getMedications().contains(medication2));
		assertEquals(true, medicalRecordSelect.getAllergies().contains(allergie));
	}

	@Test
	public void getEmptyMedicalRecordForWrongPersonTest() {
		String lastName = "";
		String firstName = "";
		MedicalRecord medicalRecordSelect = iMedicalRecordImpl.getMedicalRecord(firstName, lastName);
		MedicalRecord emptyRecord = new MedicalRecord();
		assertEquals(emptyRecord.getAllergies(), medicalRecordSelect.getAllergies());
		assertEquals(emptyRecord.getMedications(), medicalRecordSelect.getMedications());
		assertEquals(emptyRecord.getPerson(), medicalRecordSelect.getPerson());

	}

	@Test
	public void updateMedicalRecordAllergiesTest() {
		String lastName = "Boyd";
		String firstName = "John";
	String allergy="nillacilan";
		String newAllergy="Butter";
		boolean isAllergiesUpdated=iMedicalRecordImpl.updateMedicalRecordAllergy(firstName, lastName, allergy, newAllergy);
		assertEquals(true, isAllergiesUpdated);
	
	}
	
	@Test
	public void updateUnknownMedicalRecordAllergiesTest() {
		String lastName = "Boyd";
		String firstName = "John";
	String allergy="";
		String newAllergy="Butter";
		boolean isAllergiesUpdated=iMedicalRecordImpl.updateMedicalRecordAllergy(firstName, lastName, allergy, newAllergy);
		assertEquals(false, isAllergiesUpdated);
	
	}
	@Test
	public void updateMedicalRecordMedicationsTest() {
		String lastName = "Boyd";
		String firstName = "John";
		String medication="hydrapermazol:100mg";
		String newMedication="Doliprane:1000mg";
		boolean isMedicationsUpdated=iMedicalRecordImpl.updateMedicalRecordMedication(firstName, lastName, medication, newMedication);
		assertEquals(true, isMedicationsUpdated);
	}
	
	
	@Test
	public void updateUnknownPersonMedicalRecordMedicationTest() {
		String lastName = "";
		String firstName = "";
		String medication="hydrapermazol:100mg";
		String newMedication="Doliprane:1000mg";
		boolean isMedicationsUpdated=iMedicalRecordImpl.updateMedicalRecordMedication(firstName, lastName, medication, newMedication);
		assertEquals(false, isMedicationsUpdated);
		
	}
	@Test
	public void updateMedicalRecordwithUnknownMedicationTest() {
		String lastName = "Boyd";
		String firstName = "John";
		String medication="";
		String newMedication="Doliprane:1000mg";
		boolean isMedicationsUpdated=iMedicalRecordImpl.updateMedicalRecordMedication(firstName, lastName, medication, newMedication);
		assertEquals(false, isMedicationsUpdated);
		
	}
	

}
