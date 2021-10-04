package com.spl.safetyNet.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spl.safetyNet.models.FireStation;
import com.spl.safetyNet.models.MedicalRecord;
import com.spl.safetyNet.models.Person;

@Service

public class IFirestationImpl implements IFirestation {
	@Autowired
	private JsonFileData3 jSonFile;

	@Override
	public FireStation addFireStation(String fireStationNumber, String addresse) {
		// TODO Auto-generated method stub
		List<Person> listPerson;
		try {
			listPerson = jSonFile.loadPersons();
			Set<Person> fireStationByPersonAdresse = new HashSet<Person>();

			listPerson.forEach(p -> {
				if (p.getAddress().contains(addresse)) {
					fireStationByPersonAdresse.add(p);
				}
			});

			FireStation newFireStation = new FireStation(fireStationNumber);
			newFireStation.addAddress(addresse);
			newFireStation.setListOfPersons(fireStationByPersonAdresse);
			fireStationByPersonAdresse.forEach(p -> p.addFireStation(newFireStation));
			return newFireStation;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public FireStation getFireStation(String fireStationNumber) {
		// TODO Auto-generated method stub
		try {
			List<FireStation> fireStations = jSonFile.loadStations();
			for (FireStation f : fireStations) {
				if (f.getStationNumber().equals(fireStationNumber)) {
					return f;
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Set<FireStation> getFireStationForPerson(String adresse) {
		// TODO Auto-generated method stub
		Set<FireStation> fireStationSelected = new HashSet<>();
		try {
			List<FireStation> fireStations = jSonFile.loadStations();
			for (FireStation f : fireStations) {
				if (f.getAddresses().contains(adresse)) {
					fireStationSelected.add(f);
				}

			}

			return fireStationSelected;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Set<Person> getListPersonByFireStation(String fireStationNumber) {
		// TODO Auto-generated method stub
		FireStation fireStationSelected = getFireStation(fireStationNumber);
		Set<Person> listPersonByStation = new HashSet<Person>();
		listPersonByStation = fireStationSelected.getListOfPersons();
		return listPersonByStation;
	}
//http://localhost:8080/phoneAlert?firestation=<firestation_number>
	@Override
	public Set<String> getPhoneList(String fireStationNumber) {
		// TODO Auto-generated method stub
		Set<String> phoneList = new HashSet<>();
		;
		FireStation fireStationContactPhoneList = getFireStation(fireStationNumber);
		Set<Person> personsToContact = fireStationContactPhoneList.getListOfPersons();
		for (Person p : personsToContact) {
			phoneList.add(p.getPhone());
		}

		return phoneList;
	}

	@Override
	public Set<Person> getListPersonByAdresse(String adresse) {
		// TODO Auto-generated method stub
		Set<Person> ListPersonByAdresse = new HashSet<Person>();
		getFireStationForPerson(adresse);

		return null;
	}
//:http://localhost:8080/flood/stations?stations=<a list of station_numbers>
	@Override
	public Set<Person> getListPersonByFireStations(List<String> fireStations) {
		// TODO Auto-generated method stub
		
		try {
			
			List<FireStation> fireStationWithPersons;
			fireStationWithPersons = jSonFile.loadStations();
			
			Set<Person> listPersonByFireStations = new HashSet<Person>();
			
			for (FireStation fst : fireStationWithPersons) {
				for(String listedstation:fireStations) {
				if(fst.getStationNumber().equals(listedstation))
				{
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
	public void deleteStation(String fireStationNumber) {
		// TODO Auto-generated method stub
		FireStation fireStationSelected = getFireStation(fireStationNumber);
		try {
			List<FireStation> fireStations = jSonFile.loadStations();
			Set<Person> personsLinkWithStation = fireStationSelected.getListOfPersons();
			for (Person p : personsLinkWithStation) {
				p.getFireStation().remove(fireStationSelected);
			}
			fireStations.remove(fireStationSelected);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		fireStationSelected = null;
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
					if (p.getFireStation().contains(f)) {
						p.getFireStation().remove(f);
					}
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	//http://localhost:8080/phoneAlert?firestation=<firestation_number>
	@Override
	public Set<String> getListOfPhoneNumbersFromPersonLinkWithSelectedStation(String fireStationNumber) {
		// TODO Auto-generated method stub
		Set<String> ListOfPhoneNumbers = new HashSet<String>();
		Set<Person> listPersons = getFireStation(fireStationNumber).getListOfPersons();
		for (Person p : listPersons) {
			ListOfPhoneNumbers.add(p.getPhone());
		}
		return ListOfPhoneNumbers;
	}
//http://localhost:8080/firestation?stationNumber=<station_number>

	@Override
	public Set<String> ListOfPersonLinkWithSelectedStation(String fireStationNumber) {
		// TODO Auto-generated method stub
		Set<String> listOfPersonLinkWithSelectedStation = new HashSet<String>();
		Set<Person> listPersonsLinkWithSelectedStation = new HashSet<>();
		List<String> totaux=new ArrayList<String>();
		int nbMinors=0;
		
		listPersonsLinkWithSelectedStation=getListPersonByFireStation(fireStationNumber);
		int nbTotalPerson=listPersonsLinkWithSelectedStation.size();
		for (Person psws:listPersonsLinkWithSelectedStation) {
		
			if(psws.isMinor()){
				nbMinors++;
			}
			List<String> infosPersons = new ArrayList<String>();
			infosPersons.add(psws.getFirstName());
			infosPersons.add(psws.getLastName());
			infosPersons.add(psws.getAddress());
			infosPersons.add(psws.getPhone());
				
			listOfPersonLinkWithSelectedStation.addAll(infosPersons);
		}
		
		totaux.add(String.valueOf(nbMinors));
		totaux.add(String.valueOf((nbTotalPerson-nbMinors)));
		listOfPersonLinkWithSelectedStation.addAll(totaux);
		return listOfPersonLinkWithSelectedStation;
	}

	
	//http://localhost:8080/flood/stations?stations=<a list of station_numbers>

	@Override
	public Set<String> ListOfPersonLinkWithSelectedStations(List<String> fireStations) {
		Set<Person> listOfPersonLinkWithSelectedStations=getListPersonByFireStations(fireStations); 
		Set<String> listOfPersonLinkWithSelectedStationsToPrint = new HashSet<String>();
		
		for(Person p :listOfPersonLinkWithSelectedStations) {
			List<String> infosPersonsToPrint = new ArrayList<String>();
			infosPersonsToPrint.add(p.getAddress());
			infosPersonsToPrint.add(p.getLastName());
			infosPersonsToPrint.add(p.getPhone());
			infosPersonsToPrint.add(String.valueOf( p.age()) );
			infosPersonsToPrint.add(p.getMedicalRecord().getMedications().toString());
			infosPersonsToPrint.add(p.getMedicalRecord().getAllergies().toString());
			listOfPersonLinkWithSelectedStationsToPrint.addAll(infosPersonsToPrint);
		
		}
		return listOfPersonLinkWithSelectedStationsToPrint;
	}
}