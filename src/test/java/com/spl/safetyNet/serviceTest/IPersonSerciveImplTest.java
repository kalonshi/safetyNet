package com.spl.safetyNet.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
import com.spl.safetyNet.service.IFirestationImpl;
import com.spl.safetyNet.service.IPersonSerciveImpl;


@SpringBootTest

@ExtendWith(MockitoExtension.class)

@MockitoSettings(strictness = Strictness.LENIENT)
public class IPersonSerciveImplTest {
	@Autowired
	private  IPersonSerciveImpl iPersonSerciveImpl;
	
	private static  Logger logger = LogManager.getLogger(IFirestationImpl.class);
	
	@Test
	public void getPersonExistingTest(){
		
		String firstName = "Jamie";
		String lastName = "Peters";
		Person personSelected= iPersonSerciveImpl.getPerson(firstName, lastName);
		assertEquals("Jamie",personSelected.getFirstName());
		assertEquals("Peters",personSelected.getLastName());
		assertEquals("908 73rd St",personSelected.getAddress());
		
	}
	@Test
	public void getNullForGettingNonExistingPersonTest(){
		
		String firstName = "unknow";
		String lastName = "unknow";
		Person personSelected= iPersonSerciveImpl.getPerson(firstName, lastName);
		assertEquals(null,personSelected.getFirstName());
		assertEquals(null,personSelected.getLastName());
		
		
	}
	
	@Test
	public void getExistingfamilyMenbersTest(){
		String lastName="Boyd";
		String firstName="Jacob";
		List<Person> familyList=iPersonSerciveImpl.getfamilyMenbers(lastName);
		List<String> firstNameList=new ArrayList<String>();
		familyList.forEach(f-> {
			firstNameList.add(f.getFirstName()+f.getLastName());
		});
		assertEquals(true,firstNameList.contains(firstName+lastName));
		assertEquals(6,familyList.size());
		
	}
	@Test
	public void getEmptyListForNonExistingfamilyMenbersTest(){
		String lastName="unknown";
		String firstName="unknown";
		List<Person> familyList=iPersonSerciveImpl.getfamilyMenbers(lastName);
		assertEquals(0,familyList.size());
		
	}
	/*
	 * @Test public void getMinorsByAddressTest(){ String address="1509 Culver St";
	 * String lastName="Boyd"; String firstName="Roger"; String age="4";
	 * 
	 * List<Person> minors = iPersonSerciveImpl.getMinorsByAddress( address);
	 * List<String> listLastNameFirstName= new ArrayList<String>();
	 * minors.forEach(m->{
	 * listLastNameFirstName.add(m.getFirstName()+m.getLastName()+m.age());
	 * 
	 * });
	 * 
	 * assertEquals(true,listLastNameFirstName.contains(firstName+lastName+age));
	 * assertEquals(2,minors.size()); }
	 */
	
	
	@Test
	public void getResidentsByAddressTest() {
		String address="1509 Culver St";
		String lastName="Boyd";
		String firstName="John";
		int age=37;
		String phone="841-874-6512";
		List<String> medicationTest=new ArrayList<>();
		List<String>allergiesTest=new ArrayList<>();
		medicationTest.add("aznol:350mg");
		medicationTest.add( "hydrapermazol:100mg");
		allergiesTest.add("nillacilan");
		String residentInfo=firstName+lastName+age+phone+medicationTest.toString()+allergiesTest.toString();
		List<Person> listResidentsByAddress=iPersonSerciveImpl.getResidentsByAddress(address);
	List<String> residents= new ArrayList<>();
	
	listResidentsByAddress.forEach(resident->{
		residents.add(resident.getFirstName()+resident.getLastName()+resident.age()+resident.getPhone()+resident.getMedicalRecord().getMedications().toString()+resident.getMedicalRecord().getAllergies().toString());
		
	});
	
	assertEquals(true,residents.contains(residentInfo));
	
	}
	@Test
	public void getResidentsByUnknowAddressEmptyListTest() {
		String address="unknown";
		List<Person> emptyList=new ArrayList<>();
		assertEquals(emptyList,iPersonSerciveImpl.getResidentsByAddress(address));
	
	}

