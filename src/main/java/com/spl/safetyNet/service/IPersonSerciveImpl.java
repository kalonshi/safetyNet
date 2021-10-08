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
public class IPersonSerciveImpl implements IPerson {
	@Autowired
	private JsonFileData4 jSonFile;
	private String stationNum;

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
						newPerson.setFireStation(f);
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
			FireStation FireStationsToDelete = person.getFireStation();

			FireStationsToDelete.getListOfPersons().remove(person);

			
			List<MedicalRecord> listMedicalRecords = jSonFile.loadMedicalRecords();
			MedicalRecord medicalRecordToDelete = person.getMedicalRecord();
			jSonFile.loadMedicalRecords().remove(medicalRecordToDelete);
			jSonFile.loadJsonPersons().remove(person);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		person = null;
	}

	// http://localhost:8080/communityEmail?city=<city>
	@Override
	public Set<String> getListEmail(String city) {
		// TODO Auto-generated method stub
		Set<String> listEmail = new HashSet<>();
		try {
			List<Person> persons = jSonFile.loadPersons();
			listEmail.add("List of emails to contact : " );
			for (Person p : persons) {
				if (p.getCity().equals(city)) {
					listEmail.add("Email :  "+p.getEmail());
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
	public Set<Person> getMinorsByAddress(String address) {
		// TODO Auto-generated method stub

		Set<Person> minors = new HashSet<>();
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
	public Set<Person> getResidentsByAddress(String address) {
		// TODO Auto-generated method stub

		
		Set<Person> residents = new HashSet<>();
		try {
			List<Person> persons = jSonFile.loadPersons();
			for (Person p : persons) {
				if ((p.getAddress().contains(address))) {
					residents.add(p);
				}
			}
			System.out.println("residents");
			System.out.println(residents.size());
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
				personList = jSonFile.loadPersons();

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
	public List<String[]> printlistPersons(String firstName, String lastName) {
		// TODO Auto-generated method stub
		Set<Person> getInfoPersons = new HashSet<Person>();
		Set<String> listInfoPersons = new HashSet<String>();
		getInfoPersons = getListPersons(firstName, lastName);
		for (Person pf : getInfoPersons) {
			List<String> infos = new ArrayList<String>();
			infos.add("FirstName :" +pf.getFirstName());
			infos.add("LastName :" +pf.getLastName());
			infos.add("Address  :" +pf.getAddress());
			infos.add("Age : "+String.valueOf(pf.age()));
			infos.add("Email : "+pf.getEmail());
			infos.add("Medications :" +pf.getMedicalRecord().getMedications().toString());
			infos.add("Allergies :"+pf.getMedicalRecord().getAllergies().toString());

			listInfoPersons.addAll(infos);
		}

		return null;
	}

//    http://localhost:8080/fire?address=<address> Test OK
	@Override
	public List<String[]> printlistPersonByadress(String adress) {
		Set<Person> getInfoPersons = new HashSet<Person>();
		List<String[]> listInfoPersonsByAdress = new ArrayList<String[]>();
		getInfoPersons = getResidentsByAddress(adress);
		
		for (Person pf : getInfoPersons) {
			String[] infos = new String[8];
			 
			infos[0]=("Adress: "+pf.getAddress() +" "+pf.getCity()+ "  "+pf.getZip());
			
			 infos[1]=("FireStation Number :" +pf.getFireStation().getStationNumber().toString()); 
			infos[2]=("FirstName :" +pf.getFirstName());
			infos[3]=("LastName :" +pf.getLastName());
			infos[4]=("Phone Number :"+pf.getPhone());
			infos[5]=("Age : "+String.valueOf(pf.age()));
			 infos[6]=("Medications :"+pf.getMedicalRecord().getMedications().toString());
			  infos[7]=("Allergies :"+pf.getMedicalRecord().getAllergies().toString());
			 

			listInfoPersonsByAdress.add(infos);
		}
	
		return listInfoPersonsByAdress;
	}
	/*
	 * ChildAlert: Liste des personnes <18 avec liste des parents associés 
	 * Cette url doit retourner une liste d'enfants (tout individu âgé de 18 ans ou moins) habitant à cette adresse.
La liste doit comprendre le prénom et le nom de famille de chaque enfant, son âge et une liste des autres
membres du foyer. S'il n'y a pas d'enfant, cette url peut renvoyer une chaîne vide.
OK , Probleme de Mise en page !!!
	 */
	// http://localhost:8080/childAlert?address=<address>
	@Override
	public List<String[]>printlistMinorsByAddress(String adress) {
		Set<Person> getInfoPersonsMinor = new HashSet<Person>();
		Set<Person> getInfoPersonsAdult = new HashSet<Person>();
		 List<String[]> listInfoPersonsByAdress = new ArrayList<String[]>(); 
		
		getInfoPersonsMinor = getResidentsByAddress(adress);
System.out.println("Test mineur");
		for (Person pf : getInfoPersonsMinor) {
			
			String[] infos = new String[4];
			
			List<String[]> familyMenbers = new ArrayList<String[]>();
			if (pf.isMinor()) {
				infos[0]=("Minor:");
				infos[1]=("FirstName :"+pf.getFirstName());
				infos[2]=("LastName :"+pf.getLastName());
				infos[3]=("Age :" +String.valueOf(pf.age()));
				 listInfoPersonsByAdress.add(infos); 
				
				getInfoPersonsAdult = getfamilyMenbers(pf.getLastName());
				if (!getInfoPersonsAdult.isEmpty()) {
					for (Person pa : getInfoPersonsAdult) {
						if (!(pa.getFirstName().equals(pf.getFirstName()))
								&& (pa.getAddress().equals(pf.getAddress()))) {
							String[] familyMenber = new String[4];
							familyMenber[0]=("familyMenber :");
							familyMenber[1]=(" FirstName :"+pa.getFirstName());
							familyMenber[2]=(" LastName :"+pa.getLastName());
							familyMenber[3]=("Age :" +String.valueOf(pa.age()));
							listInfoPersonsByAdress.add(familyMenber);	
						
						}
					}
					 }

			}
			
		}

		 return listInfoPersonsByAdress; 
	}

//http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
	@Override
	public Set<String> getPersonInfo(String firstName, String lastName) {
		Set<Person> personsInfo = getListPersons(firstName, lastName);
		Set<String> personInfoForPrinting = new HashSet<String>();
		for (Person p : personsInfo) {
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

	@Override
	public Person updatePerson(String firstName, String lastName, String newPhone, String newZip, String newAddress,
			String newCity, String newEmail, Date newBirthDate) {
		Person personSelectedToUpdate = getPerson(firstName, lastName);
		if(!personSelectedToUpdate.equals(null)) {
		if (!newPhone.isEmpty() && !newZip.isEmpty() && !newAddress.isEmpty() && !newCity.isEmpty() && !newZip.isEmpty()
				&& !newEmail.isEmpty() && !newBirthDate.equals(null)) {
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
			


	}
		else return null;
}

	@Override
	public List<String[]> listPersonsLinkToStationSelected(String fireStationNumber) {
		// TODO Auto-generated method stub
		
		try {
			
			List<Person >personDatSource = jSonFile.loadPersons();
		
		
			
		
		
		List<String[]> listPersonsLink= new ArrayList<String[]>();
		int qtMinors =0;
		int qtAdults=0;	
		String[] totaux= new String[2];
		for(Person personStation:personDatSource) {
			
				System.out.println("test");	
			if(personStation.getFireStation().getStationNumber().equals(fireStationNumber)) {
				//prénom, nom, adresse, numéro de téléphone
				if(personStation.isMinor()) {
					qtMinors++;
				}
				String[] infosPerso = new String[4];
				infosPerso[0]=("FirstName :"+personStation.getFirstName());
				infosPerso[1]=("LastName :" +personStation.getFirstName());
				infosPerso[2]=("Adress :"+personStation.getAddress()+"  "+personStation.getCity()+"  "+personStation.getZip());
				infosPerso[3]=("Phone Number:"+personStation.getPhone());
				listPersonsLink.add(infosPerso);	
			}
			
			
		}
		totaux[0]="number Of Minors: "+String.valueOf(qtMinors);
				totaux[1]="number Of Adults: "+String.valueOf(listPersonsLink.size()-qtMinors);
		listPersonsLink.add(totaux);
		return listPersonsLink;
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}