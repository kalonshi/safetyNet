package com.spl.safetyNet.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FireStation {

	private Set<String> addresses = new HashSet<>();
	private String stationNumber;
	private List<Person> listOfPersons;

	public FireStation() {
		super();

	}

	public FireStation(String stationNumber) {
		this.stationNumber = stationNumber;
	}

	public FireStation(Set<String> addresses, String stationNumber) {
		super();
		this.addresses = addresses;
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

	public String setStationNumber(String stationNumber) {
		this.stationNumber = stationNumber;
		return stationNumber;
	}

	public List<Person> getListOfPersons() {
		return listOfPersons;
	}

	public void setListOfPersons(List<Person> listOfPersons) {
		this.listOfPersons = listOfPersons;
	}

	@Override
	public String toString() {
		return stationNumber.concat(": ") + String.join(", ", addresses);
	}

}
