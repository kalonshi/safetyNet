package com.spl.safetyNet.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;

import com.spl.safetyNet.models.FireStation;
import com.spl.safetyNet.models.MedicalRecord;
import com.spl.safetyNet.models.Person;

@Service
public class JsonFileData {
	private static final Logger logger = LogManager.getLogger(JsonFileData.class);
	
	public JsonFileData() {
		super();
		// TODO Auto-generated constructor stub
	}
	/* READ AND PARSE JSON FILE IN OBJECT */

	// Creation de Station sans personnes liée
	public List<FireStation> loadStationsWithOutListPerson() throws IOException {
		logger.info("Entering the loadStationsWithOutListPerson() method");
		/*
		 * String filePath = "src/main/resources/data.json"; byte[] bytesFile =
		 * Files.readAllBytes(new File(filePath).toPath()); JsonIterator iter =
		 * JsonIterator.parse(bytesFile);
		 */
		
		Any fireStationAny = readFileJson("src/main/resources/data.json").get("firestations");
		Map<String, FireStation> fireStationMap = new HashMap<>();
		fireStationAny.forEach(anyStation -> {
			fireStationMap.compute(anyStation.get("station").toString(),
					(k, v) -> v == null
							? new FireStation(anyStation.get("station").toString())
									.addAddress(anyStation.get("address").toString())
							: v.addAddress(anyStation.get("address").toString()));
		});

		return fireStationMap.values().stream().collect(Collectors.toList());

	}

