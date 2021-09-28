package com.spl.safetyNet.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spl.safetyNet.models.FireStation;

@RestController
public class FireStationController {

	/*
	 * ajout d'un mapping caserne/adresse ; ● mettre à jour le numéro de la caserne
	 * de pompiers d'une adresse ; ● supprimer le mapping d'une caserne ou d'une
	 * adresse.
	 */
	@PostMapping("/fireStation")
	public void addFireStation(@RequestBody FireStation fireStation) {
		
	}
}
