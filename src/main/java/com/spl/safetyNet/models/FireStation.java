package com.spl.safetyNet.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class FireStation {
	

	private Set<String> addresses = new HashSet<>();
	private String stationNumber;
	private List<Person2> listOfPersons; 
	
	public FireStation() {
		super();
		// TODO Auto-generated constructor stub
	}



	public FireStation(String stationNumber) {
		this.stationNumber = stationNumber;
	}
	
	public FireStation addAddress(String address) {
		addresses.add(address);
		return this;
	}
	
	public String getStationNumber() {
		return stationNumber;
	}
	
	public Set<String> getAddresses() {
		return addresses.stream().collect(Collectors.toSet());
	}



	



	public List<Person2> getListOfPersons() {
		return listOfPersons;
	}



	public void setListOfPersons(List<Person2> listOfPersons) {
		this.listOfPersons = listOfPersons;
	}



	@Override
	public String toString() {
		return stationNumber.concat(": ") + String.join(", ", addresses);
	}

}
