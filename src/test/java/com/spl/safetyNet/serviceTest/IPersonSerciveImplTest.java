package com.spl.safetyNet.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.spl.safetyNet.Views.InfoPerson;
import com.spl.safetyNet.Views.ListPerson;
import com.spl.safetyNet.Views.PersonEmail;
import com.spl.safetyNet.Views.PersonPrint;
import com.spl.safetyNet.Views.Personchild;
import com.spl.safetyNet.models.Person;
import com.spl.safetyNet.service.IPersonSerciveImpl;

@SpringBootTest

@ExtendWith(MockitoExtension.class)

@MockitoSettings(strictness = Strictness.LENIENT)
public class IPersonSerciveImplTest {
	@Autowired
	private IPersonSerciveImpl iPersonSerciveImpl;

	@Test
	public void addPersonTest() throws ParseException {
		String firstName = "Patrice";
		String lastName = "LASSEY";
		String phone = "06 58 59 56 32";
		String zip = "92330";
		String address = "17 rue jules vernes";
		String city = "Nogent sur marne";
		String email = "gjcvfg@gmail.com";

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date birthDate = simpleDateFormat.parse("06/12/2010");
		Person addPerson = iPersonSerciveImpl.addPerson(firstName, lastName, phone, zip, address, city, email,
				birthDate);
		assertEquals(firstName, addPerson.getFirstName());
		assertEquals(lastName, addPerson.getLastName());
		assertEquals(phone, addPerson.getPhone());
		assertEquals(zip, addPerson.getZip());
		assertEquals(address, addPerson.getAddress());
		assertEquals(city, addPerson.getCity());
		assertEquals(email, addPerson.getEmail());
		assertEquals(birthDate, addPerson.getBirthDate());

	}

	@Test
	public void addPersonWithEmptyDataTest() throws ParseException {
		String firstName = "Patrice";
		String lastName = "";
		String phone = "06 58 59 56 32";
		String zip = "92330";
		String address = "";
		String city = "Nogent sur marne";
		String email = "gjcvfg@gmail.com";

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date birthDate = simpleDateFormat.parse("06/12/2010");
		Person addPerson = iPersonSerciveImpl.addPerson(firstName, lastName, phone, zip, address, city, email,
				birthDate);
		assertEquals(null, addPerson.getFirstName());
		assertEquals(null, addPerson.getLastName());
		assertEquals(null, addPerson.getPhone());
		assertEquals(null, addPerson.getZip());
		assertEquals(null, addPerson.getAddress());
		assertEquals(null, addPerson.getCity());
		assertEquals(null, addPerson.getEmail());
		assertEquals(null, addPerson.getBirthDate());

	}

	@Test
	public void getPersonExistingTest() {

		String firstName = "Jamie";
		String lastName = "Peters";
		Person addPerson = iPersonSerciveImpl.getPerson(firstName, lastName);
		assertEquals("Jamie", addPerson.getFirstName());
		assertEquals("Peters", addPerson.getLastName());
		assertEquals("908 73rd St", addPerson.getAddress());

	}

	@Test
	public void getNullForGettingNonExistingPersonTest() {

		String firstName = "unknow";
		String lastName = "unknow";
		Person addPerson = iPersonSerciveImpl.getPerson(firstName, lastName);
		assertEquals(null, addPerson.getFirstName());
		assertEquals(null, addPerson.getLastName());

	}

	@Test
	public void getInfoExistingPersonTest() {
		String firstName = "Jamie";
		String lastName = "Peters";
		String address = "908 73rd St";
		String city = "Culver";
		String zip = "97451";
		String phone = "841-874-7462";
		String email = "jpeter@email.com";
		InfoPerson personSelect = iPersonSerciveImpl.getInfoPerson(firstName, lastName);
		assertEquals("Jamie", personSelect.getFirstName());
		assertEquals("Peters", personSelect.getLastName());
		assertEquals(address, personSelect.getAddress());
		assertEquals(city, personSelect.getCity());
		assertEquals(zip, personSelect.getZip());
		assertEquals(email, personSelect.getEmail());
	}

	@Test
	public void getEmptyInfoEmptyDataPersonTest() {
		String firstName = "";
		String lastName = "";

		InfoPerson personSelect = iPersonSerciveImpl.getInfoPerson(firstName, lastName);
		assertEquals(null, personSelect.getFirstName());
		assertEquals(null, personSelect.getLastName());

	}

	@Test
	public void printlistMinorsByExistingAddressTest() {

		String address = "1509 Culver St";
		String lastName = "Boyd";
		String firstName = "Roger";
		int age = 4;
		String lastNameFamilyMenber = "Boyd";
		String firstNameFamilyMenber = "John";
		List<Personchild> listMinorsByAddressTest = iPersonSerciveImpl.printlistMinorsByAddress(address);
		assertEquals("Boyd", listMinorsByAddressTest.get(1).getLastName());
		assertEquals("Roger", listMinorsByAddressTest.get(1).getFirstName());
		assertEquals(age, listMinorsByAddressTest.get(1).getAge());
		assertEquals(2, listMinorsByAddressTest.size());
		assertEquals("John", listMinorsByAddressTest.get(1).getFamilyMenber().get(0).getFirstName());
		assertEquals(4, listMinorsByAddressTest.get(1).getFamilyMenber().size());
	}

