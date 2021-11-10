package com.spl.safetyNet.service;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spl.safetyNet.controller.PersonController;
import com.spl.safetyNet.models.MedicalRecord;

@Service

public class IMedicalRecordImpl implements IMedicalRecord {
	@Autowired
	private JsonFileData jSonFile;
	 private static final Logger logger = LogManager.getLogger(IMedicalRecordImpl.class);

	@Override
	public boolean addMedicalRecord(List<String> medications, List<String> allergies) {
		if (medications.isEmpty() && allergies.isEmpty()) {
			return false;
		}
		MedicalRecord newMedicalRecord = new MedicalRecord(medications, allergies);
		try {
			jSonFile.loadJsonMedicalRecords().add(newMedicalRecord);
			return true;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteMedicalRecord(String firstName, String lastName) {

		if (firstName.isEmpty() && lastName.isEmpty()) {
			return false;
		}
		if (getMedicalRecord(firstName, lastName).equals(null)) {
			return false;
		}
		MedicalRecord medicalRecordSelected = getMedicalRecord(firstName, lastName);
		try {
			List<MedicalRecord> medicalRecords = jSonFile.loadJsonMedicalRecords();
			medicalRecords.remove(medicalRecordSelected);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public MedicalRecord getMedicalRecord(String firstName, String lastName) {

		MedicalRecord medicalRecordSelected = new MedicalRecord();
		if (firstName.isEmpty() && lastName.isEmpty()) {
			return medicalRecordSelected;
		}
		try {
			List<MedicalRecord> medicalRecords = jSonFile.loadMedicalRecords();
			for (MedicalRecord m : medicalRecords) {
				if ((m.getPerson().getFirstName().equals(firstName)) && (m.getPerson().getLastName().equals(lastName)))
					medicalRecordSelected = m;
				return medicalRecordSelected;
			}
			return medicalRecordSelected;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return medicalRecordSelected;
	}

	@Override
	public boolean addMedicalRecordAllergie(String firstName, String lastName, String allergy) {

		if (firstName.isEmpty() && lastName.isEmpty()) {
			return false;
		}
		if (!getMedicalRecord(firstName, lastName).equals(null)) {
			MedicalRecord medicalRecordSelected = getMedicalRecord(firstName, lastName);

			medicalRecordSelected.addAllergie(allergy);
			logger.info("add allergy!!");
			return true;
		}
		return false;
	}

	@Override
	public boolean addMedicalRecordMedication(String firstName, String lastName, String medication) {

		if (firstName.isEmpty() && lastName.isEmpty()) {
			return false;
		}
		if (getMedicalRecord(firstName, lastName).equals(null)) {
			return false;
		}
		MedicalRecord medicalRecordSelected = getMedicalRecord(firstName, lastName);
		medicalRecordSelected.addMedication(medication);

		return true;
	}

	@Override
	public boolean deleteMedicalRecordAllergy(String firstName, String lastName, String allergy) {

		if (firstName.isEmpty() && lastName.isEmpty()) {
			return false;
		}
		if (getMedicalRecord(firstName, lastName).equals(null)) {
			return false;
		}
		List<String> allergies = getMedicalRecord(firstName, lastName).getAllergies();
		for (String allergie : allergies) {
			if (allergie.equals(allergy)) {
				allergies.remove(allergie);
				getMedicalRecord(firstName, lastName).getAllergies().remove(allergie);
				return true;
			}

		}
		return false;
	}

	@Override
	public boolean deleteMedicalRecordMedication(String firstName, String lastName, String medication) {
		if (firstName.isEmpty() && lastName.isEmpty()) {
			return false;
		}
		if (getMedicalRecord(firstName, lastName).equals(null)) {
			return false;
		}
		List<String> medications = getMedicalRecord(firstName, lastName).getMedications();
		for (String medic : medications) {
			if (medic.equals(medication)) {
				medications.remove(medic);
				getMedicalRecord(firstName, lastName).getMedications().remove(medication);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean updateMedicalRecordMedication(String firstName, String lastName, String medication,
			String newMedication) {
		if (firstName.isEmpty() && lastName.isEmpty()) {
			logger.error("firstName or lastName Empty");
			return false;
		}

		if (getMedicalRecord(firstName, lastName).equals(null)) {
			logger.error("No record found!!");
			return false;
		}
		MedicalRecord medicalRecordSelected = getMedicalRecord(firstName, lastName);
		if (medicalRecordSelected.getMedications().isEmpty()) {
			logger.error("No record of Medication found!!");
			return false;
		}
		List<String> medicationsList = medicalRecordSelected.getMedications();
		int cpt = 0;
		for (String med : medicationsList) {

			if (med.equals(medication)) {

				medicalRecordSelected.getMedications().set(cpt, newMedication);
				logger.info("Success update allergy!");
				return true;
			}
			cpt++;
		}

		logger.error("No record Medication found!!");
		return false;
	}

	@Override
	public boolean updateMedicalRecordAllergy(String firstName, String lastName, String allergy, String newAllergy) {

		if (firstName.isEmpty() && lastName.isEmpty()) {
			logger.error("firstName or lastName Empty");
			return false;
		}

		if (getMedicalRecord(firstName, lastName).equals(null)) {
			logger.error("No record found!!");
			return false;
		}
		MedicalRecord medicalRecordSelected = getMedicalRecord(firstName, lastName);
		if (medicalRecordSelected.getAllergies().isEmpty()) {
			logger.error("No record of Allergy found!!");
			return false;
		}
		List<String> allergiesList = medicalRecordSelected.getAllergies();
		int cpt = 0;
		for (String allerg : allergiesList) {

			if (allerg.equals(allergy)) {

				medicalRecordSelected.getAllergies().set(cpt, newAllergy);
				logger.info("Success update allergy!");
				return true;
			}
			cpt++;
		}

		logger.error("No allergy found!!");
		return false;
	}
}