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
	private JsonFileData4 jSonFile;

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
			fireStationByPersonAdresse.forEach(p -> p.setFireStation(newFireStation));
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
			phoneList.add("phone Number : "+p.getPhone());
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
				p.setFireStation(null);
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
	//http://localhost:8080/phoneAlert?firestation=<firestation_number>
	@Override
	public Set<String> getListOfPhoneNumbersFromPersonLinkWithSelectedStation(String fireStationNumber) {
		// TODO Auto-generated method stub
		Set<String> ListOfPhoneNumbers = new HashSet<String>();
		Set<Person> listPersons = getFireStation(fireStationNumber).getListOfPersons();
		for (Person p : listPersons) {
			ListOfPhoneNumbers.add("phone Number : "+p.getPhone());
		}
		return ListOfPhoneNumbers;
	}
//http://localhost:8080/firestation?stationNumber=<station_number>

	@Override
	public List<String[]>  ListOfPersonLinkWithSelectedStation(String fireStationNumber) {
		// TODO Auto-generated method stub
		List<String[]> listOfPersonLinkWithSelectedStation = new ArrayList<String[]>(); 
		Set<Person> listPersonsLink = new HashSet<>();
		String[] totaux= new String[2];
		int nbMinors=0;
		
		listPersonsLink=getListPersonByFireStation(fireStationNumber);
		int nbTotalPerson=listPersonsLink.size();
		for (Person psws:listPersonsLink) {
		
			if(psws.isMinor()){
				nbMinors++;
			}
			/*
			 * List<String> infosPersons = new ArrayList<String>();
			 * infosPersons.add(psws.getFirstName()); infosPersons.add(psws.getLastName());
			 * infosPersons.add(psws.getAddress()); infosPersons.add(psws.getPhone());
			 */
			
			String[] infosPersons = new String[4];
			infosPersons[0]=("FirstName :"+psws.getFirstName());
			infosPersons[1]=("LastName :"+psws.getLastName());
			infosPersons[2]=("Address :"+psws.getAddress()+"  "+psws.getCity()+"  "+psws.getZip());
			infosPersons[3]=("PhoneNumber:" +psws.getPhone());
			listOfPersonLinkWithSelectedStation.add(infosPersons);
		}
		
		totaux[0]=("Number of Minors :  "+String.valueOf(nbMinors));
		totaux[1]=("Number of Adults :  "+String.valueOf((nbTotalPerson-nbMinors)));
		listOfPersonLinkWithSelectedStation.add(totaux);
		return listOfPersonLinkWithSelectedStation;
	}

	
	//http://localhost:8080/flood/stations?stations=<a list of station_numbers>

	@Override
	public Set<String[]> ListOfPersonLinkWithSelectedStations(List<String> fireStations) {
		Set<Person> listOfPersonLinkWithSelectedStations=getListPersonByFireStations(fireStations); 
		Set<String[]> listOfPersonLinkWithSelectedStationsToPrint = new HashSet<String[]>();
		
		for(Person p :listOfPersonLinkWithSelectedStations) {
			String[] infosPersonsToPrint = new String[6];
			infosPersonsToPrint[0]=(p.getAddress());
			infosPersonsToPrint[1]=(p.getLastName());
			infosPersonsToPrint[2]=(p.getPhone());
			infosPersonsToPrint[3]=(String.valueOf( p.age()) );
			infosPersonsToPrint[4]=(p.getMedicalRecord().getMedications().toString());
			infosPersonsToPrint[5]=(p.getMedicalRecord().getAllergies().toString());
			listOfPersonLinkWithSelectedStationsToPrint.add(infosPersonsToPrint);
		
		}
		return listOfPersonLinkWithSelectedStationsToPrint;
	}

	@Override
	public FireStation updateFireStationNumber(String fireStationNumber,String newStationNumber) {
		FireStation updateFireStationNumber= getFireStation(fireStationNumber);
		updateFireStationNumber.setStationNumber(newStationNumber);
		return updateFireStationNumber;
	}
}