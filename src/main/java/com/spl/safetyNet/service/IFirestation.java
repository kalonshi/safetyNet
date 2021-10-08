package com.spl.safetyNet.service;

import java.util.List;
import java.util.Set;

import com.spl.safetyNet.models.FireStation;
import com.spl.safetyNet.models.Person;

public interface IFirestation {
	public FireStation addFireStation(String fireStationNumber, String addresse);

	public FireStation getFireStation(String fireStationNumber);

	public Set<FireStation> getFireStationForPerson(String adresse);

	public Set<Person> getListPersonByFireStation(String fireStationNumber);

	public Set<String> getPhoneList(String fireStationNumber);

	public Set<Person> getListPersonByAdresse(String adresse);

	public Set<Person> getListPersonByFireStations(List<String> fireStations);

	public void deleteStation(String fireStationNumber);

	public void deleteStationAdresse(String adresse);

	public Set<String> getListOfPhoneNumbersFromPersonLinkWithSelectedStation(String fireStationNumber);

	public List<String[]> ListOfPersonLinkWithSelectedStation(String fireStationNumber);
	public Set<String[]> ListOfPersonLinkWithSelectedStations(List<String> fireStations);
	public FireStation updateFireStationNumber(String fireStationNumber,String newStationNumber);
}
