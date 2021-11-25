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
				
				e.printStackTrace();
			}
			
		}
		return isDeleted;

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