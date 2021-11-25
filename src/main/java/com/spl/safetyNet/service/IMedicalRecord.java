package com.spl.safetyNet.service;

import java.util.List;

import com.spl.safetyNet.models.MedicalRecord;

public interface IMedicalRecord {
	public MedicalRecord addMedicalRecord(List<String> medications, List<String> allergies);

	public MedicalRecord addMedicalRecordForPerson(String firstName, String lastName);

	
	public boolean deleteMedicalRecord(String firstName, String lastName);

	
	public MedicalRecord getMedicalRecord(String firstName, String lastName);

	

}
