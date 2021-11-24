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
	public void addMedicalRecordAllergieTest() {
		String lastName = "Boyd";
		String firstName = "John";
		String allergy = "shell";
		MedicalRecord addAllergy = iMedicalRecordImpl.addMedicalRecordAllergie(firstName, lastName, allergy);
		assertEquals(lastName, addAllergy.getPerson().getLastName());
		assertEquals(firstName, addAllergy.getPerson().getFirstName());
		assertEquals(true, addAllergy.getAllergies().contains(allergy));

	}

	@Test
	public void addMedicalRecordMedicationTest() {
		String lastName = "Boyd";
		String firstName = "John";
		String medication = "aspirin 500mg";
		MedicalRecord addMedication = iMedicalRecordImpl.addMedicalRecordMedication(firstName, lastName, medication);
		/* assertEquals(true, addMedication.getMedications().contains(medication)); */
		assertEquals(lastName, addMedication.getPerson().getLastName());
		assertEquals(firstName, addMedication.getPerson().getFirstName());
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

	/*
	 * @Test public void deleteMedicalRecordAllergyTest() { String lastName =
	 * "Boyd"; String firstName = "John"; String allergy = "nillacilan"; boolean
	 * deleteAllgergyFromMedicalRecord =
	 * iMedicalRecordImpl.deleteMedicalRecordAllergy(firstName, lastName, allergy);
	 * assertEquals(true, deleteAllgergyFromMedicalRecord); }
	 */

	@Test
	public void deleteUnknownMedicalRecordAllergyTest() {
		String lastName = "";
		String firstName = "";
		String allergy = "nillacilan";
		boolean deleteUnknownAllgergyFromMedicalRecord = iMedicalRecordImpl.deleteMedicalRecordAllergy(firstName,
				lastName, allergy);
		assertEquals(false, deleteUnknownAllgergyFromMedicalRecord);
	}

	@Test
	public void deleteMedicalRecordwithUnknownAllergyTest() {
		String lastName = "Boyd";
		String firstName = "John";
		String allergy = "";
		boolean deleteUnknownAllgergyFromMedicalRecord = iMedicalRecordImpl.deleteMedicalRecordAllergy(firstName,
				lastName, allergy);
		assertEquals(false, deleteUnknownAllgergyFromMedicalRecord);
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
		boolean medicalRecordFromUnknowPersonIsDeleted = iMedicalRecordImpl.deleteMedicalRecordMedication(firstName,
				lastName, medication);
		assertEquals(false, medicalRecordFromUnknowPersonIsDeleted);
	}

	@Test
	public void deleteMedicalRecordWithUnknownMedicationTest() {
		String lastName = "Boyd";
		String firstName = "John";
		String medication = "";
		boolean medicalRecordUnknownMedicationIsDeleted = iMedicalRecordImpl.deleteMedicalRecordMedication(firstName,
				lastName, medication);
		assertEquals(false, medicalRecordUnknownMedicationIsDeleted);
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

	@Test
	public void updateMedicalRecordAllergiesTest() {
		String lastName = "Boyd";
		String firstName = "John";
		String allergy = "nillacilan";
		String newAllergy = "Butter";
		MedicalRecord isAllergiesUpdated = iMedicalRecordImpl.updateMedicalRecordAllergy(firstName, lastName, allergy,
				newAllergy);
		assertEquals(newAllergy, isAllergiesUpdated.getAllergies().get(0));

	}

	@Test
	public void updateUnknownMedicalRecordAllergiesTest() {
		String lastName = "Boyd";
		String firstName = "John";
		String wrongAllergySelected = "";
		String newAllergy = "Butter";
		String allergyRecord = "nillacilan";
		MedicalRecord isAllergiesUpdated = iMedicalRecordImpl.updateMedicalRecordAllergy(firstName, lastName,
				wrongAllergySelected, newAllergy);

		assertEquals(false, isAllergiesUpdated.getAllergies().contains(newAllergy));
		assertEquals(true, isAllergiesUpdated.getAllergies().contains(allergyRecord));

	}

	@Test
	public void updateMedicalRecordMedicationsTest() {
		String lastName = "Boyd";
		String firstName = "John";
		String medication = "hydrapermazol:100mg";
		String newMedication = "Doliprane:1000mg";
		MedicalRecord isMedicationsUpdated = iMedicalRecordImpl.updateMedicalRecordMedication(firstName, lastName,
				medication, newMedication);
		assertEquals(true, isMedicationsUpdated.getMedications().contains(newMedication));
	}

	@Test
	public void updateUnknownPersonMedicalRecordMedicationTest() {
		String lastName = "";
		String firstName = "";
		String medication = "hydrapermazol:100mg";
		String newMedication = "Doliprane:1000mg";
		MedicalRecord isMedicationsUpdated = iMedicalRecordImpl.updateMedicalRecordMedication(firstName, lastName,
				medication, newMedication);
		assertEquals(null, isMedicationsUpdated.getAllergies());
		assertEquals(null, isMedicationsUpdated.getMedications());

	}

	@Test
	public void updateMedicalRecordwithUnknownMedicationTest() {
		String lastName = "Boyd";
		String firstName = "John";
		String medication = "";
		String newMedication = "Doliprane:1000mg";
		MedicalRecord isMedicationsUpdated = iMedicalRecordImpl.updateMedicalRecordMedication(firstName, lastName,
				medication, newMedication);
		assertEquals(false, isMedicationsUpdated.getMedications().contains(newMedication));

	}

}
