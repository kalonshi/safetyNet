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
import com.spl.safetyNet.Views.Family;
import com.spl.safetyNet.Views.InfoPerson;
import com.spl.safetyNet.Views.ListPerson;
import com.spl.safetyNet.Views.PersonEmail;
import com.spl.safetyNet.Views.PersonPrint;
import com.spl.safetyNet.Views.Personchild;
import com.spl.safetyNet.models.MedicalRecord;
import com.spl.safetyNet.models.Person;

@Service
@CacheConfig(cacheNames = { "stations", "persons", "medicalRecords" })
public class IPersonSerciveImpl implements IPerson {
	@Autowired
	private JsonFileData jSonFile;
	@Autowired
	private CacheManager cacheManager;
	private static final Logger logger = LogManager.getLogger(IPersonSerciveImpl.class);

	@Override
	public Person addPerson(String firstName, String lastName) {
		Person newPerson = new Person();

		logger.info("Entering the addPerson() method");
		if (!StringUtils.isEmpty(firstName) && !StringUtils.isEmpty(lastName)) {
			newPerson = new Person(firstName, lastName);
			try {
				/* jSonFile.loadJsonPersons().add(newPerson); */
				jSonFile.loadPersons().add(newPerson);
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
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
	public ListPerson listPersonsLinkToSelectedStation(String fireStationNumber) {
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
	public Person getPerson(String firstName, String lastName) {

		logger.info("Entering the getPerson() method");
		Person personByFirstNameAndLastName = new Person();
		 List<Person> personList; 
		try {

			personList = jSonFile.loadPersons();

			if (!StringUtils.isEmpty(firstName) && !StringUtils.isEmpty(lastName)) {

				for (Person p : personList) {

					if ((p.getFirstName().equals(firstName)) && (p.getLastName().equals(lastName))) {
						logger.info("Success get Person " + p.getFirstName() + " " + p.getLastName());

						personByFirstNameAndLastName = p;

					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return personByFirstNameAndLastName;
	}

	@Override
	public boolean delete(String firstName, String lastName) {
		boolean isPersonDelete = false;
		logger.info("Entering the delete() method");

		if (StringUtils.isEmpty(firstName) || StringUtils.isEmpty(lastName)) {
			logger.error("firstName isEmpty or lastName isEmpty");

		} else if (getPerson(firstName, lastName) != null) {
			Person person = getPerson(firstName, lastName);
			
				cacheManager.getCache("persons").evict(person);
				logger.info("success in deleted Person from cache ");
				isPersonDelete = true;

			
			
		}
		return isPersonDelete;
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

	@Override
	public Person updatePersonPhone(String firstName, String lastName, String updatedPhone) {
		logger.info("Entering updatePersonInfo method");

		Person updatedPersonPhone = new Person();
		MedicalRecord updatedMedicalRecord = new MedicalRecord();
		if (!StringUtils.isEmpty(firstName) && !StringUtils.isEmpty(lastName) && !StringUtils.isEmpty(updatedPhone)
				&& getPerson(firstName, lastName) != null) {
			Person personSelectedToUpdate = getPerson(firstName, lastName);

			updatedPersonPhone = personSelectedToUpdate;

			updatedPersonPhone.setPhone(updatedPhone);
			updatedMedicalRecord = personSelectedToUpdate.getMedicalRecord();
			cacheManager.getCache("persons").put(personSelectedToUpdate, updatedPhone);
			/*
			 * cacheManager.getCache("medicalRecord").put(updatedMedicalRecord.getPerson().
			 * getPhone(),updatedPhone);
			 */logger.info("updatedPhone" + updatedPersonPhone.getPhone());

		}

		return updatedPersonPhone;
	}

	@Override
	public Person updatePersonEmail(String firstName, String lastName, String updatedEmail) {
		logger.info("Entering updatePersonInfo method");
		Person updatedPersonEmail = new Person();
		if (!StringUtils.isEmpty(firstName) && !StringUtils.isEmpty(lastName) && !StringUtils.isEmpty(updatedEmail)
				&& getPerson(firstName, lastName) != null) {
			Person personSelectedToUpdate = getPerson(firstName, lastName);
			updatedPersonEmail = personSelectedToUpdate;
			updatedPersonEmail.setEmail(updatedEmail);
			cacheManager.getCache("persons").put(personSelectedToUpdate, updatedEmail);
			logger.info("updatedEmail" + updatedPersonEmail.getEmail());

		}
		logger.error("Failed to updated");
		return updatedPersonEmail;
	}

	@Override
	public Person updatePersonAdresse(String firstName, String lastName, String updatedAddress) {
		logger.info("Entering updatePersonInfo method");
		Person updatedPersonFullAddress = new Person();
		if (!StringUtils.isEmpty(firstName) && !StringUtils.isEmpty(lastName) && !StringUtils.isEmpty(updatedAddress)
				&& getPerson(firstName, lastName) != null) {
			Person personSelectedToUpdate = getPerson(firstName, lastName);
			updatedPersonFullAddress = personSelectedToUpdate;
			updatedPersonFullAddress.setAddress(updatedAddress);
			cacheManager.getCache("persons").put(personSelectedToUpdate, updatedAddress);
		}
		return updatedPersonFullAddress;
	}

	@Override
	public Person updatePersonFirstName(String firstName, String lastName, String updatedFirstName) {
		logger.info("Entering updatePersonInfo method");
		Person updatedPersonFirstName = new Person();
		if (!StringUtils.isEmpty(firstName) && !StringUtils.isEmpty(lastName) && !StringUtils.isEmpty(updatedFirstName)
				&& getPerson(firstName, lastName) != null) {
			Person personSelectedToUpdate = getPerson(firstName, lastName);
			updatedPersonFirstName = personSelectedToUpdate;
			updatedPersonFirstName.setFirstName(updatedFirstName);
			cacheManager.getCache("persons").put(personSelectedToUpdate, updatedFirstName);
			logger.info("updatedEmail" + updatedPersonFirstName.getEmail());

		}
		logger.error("Failed to updated");
		return updatedPersonFirstName;
	}

	@Override
	public Person updatePersonLaststName(String firstName, String lastName, String updatedLastName) {
		logger.info("Entering updatePersonInfo method");
		Person updatedPersonLastName = new Person();
		if (!StringUtils.isEmpty(firstName) && !StringUtils.isEmpty(lastName) && !StringUtils.isEmpty(updatedLastName)
				&& getPerson(firstName, lastName) != null) {
			Person personSelectedToUpdate = getPerson(firstName, lastName);
			updatedPersonLastName = personSelectedToUpdate;
			updatedPersonLastName.setLastName(updatedLastName);
			cacheManager.getCache("persons").put(personSelectedToUpdate, updatedLastName);
			logger.info("updatedEmail" + updatedPersonLastName.getLastName());

		}
		logger.error("Failed to updated");
		return updatedPersonLastName;
	}

}