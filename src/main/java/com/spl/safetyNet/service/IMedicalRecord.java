package com.spl.safetyNet.service;

import java.util.List;

import com.spl.safetyNet.models.MedicalRecord;

public interface IMedicalRecord {
	public MedicalRecord addMedicalRecord(List<String> medications, List<String> allergies);

	public MedicalRecord addMedicalRecordForPerson(String firstName, String lastName);

	public MedicalRecord addMedicalRecordAllergie(String firstName, String lastName, String allergies);

	public MedicalRecord addMedicalRecordMedication(String firstName, String lastName, String medications);

	public boolean deleteMedicalRecord(String firstName, String lastName);

	public boolean deleteMedicalRecordAllergy(String firstName, String lastName, String allergy);

	public boolean deleteMedicalRecordMedication(String firstName, String lastName, String medication);

	public MedicalRecord getMedicalRecord(String firstName, String lastName);

	public MedicalRecord updateMedicalRecordMedication(String firstName, String lastName, String medication,
			String newMedication);

	public MedicalRecord updateMedicalRecordAllergy(String firstName, String lastName, String allergy,
			String newAllergy);

}
