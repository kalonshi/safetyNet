package com.spl.safetyNet.serviceTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ResourceLoader;

import com.jsoniter.JsonIterator;
import com.spl.safetyNet.models.FireStation;
import com.spl.safetyNet.models.Person;
import com.spl.safetyNet.service.IFirestationImpl;
import com.spl.safetyNet.service.JsonFileData;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class IFirestationImplTest {
	
	
	private static IFirestationImpl iFirestationImpl;
	@Mock
	private static JsonFileData jSonFile;
	/*
	 * @Mock private static JsonFileData jSonFile;
	 */
	
	
	private static Logger logger = LogManager.getLogger(IFirestationImpl.class);
	
	/* @Autowired private ResourceLoader resourceLoader = null; */
	
	/*
	 * @BeforeEach
	 * 
	 * void init() throws IOException { File dataFile =
	 * resourceLoader.getResource("classpath:src/main/resources/data.json").getFile(
	 * ); }
	 */
	 
	/*
	 * private static void setUp() throws Exception { jSonFile= new JsonFileData();
	 * file=new File();
	 * 
	 * }
	 */

	@Test
	public void getFireStationstationByNumber() {
		
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
	public void getFireStationByAddress() {
		System.out.println("getFireStationByAddress()");
		try {
			System.out.println("getFireStationByAddress()2");
			jSonFile.loadStations();
			String address = "29 15th St";
			System.out.println("getFireStationByAddress()3");
			FireStation firestationSelected = iFirestationImpl.getFireStationForPerson(address);
			assertEquals("2", firestationSelected.getStationNumber());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error getFireStationByAddress()");
			e.printStackTrace();
		}
	}

	@Test

	public void getListPersonByFireStationTest() {
		logger.info("getList************************");
		try {
			jSonFile.loadPersons();
			jSonFile.loadStations();
			
			System.out.println(jSonFile.loadStations().size());
			System.out.println(jSonFile.loadPersons().size());
			String fireStationNumber = "1";
			String firstName = "Jamie";
			String lastName = "Peters";

			List<Person> listContactsFire = iFirestationImpl.getListPersonByFireStation(fireStationNumber);
			System.out.println(listContactsFire.size());
			List<String> firstNameList = new ArrayList<String>();
			List<String> lastNameList = new ArrayList<String>();
			for (Person p : listContactsFire) {
				firstNameList.add(p.getFirstName());
				lastNameList.add(lastName);
			}

			assertEquals(5, listContactsFire.size());

			assertEquals(true, firstNameList.contains(firstName));
			assertEquals(true, lastNameList.contains(lastName));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
