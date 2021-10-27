package com.spl.safetyNet.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spl.safetyNet.Views.InfoPerson;
import com.spl.safetyNet.Views.ListPerson;
import com.spl.safetyNet.Views.PersonEmail;
import com.spl.safetyNet.Views.PersonPrint;
import com.spl.safetyNet.Views.Personchild;
import com.spl.safetyNet.models.Person;
import com.spl.safetyNet.service.IFirestationImpl;
import com.spl.safetyNet.service.IPersonSerciveImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
public class PersonController {
	@Autowired
	private IFirestationImpl iFirestationImpl;
	@Autowired
	private IPersonSerciveImpl iPersonImpl;

	private static final Logger logger = LogManager.getLogger(PersonController.class);
 
	// TODO
	/*
	 * ajouter une nouvelle personne ; ● mettre à jour une personne existante (pour
	 * le moment, supposons que le prénom et le nom de famille ne changent pas, mais
	 * que les autres champs peuvent être modifiés) ; ● supprimer une personne
	 * (utilisez une combinaison de prénom et de nom comme identificateur unique).
	 */


	// http://localhost:8080/firestation?stationNumber=<station_number>

	@GetMapping("/firestation")

	@ResponseBody
	public ListPerson getListPersonLinkedWithStation(@RequestParam String stationNumber) {

		return iPersonImpl.listPersonsLinkToStationSelected(stationNumber);

	}

	// TEST OK **************
	// http://localhost:8080/childAlert?address=<address>
	@GetMapping("/childAlert")
	@ResponseBody
	public List<Personchild> getListOfMinorAndRelative(@RequestParam String address) {
		/* return null; */

		return iPersonImpl.printlistMinorsByAddress(address);
	}
	


	// Test A verifier
	// http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
	@GetMapping("/personInfo")
	@ResponseBody
	public List<InfoPerson> getPersonInfo(@RequestParam String firstName, @RequestParam String lastName) {
		if(!firstName.isEmpty()&&!lastName.isEmpty()) {
			return iPersonImpl.getListInfoPerson(firstName, lastName);
		}
		return null;
	}
	

	
	
	// http://localhost:8080/communityEmail?city OK

		@GetMapping("/communityEmail")
		@ResponseBody
		public List<PersonEmail> getListEmailCitizens(@RequestParam String city) {
			List<PersonEmail> list = iPersonImpl.listEmail(city);
			return list;
		}	
	
	

	@DeleteMapping("/person/delete")
	@ResponseBody
	public void deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
		iPersonImpl.delete(firstName, lastName);

	}

	@PostMapping("/person/add")
	@ResponseBody
	public Person add(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String phone,
			@RequestParam String zip, @RequestParam String address, @RequestParam String city,
			@RequestParam String email, @RequestParam Date birthDate) {

		Person newPerson = iPersonImpl.addPerson(firstName, lastName, phone, zip, address, city, email, birthDate);

		return newPerson;

	}

	@PutMapping("person/update")
	@ResponseBody
	public Person updatePerson(@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String newPhone, @RequestParam String newZip, @RequestParam String newAddress,
			@RequestParam String newCity, @RequestParam String newEmail, @RequestParam Date newBirthDate) {
		Person updatePersonRecord = iPersonImpl.updatePerson(firstName, lastName, newPhone, newZip, newAddress, newCity,
				newEmail);

		return updatePersonRecord;

	}

}
