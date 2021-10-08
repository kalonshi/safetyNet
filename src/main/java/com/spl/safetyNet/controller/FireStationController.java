package com.spl.safetyNet.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spl.safetyNet.models.FireStation;
import com.spl.safetyNet.service.IFirestation;
import com.spl.safetyNet.service.IFirestationImpl;
import com.spl.safetyNet.service.IPerson;
import com.spl.safetyNet.service.IPersonSerciveImpl;

@RestController
public class FireStationController {
	@Autowired
	private IFirestationImpl iFirestationImpl;
	@Autowired
	private IPersonSerciveImpl iPersonImpl;
	/*
	 * ajout d'un mapping caserne/adresse ; ● mettre à jour le numéro de la caserne
	 * de pompiers d'une adresse ; ● supprimer le mapping d'une caserne ou d'une
	 * adresse.
	 */
	// Recherche http://localhost:8080/firestation?stationNumber=%3Cstation_number
		
	@GetMapping("/firestation/{stationNumber}")
	    public List<String[]> personsLinkWithSelectedStation(@PathVariable String stationNumber) {
	    	List<String[]> listOfPersons= iFirestationImpl.ListOfPersonLinkWithSelectedStation(stationNumber);
	        
			return listOfPersons;
	    }
	
	
	@PostMapping("/fireStation/add")
	public FireStation addFireStation(@RequestBody String fireStationNumber,@RequestBody String address) {
		FireStation newFireStation =iFirestationImpl.addFireStation(fireStationNumber, address);
	return newFireStation;
	}
	
	@PostMapping("/fireStation/delete")
	public void deleteFireStation(@RequestBody String fireStationNumber) {
		iFirestationImpl.deleteStation(fireStationNumber);
	}
	@PostMapping("/fireStation/updateNumber")
	public FireStation updateFireStationNumber(@RequestBody String fireStationNumber,@RequestBody String newStationNumber) {
		FireStation updateNumberFireStation=iFirestationImpl.updateFireStationNumber(fireStationNumber, newStationNumber);
	return updateNumberFireStation;
	}
	
	@PostMapping("/fireStation/delete/Address")
	public void deleteAddressFireStation(@RequestBody String address) {
		iFirestationImpl.deleteStationAdresse(address);
	}
}
