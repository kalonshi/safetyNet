package com.spl.safetyNet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spl.safetyNet.Views.ListContactsForFire;
import com.spl.safetyNet.Views.ListPerson;
import com.spl.safetyNet.Views.PersonFire;
import com.spl.safetyNet.Views.PersonPhone;
import com.spl.safetyNet.models.FireStation;
import com.spl.safetyNet.models.Person;
import com.spl.safetyNet.service.IFirestation;
import com.spl.safetyNet.service.IFirestationImpl;
import com.spl.safetyNet.service.IPerson;
import com.spl.safetyNet.service.IPersonSerciveImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class FireStationController {
	@Autowired
	private IFirestationImpl iFirestationImpl;
	private Logger logger = LogManager.getLogger(FireStationController.class);
	
	/*
	 * ajout d'un mapping caserne/adresse ; ● mettre à jour le numéro de la caserne
	 * de pompiers d'une adresse ; ● supprimer le mapping d'une caserne ou d'une
	 * adresse.
	 */
	
	@GetMapping("/fire") //OK

	public @ResponseBody ListContactsForFire ListOfResidents(@RequestParam String address) {
		logger.info("Entering ListOfResidents method : list of contact by address :"+address);
		
			return iFirestationImpl.getlistContactsByAddressAndStation(address);
		
	}
	
	// **********A TESTER*****
	// http://localhost:8080/flood/stations?stations=<a list of station_numbers>
	@GetMapping("/flood/Stations")
	@ResponseBody
	public List<ListContactsForFire> getListOfHouseAndPersonsWithMedicalrecordlinkToStation(
			@RequestParam java.util.List<String> stations) {
		logger.info("Entering  flood method : list of contacts by address :"+stations.toString());
		
				return iFirestationImpl.listFlood(stations);
		
	}
	// http://localhost:8080/phoneAlert?firestation=1 Test OK
	@GetMapping("/phoneAlert")

	@ResponseBody
	public List<PersonPhone> getListOfPhoneNumberFromPersonLinkWithStationNumber(@RequestParam String firestation) {
		logger.info("Entering http://localhost:8080/phoneAlert?firestation :"+firestation);
		
		return iFirestationImpl.phoneList(firestation);
	}

	// Test Ok pour adresse et number. ListPerson à checker
	@PostMapping("/fireStation/add")
	@ResponseBody
	public FireStation addFireStation(@RequestParam String fireStationNumber, @RequestParam String address) {
		logger.info("Add a fireStation :"+fireStationNumber+ "at the following address :" +address);
		
		FireStation newFireStation = iFirestationImpl.addFireStation(fireStationNumber, address);
		return newFireStation;
	}

	@DeleteMapping("/fireStation/delete")
	public void deleteFireStation(@RequestParam String fireStationNumber) {
		logger.info("Delete a fireStation :"+fireStationNumber);
		
		iFirestationImpl.deleteStation(fireStationNumber);
	}

	@PutMapping("/fireStation/updateNumber")
	public boolean updateFireStationNumber(@RequestParam String fireStationNumber,
			@RequestBody String newStationNumber) {
		logger.info("Update number of fireStation :"+fireStationNumber+ "to :" +newStationNumber);
		
		boolean updateNumberFireStation = iFirestationImpl.updateFireStationNumber(fireStationNumber,
				newStationNumber);
		return true;
	}

	@DeleteMapping("/fireStation/delete/")
	public void deleteAddressFireStation(@RequestParam String address) {
		logger.info("Delete address :"+address+" from  fireStation ");
		
		iFirestationImpl.deleteStationAdresse(address);
	}
}
