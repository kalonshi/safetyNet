package com.spl.safetyNet.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spl.safetyNet.models.MedicalRecord;
import com.spl.safetyNet.service.IMedicalRecordImpl;
import com.spl.safetyNet.service.IPersonSerciveImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
@RestController
public class MedicalRecordController {
	/*
	 * ajouter un dossier médical ; ● mettre à jour un dossier médical existant
	 * (comme évoqué précédemment, supposer que le prénom et le nom de famille ne
	 * changent pas) ; ● supprimer un dossier médical (utilisez une combinaison de
	 * prénom et de nom comme identificateur unique).
	 */
	@Autowired
	private IMedicalRecordImpl iMedicalRecordImpl;
	@Autowired
	private IPersonSerciveImpl IPersonSerciveImpl;
	private static final Logger logger = LogManager.getLogger(PersonController.class);

	@PostMapping("/medicalRecord/add")
	public void addMedicalRecord(@RequestParam MedicalRecord medicalRecord) {
		/* medicalRecordDao.save(medicalRecord); */
	}

	@PostMapping("/medicalRecord/delete")
	public void deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {
		iMedicalRecordImpl.deleteMedicalRecord(firstName, lastName);
	}

	@PostMapping("/medicalRecord/deleteMedication")
	public void deleteMedication(@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String medication) {
		iMedicalRecordImpl.deleteMedicalRecordMedication(firstName, lastName, medication);
	}

	@PostMapping("/medicalRecord/deleteAllergy")
	public void deleteAllergy(@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String allergy) {
		iMedicalRecordImpl.deleteMedicalRecordAllergy(firstName, lastName, allergy);
	}

	@PostMapping("/medicalRecord/updateMedication")
	public void updateMedicalRecord(@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String medication, @RequestParam String newMedication) {
		iMedicalRecordImpl.updateMedicalRecordMedication(firstName, lastName, medication, newMedication);
	}

	@PostMapping("/medicalRecord/updateAllergy")
	public void updateMedication(@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String allergy, @RequestParam String newAllergy) {

		iMedicalRecordImpl.updateMedicalRecordAllergy(firstName, lastName, allergy, newAllergy);
	}

	@PostMapping("/medicalRecord/addMedication")
	public void addMedication(@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String medication) {
		iMedicalRecordImpl.addMedicalRecordMedication(firstName, lastName, medication);
	}

	@PostMapping("/medicalRecord/addAllergy")
	public void addAllergy(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String allergy) {
		iMedicalRecordImpl.addMedicalRecordAllergie(firstName, lastName, allergy);

	}
}