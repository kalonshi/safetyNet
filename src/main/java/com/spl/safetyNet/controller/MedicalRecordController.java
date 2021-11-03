package com.spl.safetyNet.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
	
	private Logger logger = LogManager.getLogger(MedicalRecordController.class);
	
	
	@PostMapping("/medicalRecord/add")
	@ResponseBody
	public void addMedicalRecord(@RequestParam MedicalRecord medicalRecord) {
		logger.info("add MedicalRecord");
		
		/* medicalRecordDao.save(medicalRecord); */
	}

	@PostMapping("/medicalRecord/delete")
	@ResponseBody
	public void deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {
		logger.info("delete MedicalRecord of " +firstName+" "+lastName);
		
		iMedicalRecordImpl.deleteMedicalRecord(firstName, lastName);
	}

	@DeleteMapping("/medicalRecord/deleteMedication")
	@ResponseBody
	public void deleteMedication(@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String medication) {
		logger.info("delete medication :"+medication+" from medicalRecord of " +firstName+" "+lastName);
		
		iMedicalRecordImpl.deleteMedicalRecordMedication(firstName, lastName, medication);
	}

	@DeleteMapping("/medicalRecord/deleteAllergy")
	@ResponseBody
	public void deleteAllergy(@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String allergy) {
		logger.info("delete allergy :"+allergy+" from medicalRecord of " +firstName+" "+lastName);
		
		iMedicalRecordImpl.deleteMedicalRecordAllergy(firstName, lastName, allergy);
	}

	@PutMapping("/medicalRecord/updateMedication")
	@ResponseBody
	public void updateMedicalRecord(@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String medication, @RequestParam String newMedication) {
		logger.info("update medication :"+medication+" to " +firstName+" "+lastName);
		
		
		iMedicalRecordImpl.updateMedicalRecordMedication(firstName, lastName, medication, newMedication);
	}

	@PutMapping("/medicalRecord/updateAllergy")
	@ResponseBody
	public void updateAllergy(@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String allergy, @RequestParam String newAllergy) {
		logger.info("update medication :"+allergy+" to " +firstName+" "+lastName);
		
		iMedicalRecordImpl.updateMedicalRecordAllergy(firstName, lastName, allergy, newAllergy);
	
	
	}

	@PostMapping("/medicalRecord/addMedication")
	@ResponseBody
	public void addMedication(@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String medication) {
		logger.info("add medication :"+medication+" to " +firstName+" "+lastName);
		iMedicalRecordImpl.addMedicalRecordMedication(firstName, lastName, medication);
	}

	@PostMapping("/medicalRecord/addAllergy")
	@ResponseBody
	public void addAllergy(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String allergy) {
		logger.info("add allergy :"+allergy+" to " +firstName+" "+lastName);
		iMedicalRecordImpl.addMedicalRecordAllergie(firstName, lastName, allergy);

	}
}