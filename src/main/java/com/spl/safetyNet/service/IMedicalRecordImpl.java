package com.spl.safetyNet.service;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
	public MedicalRecord addMedicalRecord(List<String> medications, List<String> allergies) {
		MedicalRecord newMedicalRecord = new MedicalRecord();
		if (!medications.isEmpty() && !allergies.isEmpty()) {
			newMedicalRecord = new MedicalRecord(medications, allergies);
			try {
				jSonFile.loadMedicalRecords().add(newMedicalRecord);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return newMedicalRecord;
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
		if (!StringUtils.isEmpty(firstName) && !StringUtils.isEmpty(lastName)) {

			try {
				List<MedicalRecord> medicalRecords = jSonFile.loadMedicalRecords();
				for (MedicalRecord m : medicalRecords) {
					if ((m.getPerson().getFirstName().equals(firstName))
							&& (m.getPerson().getLastName().equals(lastName)))
						medicalRecordSelected = m;

				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return medicalRecordSelected;
	}

	@Override
	public MedicalRecord addMedicalRecordAllergie(String firstName, String lastName, String allergy) {
		MedicalRecord MedicalRecordAllergyUpdate = new MedicalRecord();
		if (!StringUtils.isEmpty(firstName) && !StringUtils.isEmpty(lastName)
				&& getMedicalRecord(firstName, lastName) != null) {

			MedicalRecordAllergyUpdate = getMedicalRecord(firstName, lastName);

			MedicalRecordAllergyUpdate.addAllergie(allergy);
			logger.info("add allergy!!");

		}
		return MedicalRecordAllergyUpdate;
	}

	@Override
	public MedicalRecord addMedicalRecordMedication(String firstName, String lastName, String medication) {
		MedicalRecord medicalRecordMedicationUpdate = new MedicalRecord();
		if (!StringUtils.isEmpty(firstName) && !StringUtils.isEmpty(lastName)
				&& getMedicalRecord(firstName, lastName) != null) {
			medicalRecordMedicationUpdate = getMedicalRecord(firstName, lastName);
			medicalRecordMedicationUpdate.addMedication(medication);

		}
		return medicalRecordMedicationUpdate;
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
	public MedicalRecord updateMedicalRecordMedication(String firstName, String lastName, String medication,
			String newMedication) {
		MedicalRecord medicalRecordMedicationUpdate = new MedicalRecord();
		if ((!StringUtils.isEmpty(firstName) && !StringUtils.isEmpty(lastName)
				&& getMedicalRecord(firstName, lastName) != null)) {
			medicalRecordMedicationUpdate = getMedicalRecord(firstName, lastName);

			List<String> medicationsList = medicalRecordMedicationUpdate.getMedications();
			int cpt = 0;
			for (String med : medicationsList) {

				if (med.equals(medication)) {

					medicalRecordMedicationUpdate.getMedications().set(cpt, newMedication);
					logger.info("Success update allergy!");

				}
				cpt++;
			}
		}

		return medicalRecordMedicationUpdate;
	}

	@Override
	public MedicalRecord updateMedicalRecordAllergy(String firstName, String lastName, String allergy,
			String newAllergy) {
		MedicalRecord medicalRecordAllergyUpdate = new MedicalRecord();
		if (!StringUtils.isEmpty(firstName) && !StringUtils.isEmpty(lastName)
				&& getMedicalRecord(firstName, lastName) != null) {

			medicalRecordAllergyUpdate = getMedicalRecord(firstName, lastName);
			if (!StringUtils.isEmpty(allergy) && !StringUtils.isEmpty(newAllergy)) {
				List<String> allergiesList = medicalRecordAllergyUpdate.getAllergies();
				int cpt = 0;
				for (String allerg : allergiesList) {

					if (allerg.equals(allergy)) {

						medicalRecordAllergyUpdate.getAllergies().set(cpt, newAllergy);
						logger.info("Success update allergy!");

					}
					cpt++;
				}
			}
			return medicalRecordAllergyUpdate;
		}
		return null;
	}

}