package com.spl.safetyNet.service;

import java.util.List;



import org.springframework.stereotype.Service;

import com.spl.safetyNet.models.MedicalRecord;
@Service

public class IMedicalRecordImpl implements IMedicalRecord{

	@Override
	public MedicalRecord addMedicalRecord(List<String> medications, List<String> allergies) {
		MedicalRecord newMedicalRecord= new MedicalRecord( medications,  allergies);
		return  newMedicalRecord ;
	}

	@Override
	public void deleteMedicalRecord() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MedicalRecord getMedicalRecord(String firstName, String lastName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateMedicalRecord(String firstName, String lastName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MedicalRecord addMedicalRecordAllergie(String firstName, String lastName, List<String> allergies) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MedicalRecord addMedicalRecordMedication(String firstName, String lastName, List<String> medications) {
		// TODO Auto-generated method stub
		return null;
	}

}
