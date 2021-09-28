package com.spl.safetyNet.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spl.safetyNet.models.FireStation;
import com.spl.safetyNet.models.Person2;
@Service

public class IFirestationImpl implements IFirestation{
@Autowired 
private JsonFileData3 jSonFile;
	@Override
	public FireStation addFireStation(String fireStationNumber, String addresse) {
		// TODO Auto-generated method stub
		List<Person2> listPerson;
		try {
			listPerson = jSonFile.loadPersons();
			List<Person2> FireStationByPersonAdresse = new ArrayList<>();
			
			listPerson.forEach(p-> {if (p.getAddress().contains(addresse)) {
				FireStationByPersonAdresse.add(p);
			}
			} );
			
			FireStation newFireStation=new FireStation(fireStationNumber);
			newFireStation.addAddress(addresse);
			newFireStation.setListOfPersons(FireStationByPersonAdresse);
			return newFireStation;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public FireStation getFireStation(String fireStationNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FireStation getFireStationForPerson(String adresse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Person2> getListPersonByFireStation(String fireStationNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getPhoneList(String fireStationNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Person2> getListPersonByAdresse(String adresse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Person2> getListPersonByFireStations(List<String> fireStations) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteStation(String fireStationNumber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteStationAdresse(String adresse) {
		// TODO Auto-generated method stub
		
	}

}
