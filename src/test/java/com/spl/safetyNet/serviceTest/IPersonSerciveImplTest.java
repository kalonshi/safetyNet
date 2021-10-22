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
	@Test
	public void getMinorsByAddressTest(){
		String address="1509 Culver St";
				String lastName="Boyd";
		String firstName="Roger";
		String age="4";
				
		List<Person> minors = iPersonSerciveImpl.getMinorsByAddress( address);
	List<String> listLastNameFirstName= new ArrayList<String>();
	minors.forEach(m->{
		listLastNameFirstName.add(m.getFirstName()+m.getLastName()+m.age());
	
	});
	
	assertEquals(true,listLastNameFirstName.contains(firstName+lastName+age));
	assertEquals(2,minors.size());
	}
	
	
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
	public void printlistMinorsByAddressTest() {
		
		String address="1509 Culver St";
		String lastName="Boyd";
        String firstName="Roger";
        String age="4";
        List<Personchild> listMinorsByAddressTest=iPersonSerciveImpl.printlistMinorsByAddress(address);


	}
	
	@Test
	public void getInfoPersonTest() {
		
	}
	
	
	@Test
	public void listPersonsLinkToStationSelectedTest() {
		
	}
	
	
	
	@Test
	public void listEmailTest() {
		
	}
	
	
	@Test
	public void listResidentsByAddressTest() {
		
	}
	
	
}
