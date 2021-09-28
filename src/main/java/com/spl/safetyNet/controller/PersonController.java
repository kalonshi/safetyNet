package com.spl.safetyNet.controller;

import java.awt.List;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class PersonController<Person> {



	
	//	TODO
	/*
	 * ajouter une nouvelle personne ; ● mettre à jour une personne existante (pour
	 * le moment, supposons que le prénom et le nom de famille ne changent pas, mais
	 * que les autres champs peuvent être modifiés) ; ● supprimer une personne
	 * (utilisez une combinaison de prénom et de nom comme identificateur unique).
	 */
	
	@PostMapping("/person")
	public void addPerson(@RequestBody Person person) {
		
	}
	
	/*
	 * Liste de personnes dépend d'une station Cette url doit retourner une liste
	 * des personnes couvertes par la caserne de pompiers correspondante. Donc, si
	 * le numéro de station = 1, elle doit renvoyer les habitants couverts par la
	 * station numéro 1. La liste doit inclure les informations spécifiques
	 * suivantes : prénom, nom, adresse, numéro de téléphone. De plus, elle doit
	 * fournir un décompte du nombre d'adultes et du nombre d'enfants (tout individu
	 * âgé de 18 ans ou moins) dans la zone desservie.
	 */

	@GetMapping("/firestation/stationNumber{stationNumber}")
	public List getPersonLinkwithStation(@PathVariable int stationNumber){
		return null;
		
	}
	
	/*
	 * ChildAlert: Liste des personnes <18 avec liste des parents associés 
	 * Cette url doit retourner une liste d'enfants (tout individu âgé de 18 ans ou moins) habitant à cette adresse.
La liste doit comprendre le prénom et le nom de famille de chaque enfant, son âge et une liste des autres
membres du foyer. S'il n'y a pas d'enfant, cette url peut renvoyer une chaîne vide.

	 */
	
	@GetMapping("/childAlert/address{adress}")
	public List getListOfMinorAndRelative(@PathVariable String adress) {
		
		
		return null;
	}
	
	/*
	 * Liste des numeros de résident dépendant d'une caserne 
	 * Cette url doit
	 * retourner la liste des habitants vivant à l’adresse donnée ainsi que le
	 * numéro de la caserne de pompiers la desservant. La liste doit inclure le nom,
	 * le numéro de téléphone, l'âge et les antécédents médicaux (médicaments,
	 * posologie et allergies) de chaque personne.
	 */

	
	@GetMapping("/phoneAlert/firestation{stationNumber}") 
	public List getListOfPhoneNumberFromPersonLinkWithStationNumber(@PathVariable int stationNumber ) {
		
		return null;
	}
	
	/*
	 * Liste personnes par foyer dependant de plusieurs stations Avec antécedent
	 * medicaux
	 *  Cette url doit retourner une liste de tous les foyers desservis par
	 * la caserne. Cette liste doit regrouper les personnes par adresse. Elle doit
	 * aussi inclure le nom, le numéro de téléphone et l'âge des habitants, et faire
	 * figurer leurs antécédents médicaux (médicaments, posologie et allergies) à
	 * côté de chaque nom
	 */
	
	@GetMapping("/flood/stations{stationNumber}")
		public List getListOfHouseAndPersonsWithMedicalrecordlinkToStation(@PathVariable int stationNumber) {
			return null;
		}
	
	
	/*
	 * Liste personnes avec nom et prenom 
	 * Cette url doit retourner le nom,
	 * l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments,
	 * posologie, allergies) de chaque habitant. Si plusieurs personnes portent le
	 * même nom, elles doivent toutes apparaître.
	 */
	@GetMapping("/personInfo{firstName,lastName}")
	public List getPersonInfo(@PathVariable String firstName,@PathVariable String lastName) {
		return null;
	}
	
	 
	/*Liste personnes avec nom et prenom 
	 * Cette url doit retourner les adresses mail de tous les habitants de la ville
	 */
		@GetMapping("/communityEmail{city}")
		public List getEmailCitizens(@PathVariable String city) {
			return null;
		}
	
	
	
}
