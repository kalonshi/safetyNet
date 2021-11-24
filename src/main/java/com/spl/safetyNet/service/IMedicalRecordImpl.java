package com.spl.safetyNet.service;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import com.spl.safetyNet.models.MedicalRecord;
import com.spl.safetyNet.models.Person;

@Service
@CacheConfig(cacheNames = { "stations", "persons", "medicalRecords" })
public class IMedicalRecordImpl implements IMedicalRecord {
	@Autowired
	private JsonFileData jSonFile;
	@Autowired
	private CacheManager cacheManager;
	private static final Logger logger = LogManager.getLogger(IMedicalRecordImpl.class);

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

				e.printStackTrace();
			}
		}
		return medicalRecordSelected;
	}

	@Override
	public MedicalRecord addMedicalRecord(List<String> medications, List<String> allergies) {
		MedicalRecord newMedicalRecord = new MedicalRecord();
		if (!medications.isEmpty() && !allergies.isEmpty()) {
			newMedicalRecord = new MedicalRecord(medications, allergies);

			try {
				jSonFile.loadMedicalRecords().add(newMedicalRecord);

			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		return newMedicalRecord;
	}

	@Override
	public boolean deleteMedicalRecord(String firstName, String lastName) {
		boolean isDeleted = false;
		if ((!firstName.isEmpty() && !lastName.isEmpty()) && (getMedicalRecord(firstName, lastName) != null)) {
			MedicalRecord medicalRecordSelected = getMedicalRecord(firstName, lastName);
			try {
				jSonFile.loadMedicalRecords().remove(medicalRecordSelected);
				isDeleted = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return isDeleted;

	}

	@Override
	public MedicalRecord addMedicalRecordAllergie(String firstName, String lastName, String allergy) {
		MedicalRecord medicalRecordAllergyUpdate = new MedicalRecord();
		if (!StringUtils.isEmpty(firstName) && !StringUtils.isEmpty(lastName)
				&& getMedicalRecord(firstName, lastName) != null) {

			MedicalRecord medicalRecordSelected = getMedicalRecord(firstName, lastName);
			/* List medjSonFile.loadMedicalRecords() */
			medicalRecordAllergyUpdate = medicalRecordSelected;
			medicalRecordAllergyUpdate.addAllergie(allergy);

			cacheManager.getCache("medicalRecords").put(medicalRecordSelected.getAllergies(), allergy);

			logger.info("add allergy!!");

		}
		return medicalRecordAllergyUpdate;
	}

	@Override
	public MedicalRecord addMedicalRecordMedication(String firstName, String lastName, String medication) {
		MedicalRecord medicalRecordMedicationUpdate = new MedicalRecord();
		/* Person personMedicalRecord=new Person(); */
		if (!StringUtils.isEmpty(firstName) && !StringUtils.isEmpty(lastName)
				&& getMedicalRecord(firstName, lastName) != null) {
			/* personMedicalRecord=new Person(firstName, lastName); */
			MedicalRecord medicalRecord = getMedicalRecord(firstName, lastName);
			medicalRecordMedicationUpdate = medicalRecord;
			medicalRecordMedicationUpdate.addMedication(medication);
			cacheManager.getCache("medicalRecords").put(medicalRecord, medication);

		}
		return medicalRecordMedicationUpdate;
	}

	@Override
	public boolean deleteMedicalRecordAllergy(String firstName, String lastName, String allergy) {

		Boolean isDeleteMedicalRecordAllergy=false;
		if ((!firstName.isEmpty() && !lastName.isEmpty()) && getMedicalRecord(firstName, lastName)!= null) {
			
			List<String> allergies = getMedicalRecord(firstName, lastName).getAllergies();
			for (String allergie : allergies) {
				if (allergie.equals(allergy)) {
					
					isDeleteMedicalRecordAllergy=true;
				}

			}
			if(isDeleteMedicalRecordAllergy) {
				getMedicalRecord(firstName, lastName).getAllergies().remove(allergy);
			}
		}

		
		return isDeleteMedicalRecordAllergy;
	}

	@Override
	public boolean deleteMedicalRecordMedication(String firstName, String lastName, String medication) {
		Boolean isDeleteMedicalRecordMedication=false;
		if ((!firstName.isEmpty() && !lastName.isEmpty()) && getMedicalRecord(firstName, lastName)!= null) {
			List<String> medications = getMedicalRecord(firstName, lastName).getMedications();
			for (String medic : medications) {
				if (medic.equals(medication)) {
					
					
					isDeleteMedicalRecordMedication= true;
				 }
			}
			if(isDeleteMedicalRecordMedication) {
				getMedicalRecord(firstName, lastName).getMedications().remove(medication);
			}
		}
		
		return isDeleteMedicalRecordMedication;
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

		}
		return medicalRecordAllergyUpdate;
	}

	@Override
	public MedicalRecord addMedicalRecordForPerson(String firstName, String lastName) {
		MedicalRecord newMedicalRecord = new MedicalRecord();
		if (!StringUtils.isEmpty(firstName) && !StringUtils.isEmpty(lastName)) {
			Person medicalRecordForPerson = new Person(firstName, lastName);
			newMedicalRecord = new MedicalRecord(medicalRecordForPerson);
			try { 
				jSonFile.loadPersons().add(medicalRecordForPerson);
				jSonFile.loadMedicalRecords().add(newMedicalRecord);
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		return newMedicalRecord;
	}

}