package com.spl.safetyNet.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spl.safetyNet.Views.Family;
import com.spl.safetyNet.Views.InfoPerson;
import com.spl.safetyNet.Views.ListPerson;
import com.spl.safetyNet.Views.PersonEmail;
import com.spl.safetyNet.Views.PersonFire;
import com.spl.safetyNet.Views.PersonPrint;
import com.spl.safetyNet.Views.Personchild;

import com.spl.safetyNet.models.MedicalRecord;
import com.spl.safetyNet.models.Person;

@Service
public class IPersonSerciveImpl implements IPerson {
	@Autowired
	private JsonFileData jSonFile;
	private static final Logger logger = LogManager.getLogger(IPersonSerciveImpl.class);

	@Override
	public Person addPerson(String firstName, String lastName, String phone, String zip, String address, String city,
			String email, Date birthDate) {
		Person newPerson = new Person();

		logger.info("Entering the addPerson() method");
		if (StringUtils.isEmpty(firstName) || StringUtils.isEmpty(lastName) || StringUtils.isEmpty(phone)
				|| StringUtils.isEmpty(zip) || StringUtils.isEmpty(address) && StringUtils.isEmpty(city)
				|| StringUtils.isEmpty(email) || birthDate.equals(null)) {
			logger.error("Failed to add due to empty field ");

		} else
			newPerson = new Person(firstName, lastName, phone, zip, address, city, email, birthDate);

		try {
			jSonFile.loadJsonPersons().add(newPerson);
			logger.info("succes add a new Person");

		} catch (IOException e) {

			e.printStackTrace();
		}
		logger.error("failed to add new Person");
		return newPerson;
	}

	@Override
	public InfoPerson getInfoPerson(String firstName, String lastName) {

		logger.info("Entering the getPerson() method");
		InfoPerson personInfo = new InfoPerson();
		List<Person> personList;
		try {
			personList = jSonFile.loadPersons();
			if (!StringUtils.isEmpty(firstName) && !StringUtils.isEmpty(lastName)) {
				for (Person p : personList) {
					if ((p.getFirstName().equals(firstName)) && (p.getLastName().equals(lastName))) {
						personInfo = new InfoPerson(p.getFirstName(), p.getLastName(), p.getAddress(), p.getZip(),
								p.getCity(), p.age(), p.getEmail(), p.getMedicalRecord().getMedications(),
								p.getMedicalRecord().getAllergies());
						logger.info("Success find Person with firstName  and lastName");
						return (personInfo);

					}
				}
			}
		} catch (IOException e) {

			logger.info("Failed to  find Person with firstName  and lastName");

			e.printStackTrace();
		}

		return personInfo;
	}