	// Creation convertion Json to Person sans Medical Record et Station et date
	public List<Person> loadJsonPersons() throws IOException {
		logger.info("Entering the loadJsonPersons() method");
		/*
		 * String filePath = "src/main/resources/data.json"; byte[] bytesFile =
		 * Files.readAllBytes(new File(filePath).toPath()); JsonIterator iter =
		 * JsonIterator.parse(bytesFile); Any any = iter.readAny();
		 */ 
		Any personAny = readFileJson("src/main/resources/data.json").get("persons");
		Any medicalAny = readFileJson("src/main/resources/data.json").get("medicalrecords");
		List<Person> persons = new ArrayList<>();
		personAny.forEach(a -> {
			medicalAny.forEach(medicalRecord -> {
				if ((medicalRecord.get("firstName").toString().equals(a.get("firstName").toString())
						&& (medicalRecord.get("lastName").toString().equals(a.get("lastName").toString())))) {
					SimpleDateFormat formatSortie = new SimpleDateFormat("dd/MM/yyyy");
					Date date = null;
					try {
						date = formatSortie.parse(medicalRecord.get("birthdate").toString());
						persons.add(new Person(a.get("firstName").toString(), a.get("lastName").toString(),
								a.get("phone").toString(), a.get("zip").toString(), a.get("address").toString(),
								a.get("city").toString(), a.get("email").toString(), date));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		});

		return persons;
	}

	// Creation du dossier medical sans lien avec classe personne
	public List<MedicalRecord> loadJsonMedicalRecords() throws IOException {
		logger.info("Entering the loadJsonMedicalRecords() method");
		/*
		 * String filePath = "src/main/resources/data.json"; byte[] bytesFile =
		 * Files.readAllBytes(new File(filePath).toPath()); JsonIterator iter =
		 * JsonIterator.parse(bytesFile); Any any = iter.readAny();
		 */
		Any medicalAny = readFileJson("src/main/resources/data.json").get("medicalrecords");
		List<MedicalRecord> JsonMedicalRecords = new ArrayList<>();
		medicalAny.forEach(medicalRecord -> {
			List<String> allergies = new ArrayList<>();
			List<String> medications = new ArrayList<>();
			for (int i = 0; i < medicalRecord.get("medications").size(); i++) {
				medications.add(medicalRecord.get("medications").get(i).toString());
				logger.info("Success add medication in medicalRecord");
			}
			for (int i = 0; i < medicalRecord.get("allergies").size(); i++) {
				allergies.add(medicalRecord.get("allergies").get(i).toString());
				logger.info("Success add allergy in medicalRecord");
			}
			JsonMedicalRecords.add(new MedicalRecord(medications, allergies));
			logger.info("Success add new medicalRecord");
		});

		return JsonMedicalRecords;

	}

	/* CREATE LIST OF OBJECT FROM JSON FILE */

	// Methode pour créer une liste de MedicalRecord avec dépendances***********
	public List<MedicalRecord> loadMedicalRecords() throws IOException {
		logger.info("Entering the loadMedicalRecords() method");
		List<MedicalRecord> medicalRecordsParse = new ArrayList<>();
		List<Person> listContactsMedicalRecord = loadJsonPersons();
		List<MedicalRecord> listMedicalRecordWithNocontact = loadJsonMedicalRecords();
		for (int i = 0; i < listContactsMedicalRecord.size(); i++) {
			listMedicalRecordWithNocontact.get(i).setPerson(listContactsMedicalRecord.get(i));
		}
		logger.info("listMedicalRecordWithNocontact size=" + listMedicalRecordWithNocontact.size());
		logger.info(
				"listMedicalRecordWithNocontact exemple record=" + listMedicalRecordWithNocontact.get(0).toString());
		return listMedicalRecordWithNocontact;

	}

//Creation Liste de Person complete avec  dépendances

	public List<Person> loadPersons() throws IOException {
		logger.info("Entering the loadPersons() method");
// Creation du dossier medical par personne
		List<Person> personsList = loadJsonPersons();
		List<MedicalRecord> medicalRecordList = loadMedicalRecords();
		for (int i = 0; i < personsList.size(); i++) {
			personsList.get(i).setMedicalRecord(medicalRecordList.get(i));
		}

		personsList.forEach(p -> {

			// Ajout de la liste des Stations à une personne

			List<FireStation> stations;
			try {
				stations = loadStationsWithOutListPerson();
				logger.info("Success load firestations with list<Person> empty");

				for (FireStation fireStation : stations) {

					if (fireStation.getAddresses().contains(p.getAddress())) {
						p.setFireStation(fireStation);
						logger.info("Success add firestation to person " + p.getFireStation());
					}
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.info("Failed to  add firestation to person");

				e.printStackTrace();
			}
			System.out.println("Prenom: " + p.getFirstName() + "   " + " Nom : " + p.getLastName()
					+ " Date de naissance : " + p.getBirthDate() + " agée de : " + p.age() + " est elle mineure? : "
					+ p.isMinor() + " Dossier Medicale : Medications :" + p.getMedicalRecord().getMedications()
					+ "Allergies  :" + p.getMedicalRecord().getAllergies() + " Depend de la caserne :  "
					+ p.getFireStation().getStationNumber());
		});

		return personsList;
	}

	// Methode pour créer une liste de stations avec dépendances***********
	public List<FireStation> loadStations() throws IOException {
		logger.info("Entering the loadStations() method");
		// Ajout de la Liste des personnes dépend d'une station
		List<Person> persons = loadPersons();

		List<FireStation> fireStations = loadStationsWithOutListPerson();
		for (FireStation fireStation : fireStations) {
			List<Person> FireStationPersonAdresse = new ArrayList<>();
			for (Person p : persons) {
				Set<String> stationaddresses = fireStation.getAddresses();
				for (String ad : stationaddresses) {
					if (p.getAddress().equals(ad)) {
						FireStationPersonAdresse.add(p);
						logger.info("Success add person to FireStation");

					}
				}
			}
			fireStation.setListOfPersons(FireStationPersonAdresse);
			fireStation.getListOfPersons().forEach(
					p -> System.out.println("Prenom: " + p.getFirstName() + "   " + " Nom : " + p.getLastName()));
			System.out.println("nb de personnes");
			System.out.println(fireStation.getListOfPersons().size());
		}

		return fireStations;
	}
public Any readFileJson(String filePath ) throws IOException {
	byte[] bytesFile = Files.readAllBytes(new File(filePath).toPath());
	JsonIterator iter = JsonIterator.parse(bytesFile);
	Any any = iter.readAny();
	
	return any;
	
}
}