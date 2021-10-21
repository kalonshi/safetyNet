package com.spl.safetyNet.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;

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

		// TODO Auto-generated method stub
		logger.info("Entering the addPerson() method");
		if (firstName.isEmpty() && lastName.isEmpty() && phone.isEmpty() && zip.isEmpty() && address.isEmpty()
				&& city.isEmpty() && email.isEmpty() && birthDate.equals(null)) {
			return null;
		} else
			newPerson = new Person(firstName, lastName, phone, zip, address, city, email, birthDate);
		try {
			jSonFile.loadJsonPersons().add(newPerson);
			return newPerson;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newPerson;
	}
	
	@Override
	public InfoPerson getInfoPerson(String firstName, String lastName) {
		// TODO Auto-generated method stub
		logger.info("Entering the getPerson() method");
		InfoPerson personInfo = new InfoPerson();
		List<Person> personList;
		try {
			personList = jSonFile.loadPersons();
			if (!firstName.isEmpty() && !lastName.isEmpty()) {
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
			// TODO Auto-generated catch block
			logger.info("Failed to  find Person with firstName  and lastName");

			e.printStackTrace();
		}

		return personInfo;
	}

	@Override
	public Person getPerson(String firstName, String lastName) {
		// TODO Auto-generated method stub
		logger.info("Entering the getPerson() method");

		List<Person> personList;
		try {
			personList = jSonFile.loadPersons();
			if (!firstName.isEmpty() && !lastName.isEmpty())
				for (Person p : personList) {
					if ((p.getFirstName().equals(firstName)) && (p.getLastName().equals(lastName)))
						logger.info("Success get Person " + p.getFirstName() + " " + p.getLastName());
					return p;
				}

		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void delete(String firstName, String lastName) {
		// TODO Auto-generated method stub
		logger.info("Entering the delete() method");
		Person person = getPerson(firstName, lastName);

		try {

			MedicalRecord medicalRecordToDelete = person.getMedicalRecord();
			jSonFile.loadJsonMedicalRecords().remove(medicalRecordToDelete);
			jSonFile.loadJsonPersons().remove(person);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		person = null;
	}

	@Override
	public List<Person> getfamilyMenbers(String lastName) {
		// TODO Auto-generated method stub
		logger.info("Entering the getfamilyMenbers() method");
		List<Person> familyMenbers = new ArrayList<Person>();
		try {
			List<Person> persons = jSonFile.loadPersons();
			for (Person p : persons) {
				if (p.getLastName().equals(lastName)) {
					familyMenbers.add(p);
				}
			}
			return familyMenbers;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return familyMenbers;
	}

	@Override
	public List<Person> getMinorsByAddress(String address) {
		// TODO Auto-generated method stub
		logger.info("Entering getMinorsByAddress() method");
		List<Person> minors = new ArrayList<>();
		try {
			List<Person> persons = jSonFile.loadPersons();
			for (Person p : persons) {
				if ((p.getAddress().contains(address)) && (p.isMinor())) {
					minors.add(p);
				}
			}
			return minors;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Person> getAdultsByAddress(String address) {
		// TODO Auto-generated method stub
		List<Person> adults = new ArrayList<>();
		try {
			List<Person> persons = jSonFile.loadPersons();
			for (Person p : persons) {
				if ((p.getAddress().contains(address)) && (!p.isMinor())) {
					adults.add(p);
				}
			}
			return adults;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	@Override
	public List<Person> getResidentsByAddress(String address) {
		// TODO Auto-generated method stub

		logger.info("Entering getResidentsByAddress(String address) method");
		List<Person> residents = new ArrayList<>();
		try {
			List<Person> persons = jSonFile.loadPersons();
			for (Person p : persons) {
				if ((p.getAddress().contains(address))) {
					residents.add(p);
				}
			}

			return residents;
		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		return residents;

	}

// http://localhost:8080/personInfo?firstName=%3CfirstName%3E&lastName=%3ClastName
	@Override
	public List<InfoPerson> getListInfoPerson(String firstName, String lastName) {
		// TODO Auto-generated method stub
		logger.info("Entering getListPersons() method");
		List<InfoPerson> getPersons = new ArrayList<InfoPerson>();
		List<Person> personList;
		if (!firstName.isEmpty() && !lastName.isEmpty()) {
			try {
				personList = jSonFile.loadPersons();

				getPersons.add(getInfoPerson(firstName, lastName));
				for (Person p : personList) {
					InfoPerson info = new InfoPerson();
					if (p.getLastName().equals(lastName) && !p.getFirstName().equals(firstName)) {

						info = new InfoPerson(p.getFirstName(), p.getLastName(), p.getAddress(), p.getZip(),
								p.getCity(), p.age(), p.getEmail(), p.getMedicalRecord().getMedications(),
								p.getMedicalRecord().getAllergies());
						getPersons.add(info);
						}
					logger.info("Success add list person with   lastName " + getPersons.size());
				}
				return getPersons;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("Failed add person with   lastName ");

				e.printStackTrace();
			}

		}

		return getPersons;
	}

	@Override

	public List<Person> printlistPersonByadress(String adress) {
		logger.info("Entering printlistPersonByadress() method");
		List<Person> getInfoPersons = new ArrayList<Person>();
		if (adress.isEmpty()) {
			return getInfoPersons;
		}
		getInfoPersons = getResidentsByAddress(adress);

		return getInfoPersons;
	}

	// http://localhost:8080/childAlert?address=<address>

	@Override
	public List<Personchild> printlistMinorsByAddress(String adress) {
		logger.info("Entering printlistMinorsByAddress() method");
		List<Person> getInfoPersons = new ArrayList<Person>();
		List<Person> getInfoPersonsAdult = new ArrayList<Person>();

		List<Personchild> childAlertList = new ArrayList<Personchild>();
		getInfoPersons = getResidentsByAddress(adress);

		for (Person pf : getInfoPersons) {

			// ******TEST ajout ChildClass***************************

			List<Family> familyMenbers = new ArrayList<Family>();
			if (pf.isMinor()) {
				Personchild newChild = new Personchild(pf.getFirstName(), pf.getLastName(), pf.age());
				getInfoPersonsAdult = getfamilyMenbers(pf.getLastName());
				if (!getInfoPersonsAdult.isEmpty()) {
					for (Person pa : getInfoPersonsAdult) {
						if (!(pa.getFirstName().equals(pf.getFirstName()))
								&& (pa.getAddress().equals(pf.getAddress()))) {

							Family relative = new Family(pa.getFirstName(), pa.getLastName(), pa.age());

							familyMenbers.add(relative);
						}
					}
				}
				newChild.setFamilyMenber(familyMenbers);
				childAlertList.add(newChild);
			}

		}

		return childAlertList;
	}

	@Override
	public Person updatePerson(String firstName, String lastName, String newPhone, String newZip, String newAddress,
			String newCity, String newEmail, Date newBirthDate) {
		logger.info("Entering updatePersonInfo method");
		Person personSelectedToUpdate = getPerson(firstName, lastName);
		if (!personSelectedToUpdate.equals(null)) {
			if (!newPhone.isEmpty() && !newZip.isEmpty() && !newAddress.isEmpty() && !newCity.isEmpty()
					&& !newZip.isEmpty() && !newEmail.isEmpty() && !newBirthDate.equals(null)) {
				personSelectedToUpdate.setAddress(newAddress);
				personSelectedToUpdate.setBirthDate(newBirthDate);
				personSelectedToUpdate.setCity(newCity);
				personSelectedToUpdate.setEmail(newEmail);
				personSelectedToUpdate.setPhone(newPhone);
				personSelectedToUpdate.setZip(newZip);
				return personSelectedToUpdate;
			} else if (!newZip.isEmpty() && !newAddress.isEmpty() && !newCity.isEmpty() && !newZip.isEmpty()
					&& !newEmail.isEmpty() && !newBirthDate.equals(null)) {
				personSelectedToUpdate.setAddress(newAddress);
				personSelectedToUpdate.setBirthDate(newBirthDate);
				personSelectedToUpdate.setCity(newCity);
				personSelectedToUpdate.setEmail(newEmail);
				personSelectedToUpdate.setZip(newZip);
				return personSelectedToUpdate;
			} else if (!newAddress.isEmpty() && !newCity.isEmpty() && !newZip.isEmpty() && newEmail.isEmpty()
					&& !newBirthDate.equals(null)) {
				personSelectedToUpdate.setAddress(newAddress);
				personSelectedToUpdate.setBirthDate(newBirthDate);
				personSelectedToUpdate.setCity(newCity);
				personSelectedToUpdate.setEmail(newEmail);
				personSelectedToUpdate.setZip(newZip);
				return personSelectedToUpdate;

			}

			else if (!newCity.isEmpty() && !newZip.isEmpty() && !newEmail.isEmpty() && !newBirthDate.equals(null)) {

				personSelectedToUpdate.setBirthDate(newBirthDate);
				personSelectedToUpdate.setCity(newCity);
				personSelectedToUpdate.setEmail(newEmail);
				personSelectedToUpdate.setZip(newZip);
				return personSelectedToUpdate;
			} else if (!newZip.isEmpty() && !newEmail.isEmpty() && !newBirthDate.equals(null)) {
				personSelectedToUpdate.setBirthDate(newBirthDate);
				personSelectedToUpdate.setEmail(newEmail);
				personSelectedToUpdate.setZip(newZip);
				return personSelectedToUpdate;
			} else if (!newEmail.isEmpty() && !newBirthDate.equals(null)) {
				personSelectedToUpdate.setBirthDate(newBirthDate);

				personSelectedToUpdate.setEmail(newEmail);

				return personSelectedToUpdate;

			} else if (newBirthDate.equals(null)) {
				personSelectedToUpdate.setBirthDate(newBirthDate);

				personSelectedToUpdate.setEmail(newEmail);

				return personSelectedToUpdate;

			}

			return personSelectedToUpdate;

		} else
			return null;
	}

// http://localhost:8080/firestation?stationNumber=%3Cstation_number OK 14 10
	@Override
	public ListPerson listPersonsLinkToStationSelected(String fireStationNumber) { // TODO Auto-generated method
		logger.info("Entering listPersonsLinkToStationSelected method");
		ListPerson list = new ListPerson(); // stub
		List<PersonPrint> listPersonsLinkTest = new ArrayList<PersonPrint>();
		try {

			List<Person> personDatSource = jSonFile.loadPersons();
			int qtMinors = 0;
			for (Person personStation : personDatSource) {

				if (personStation.getFireStation().getStationNumber().equals(fireStationNumber)) {
					// prénom, nom, adresse, numéro de téléphone
					if (personStation.isMinor()) {

						qtMinors++;
					}
					PersonPrint infoPerso = new PersonPrint(personStation.getFirstName(), personStation.getLastName(),
							personStation.getAddress(), personStation.getCity(), personStation.getZip(),
							personStation.getPhone());
					listPersonsLinkTest.add(infoPerso);
				}

			}
			list.setContactsList(listPersonsLinkTest);
			list.setNbMinors(qtMinors);
			list.setNbAdults(listPersonsLinkTest.size() - qtMinors);
			logger.info(" Creation de la list firestation?stationNumber ");
			return list;
		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Person> getListPersons(String firstName, String lastName) {
		logger.info("Entering getListPersons() method");
		List<Person> getPersons = new ArrayList<Person>();
		List<Person> personList;
		if (!firstName.isEmpty() && !lastName.isEmpty()) {
			try {
				personList = jSonFile.loadPersons();

				getPersons.add(getPerson(firstName, lastName));
				logger.info("Success add person with  firstName, lastName ");
				for (Person p : personList) {
					if (p.getLastName().equals(lastName))

						getPersons.add(p);
					logger.info(
							"Success add  family menber with   lastName " + p.getLastName() + " " + p.getFirstName());
				}
				logger.info("Success add list person with   lastName ");

				return getPersons;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("Failed add person with   lastName ");

				e.printStackTrace();
			}

		}

		return getPersons;
	}

	@Override
	public List<PersonEmail> listEmail(String city) {
		List<PersonEmail> listEmail = new ArrayList<PersonEmail>();
		try {
			List<Person> persons = jSonFile.loadPersons();

			for (Person p : persons) {
				if (p.getCity().equals(city)) {
					PersonEmail newEmail = new PersonEmail(p.getEmail());
					listEmail.add(newEmail);
				}
			}
			return listEmail;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listEmail;

	}
//http://localhost:8080/fire?address=1509%20Culver%20St
	@Override
	public List<PersonFire> listResidentsByAddress(String address) {
		logger.info("Entering getResidentsByAddress(String address) method");
		List<PersonFire> residents = new ArrayList<>();
		try {
			List<Person> persons = jSonFile.loadPersons();
			for (Person p : persons) {
				if ((p.getAddress().contains(address))) {

				}
			}
			
			return residents;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Pbl import JsonFile!!");
			e.printStackTrace();
		}

		return residents;
	}

}