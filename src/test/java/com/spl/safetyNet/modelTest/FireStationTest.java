package com.spl.safetyNet.modelTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.spl.safetyNet.models.FireStation;

class FireStationTest {

	@Test
	public void addNewFireSationWithAddressAndNumberTest() {
		String addStationNumber = "6";
		Set<String> addAdress = new HashSet<String>();
		addAdress.add("17 av Jules Guesdes");
		FireStation newFireStation = new FireStation(addAdress, addStationNumber);

		Set<String> ad = newFireStation.getAddresses();
		ad.iterator().next();
		assertEquals("17 av Jules Guesdes", ad.iterator().next());
		assertEquals("6", newFireStation.getStationNumber());
	}

	@Test
	public void addAddressToExistingStationTest() {
		String addStationNumber = "6";
		Set<String> addresses = new HashSet<String>();
		addresses.add("17 av Jules Guesdes");
		FireStation newFireStation = new FireStation(addresses, addStationNumber);
		String newAddress = "14 rue du petit Lac";
		newFireStation.addAddress(newAddress);
		Set<String> listadr=newFireStation.getAddresses();
		assertEquals(true,listadr.contains("14 rue du petit Lac"));
	}
}
