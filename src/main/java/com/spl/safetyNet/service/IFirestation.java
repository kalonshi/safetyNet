package com.spl.safetyNet.service;

import java.util.List;
import java.util.Set;

import com.spl.safetyNet.models.FireStation;
import com.spl.safetyNet.models.Person2;


public interface IFirestation {
	public FireStation addFireStation (String fireStationNumber,String addresse );
	public FireStation  getFireStation (String fireStationNumber );
	public FireStation  getFireStationForPerson (String adresse );
	public List<Person2> getListPersonByFireStation (String fireStationNumber );
	public List<String> getPhoneList (String fireStationNumber );
	public List<Person2> getListPersonByAdresse (String adresse );
	public List<Person2> getListPersonByFireStations (List<String> fireStations );
	public void deleteStation(String fireStationNumber);
	public void deleteStationAdresse(String adresse);
}
