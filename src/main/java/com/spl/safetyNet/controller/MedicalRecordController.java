package com.spl.safetyNet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spl.safetyNet.models.MedicalRecord;
import com.spl.safetyNet.service.IMedicalRecordImpl;
import com.spl.safetyNet.service.IPersonSerciveImpl;

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
	private IPersonSerciveImpl IPersonSerciveImpl;

	@PostMapping("/medicalRecord/add")
	public void addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
		/* medicalRecordDao.save(medicalRecord); */
	}

	@PostMapping("/medicalRecord/delete")
	public void deleteMedicalRecord(@RequestBody String firstName, @RequestBody String lastName) {
		iMedicalRecordImpl.deleteMedicalRecord(firstName, lastName);
	}

	@PostMapping("/medicalRecord/deleteMedication")
	public void deleteMedication(@RequestBody String firstName, @RequestBody String lastName,
			@RequestBody String medication) {
		iMedicalRecordImpl.deleteMedicalRecordMedication(firstName, lastName, medication);
	}

	@PostMapping("/medicalRecord/deleteAllergy")
	public void deleteAllergy(@RequestBody String firstName, @RequestBody String lastName,
			@RequestBody String allergy) {
		iMedicalRecordImpl.deleteMedicalRecordAllergy(firstName, lastName, allergy);
	}

	/*
	 * @PostMapping("/medicalRecord/updateMedication") public void
	 * updateMedicalRecord(@RequestBody String firstName, @RequestBody String
	 * lastName) {
	 * 
	 * }
	 * 
	 * @PostMapping("/medicalRecord/updateAllergy") public void
	 * updateMedication(@RequestBody String firstName, @RequestBody String lastName,
	 * 
	 * @RequestBody String firstNamej, @RequestBody String lastNamej) {
	 * 
	 * }
	 */

	@PostMapping("/medicalRecord/addMedication")
	public void addMedication(@RequestBody String firstName, @RequestBody String lastName,
			@RequestBody String medication) {
		iMedicalRecordImpl.addMedicalRecordMedication(firstName, lastName, medication);
	}

	@PostMapping("/medicalRecord/addAllergy")
	public void addAllergy(@RequestBody String firstName, @RequestBody String lastName, @RequestBody String allergy) {
		iMedicalRecordImpl.addMedicalRecordAllergie(firstName, lastName, allergy);

	}
}