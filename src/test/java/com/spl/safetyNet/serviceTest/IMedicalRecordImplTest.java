package com.spl.safetyNet.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.spl.safetyNet.models.MedicalRecord;
import com.spl.safetyNet.service.IMedicalRecordImpl;

@SpringBootTest

@ExtendWith(MockitoExtension.class)

@MockitoSettings(strictness = Strictness.LENIENT)

public class IMedicalRecordImplTest {
	@Autowired
	private IMedicalRecordImpl iMedicalRecordImpl;

	@Test
	public void addMedicalRecordTest() {

		List<String> medicationTest = new ArrayList<>();
		List<String> allergiesTest = new ArrayList<>();
		medicationTest.add("aznol:350mg");
		medicationTest.add("hydrapermazol:100mg");
		allergiesTest.add("nillacilan");
		MedicalRecord addMedicalRecord = iMedicalRecordImpl.addMedicalRecord(medicationTest, allergiesTest);

		assertEquals(true, addMedicalRecord.getAllergies().contains("nillacilan"));
		assertEquals(true, addMedicalRecord.getMedications().contains("aznol:350mg"));
		assertEquals(2, addMedicalRecord.getMedications().size());
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
	public void getMedicalRecordTest() {
		String lastName = "Boyd";
		String firstName = "John";
		MedicalRecord medicalRecordSelect = iMedicalRecordImpl.getMedicalRecord(firstName, lastName);

		String medication = "aznol:350mg";
		String medication2 = "hydrapermazol:100mg";
		String allergie = "nillacilan";

		assertEquals(lastName, medicalRecordSelect.getPerson().getLastName());
		assertEquals(firstName, medicalRecordSelect.getPerson().getFirstName());
		/*
		 * assertEquals(true,
		 * medicalRecordSelect.getMedications().contains(medication));
		 * assertEquals(true,
		 * medicalRecordSelect.getMedications().contains(medication2));
		 * assertEquals(allergie, medicalRecordSelect.getAllergies().get(0));
		 */
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

	
}
