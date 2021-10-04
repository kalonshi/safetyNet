package com.spl.safetyNet.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spl.safetyNet.models.FireStation;
import com.spl.safetyNet.models.MedicalRecord;
import com.spl.safetyNet.models.Person;

@Service
class IPersonSerciveImpl implements IPerson {
	@Autowired
	private JsonFileData3 jSonFile;

	@Override
	public Person addPerson(String firstName, String lastName, String phone, String zip, String address, String city,
			String email, Date birthDate) {
		// TODO Auto-generated method stub
		Person newPerson = new Person(firstName, lastName, phone, zip, address, city, email);

		try {
			List<FireStation> stations = jSonFile.loadStations();
			/* stations.contains(address); */
			for (FireStation f : stations) {
				f.getAddresses().forEach(ad -> {
					if (newPerson.getAddress().contains(ad)) {
						newPerson.addFireStation(f);
					}
				});
			}

			jSonFile.loadJsonPersons().add(newPerson);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newPerson;
	}

	@Override
	public Person getPerson(String firstName, String lastName) {
		// TODO Auto-generated method stub
		Set<Person> getPersons = new HashSet<Person>();
		List<Person> personList;
		try {
			personList = jSonFile.loadJsonPersons();
			if (!firstName.isEmpty() && !lastName.isEmpty())
				for (Person p : personList) {
					if ((p.getFirstName().equals(firstName)) && (p.getLastName().equals(lastName)))
						getPersons.add(p);
				}
			if (firstName.isEmpty())
				for (Person p : personList) {
					if (p.getLastName().equals(lastName))
						getPersons.add(p);
				}
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void delete(String firstName, String lastName) {
		// TODO Auto-generated method stub
		Person person = getPerson(firstName, lastName);

		try {
			List<FireStation> listFireStations = jSonFile.loadStations();
			Set<FireStation> listFireStationsToDelete= person.getFireStation();
			
			for (FireStation fs : listFireStationsToDelete) {
				fs.getListOfPersons().remove(person);

			}
List<MedicalRecord> listMedicalRecords=jSonFile.loadMedicalRecords();
	MedicalRecord medicalRecordToDelete=person.getMedicalRecord();	
	jSonFile.loadMedicalRecords().remove(medicalRecordToDelete);
jSonFile.loadJsonPersons().remove(person);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		person = null;
	}

	//http://localhost:8080/communityEmail?city=<city>
	@Override
	public Set<String> getListEmail(String city) {
		// TODO Auto-generated method stub
		Set<String> listEmail = new HashSet<>();
		try {
			List<Person> persons = jSonFile.loadJsonPersons();
			for (Person p : persons) {
				if (p.getCity().equals(city)) {
					listEmail.add(p.getEmail());
				}
			}
			return listEmail;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listEmail;
	}

	@Override
	public Set<Person> getfamilyMenbers(String lastName) {
		// TODO Auto-generated method stub
		Set<Person> familyMenbers = new HashSet<>();
		try {
			List<Person> persons = jSonFile.loadJsonPersons();
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
	public Set<Person> getMinorsByAddress(String address) {
		// TODO Auto-generated method stub

		Set<Person> minors = new HashSet<>();
		try {
			List<Person> persons = jSonFile.loadJsonPersons();
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
	public int getNumberMinorsByAddress(String address) {
		// TODO Auto-generated method stub

		int NumberMinorsByAddress = getMinorsByAddress(address).size();
		return NumberMinorsByAddress;
	}

	@Override
	public int getNumberAdultsByAddress(String address) {
		// TODO Auto-generated method stub

		int NumberAdultsByAddress = 0;
		if (!address.isEmpty()) {
			NumberAdultsByAddress = getAdultsByAddress(address).size();
			return NumberAdultsByAddress;
		}
		return NumberAdultsByAddress;
	}

	@Override
	public Set<Person> getAdultsByAddress(String address) {
		// TODO Auto-generated method stub
		Set<Person> adults = new HashSet<>();
		try {
			List<Person> persons = jSonFile.loadJsonPersons();
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
	public Set<Person> getResidentsByAddress(String address) {
		// TODO Auto-generated method stub

		Set<String> listInfoPersonByAdress = new HashSet<String>();
		Set<Person> residents = new HashSet<>();
		try {
			List<Person> persons = jSonFile.loadJsonPersons();
			for (Person p : persons) {
				if ((p.getAddress().contains(address))) {
					residents.add(p);
				}
			}
			return residents;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return residents;
	}

	@Override
	public Set<Person> getListPersons(String firstName, String lastName) {
		// TODO Auto-generated method stub
		Set<Person> getPersons = new HashSet<Person>();
		List<Person> personList;
		if (!firstName.isEmpty() && !lastName.isEmpty()) {
			try {
				personList = jSonFile.loadJsonPersons();

				for (Person p : personList) {
					if ((p.getFirstName().equals(firstName)) && (p.getLastName().equals(lastName)))
						getPersons.add(p);
				}
				if (!lastName.isEmpty())
					for (Person p : personList) {
						if (p.getLastName().equals(lastName))
							getPersons.add(p);
					}
				return getPersons;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return getPersons;
	}

	@Override
	public Set<String> printlistPersons(String firstName, String lastName) {
		// TODO Auto-generated method stub
		Set<Person> getInfoPersons = new HashSet<Person>();
		Set<String> listInfoPersons = new HashSet<String>();
		getInfoPersons = getListPersons(firstName, lastName);
		for (Person pf : getInfoPersons) {
			List<String> infos = new ArrayList<String>();
			infos.add(pf.getFirstName());
			infos.add(pf.getLastName());
			infos.add(pf.getAddress());
			infos.add(String.valueOf(pf.age()));
			infos.add(pf.getEmail());
			infos.add(pf.getMedicalRecord().getMedications().toString());
			infos.add(pf.getMedicalRecord().getAllergies().toString());

			listInfoPersons.addAll(infos);
		}

		return listInfoPersons;
	}
//    http://localhost:8080/fire?address=<address>
	@Override
	public Set<String> printlistPersonByadress(String adress) {
		Set<Person> getInfoPersons = new HashSet<Person>();
		Set<String> listInfoPersonsByAdress = new HashSet<String>();
		getInfoPersons = getResidentsByAddress(adress);
		for (Person pf : getInfoPersons) {
			List<String> infos = new ArrayList<String>();
			infos.add(pf.getAddress());
			infos.add(pf.getFireStation().toString());
			infos.add(pf.getFirstName());
			infos.add(pf.getLastName());
			infos.add(pf.getPhone());
			infos.add(String.valueOf(pf.age()));

			infos.add(pf.getMedicalRecord().getMedications().toString());
			infos.add(pf.getMedicalRecord().getAllergies().toString());

			listInfoPersonsByAdress.addAll(infos);
		}

		return listInfoPersonsByAdress;
	}

	//http://localhost:8080/childAlert?address=<address>
	@Override
	public Set<String> printlistMinorsByAddress(String adress) {
		Set<Person> getInfoPersonsMinor = new HashSet<Person>();
		Set<Person> getInfoPersonsAdult = new HashSet<Person>();
		Set<String> listInfoPersonsByAdress = new HashSet<String>();
		getInfoPersonsMinor = getResidentsByAddress(adress);

		for (Person pf : getInfoPersonsMinor) {
			List<String> infos = new ArrayList<String>();
			List<String> familyMenbers = new ArrayList<String>();
			if(pf.isMinor()) {
			infos.add(pf.getFirstName());
			infos.add(pf.getLastName());
			infos.add(String.valueOf(pf.age()));
			getInfoPersonsAdult = getfamilyMenbers(pf.getLastName());
			if(!getInfoPersonsMinor.isEmpty()) {
			for (Person pa : getInfoPersonsAdult) {
				if (!(pa.getFirstName().equals(pf.getFirstName())) && (pa.getAddress().equals(pf.getAddress()))) {
					familyMenbers.add(pa.getFirstName());
					familyMenbers.add(pa.getLastName());
					familyMenbers.add(String.valueOf(pa.age()));
					infos.add(familyMenbers.toString());
				}
			}
			}
			
			}
			listInfoPersonsByAdress.addAll(infos);
		}

		return listInfoPersonsByAdress;
	}
//http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
	@Override
	public Set<String> getPersonInfo(String firstName, String lastName) {
		Set<Person> personsInfo =getListPersons(firstName, lastName);
		Set<String> personInfoForPrinting=new HashSet<String>();
		for (Person p:personsInfo){
			List<String> personInfos = new ArrayList<String>();
			personInfos.add(p.getLastName());
			personInfos.add(p.getAddress());
			personInfos.add(String.valueOf(p.age()));
			personInfos.add(p.getEmail());
			personInfos.add(p.getMedicalRecord().getMedications().toString());
			personInfos.add(p.getMedicalRecord().getAllergies().toString());
			personInfoForPrinting.addAll(personInfos);
		}
		
		return personInfoForPrinting;
	}
}