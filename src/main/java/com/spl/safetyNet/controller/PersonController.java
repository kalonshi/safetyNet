package com.spl.safetyNet.controller;


import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spl.safetyNet.models.Person;
import com.spl.safetyNet.service.IFirestationImpl;
import com.spl.safetyNet.service.IPersonSerciveImpl;



@RestController
public class PersonController {
	@Autowired
	private IFirestationImpl iFirestationImpl;
	@Autowired
	private IPersonSerciveImpl iPersonImpl;


	
	//	TODO
	/*
	 * ajouter une nouvelle personne ; ● mettre à jour une personne existante (pour
	 * le moment, supposons que le prénom et le nom de famille ne changent pas, mais
	 * que les autres champs peuvent être modifiés) ; ● supprimer une personne
	 * (utilisez une combinaison de prénom et de nom comme identificateur unique).
	 */
	
	/*
	 * @PostMapping("/person") public void addPerson(@RequestBody Person person) {
	 * 
	 * }
	 */
	
	/*
	 * Liste de personnes dépend d'une station Cette url doit retourner une liste
	 * des personnes couvertes par la caserne de pompiers correspondante. Donc, si
	 * le numéro de station = 1, elle doit renvoyer les habitants couverts par la
	 * station numéro 1. La liste doit inclure les informations spécifiques
	 * suivantes : prénom, nom, adresse, numéro de téléphone. De plus, elle doit
	 * fournir un décompte du nombre d'adultes et du nombre d'enfants (tout individu
	 * âgé de 18 ans ou moins) dans la zone desservie.
	 * Test OK
	 */
	
	@PostMapping("/person/delete")
	public void deletePerson(String firstName, String lastName) {
		iPersonImpl.delete(firstName, lastName);
	
	}
	
	
	@PostMapping("/person/add")
	public Person add(String firstName, String lastName, String phone, String zip, String address, String city,
			String email, Date birthDate) {
		
		Person newPerson=iPersonImpl.addPerson(firstName,lastName,phone,zip,address,city,
				email,birthDate);
		
		return newPerson;
		
	}
	
	@PostMapping("person/update")
	public Person updatePerson(String firstName, String lastName, String newPhone, String newZip, String newAddress,
			String newCity, String newEmail, Date newBirthDate) {
		Person updatePersonRecord=iPersonImpl.updatePerson(firstName, lastName, newPhone, newZip, newAddress, newCity, newEmail, newBirthDate);
		
		return updatePersonRecord;
		
		
	}
	
@GetMapping ("/stationNumber/{stationnumber}")
	public List<String[]> getListPersonLinkedWithStation(@PathVariable String stationnumber){
	
	
	return iPersonImpl.listPersonsLinkToStationSelected(stationnumber);
		
	}
	
	/*
	 * ChildAlert: Liste des personnes <18 avec liste des parents associés 
	 * Cette url doit retourner une liste d'enfants (tout individu âgé de 18 ans ou moins) habitant à cette adresse.
La liste doit comprendre le prénom et le nom de famille de chaque enfant, son âge et une liste des autres
membres du foyer. S'il n'y a pas d'enfant, cette url peut renvoyer une chaîne vide.

	 */
	//http://localhost:8080/childAlert?address=<address>
	@GetMapping("/childAlert/{adress}")
	public List<String[]> getListOfMinorAndRelative(@PathVariable String adress) {
		/* return null; */
		
		
		
		 return iPersonImpl.printlistMinorsByAddress(adress); 
	}
	//http://localhost:8080/fire?address=<address>

		@GetMapping("/fire/{adress}")
		public List<String[]> getListOfResidents(@PathVariable String adress) {
				
			return iPersonImpl.printlistPersonByadress(adress);
		}
	/*
	 * Liste des numeros de résident dépendant d'une caserne 
	 * Cette url doit
	 * retourner la liste des habitants vivant à l’adresse donnée ainsi que le
	 * numéro de la caserne de pompiers la desservant. La liste doit inclure le nom,
	 * le numéro de téléphone, l'âge et les antécédents médicaux (médicaments,
	 * posologie et allergies) de chaque personne.
	 */

	//Test Ok
	@GetMapping("/phoneAlert/{stationNumber}") 
	public Set<String> getListOfPhoneNumberFromPersonLinkWithStationNumber(@PathVariable String stationNumber ) {
		
		return iFirestationImpl.getListOfPhoneNumbersFromPersonLinkWithSelectedStation(stationNumber);
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
	
	@GetMapping("/flood/{stationNumber}")
		public Set<String[]> getListOfHouseAndPersonsWithMedicalrecordlinkToStation(@PathVariable java.util.List<String> fireStations) {
			return iFirestationImpl.ListOfPersonLinkWithSelectedStations(fireStations);
			
		}
	
	
	/*
	 * Liste personnes avec nom et prenom 
	 * Cette url doit retourner le nom,
	 * l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments,
	 * posologie, allergies) de chaque habitant. Si plusieurs personnes portent le
	 * même nom, elles doivent toutes apparaître.
	 */
	@GetMapping("/personInfo/{firstName,lastName}")
	public List<String[]> getPersonInfo(@PathVariable String firstName,@PathVariable String lastName) {
		
		return iPersonImpl.printlistPersons(firstName, lastName);
	}
	
	 
	/*Liste personnes avec nom et prenom 
	 * Cette url doit retourner les adresses mail de tous les habitants de la ville
	 * Test Ok
	 */
		@GetMapping("/communityEmail/{city}")
		public Set <String>getEmailCitizens(@PathVariable String city) {
			Set<String> list=iPersonImpl.getListEmail(city);
			return list;
		}
	
	
	
}
