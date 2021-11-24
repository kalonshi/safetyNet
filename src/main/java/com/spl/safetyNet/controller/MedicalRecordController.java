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

	@PostMapping("/medicalRecord/delete")
	@ResponseBody
	public void deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {
		logger.info("delete MedicalRecord of " + firstName + " " + lastName);

		iMedicalRecordImpl.deleteMedicalRecord(firstName, lastName);
	}

	@DeleteMapping("/medicalRecord/deleteMedication")
	@ResponseBody
	public void deleteMedication(@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String medication) {
		logger.info("delete medication :" + medication + " from medicalRecord of " + firstName + " " + lastName);

		iMedicalRecordImpl.deleteMedicalRecordMedication(firstName, lastName, medication);
	}

	@DeleteMapping("/medicalRecord/deleteAllergy")
	@ResponseBody
	public void deleteAllergy(@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String allergy) {
		logger.info("delete allergy :" + allergy + " from medicalRecord of " + firstName + " " + lastName);

		iMedicalRecordImpl.deleteMedicalRecordAllergy(firstName, lastName, allergy);
	}

	@PutMapping("/medicalRecord/updateMedication")
	@ResponseBody
	public void updateMedicalRecord(@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String medication, @RequestParam String newMedication) {
		logger.info("update medication :" + medication + " to " + firstName + " " + lastName);

		iMedicalRecordImpl.updateMedicalRecordMedication(firstName, lastName, medication, newMedication);
	}

	@PutMapping("/medicalRecord/updateAllergy")
	@ResponseBody
	public void updateAllergy(@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String allergy, @RequestParam String newAllergy) {
		logger.info("update medication :" + allergy + " to " + firstName + " " + lastName);

		iMedicalRecordImpl.updateMedicalRecordAllergy(firstName, lastName, allergy, newAllergy);

	}

	@PostMapping("/medicalRecord/addMedication")
	@ResponseBody
	public void addMedication(@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String medication) {
		logger.info("add medication :" + medication + " to " + firstName + " " + lastName);
		iMedicalRecordImpl.addMedicalRecordMedication(firstName, lastName, medication);
	}

	@PostMapping("/medicalRecord/addAllergy")
	@ResponseBody
	public void addAllergy(@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String allergy) {
		logger.info("add allergy :" + allergy + " to " + firstName + " " + lastName);
		iMedicalRecordImpl.addMedicalRecordAllergie(firstName, lastName, allergy);

	}
	@PostMapping("/medicalRecord/add")
	@ResponseBody
	public MedicalRecord addPersonMedicaleRecord(@RequestParam String firstName, @RequestParam String lastName) {
		MedicalRecord addMedicalRecord=iMedicalRecordImpl.addMedicalRecordForPerson(firstName, lastName);
return addMedicalRecord;
	}
}