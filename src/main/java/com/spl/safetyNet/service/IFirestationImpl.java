package com.spl.safetyNet.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jacoco.agent.rt.internal_43f5073.asm.tree.TryCatchBlockNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.spl.safetyNet.Views.ListContactsForFire;
import com.spl.safetyNet.Views.PersonFire;
import com.spl.safetyNet.Views.PersonPhone;
import com.spl.safetyNet.controller.PersonController;
import com.spl.safetyNet.models.FireStation;
import com.spl.safetyNet.models.MedicalRecord;
import com.spl.safetyNet.models.Person;

@Service

public class IFirestationImpl implements IFirestation {
	@Autowired
	private JsonFileData jSonFile;
	
	private static  Logger logger = LogManager.getLogger(IFirestationImpl.class);

	@Override
	public FireStation addFireStation(String fireStationNumber, String addresse) {
		// TODO Auto-generated method stub
		logger.info("Entering the addFireStation() method");
		FireStation newFireStation = new FireStation(fireStationNumber);
		newFireStation.addAddress(addresse);
		try {
			jSonFile.loadStationsWithOutListPerson().add(newFireStation);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newFireStation;

	}
	/*
	 * @Override public FireStation addFireStation(String fireStationNumber, String
	 * addresse) { // TODO Auto-generated method stub
	 * logger.info("Entering the addFireStation() method"); List<Person> listPerson;
	 * Set<Person> fireStationByPersonAdresse = new HashSet<Person>(); try {
	 * 
	 * System.out.println("Test Junit*******************"); listPerson =
	 * jSonFile.loadPersons(); System.out.println("Test Junit2*******************");
	 * logger.info("listPerson.forEach( method"); listPerson.forEach(p -> { if
	 * (p.getAddress().contains(addresse)) { fireStationByPersonAdresse.add(p); }
	 * });
	 * 
	 * FireStation newFireStation = new FireStation(fireStationNumber);
	 * newFireStation.addAddress(addresse);
	 * newFireStation.setListOfPersons(fireStationByPersonAdresse);
	 * fireStationByPersonAdresse.forEach(p -> p.setFireStation(newFireStation));
	 * return newFireStation; } catch (IOException e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); } return null;
	 * 
	 * }
	 */

	@Override
	public FireStation getFireStation(String fireStationNumber) {
		// TODO Auto-generated method stub
		logger.info("GET FIRESTATION BY NUMBER ");
		try {
			List<FireStation> fireStations = jSonFile.loadStations();
			logger.info(" ");
			for (FireStation f : fireStations) {
				if (f.getStationNumber().equals(fireStationNumber)) {
					return f;
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(" Pas de resultat");
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public FireStation getFireStationForPerson(String adresse) {
		// TODO Auto-generated method stub
		logger.info("Entering getFireStationForPerson ");
		FireStation fireStationSelected = new FireStation();
		try {
			List<FireStation> fireStations = jSonFile.loadStations();
			logger.info(" ");
			for (FireStation f : fireStations) {

				Set<String> addresses = f.getAddresses();
				for (String ad : addresses) {

					if (ad.equals(adresse)) {

						fireStationSelected = f;
						logger.info("select station OK");
						return fireStationSelected;
					}
					logger.info("select station NOK!!!!!!");
				}

			}

			return fireStationSelected;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fireStationSelected;
	}

	@Override
	public List<Person> getListPersonByFireStation(String fireStationNumber) {
		// TODO Auto-generated method stub
		FireStation fireStationSelected = getFireStation(fireStationNumber);
		List<Person> listPersonByStation = new ArrayList<Person>();
		listPersonByStation = fireStationSelected.getListOfPersons();
		return listPersonByStation;
	}

	@Override
	public List<Person> getListPersonByAdresse(String adresse) {
		// TODO Auto-generated method stub
		List<Person> listPersonsByAdresse=new ArrayList<Person>();
		if (!adresse.isEmpty()) {
			FireStation f = getFireStationForPerson(adresse);
			List<Person> listPersonByStation = f.getListOfPersons();
			listPersonByStation.forEach(p->{
				if(p.getAddress().equals(adresse)) {
					listPersonsByAdresse.add(p);
				}
			});

			return listPersonsByAdresse;
		}
		return listPersonsByAdresse;
	}

//:http://localhost:8080/flood/stations?stations=<a list of station_numbers>
	@Override
	public List<Person> getListPersonByFireStations(List<String> fireStations) {
		// TODO Auto-generated method stub

		try {

			List<FireStation> fireStationWithPersons;
			fireStationWithPersons = jSonFile.loadStations();

			List<Person> listPersonByFireStations = new ArrayList<Person>();

			for (FireStation fst : fireStationWithPersons) {
				for (String listedstation : fireStations) {
					if (fst.getStationNumber().equals(listedstation)) {
						for (Person ps : fst.getListOfPersons()) {
							listPersonByFireStations.add(ps);
						}
					}
				}
			}
			return listPersonByFireStations;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	
	
	  @Override
	  public boolean deleteStation(String fireStationNumber) { 
		  // TODO Auto-generated
	  
		 
	  FireStation fireStationSelected = getFireStation(fireStationNumber);
	  System.out.println("fireStationSelected :"+
	  fireStationSelected.getStationNumber()); 
	   try {
		List<FireStation> fireStations = jSonFile.loadStationsWithOutListPerson();
			
			  System.out.println("fireStations Size");
			  System.out.println(fireStations.size());
			 
		List<Person> personsLinkWithStation = fireStationSelected.getListOfPersons();
			/*
			 * System.out.println("personsLinkWithStation Size");
			 * System.out.println(personsLinkWithStation.size());
			 */
			/*
			 * for (Person p : personsLinkWithStation) { p.setFireStation(null); }
			 */ fireStations.remove(fireStationSelected);
		  jSonFile.loadStationsWithOutListPerson().remove(fireStationSelected);
			
			  System.out.println(
			  "jSonFile.loadStationsWithOutListPerson().remove(fireStationSelected)");
			  System.out.println(jSonFile.loadStationsWithOutListPerson().remove(
			  fireStationSelected));
			 
		  jSonFile.loadStations().remove(fireStationSelected); 
		  
			/*
			 * System.out.println(
			 * "jSonFile.loadStationsWithOutListPerson().remove(fireStationSelected)");
			 * System.out.println( jSonFile.loadStations().remove(fireStationSelected));
			 * 
			 */
	   return jSonFile.loadStations().remove(fireStationSelected);
	   
	   } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return false;
	  
	 
	  
	  
	  }
	 

	@Override
	public void deleteStationAdresse(String adresse) {
		// TODO Auto-generated method stub
		List<FireStation> fireStations;

		try {
			fireStations = jSonFile.loadStations();

			for (FireStation f : fireStations) {

				if (f.getAddresses().contains(adresse)) {
					f.getAddresses().remove(adresse);
				}
				if (f.getAddresses().contains(adresse)) {
					f.getAddresses().remove(adresse);
				}
				List<Person> persons = jSonFile.loadPersons();
				for (Person p : persons) {
					if (p.getFireStation().equals(f)) {
						p.setFireStation(null);
					}
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// http://localhost:8080/flood/stations?stations=<a list of station_numbers>

	@Override
	public List<Person> ListOfPersonLinkWithSelectedStations(List<String> fireStations) {
		List<Person> listOfPersonLinkWithSelectedStations = getListPersonByFireStations(fireStations);
		return listOfPersonLinkWithSelectedStations;
	}

	@Override
	public FireStation updateFireStationNumber(String fireStationNumber, String newStationNumber) {
		if (!fireStationNumber.isEmpty() && !newStationNumber.isEmpty()) {
			FireStation updateFireStationNumber = getFireStation(fireStationNumber);
			updateFireStationNumber.setStationNumber(newStationNumber);

			return updateFireStationNumber;
		} else
			return null;
	}

	@Override
	public List<PersonFire> getListPersonFireByAdresse(String adresse) {
		List<Person> listPersonByAdresse = getListPersonByAdresse(adresse);
		logger.info("listPersonByAdresse " + listPersonByAdresse.size());
		List<PersonFire> getListPersonFireByAdresse = new ArrayList<PersonFire>();
		for (Person p : listPersonByAdresse) {
			PersonFire pf = new PersonFire(p.getFirstName(), p.getLastName(), p.getPhone(), p.age(),
					p.getMedicalRecord().getMedications(), p.getMedicalRecord().getAllergies());

			getListPersonFireByAdresse.add(pf);
		}
		return getListPersonFireByAdresse;
	}

	@Override
	public List<PersonFire> getListPersonFireByFireStations(List<String> fireStations) {

		List<PersonFire> listPersonFireByFireStations = new ArrayList<PersonFire>();
		List<Person> listOfPersonLinkWithSelectedStations = getListPersonByFireStations(fireStations);
		for (Person p : listOfPersonLinkWithSelectedStations) {
			PersonFire pf = new PersonFire(p.getFirstName(), p.getLastName(), p.getPhone(), p.age(),
					p.getMedicalRecord().getMedications(), p.getMedicalRecord().getAllergies());
			listPersonFireByFireStations.add(pf);
		}
		return listPersonFireByFireStations;

	}

	

	@Override
	public List<PersonPhone> phoneList(String fireStationNumber) {
		List<PersonPhone> phoneList = new ArrayList<>();

		FireStation fireStationContactPhoneList = getFireStation(fireStationNumber);
		List<Person> personsToContact = fireStationContactPhoneList.getListOfPersons();
		for (Person p : personsToContact) {
			PersonPhone phonecontact = new PersonPhone(p.getPhone());
			phoneList.add(phonecontact);
		}

		return phoneList;
	}

//@GetMapping("/fire") http://localhost:8080/fire?address=<address>
//OK
	@Override
	public ListContactsForFire getlistContactsByAddressAndStation(String adresse) {
		// TODO Auto-generated method stub
		logger.info("Entering the GetlistContactsByAddressAndStation method");
		ListContactsForFire finalList = new ListContactsForFire();
		try {
			String stationNumber = getFireStationForPerson(adresse).getStationNumber().toString();

			List<PersonFire> list = getListPersonFireByAdresse(adresse);
			finalList = new ListContactsForFire(adresse, stationNumber, list);
			logger.info("create a list with adresse then station number then a list of contact");
			return finalList;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return finalList;
	}
//http://localhost:8080/flood/stations?stations=<a list of station_numbers>

	@Override
	public List<ListContactsForFire> listFlood(List<String> fireStations) {

		List<ListContactsForFire> listContactsForFlood = new ArrayList<ListContactsForFire>();
		if (fireStations.isEmpty()) {
			return listContactsForFlood;
		}
		for (String stationNumber : fireStations) {
			try {
				FireStation fs = getFireStation(stationNumber);

				for (String address : fs.getAddresses()) {
					listContactsForFlood.add(getlistContactsByAddressAndStation(address));
				}

			} catch (Exception e) {
				// TODO: handle exception
				logger.error("Invalid numbers!!!");
			}
		}
		return listContactsForFlood;
	}

}
