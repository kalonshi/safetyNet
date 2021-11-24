package com.spl.safetyNet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spl.safetyNet.Views.ListContactsForFire;
import com.spl.safetyNet.Views.PersonPhone;
import com.spl.safetyNet.models.FireStation;
import com.spl.safetyNet.service.IFirestationImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
public class FireStationController {

	@Autowired
	private IFirestationImpl iFirestationImpl;

	private static final Logger logger = LogManager.getLogger(FireStationController.class);

	@GetMapping("/fire")

	public @ResponseBody ListContactsForFire ListOfResidents(@RequestParam String address) {
		logger.info("Entering ListOfResidents method : list of contact by address :" + address);

		return iFirestationImpl.getlistContactsByAddressAndStation(address);

	}

	@GetMapping("/flood/stations")
	@ResponseBody
	public List<ListContactsForFire> getListOfHouseAndPersonsWithMedicalrecordlinkToStation(
			@RequestParam List<String> stations) {
		logger.info("Entering  flood method : list of contacts by address :" + stations.toString());

		return iFirestationImpl.listFlood(stations);

	}

	@GetMapping("/phoneAlert")

	@ResponseBody
	public List<PersonPhone> getListOfPhoneNumberFromPersonLinkWithStationNumber(@RequestParam String firestation) {
		logger.info("Entering http://localhost:8080/phoneAlert?firestation :" + firestation);

		return iFirestationImpl.phoneList(firestation);
	}

	@GetMapping("/fireStation")

	@ResponseBody
	public FireStation getStation(@RequestParam String fireStationNumber) {
		logger.info("Entering http://localhost:8080/fireStation?firestation :" + fireStationNumber);

		return iFirestationImpl.getFireStation(fireStationNumber);
	}

	@PostMapping("/fireStation/add")
	@ResponseBody
	public FireStation addFireStation(@RequestParam String fireStationNumber, @RequestParam String address) {
		logger.info("Add a fireStation :" + fireStationNumber + "at the following address :" + address);

		FireStation newFireStation = iFirestationImpl.addFireStation(fireStationNumber, address);
		return newFireStation;
	}

	@DeleteMapping("/fireStation/delete")
	@ResponseBody
	public void deleteFireStation(@RequestParam String stationNumber) {
		logger.info("Delete a fireStation :" + stationNumber);

		iFirestationImpl.deleteStation(stationNumber);
	}

	@PutMapping("/fireStation/updateNumber")
	@ResponseBody
	public void updateFireStationNumber(@RequestParam String fireStationNumber, @RequestParam String newStationNumber) {
		logger.info("Update number of fireStation :" + fireStationNumber + "to :" + newStationNumber);

		iFirestationImpl.updateFireStationNumber(fireStationNumber, newStationNumber);

	}

	@DeleteMapping("/fireStation/address")
	@ResponseBody
	public void deleteAddressFireStation(@RequestParam String address) {
		logger.info("Delete address :" + address + " from  fireStation ");

		iFirestationImpl.deleteStationAdresse(address);
	}
}
