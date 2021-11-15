package com.spl.safetyNet.serviceTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.spl.safetyNet.Views.ListContactsForFire;
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
		FireStation newFireStation = iFirestationImpl.addFireStation(addStationNumber, addAdress);
		assertEquals(true, newFireStation.getAddresses().contains(addAdress));
		assertEquals("6", newFireStation.getStationNumber());
	}

	@Test
	public void addEmptyFireStationTest() {
		String addStationNumber = "";
		String addAdress = "";

		assertEquals(true, iFirestationImpl.addFireStation(addStationNumber, addAdress).getAddresses().isEmpty());
		assertEquals(null, iFirestationImpl.addFireStation(addStationNumber, addAdress).getStationNumber());
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
		String phone = "841-874-6513";
		String unknownPhone = "unknown";
		List<PersonPhone> phoneList = iFirestationImpl.phoneList(stationNumber);
		List<String> contactsPhone = new ArrayList<>();
		phoneList.forEach(p -> {
			contactsPhone.add(p.getPhone());
		});
		assertEquals(true, contactsPhone.contains(phone));
		assertEquals(false, contactsPhone.contains(unknownPhone));
		assertEquals(4, contactsPhone.size());
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
		String firstName = "Jonanathan";
		String lastName = "Marrack";
		String phone = "841-874-6513";
		int age = 32;
		ListContactsForFire listContactsByAddressAndStation = iFirestationImpl
				.getlistContactsByAddressAndStation(address);

		assertEquals(true, listContactsByAddressAndStation.getAddress().equals(address));
		assertEquals(stationNumber, listContactsByAddressAndStation.getStationNumber());
		assertEquals(1, listContactsByAddressAndStation.getListcontactsFire().size());
		assertEquals(firstName, listContactsByAddressAndStation.getListcontactsFire().get(0).getFirstName());
		assertEquals(lastName, listContactsByAddressAndStation.getListcontactsFire().get(0).getLastName());
		assertEquals(phone, listContactsByAddressAndStation.getListcontactsFire().get(0).getPhone());
		assertEquals(age, listContactsByAddressAndStation.getListcontactsFire().get(0).getAge());
		assertEquals(true, listContactsByAddressAndStation.getListcontactsFire().get(0).getAllergies().isEmpty());
		assertEquals(true, listContactsByAddressAndStation.getListcontactsFire().get(0).getMedications().isEmpty());

	}

	@Test
	public void getListPersonForFloodByFireStationsTest() {
		List<String> fireStations = new ArrayList<String>();
		fireStations.add("1");
		fireStations.add("2");
		List<ListContactsForFire> listContactsForFlood = iFirestationImpl.listFlood(fireStations);

		String address = "908 73rd St";
		String stationNumber = "1";
		String firstName = "Reginold";
		String lastName = "Walker";
		String phone = "841-874-8547";
		int age = 40;
		String addressResult = listContactsForFlood.get(0).getAddress();
		String stationNumberResult = listContactsForFlood.get(0).getStationNumber();
		String firstNameResult = listContactsForFlood.get(0).getListcontactsFire().get(0).getFirstName();
		String lastNameResult = listContactsForFlood.get(0).getListcontactsFire().get(0).getLastName();
		String phoneResult = listContactsForFlood.get(0).getListcontactsFire().get(0).getPhone();
		int ageResult = listContactsForFlood.get(0).getListcontactsFire().get(0).getAge();
		assertEquals(false, listContactsForFlood.isEmpty());
		assertEquals(address, addressResult);
		assertEquals(stationNumber, stationNumberResult);
		assertEquals(firstName, firstNameResult);
		assertEquals(lastName, lastNameResult);
		assertEquals(phone, phoneResult);
		assertEquals(age, ageResult);
	}

	@Test
	public void getListPersonForFloodWithWrongStationNumberTest() {
		List<String> fireStations = new ArrayList<String>();
		fireStations.add("10");
		fireStations.add("20");
		List<ListContactsForFire> listContactsForFlood = iFirestationImpl.listFlood(fireStations);
		assertEquals(true, listContactsForFlood.isEmpty());
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
	public void deleteStationAddressTest() throws IOException {

		String address = "892 Downing Ct";
		boolean stationIsDelete = iFirestationImpl.deleteStationAdresse(address);
		assertEquals(true, stationIsDelete);
	}

	@Test
	public void updateExistingFireStationNumber() {
		String numberStation = "1";
		String newNumberStation = "65";
		boolean isStationUpdated = iFirestationImpl.updateFireStationNumber(numberStation, newNumberStation);
		assertEquals(true, isStationUpdated);
	}

	@Test
	public void updateNotExistingFireStationNumber() {
		String numberStation = "";
		String newNumberStation = "65";

		assertEquals(false, iFirestationImpl.updateFireStationNumber(numberStation, newNumberStation));
	}

}