	@Test
	public void getListInfoPersonTest() {
		String firstName = "Jamie";
		String lastName = "Peters";
		InfoPerson personSelected= iPersonSerciveImpl.getInfoPerson(firstName, lastName);
		assertEquals("Jamie",personSelected.getFirstName());
		assertEquals("Peters",personSelected.getLastName());
		assertEquals("908 73rd St",personSelected.getAddress());
		
	}
	
	@Test
	public void printlistMinorsByExistingAddressTest() {
		
		String address="1509 Culver St";
		String lastName="Boyd";
        String firstName="Roger";
        int age=4;
        String lastNameFamilyMenber="Boyd";
        String firstNameFamilyMenber="John";
        List<Personchild> listMinorsByAddressTest=iPersonSerciveImpl.printlistMinorsByAddress(address);
 assertEquals("Boyd",listMinorsByAddressTest.get(1).getLastName());
        assertEquals("Roger", listMinorsByAddressTest.get(1).getFirstName());
        assertEquals(age,listMinorsByAddressTest.get(1).getAge());
        assertEquals(2,listMinorsByAddressTest.size());
        assertEquals("John",listMinorsByAddressTest.get(1).getFamilyMenber().get(0).getFirstName());
        assertEquals(4,listMinorsByAddressTest.get(1).getFamilyMenber().size());
    }
	
	
	@Test
	public void printEmptylistMinorsByNonExistingAddressTest() {
		
		String address="unknow";
		List<Personchild> listMinorsByAddressTest=iPersonSerciveImpl.printlistMinorsByAddress(address);
        
        assertEquals(true,listMinorsByAddressTest.isEmpty());
   }
	
	
	
	@Test
	public void listPersonsLinkToStationSelectedTest() {
		String stationNumber="1";
		int qtMinors=1;
		int qtAdult=5;
		List<PersonPrint> PersonsLinkToStationSelectedTest= new ArrayList<PersonPrint>();
		String firstName="Peter";
		String lastName="Duncan";
		String zip="97451";
		String address="644 Gershwin Cir";
		String city="Culver"; 
		String phone="841-874-6512";
		ListPerson personsLinkToStationSelectedTest= iPersonSerciveImpl.listPersonsLinkToStationSelected(stationNumber);
		 List<PersonPrint> liste=personsLinkToStationSelectedTest.getContactsList();
		
		 assertEquals(1,personsLinkToStationSelectedTest.getNbMinors());
		 assertEquals(5,personsLinkToStationSelectedTest.getNbAdults());
		 assertEquals(6,personsLinkToStationSelectedTest.getContactsList().size());
		 assertEquals(firstName	,liste.get(0).getFirstName());	
		 assertEquals(lastName	,liste.get(0).getLastName());
		 assertEquals(address	,liste.get(0).getAddress());
		 assertEquals(zip	,liste.get(0).getZip());
		 assertEquals(city	,liste.get(0).getCity());
		 assertEquals(phone	,liste.get(0).getPhone());
	
	}
	
	@Test
	public void listNullPersonsLinkToUnknownStationSelectedTest() {
		String stationNumber="unknown";
		 
		ListPerson personsLinkToStationSelectedTest= iPersonSerciveImpl.listPersonsLinkToStationSelected(stationNumber);
		 
		 assertEquals(0,personsLinkToStationSelectedTest.getContactsList().size());

		 
	}
	
	@Test
	public void listEmailByCityTest() {
		String city="Culver";
		List<PersonEmail> emails=iPersonSerciveImpl.listEmail(city);
		 assertEquals(23,emails.size());
	}
	@Test
	public void listEmailByUnknownCityTest() {
		String city="unknown";
		List<PersonEmail> emails=iPersonSerciveImpl.listEmail(city);
		 assertEquals(true,emails.isEmpty());
	}
	
	
}
