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

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.spl.safetyNet.models.FireStation;
import com.spl.safetyNet.models.MedicalRecord;
import com.spl.safetyNet.models.Person;

public class IJsonReaderImpl implements IJsonReader {

	@Override
	public List<FireStation> loadStationsWithOutListPerson() {
		// TODO Auto-generated method stub
		String filePath = "src/main/resources/data.json";
		byte[] bytesFile;
		Map<String, FireStation> fireStationMap = new HashMap<>();
		try {
			bytesFile = Files.readAllBytes(new File(filePath).toPath());

			JsonIterator iter = JsonIterator.parse(bytesFile);
			Any any = iter.readAny();
			Any fireStationAny = any.get("firestations");

			fireStationAny.forEach(anyStation -> {
				fireStationMap.compute(anyStation.get("station").toString(),
						(k, v) -> v == null
								? new FireStation(anyStation.get("station").toString())
										.addAddress(anyStation.get("address").toString())
								: v.addAddress(anyStation.get("address").toString()));
			});

			return fireStationMap.values().stream().collect(Collectors.toList());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fireStationMap.values().stream().collect(Collectors.toList());

	}

	@Override
	public List<Person> loadJsonPersons() {
		String filePath = "src/main/resources/data.json";
		byte[] bytesFile;
		List<Person> persons = new ArrayList<>();
		try {
			bytesFile = Files.readAllBytes(new File(filePath).toPath());

			JsonIterator iter = JsonIterator.parse(bytesFile);
			Any any = iter.readAny();
			Any personAny = any.get("persons");
			Any medicalAny = any.get("medicalrecords");

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
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return persons;
	}

	@Override
	public List<MedicalRecord> loadJsonMedicalRecords() {
		// TODO Auto-generated method stub
		String filePath = "src/main/resources/data.json";
		byte[] bytesFile;
		List<MedicalRecord> JsonMedicalRecords = new ArrayList<>();
		try {
			bytesFile = Files.readAllBytes(new File(filePath).toPath());
			JsonIterator iter = JsonIterator.parse(bytesFile);
			Any any = iter.readAny();
			Any medicalAny = any.get("medicalrecords");

			medicalAny.forEach(medicalRecord -> {
				List<String> allergies = new ArrayList<>();
				List<String> medications = new ArrayList<>();
				for (int i = 0; i < medicalRecord.get("medications").size(); i++) {
					medications.add(medicalRecord.get("medications").get(i).toString());
				}
				for (int i = 0; i < medicalRecord.get("allergies").size(); i++) {
					allergies.add(medicalRecord.get("allergies").get(i).toString());
				}
				JsonMedicalRecords.add(new MedicalRecord(medications, allergies));

			});

			return JsonMedicalRecords;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JsonMedicalRecords;
	}

	@Override
	public List<Person> loadPersons() {
		// TODO Auto-generated method stub

		// Creation du dossier medical par personne
		List<Person> personsList = loadJsonPersons();
		List<MedicalRecord> medicalRecordList = loadJsonMedicalRecords();
		for (int i = 0; i < personsList.size(); i++) {
			personsList.get(i).setMedicalRecord(medicalRecordList.get(i));
		}

		personsList.forEach(p -> {

			// Ajout de la liste des Stations à une personne

			List<FireStation> stations;
			stations = loadStationsWithOutListPerson();
			Set<FireStation> FireStationByPersonAdresse = new HashSet<>();
			for (FireStation fireStation : stations) {

				if (fireStation.getAddresses().equals(p.getAddress())) {
					p.setFireStation(fireStation);
				}
			}

			System.out.println("Prenom: " + p.getFirstName() + "   " + " Nom : " + p.getLastName()
					+ " Date de naissance : " + p.getBirthDate() + " Dossier Medicale : Medications :"
					+ p.getMedicalRecord().getMedications() + "Allergies  :" + p.getMedicalRecord().getAllergies()
					+ " Depend de la caserne :  " + p.getFireStation());
		});

		return personsList;

	}

	@Override
	public List<FireStation> loadStations() {
		// TODO Auto-generated method stub
		// Ajout de la Liste des personnes dépend d'une station
		List<Person> persons = loadPersons();
		List<FireStation> fireStations = loadStationsWithOutListPerson();
		for (FireStation fireStation : fireStations) {
			List<Person> FireStationPersonAdresse = new ArrayList<>();
			for (Person p : persons) {

				if (fireStation.getAddresses().contains(p.getAddress())) {
					FireStationPersonAdresse.add(p);

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

	@Override
	public List<MedicalRecord> loadMedicalRecords() {
		// TODO Auto-generated method stub
		List<MedicalRecord> medicalRecordsParse = new ArrayList<>();
		loadJsonPersons().forEach(p -> {

			loadJsonMedicalRecords().forEach(medicalRecord -> {
				medicalRecord.setPerson(p);
				medicalRecordsParse.add(medicalRecord);

			});
		});
		return medicalRecordsParse;
	}

	public Any getAnyfunction() {
		String filePath = "src/main/resources/data.json";
		byte[] bytesFile;
		try {
			bytesFile = Files.readAllBytes(new File(filePath).toPath());
			JsonIterator iter = JsonIterator.parse(bytesFile);
			Any any = iter.readAny();
			return any;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
