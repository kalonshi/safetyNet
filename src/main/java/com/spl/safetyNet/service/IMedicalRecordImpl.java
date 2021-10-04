package com.spl.safetyNet.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spl.safetyNet.models.MedicalRecord;
@Service

public class IMedicalRecordImpl implements IMedicalRecord{
	@Autowired 
	private JsonFileData3 jSonFile;
	@Override
	public MedicalRecord addMedicalRecord(List<String> medications, List<String> allergies) {
		MedicalRecord newMedicalRecord= new MedicalRecord( medications,  allergies);
		return  newMedicalRecord ;
	}

	@Override
	public void deleteMedicalRecord(String firstName, String lastName) {
		// TODO Auto-generated method stub
		MedicalRecord medicalRecordSelected= getMedicalRecord(firstName, lastName);
		try {
			List<MedicalRecord> medicalRecords=jSonFile.loadMedicalRecords();
			medicalRecords.remove(medicalRecordSelected);
		
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		medicalRecordSelected=null;
	
	}

	@Override
	public MedicalRecord getMedicalRecord(String firstName, String lastName) {
		// TODO Auto-generated method stub
		try {
			List<MedicalRecord> medicalRecords=jSonFile.loadMedicalRecords();
		for (MedicalRecord m:medicalRecords) {
			if ((m.getPerson().getFirstName().equals(firstName))&&(m.getPerson().getLastName().equals(lastName))) 
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
		MedicalRecord medicalRecordSelected =getMedicalRecord(firstName, lastName);
		medicalRecordSelected.addAllergie(allergy);
		return medicalRecordSelected;
	}

	@Override
	public MedicalRecord addMedicalRecordMedication(String firstName, String lastName,String medication) {
		// TODO Auto-generated method stub
		MedicalRecord medicalRecordSelected =getMedicalRecord(firstName, lastName);
		medicalRecordSelected.addMedication(medication);
		return medicalRecordSelected;
	}

	@Override
	public void updateMedicalRecord(String firstName, String lastName, String update) {
		// TODO Auto-generated method stub
		
	}

}