	@Test
	public void printEmptylistMinorsByNonExistingAddressTest() {

		String address = "unknow";
		List<Personchild> listMinorsByAddressTest = iPersonSerciveImpl.printlistMinorsByAddress(address);

		assertEquals(true, listMinorsByAddressTest.isEmpty());
	}

	@Test
	public void getExistingfamilyMenbersTest() {
		String lastName = "Boyd";
		String firstName = "Jacob";
		List<Person> familyList = iPersonSerciveImpl.getfamilyMenbers(lastName);
		List<String> firstNameList = new ArrayList<String>();
		familyList.forEach(f -> {
			firstNameList.add(f.getFirstName() + f.getLastName());
		});
		assertEquals(true, firstNameList.contains(firstName + lastName));
		assertEquals(6, familyList.size());

	}

	@Test
	public void getEmptyListForNonExistingfamilyMenbersTest() {
		String lastName = "unknown";
		String firstName = "unknown";
		List<Person> familyList = iPersonSerciveImpl.getfamilyMenbers(lastName);
		assertEquals(0, familyList.size());

	}

	@Test
	public void updateExistingPersonTest() throws ParseException {

		String firstName = "Jamie";
		String lastName = "Peters";
		String newPhone = "841-884-7862";
		String newZip = "97851";
		String newAddress = "958 73rd St";
		String newCity = "London";
		String newEmail = "newemail@gmail.com";
		/*
		 * SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy"); Date
		 * newBirthDate = simpleDateFormat.parse("06/12/2010");
		 */
		Person personToUpdated = iPersonSerciveImpl.updatePerson(firstName, lastName, newPhone, newZip, newAddress,
				newCity, newEmail);
		assertEquals(firstName, personToUpdated.getFirstName());
		assertEquals(lastName, personToUpdated.getLastName());
		assertEquals(newPhone, personToUpdated.getPhone());
		assertEquals(newZip, personToUpdated.getZip());
		assertEquals(newAddress, personToUpdated.getAddress());
		assertEquals(newCity, personToUpdated.getCity());
		assertEquals(newEmail, personToUpdated.getEmail());

	}

	@Test
	public void updatePhoneExistingPersonTest() throws ParseException {

		String firstName = "Jamie";
		String lastName = "Peters";

		String address = "908 73rd St";
		String city = "Culver";
		String zip = "97451";

		String email = "jpeter@email.com";

		String newPhone = "841-884-7862";

		Person personToUpdated = iPersonSerciveImpl.updatePerson(firstName, lastName, newPhone, "", "", "", "");
		assertEquals(firstName, personToUpdated.getFirstName());
		assertEquals(lastName, personToUpdated.getLastName());
		assertEquals(newPhone, personToUpdated.getPhone());
		assertEquals(zip, personToUpdated.getZip());
		assertEquals(address, personToUpdated.getAddress());
		assertEquals(city, personToUpdated.getCity());
		assertEquals(email, personToUpdated.getEmail());

	}

	@Test
	public void updatePhoneNonExistingPersonTest() throws ParseException {

		String firstName = "";
		String lastName = "";
		String newPhone = "841-884-7862";

		Person personToUpdated = iPersonSerciveImpl.updatePerson(firstName, lastName, newPhone, "", "", "", "");
		assertEquals(null, personToUpdated.getFirstName());
		assertEquals(null, personToUpdated.getLastName());

	}

	@Test
	public void deleteExistingPersonTest() {
		String firstName = "Jamie";
		String lastName = "Peters";

		assertEquals(true, iPersonSerciveImpl.delete(firstName, lastName));
	}

	@Test
	public void deleteEmptyExistingPersonTest() {
		String firstName = "";
		String lastName = "";

		assertEquals(false, iPersonSerciveImpl.delete(firstName, lastName));
	}

	@Test
	public void getResidentsByAddressTest() {
		String address = "1509 Culver St";
		String lastName = "Boyd";
		String firstName = "John";
		int age = 37;
		String phone = "841-874-6512";
		List<String> medicationTest = new ArrayList<>();
		List<String> allergiesTest = new ArrayList<>();
		medicationTest.add("aznol:350mg");
		medicationTest.add("hydrapermazol:100mg");
		allergiesTest.add("nillacilan");
		String residentInfo = firstName + lastName + age + phone + medicationTest.toString() + allergiesTest.toString();
		List<Person> listResidentsByAddress = iPersonSerciveImpl.getResidentsByAddress(address);
		List<String> residents = new ArrayList<>();

		listResidentsByAddress.forEach(resident -> {
			residents.add(resident.getFirstName() + resident.getLastName() + resident.age() + resident.getPhone()
					+ resident.getMedicalRecord().getMedications().toString()
					+ resident.getMedicalRecord().getAllergies().toString());

		});

		assertEquals(true, residents.contains(residentInfo));

	}

