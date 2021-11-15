package com.spl.safetyNet.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
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

	private static final Logger logger = LogManager.getLogger(IFirestationImpl.class);

	@Override
	public FireStation addFireStation(String fireStationNumber, String addresse) {

		logger.info("Entering the addFireStation() method");
		FireStation newFireStation = new FireStation();

		if (!StringUtils.isEmpty(fireStationNumber) && !StringUtils.isEmpty(addresse)) {
			newFireStation = new FireStation(fireStationNumber);

			newFireStation.addAddress(addresse);
			try {
				jSonFile.loadStationsWithOutListPerson().add(newFireStation);

			} catch (IOException e) {

				e.printStackTrace();
			}

		}

		return newFireStation;
	}

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
						}
						logger.info("select station NOK!!!!!!");
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
	public boolean deleteStation(String fireStationNumber) {
		boolean isDeleted = false;
		if (!StringUtils.isEmpty(fireStationNumber) && getFireStation(fireStationNumber) != null) {

			FireStation fireStationSelected = getFireStation(fireStationNumber);
			logger.info("fireStationSelected :" + fireStationSelected.getStationNumber());
			try {
				List<FireStation> listOfStations = jSonFile.loadStations();

				listOfStations.remove(fireStationSelected);
				jSonFile.loadStationsWithOutListPerson().remove(fireStationSelected);

				isDeleted = listOfStations.remove(fireStationSelected);

			} catch (IOException e) {

				e.printStackTrace();
			}

		}

		return isDeleted;
	}

	@Override
	public boolean deleteStationAdresse(String adresse) {
		boolean isAdressDeleted = false;
		List<FireStation> fireStations;
		if (!StringUtils.isEmpty(adresse)) {
			try {
				fireStations = jSonFile.loadStations();

				for (FireStation f : fireStations) {

					if (f.getAddresses().contains(adresse)) {
						f.getAddresses().remove(adresse);
						isAdressDeleted = true;
					}

					List<Person> persons = jSonFile.loadPersons();
					for (Person p : persons) {
						if (p.getFireStation().equals(f)) {
							p.setFireStation(null);
						}
					}

				}

			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		return isAdressDeleted;
	}

	@Override
	public boolean updateFireStationNumber(String fireStationNumber, String newStationNumber) {
		boolean isStationUpdated = false;
		if (!StringUtils.isEmpty(fireStationNumber) && !StringUtils.isEmpty(newStationNumber)
				&& getFireStation(fireStationNumber) != null) {
			FireStation updateFireStationNumber = getFireStation(fireStationNumber);
			updateFireStationNumber.setStationNumber(newStationNumber);

			isStationUpdated = true;
		}
		return isStationUpdated;
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

				return phoneList;
			} catch (Exception e) {
				logger.error("unabled to get a phoneList");

			}
			return phoneList;
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
