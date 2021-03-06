package com.spl.safetyNet.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import com.spl.safetyNet.Views.ListContactsForFire;
import com.spl.safetyNet.Views.PersonFire;
import com.spl.safetyNet.Views.PersonPhone;
import com.spl.safetyNet.models.FireStation;
import com.spl.safetyNet.models.Person;

@Service
@CacheConfig(cacheNames = { "stations", "persons", "medicalRecords" })

public class IFirestationImpl implements IFirestation {
	@Autowired
	private JsonFileData jSonFile;
	@Autowired
	private CacheManager cacheManager;
	private static final Logger logger = LogManager.getLogger(IFirestationImpl.class);

	@Override
	public FireStation getFireStation(String fireStationNumber) {
		FireStation selectedStation = new FireStation();
		logger.info("GET FIRESTATION BY NUMBER ");
		if (!StringUtils.isEmpty(fireStationNumber)) {
			try {

				List<FireStation> fireStations = jSonFile.loadStations();
				logger.info(" ");
				for (FireStation f : fireStations) {
					if (f.getStationNumber().equals(fireStationNumber)) {
						selectedStation = f;
					}
				}

			} catch (IOException e) {

				logger.error(" Pas de resultat");
				e.printStackTrace();
			}

		}

		return selectedStation;
	}

	@Override
	public FireStation addFireStation(String fireStationNumber, String addresse) {

		logger.info("Entering the addFireStation() method");
		FireStation newFireStation = new FireStation();
		if (!StringUtils.isEmpty(fireStationNumber) && !StringUtils.isEmpty(addresse)) {
			newFireStation = new FireStation(fireStationNumber);

			newFireStation.addAddress(addresse);
			try {
				jSonFile.loadStations().add(newFireStation);
			} catch (IOException e) {

				e.printStackTrace();
			}

		}

		return newFireStation;
	}

