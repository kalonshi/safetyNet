package com.spl.safetyNet.service;

import java.util.List;

import com.spl.safetyNet.models.MedicalRecord;

public interface IMedicalRecord {
public boolean addMedicalRecord(List<String> medications, List<String> allergies);
public boolean addMedicalRecordAllergie(String firstName,String lastName, String allergies);
public boolean addMedicalRecordMedication(String firstName,String lastName, String medications);
public boolean deleteMedicalRecord(String firstName, String lastName);
public boolean deleteMedicalRecordAllergy(String firstName, String lastName,String allergy);
public boolean deleteMedicalRecordMedication(String firstName, String lastName,String medication);

public MedicalRecord getMedicalRecord(String firstName,String lastName);
public boolean updateMedicalRecordMedication(String firstName,String lastName,String medication,String newMedication);
public boolean updateMedicalRecordAllergy(String firstName,String lastName,String allergy,String newAllergy);

}
