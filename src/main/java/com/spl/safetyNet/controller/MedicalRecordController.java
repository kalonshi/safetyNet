package com.spl.safetyNet.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spl.safetyNet.models.MedicalRecord;
import com.spl.safetyNet.service.IMedicalRecordImpl;

@RestController
public class MedicalRecordController {

	@Autowired
	private IMedicalRecordImpl iMedicalRecordImpl;

	private static final Logger logger = LogManager.getLogger(MedicalRecordController.class);

	@GetMapping("/medicalRecord")
	@ResponseBody
	public MedicalRecord getMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {
		logger.info("get MedicalRecord of " + firstName + " " + lastName);

		return iMedicalRecordImpl.getMedicalRecord(firstName, lastName);
	}

	@DeleteMapping("/medicalRecord/delete")
	@ResponseBody
	public void deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {
		logger.info("delete MedicalRecord of " + firstName + " " + lastName);

		iMedicalRecordImpl.deleteMedicalRecord(firstName, lastName);
	}

	
	@PostMapping("/medicalRecord/add")
	@ResponseBody
	public MedicalRecord addPersonMedicaleRecord(@RequestParam String firstName, @RequestParam String lastName) {
		MedicalRecord addMedicalRecord=iMedicalRecordImpl.addMedicalRecordForPerson(firstName, lastName);
return addMedicalRecord;
	}
}