	@Override
	public FireStation getFireStationForPerson(String adresse) {

		logger.info("Entering getFireStationForPerson ");
		FireStation fireStationSelected = new FireStation();
		if (!StringUtils.isEmpty(adresse)) {
			try {
				List<FireStation> fireStations = jSonFile.loadStations();

				for (FireStation f : fireStations) {

					Set<String> addresses = f.getAddresses();
					for (String ad : addresses) {

						if (ad.equals(adresse)) {

							fireStationSelected = f;
							logger.info("select station OK");
							return fireStationSelected;
						} else {
							logger.info("select station NOK!!!!!!");
						}
					}

				}

			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		return fireStationSelected;
	}

	@Override
	public List<Person> getListPersonByAdresse(String adresse) {

		List<Person> listPersonsByAdresse = new ArrayList<Person>();
		if (!StringUtils.isEmpty(adresse)) {
			FireStation f = getFireStationForPerson(adresse);
			List<Person> listPersonByStation = f.getListOfPersons();
			listPersonByStation.forEach(p -> {
				if (p.getAddress().equals(adresse)) {
					listPersonsByAdresse.add(p);
				}
			});

		}
		return listPersonsByAdresse;
	}

	@Override
	public boolean deleteStation(String stationNumber) {
		boolean isDeleted = false;
		if (!StringUtils.isEmpty(stationNumber) && getFireStation(stationNumber)!= null) {
 
			FireStation fireStationSelected = getFireStation(stationNumber);
			logger.info("fireStationSelected :" + fireStationSelected.getStationNumber());
try {
	jSonFile.loadStations().remove(fireStationSelected);
	isDeleted = true;} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
			
			
		}

		return isDeleted;
	}

	@Override
	public boolean deleteStationAdresse(String adresse) {
		boolean isAdressDeleted = false;
		int indexFireStation=0;
		List<FireStation> fireStations;
		if (!StringUtils.isEmpty(adresse)) {
			try {
				fireStations = jSonFile.loadStations();
				int n=0;
				for (FireStation f : fireStations) {

					if (f.getAddresses().contains(adresse)) {
						indexFireStation=n;
						/* f.getAddresses().remove(adresse); */
						jSonFile.loadStations().get(n).getAddresses().remove(adresse);
						isAdressDeleted = true;
					}
					n++;
				}
				
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		return isAdressDeleted;
	}

	@Override

	public FireStation updateFireStationNumber(String fireStationNumber, String newStationNumber) {
		FireStation updatedStationNumber = new FireStation();
		if (!StringUtils.isEmpty(fireStationNumber) && !StringUtils.isEmpty(newStationNumber)
				&& getFireStation(fireStationNumber) != null) {
			FireStation station = getFireStation(fireStationNumber);
			updatedStationNumber = station;
			updatedStationNumber.setStationNumber(newStationNumber);

			cacheManager.getCache("stations").put(station.getStationNumber(), newStationNumber);

		}
		return updatedStationNumber;
	}

	@Override
	public List<PersonFire> getListPersonFireByAdresse(String adresse) {
		List<PersonFire> listPersonsByAdresseInCaseOfFire = new ArrayList<PersonFire>();
		if (!StringUtils.isEmpty(adresse)) {
			List<Person> listPersonsByAdresse = getListPersonByAdresse(adresse);
			logger.info("listPersonsByAdresse " + listPersonsByAdresse.size());

			for (Person p : listPersonsByAdresse) {
				PersonFire pf = new PersonFire(p.getFirstName(), p.getLastName(), p.getPhone(), p.age(),
						p.getMedicalRecord().getMedications(), p.getMedicalRecord().getAllergies());

				listPersonsByAdresseInCaseOfFire.add(pf);
			}
		}

		return listPersonsByAdresseInCaseOfFire;
	}

	@Override
	public List<PersonPhone> phoneList(String fireStationNumber) {
		List<PersonPhone> phoneList = new ArrayList<>();
		if (!fireStationNumber.isEmpty()) {
			try {
				FireStation fireStationContactPhoneList = getFireStation(fireStationNumber);
				List<Person> personsToContact = fireStationContactPhoneList.getListOfPersons();
				Set<String> phoneParse = new HashSet<String>();
				for (Person p : personsToContact) {
					phoneParse.add(p.getPhone());
				}

				for (String phone : phoneParse) {
					PersonPhone phonecontact = new PersonPhone(phone);
					phoneList.add(phonecontact);
				}

			} catch (Exception e) {
				logger.error("unabled to get a phoneList");

			}

		}
		logger.error("Failed due to unknown Station Number");
		return phoneList;
	}

	@Override
	public ListContactsForFire getlistContactsByAddressAndStation(String adresse) {

		logger.info("Entering the GetlistContactsByAddressAndStation method");
		ListContactsForFire listContactsForFire = new ListContactsForFire();
		try {
			String stationNumber = getFireStationForPerson(adresse).getStationNumber().toString();

			List<PersonFire> listPersonFireByAdresse = getListPersonFireByAdresse(adresse);
			listContactsForFire = new ListContactsForFire(adresse, stationNumber, listPersonFireByAdresse);
			logger.info("create a list with adresse then station number then a list of contact");
			return listContactsForFire;

		} catch (Exception e) {
			logger.error("List can't be found at this address" + adresse);

		}
		return listContactsForFire;
	}

	@Override
	public List<ListContactsForFire> listFlood(List<String> fireStations) {

		List<ListContactsForFire> listContactsForFlood = new ArrayList<ListContactsForFire>();
		if (!fireStations.isEmpty()) {

			for (String stationNumber : fireStations) {
				try {
					FireStation fs = getFireStation(stationNumber);

					for (String address : fs.getAddresses()) {
						listContactsForFlood.add(getlistContactsByAddressAndStation(address));
					}

				} catch (Exception e) {

					logger.error("Invalid numbers!!!");
				}
			}
		}
		return listContactsForFlood;
	}

}
