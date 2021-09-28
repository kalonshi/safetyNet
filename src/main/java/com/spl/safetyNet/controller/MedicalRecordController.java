package com.spl.safetyNet.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.spl.safetyNet.models.MedicalRecord;

@RestController
public class MedicalRecordController {
	/*
	 * ajouter un dossier médical ; ● mettre à jour un dossier médical existant
	 * (comme évoqué précédemment, supposer que le prénom et le nom de famille ne
	 * changent pas) ; ● supprimer un dossier médical (utilisez une combinaison de
	 * prénom et de nom comme identificateur unique).
	 */
	/* @Autowired MedicalRecord medicalRecordDao; */
	@PostMapping("/medicalRecord")
	public void addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
		/* medicalRecordDao.save(medicalRecord); */
	}
	
	
	
}
