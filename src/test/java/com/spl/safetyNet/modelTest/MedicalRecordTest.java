package com.spl.safetyNet.modelTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.spl.safetyNet.models.FireStation;
import com.spl.safetyNet.models.MedicalRecord;

class MedicalRecordTest {
private static MedicalRecord medicalRecord;
private String List;
	@BeforeEach
	private void setUpPerTest() {
		
		List<String> medications = new ArrayList<String>() ;
		medications.add("aspirin:1000mg");
		List<String> allergies = new ArrayList<String>() ;
		allergies.add("peanuts");
	medicalRecord=new MedicalRecord(medications, allergies);
	
	}

	
	@Test
	public void addNewMedicationTest() {
	
	String newMedic="Doliprane:500mg";
	medicalRecord.addMedication(newMedic);
	assertEquals(true,medicalRecord.getMedications().contains(newMedic));
	}
	
	@Test
	public void addNewAllergyTest() {
	
	String newAllergy="SeaFood";
	medicalRecord.addAllergie(newAllergy);
	assertEquals(true,medicalRecord.getAllergies().contains(newAllergy));
	}
}
