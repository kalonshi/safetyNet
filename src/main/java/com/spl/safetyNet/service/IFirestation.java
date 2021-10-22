package com.spl.safetyNet.service;

import java.util.List;

import java.util.TreeMap;

import com.spl.safetyNet.Views.ListContactsForFire;
import com.spl.safetyNet.Views.PersonFire;
import com.spl.safetyNet.Views.PersonPhone;
import com.spl.safetyNet.models.FireStation;
import com.spl.safetyNet.models.Person;

public interface IFirestation {
	public FireStation addFireStation(String fireStationNumber, String addresse);

	public FireStation getFireStation(String fireStationNumber);

	public FireStation getFireStationForPerson(String adresse);

	public ListContactsForFire getlistContactsByAddressAndStation(String adresse);

	public List<Person> getListPersonByFireStation(String fireStationNumber);

	public List<PersonPhone> phoneList(String fireStationNumber);

	public List<Person> getListPersonByAdresse(String adresse);

	public List<PersonFire> getListPersonFireByAdresse(String adresse);

	public List<Person> getListPersonByFireStations(List<String> fireStations);

	public List<PersonFire> getListPersonFireByFireStations(List<String> fireStations);

	public List<ListContactsForFire> listFlood(List<String> fireStations);

	public boolean deleteStation(String fireStationNumber);

	public void deleteStationAdresse(String adresse);

	public List<Person> ListOfPersonLinkWithSelectedStations(List<String> fireStations);

	public FireStation updateFireStationNumber(String fireStationNumber, String newStationNumber);
}
