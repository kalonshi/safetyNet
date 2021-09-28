package com.spl.safetyNet.service;

import java.util.List;

import com.spl.safetyNet.models.MedicalRecord;

public interface IMedicalRecord {
public MedicalRecord addMedicalRecord(List<String> medications, List<String> allergies);
public MedicalRecord addMedicalRecordAllergie(String firstName,String lastName, String allergies);
public MedicalRecord addMedicalRecordMedication(String firstName,String lastName, String medications);
public void deleteMedicalRecord(String firstName, String lastName);
public MedicalRecord getMedicalRecord(String firstName,String lastName);
public void updateMedicalRecord(String firstName,String lastName,String update);
}