	@Test
	public void getResidentsByUnknowAddressEmptyListTest() {
		String address = "unknown";
		List<Person> emptyList = new ArrayList<>();
		assertEquals(emptyList, iPersonSerciveImpl.getResidentsByAddress(address));

	}

	@Test
	public void getListInfoPersonTest() {
		String firstName = "Tenley";
		String lastName = "Boyd";
		String address = "1509 Culver St";
		String zip = "97451";
		String city = "Culver";
		String allergy = "peanut";
		int age = 8;
		String email = "tenz@email.com";
		List<String> medications = new ArrayList<String>();

		String firstNameFamilyMenber = "John";
		String lastNameFamilyMenber = "Boyd";
		int ageFamilyMenber = 37;
		List<InfoPerson> getPersons = iPersonSerciveImpl.getListInfoPerson(firstName, lastName);
		assertEquals(firstName, getPersons.get(0).getFirstName());
		assertEquals(lastName, getPersons.get(0).getLastName());
		assertEquals(address, getPersons.get(0).getAddress());
		assertEquals(zip, getPersons.get(0).getZip());
		assertEquals(city, getPersons.get(0).getCity());
		assertEquals(age, getPersons.get(0).getAge());
		assertEquals(email, getPersons.get(0).getEmail());
		assertEquals(medications, getPersons.get(0).getMedications());
		assertEquals(true, getPersons.get(0).getAllergies().contains(allergy));
		assertEquals(firstNameFamilyMenber, getPersons.get(1).getFirstName());
		assertEquals(lastNameFamilyMenber, getPersons.get(1).getLastName());
		assertEquals(ageFamilyMenber, getPersons.get(1).getAge());
		assertEquals(6, getPersons.size());
	}

	@Test
	public void getEmptyListInfoPersonWithunknownContactTest() {
		String firstName = "unknown";
		String lastName = "unknown";
		List<InfoPerson> getPersons = iPersonSerciveImpl.getListInfoPerson(firstName, lastName);
		System.out.println("getPersons.size()");
		System.out.println(getPersons.size());
		System.out.println(getPersons.get(0).getFirstName());
		System.out.println(getPersons.get(0).getLastName());

		assertEquals(null, getPersons.get(0).getFirstName());

		assertEquals(1, getPersons.size());

	}

	@Test
	public void getEmptyListInfoPersonWithEmptyContactTest() {
		String firstName = "";
		String lastName = "";
		List<InfoPerson> getPersons = iPersonSerciveImpl.getListInfoPerson(firstName, lastName);
		assertEquals(true, getPersons.isEmpty());
	}

	@Test
	public void listPersonsLinkToStationSelectedTest() {
		String stationNumber = "1";
		int qtMinors = 1;
		int qtAdult = 5;
		List<PersonPrint> PersonsLinkToStationSelectedTest = new ArrayList<PersonPrint>();
		String firstName = "Peter";
		String lastName = "Duncan";
		String zip = "97451";
		String address = "644 Gershwin Cir";
		String city = "Culver";
		String phone = "841-874-6512";
		ListPerson personsLinkToStationSelectedTest = iPersonSerciveImpl
				.listPersonsLinkToStationSelected(stationNumber);
		List<PersonPrint> liste = personsLinkToStationSelectedTest.getContactsList();

		assertEquals(1, personsLinkToStationSelectedTest.getNbMinors());
		assertEquals(5, personsLinkToStationSelectedTest.getNbAdults());
		assertEquals(6, personsLinkToStationSelectedTest.getContactsList().size());
		assertEquals(firstName, liste.get(0).getFirstName());
		assertEquals(lastName, liste.get(0).getLastName());
		assertEquals(address, liste.get(0).getAddress());
		assertEquals(zip, liste.get(0).getZip());
		assertEquals(city, liste.get(0).getCity());
		assertEquals(phone, liste.get(0).getPhone());

	}

	@Test
	public void listNullPersonsLinkToUnknownStationSelectedTest() {
		String stationNumber = "unknown";

		ListPerson personsLinkToStationSelectedTest = iPersonSerciveImpl
				.listPersonsLinkToStationSelected(stationNumber);

		assertEquals(0, personsLinkToStationSelectedTest.getContactsList().size());

	}

	@Test
	public void listEmailByCityTest() {
		String city = "Culver";
		List<PersonEmail> emails = iPersonSerciveImpl.listEmail(city);
		assertEquals(15, emails.size());
	}

	@Test
	public void listEmailByUnknownCityTest() {
		String city = "unknown";
		List<PersonEmail> emails = iPersonSerciveImpl.listEmail(city);
		assertEquals(true, emails.isEmpty());
	}

}
