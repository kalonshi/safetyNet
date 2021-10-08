package com.spl.safetyNet.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spl.safetyNet.models.MedicalRecord;

@Service

public class IMedicalRecordImpl implements IMedicalRecord {
	@Autowired
	private JsonFileData4 jSonFile;

	@Override
	public MedicalRecord addMedicalRecord(List<String> medications, List<String> allergies) {
		MedicalRecord newMedicalRecord = new MedicalRecord(medications, allergies);
		return newMedicalRecord;
	}

	@Override
	public void deleteMedicalRecord(String firstName, String lastName) {
		// TODO Auto-generated method stub
		MedicalRecord medicalRecordSelected = getMedicalRecord(firstName, lastName);
		try {
			List<MedicalRecord> medicalRecords = jSonFile.loadMedicalRecords();
			medicalRecords.remove(medicalRecordSelected);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		medicalRecordSelected = null;

	}

	@Override
	public MedicalRecord getMedicalRecord(String firstName, String lastName) {
		// TODO Auto-generated method stub
		try {
			List<MedicalRecord> medicalRecords = jSonFile.loadMedicalRecords();
			for (MedicalRecord m : medicalRecords) {
				if ((m.getPerson().getFirstName().equals(firstName)) && (m.getPerson().getLastName().equals(lastName)))
					return m;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public MedicalRecord addMedicalRecordAllergie(String firstName, String lastName, String allergy) {
		// TODO Auto-generated method stub
		MedicalRecord medicalRecordSelected = getMedicalRecord(firstName, lastName);
		medicalRecordSelected.addAllergie(allergy);
		return medicalRecordSelected;
	}

	@Override
	public MedicalRecord addMedicalRecordMedication(String firstName, String lastName, String medication) {
		// TODO Auto-generated method stub
		MedicalRecord medicalRecordSelected = getMedicalRecord(firstName, lastName);
		medicalRecordSelected.addMedication(medication);
		return medicalRecordSelected;
	}

	@Override
	public void deleteMedicalRecordAllergy(String firstName, String lastName, String allergy) {
		// TODO Auto-generated method stub
		List<String> allergies = getMedicalRecord(firstName, lastName).getAllergies();
		for (String allergie : allergies) {
			if (allergie.equals(allergy)) {
				allergies.remove(allergie);
			}
		}

	}

	@Override
	public void deleteMedicalRecordMedication(String firstName, String lastName, String medication) {
		List<String> medications = getMedicalRecord(firstName, lastName).getMedications();
		for (String medic : medications) {
			if (medic.contains(medication)) {
				medications.remove(medic);
			}
		}
	}

	@Override
	public void updateMedicalRecordMedication(String firstName, String lastName, String medication,
			String newMedication) {
		if (firstName.isEmpty() && lastName.isEmpty()) {
		
			if(!getMedicalRecord(firstName, lastName).equals(null)) {
			MedicalRecord medicalRecordSelected = getMedicalRecord(firstName, lastName);
		
			if(!medicalRecordSelected.getMedications().isEmpty()) {
		List<String> medicationsList = medicalRecordSelected.getMedications();
		
		for (String medic : medicationsList) {
			if (medic.contains(medication)) {
				medic = newMedication;
				break;
			}
			else {
				System.out.println("medication not found!");
			}
		}
	}
			else {
				System.out.println("medicalRecordSelected is empty!!");
			}
		}
		else {
			System.out.println("firstName is Empty or lastName is Empty()");
		}
	}
	}
	@Override
	public void updateMedicalRecordAllergy(String firstName, String lastName, String allergy, String newAllergy) {
		// TODO Auto-generated method stub
		if (firstName.isEmpty() && lastName.isEmpty()) {
			MedicalRecord medicalRecordSelected = getMedicalRecord(firstName, lastName);
			if(!medicalRecordSelected.equals(null)) {
			List<String> allergiesList = medicalRecordSelected.getAllergies();
			if(!allergiesList.isEmpty()) {
			for (String allerg : allergiesList) {
				if (allerg.contains(newAllergy)) {
					allerg = newAllergy;
					break;
				}
				else {
					System.out.println("allergy not found!");
				}
			}
			} else {
				System.out.println("allergies List is Empty!!");
			}
		}  
			
		else {
			System.out.println("medicalRecordSelected is empty!!");
		}
	}
		else {
			System.out.println("firstName is Empty or lastName is Empty()");
		}
}
}