	@Override
	public Person getPerson(String firstName, String lastName) {

		logger.info("Entering the getPerson() method");
		Person personSearch = new Person();
		List<Person> personList;
		try {

			personList = jSonFile.loadPersons();

			if (!StringUtils.isEmpty(firstName) && !StringUtils.isEmpty(lastName)) {

				for (Person p : personList) {

					if ((p.getFirstName().equals(firstName)) && (p.getLastName().equals(lastName))) {
						logger.info("Success get Person " + p.getFirstName() + " " + p.getLastName());

						personSearch = p;
						return personSearch;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return personSearch;
	}

	@Override
	public boolean delete(String firstName, String lastName) {
		boolean isPersonDelete = false;
		logger.info("Entering the delete() method");

		if (StringUtils.isEmpty(firstName) || StringUtils.isEmpty(lastName)) {
			logger.error("firstName isEmpty or lastName isEmpty");

		} else if (getPerson(firstName, lastName) != null) {
			Person person = getPerson(firstName, lastName);

			try {

				MedicalRecord medicalRecordToDelete = person.getMedicalRecord();
				jSonFile.loadJsonMedicalRecords().remove(medicalRecordToDelete);
				jSonFile.loadJsonPersons().remove(person);
				logger.info("success in deleted Person  ");
				isPersonDelete = true;

			} catch (IOException e) {
				logger.error("unknown firstName or lastName ");
				e.printStackTrace();

			}
		}

		return isPersonDelete;
	}

	@Override
	public List<Person> getfamilyMenbers(String lastName) {

		logger.info("Entering the getfamilyMenbers() method");
		List<Person> familyMenbers = new ArrayList<Person>();
		if (!StringUtils.isEmpty(lastName)) {
			try {
				List<Person> persons = jSonFile.loadPersons();
				for (Person p : persons) {
					if (p.getLastName().equals(lastName)) {
						familyMenbers.add(p);
					}
				}

			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		return familyMenbers;
	}

	@Override
	public List<Person> getResidentsByAddress(String address) {

		logger.info("Entering getResidentsByAddress(String address) method");
		List<Person> residents = new ArrayList<>();
		if (!StringUtils.isEmpty(address)) {
			try {
				List<Person> persons = jSonFile.loadPersons();
				for (Person p : persons) {
					if ((p.getAddress().equals(address))) {
						residents.add(p);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return residents;

	}

	@Override
	public List<InfoPerson> listSelectedPersonByFirstNameAndLasrtNameAndRelatives(String firstName, String lastName) {

		logger.info("Entering getListPersons() method");
		List<InfoPerson> listSelectedPersonAndRelatives = new ArrayList<InfoPerson>();
		List<Person> listOfAllrecordPersons;
		if (!StringUtils.isEmpty(firstName) && !StringUtils.isEmpty(lastName)) {
			try {
				listOfAllrecordPersons = jSonFile.loadPersons();

				listSelectedPersonAndRelatives.add(getInfoPerson(firstName, lastName));
				for (Person p : listOfAllrecordPersons) {
					InfoPerson info = new InfoPerson();
					if (p.getLastName().equals(lastName) && !p.getFirstName().equals(firstName)) {

						info = new InfoPerson(p.getFirstName(), p.getLastName(), p.getAddress(), p.getZip(),
								p.getCity(), p.age(), p.getEmail(), p.getMedicalRecord().getMedications(),
								p.getMedicalRecord().getAllergies());
						listSelectedPersonAndRelatives.add(info);
					}
					logger.info("Success add list person with   lastName " + listSelectedPersonAndRelatives.size());
				}
				return listSelectedPersonAndRelatives;
			} catch (IOException e) {

				logger.error("Failed add person with   lastName ");

				e.printStackTrace();
			}

		}

		return listSelectedPersonAndRelatives;
	}

	@Override
	public List<Personchild> printlistMinorsByAddress(String adress) {
		logger.info("Entering url http://localhost:8080/childAlert?address= " + adress);

		List<Person> listPersonsByAdresse = new ArrayList<Person>();
		List<Person> listFamilyMenber = new ArrayList<Person>();

		List<Personchild> childAlertList = new ArrayList<Personchild>();
		listPersonsByAdresse = getResidentsByAddress(adress);

		for (Person pf : listPersonsByAdresse) {

			List<Family> familyMenbers = new ArrayList<Family>();
			if (pf.isMinor()) {
				Personchild minor = new Personchild(pf.getFirstName(), pf.getLastName(), pf.age());
				listFamilyMenber = getfamilyMenbers(pf.getLastName());
				if (!listFamilyMenber.isEmpty()) {
					for (Person familyMenber : listFamilyMenber) {
						if (!(familyMenber.getFirstName().equals(pf.getFirstName()))
								&& (familyMenber.getAddress().equals(pf.getAddress()))) {

							Family relative = new Family(familyMenber.getFirstName(), familyMenber.getLastName(),
									familyMenber.age());

							familyMenbers.add(relative);
						}
					}
				}
				logger.info("No Familly Menbers ");

				minor.setFamilyMenber(familyMenbers);
				childAlertList.add(minor);
			}

		}

		return childAlertList;
	}

	@Override
	public Person updatePerson(String firstName, String lastName, String newPhone, String newZip, String newAddress,
			String newCity, String newEmail) {
		logger.info("Entering updatePersonInfo method");
		Person personSelectedToUpdate = getPerson(firstName, lastName);
		if (personSelectedToUpdate != null) {
			if (!StringUtils.isEmpty(newPhone)) {
				personSelectedToUpdate.setPhone(newPhone);
				logger.info("new phone" + personSelectedToUpdate.getPhone());
			}
			if (!StringUtils.isEmpty(newZip)) {
				personSelectedToUpdate.setZip(newZip);
			}
			if (!StringUtils.isEmpty(newAddress)) {
				personSelectedToUpdate.setAddress(newAddress);
			}
			if (!StringUtils.isEmpty(newCity)) {
				personSelectedToUpdate.setCity(newCity);
			}
			if (!StringUtils.isEmpty(newEmail)) {
				personSelectedToUpdate.setEmail(newEmail);

			}
			return personSelectedToUpdate;
		}
		logger.error("Failed to updated");
		return personSelectedToUpdate;
	}

	@Override
	public ListPerson listPersonsLinkToSelectedStation(String fireStationNumber) { // TODO Auto-generated method
		logger.info("Entering listPersonsLinkToStationSelected method" + fireStationNumber);
		ListPerson list = new ListPerson();
		List<PersonPrint> listPersonsLinkToSelectedStation = new ArrayList<PersonPrint>();
		if (!StringUtils.isEmpty(fireStationNumber)) {
			try {
				List<Person> listOfAllRegisterPerson = jSonFile.loadPersons();
				int qtMinors = 0;
				for (Person personLinkToSelectedStation : listOfAllRegisterPerson) {

					if (personLinkToSelectedStation.getFireStation().getStationNumber().equals(fireStationNumber)) {

						if (personLinkToSelectedStation.isMinor()) {

							qtMinors++;
						}
						PersonPrint infoPerso = new PersonPrint(personLinkToSelectedStation.getFirstName(),
								personLinkToSelectedStation.getLastName(), personLinkToSelectedStation.getAddress(),
								personLinkToSelectedStation.getCity(), personLinkToSelectedStation.getZip(),
								personLinkToSelectedStation.getPhone());
						listPersonsLinkToSelectedStation.add(infoPerso);
					}

				}

				list.setContactsList(listPersonsLinkToSelectedStation);
				list.setNbMinors(qtMinors);
				list.setNbAdults(listPersonsLinkToSelectedStation.size() - qtMinors);
				logger.info(" Creation de la list firestation?stationNumber ");

			} catch (IOException e) {
				e.printStackTrace();

				logger.error(" Failed create list . Return empty List ");
			}
		}

		return list;
	}

	@Override
	public List<PersonEmail> listEmail(String city) {
		List<PersonEmail> listEmails = new ArrayList<PersonEmail>();
		if (!StringUtils.isEmpty(city)) {
			try {
				List<Person> persons = jSonFile.loadPersons();
				List<Person> personEmails = new ArrayList<Person>();
				Set<String> emailsWithoutDouble = new HashSet<String>();

				for (Person p : persons) {
					if (p.getCity().equals(city)) {
						personEmails.add(p);
					}
				}
				for (Person p : personEmails) {
					emailsWithoutDouble.add(p.getEmail());
				}

				for (String email : emailsWithoutDouble) {
					PersonEmail newEmail = new PersonEmail(email);
					listEmails.add(newEmail);
				}
				logger.info("list Email size :" + listEmails);

			} catch (IOException e) {

				e.printStackTrace();
			}
			logger.error("Failed to get List of email by city :" + city);

		}
		return listEmails;
	}

}