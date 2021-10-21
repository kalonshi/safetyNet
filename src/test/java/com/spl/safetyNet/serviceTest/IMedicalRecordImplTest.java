package com.spl.safetyNet.serviceTest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import com.spl.safetyNet.models.MedicalRecord;

import com.spl.safetyNet.service.IMedicalRecordImpl;



public class IMedicalRecordImplTest {
	private static IMedicalRecordImpl iMedicalRecordImpl;
	private MedicalRecord medicalRecord;
	@BeforeAll
	private static void setUp() {
		iMedicalRecordImpl=new IMedicalRecordImpl(); 
	}
	
	@BeforeEach
	private void setUpPerTest() {
		medicalRecord = new MedicalRecord();
	}
	
}
