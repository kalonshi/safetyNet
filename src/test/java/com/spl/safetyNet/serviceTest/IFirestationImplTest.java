package com.spl.safetyNet.serviceTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.spl.safetyNet.Views.ListContactsForFire;
import com.spl.safetyNet.Views.PersonFire;
import com.spl.safetyNet.Views.PersonPhone;
import com.spl.safetyNet.models.FireStation;
import com.spl.safetyNet.models.Person;
import com.spl.safetyNet.service.IFirestationImpl;
import com.spl.safetyNet.service.JsonFileData;

@SpringBootTest

@ExtendWith(MockitoExtension.class)

@MockitoSettings(strictness = Strictness.LENIENT)

public class IFirestationImplTest {
	@Autowired
	private IFirestationImpl iFirestationImpl;
	@Autowired
	private JsonFileData jSonFile;

	private static Logger logger = LogManager.getLogger(IFirestationImpl.class);
	@Test
	public void addFireStationTest() {
		String addStationNumber = "6";
		String addAdress = "17 av Jules Guesdes";
		FireStation newFireStation=iFirestationImpl.addFireStation(addStationNumber, addAdress);
		assertEquals(true, newFireStation.getAddresses().contains(addAdress));
		assertEquals("6", newFireStation.getStationNumber());
	}
	@Test
	public void addEmptyFireStationTest() {
		String addStationNumber = "";
		String addAdress = "";
		FireStation newFireStation=iFirestationImpl.addFireStation(addStationNumber, addAdress);
		assertEquals(null, iFirestationImpl.addFireStation(addStationNumber, addAdress));
		
	}
	
	@Test
	public void getFireStationstationByNumberTest() {

		try {
			logger.info("Entering test getFireStationNumber");

			jSonFile.loadStations();
			String fireStationNumber = "1";
			FireStation fireStation = iFirestationImpl.getFireStation(fireStationNumber);

			assertEquals("1", fireStation.getStationNumber());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void getFireStationForPersonByAddress() {
		
			String address = "29 15th St";

			FireStation firestationSelected = iFirestationImpl.getFireStationForPerson(address);

			assertEquals("2", firestationSelected.getStationNumber());

		
	}
	
	@Test
	public void phoneListTest() {
		String stationNumber = "2";
		String phone="841-874-6513";
		String unknownPhone="unknown";
		List<PersonPhone> phoneList = iFirestationImpl.phoneList(stationNumber);
	List<String> contactsPhone=new ArrayList<>();
	phoneList.forEach(p->{
		contactsPhone.add(p.getPhone());
	});
		assertEquals(true, contactsPhone.contains(phone));
	assertEquals(false, contactsPhone.contains(unknownPhone));
	assertEquals(5, contactsPhone.size());
	}
	
	@Test
	public void phoneListUnknowStationTest() {
		String stationNumber = "unknown";
		
		List<PersonPhone> phoneList = iFirestationImpl.phoneList(stationNumber);
	
		assertEquals(true, phoneList.isEmpty());	
	
	}
	
	@Test
	public void getlistContactsByAddressAndStation() {
		String address = "29 15th St";
		String stationNumber = "2";
		String firstName="Jonanathan";
		String lastName="Marrack";
		String phone="841-874-6513";
		int age=32;
		ListContactsForFire listContactsByAddressAndStation= iFirestationImpl.getlistContactsByAddressAndStation(address);
	
	assertEquals(true, listContactsByAddressAndStation.getAddress().equals(address));
	assertEquals(stationNumber,listContactsByAddressAndStation.getStationNumber());
	assertEquals(1,listContactsByAddressAndStation.getListcontactsFire().size());
	assertEquals(firstName,listContactsByAddressAndStation.getListcontactsFire().get(0).getFirstName());
	assertEquals(lastName,listContactsByAddressAndStation.getListcontactsFire().get(0).getLastName());
	assertEquals(phone,listContactsByAddressAndStation.getListcontactsFire().get(0).getPhone());
	assertEquals(age,listContactsByAddressAndStation.getListcontactsFire().get(0).getAge());
	assertEquals(true,listContactsByAddressAndStation.getListcontactsFire().get(0).getAllergies().isEmpty());
	assertEquals(true,listContactsByAddressAndStation.getListcontactsFire().get(0).getMedications().isEmpty());
	
	}
	
	@Test
	public void getListPersonFireByFireStationsTest() {
		List<String> fireStations=new ArrayList<String>();
			fireStations.add("1");
			fireStations.add("2");
			List<PersonFire> listPersonFireByFireStations=iFirestationImpl.getListPersonFireByFireStations(fireStations);	
	
	}
	
	
	
	
	
	
	@Test
	public void getListPersonFireByAdresseTest() {
		String address = "892 Downing Ct";
		String firstName = "Warren";
		String lastName = "Zemicks";
		List<Person> listPersonByAdresse = iFirestationImpl.getListPersonByAdresse(address);
		List<String> firstNameList = new ArrayList<String>();
		List<String> lastNameList = new ArrayList<String>();
		listPersonByAdresse.forEach(p -> {
			firstNameList.add(p.getFirstName());
			lastNameList.add(p.getLastName());
		});	
	}
	
	
	
	@Test
	public void listFloodTest() {
		
	}
	
	
	
	
	

	
	
	/*
	 * @Test
	 * 
	 * public void getListPersonByFireStationTest() { String fireStationNumber =
	 * "1"; String firstName = "Jamie"; String lastName = "Peters";
	 * 
	 * List<Person> listContactsFire =
	 * iFirestationImpl.getListPersonByFireStation(fireStationNumber);
	 * 
	 * List<String> firstNameList = new ArrayList<String>(); List<String>
	 * lastNameList = new ArrayList<String>(); for (Person p : listContactsFire) {
	 * firstNameList.add(p.getFirstName()); lastNameList.add(lastName); }
	 * 
	 * assertEquals(6, listContactsFire.size());
	 * 
	 * assertEquals(true, firstNameList.contains(firstName)); assertEquals(true,
	 * lastNameList.contains(lastName));
	 * 
	 * 
	 * 
	 * }
	 */
	 
	@Test
	public void getListPersonByAdresseTest() throws IOException {
		
		String address = "892 Downing Ct";
		String firstName = "Warren";
		String lastName = "Zemicks";
		List<Person> listPersonByAdresse = iFirestationImpl.getListPersonByAdresse(address);
		List<String> firstNameList = new ArrayList<String>();
		List<String> lastNameList = new ArrayList<String>();
		listPersonByAdresse.forEach(p -> {
			firstNameList.add(p.getFirstName());
			lastNameList.add(p.getLastName());
		});
		
		assertEquals(3, listPersonByAdresse.size());
		assertEquals(true, firstNameList.contains(firstName));
		assertEquals(true, lastNameList.contains(lastName));
	}
	
@Test
public void deleteStationTest() throws IOException {
	
	String numberStation="1";
	iFirestationImpl.deleteStation(numberStation);
		
		/*
		 * List<FireStation> stations=jSonFile.loadStations(); List<String>
		 * stationNumbers=new ArrayList<String>(); stations.forEach(f->{
		 * stationNumbers.add(f.getStationNumber()); });
		 */
		 
		/* assertEquals(true,iFirestationImpl.deleteStation(numberStation)); */
}







}
