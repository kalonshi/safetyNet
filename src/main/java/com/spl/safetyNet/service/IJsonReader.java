package com.spl.safetyNet.service;

import java.io.IOException;
import java.util.List;

import com.spl.safetyNet.models.FireStation;
import com.spl.safetyNet.models.MedicalRecord;
import com.spl.safetyNet.models.Person;

public interface IJsonReader {
	public List<FireStation> loadStationsWithOutListPerson();

	public List<Person> loadJsonPersons();

	public List<MedicalRecord> loadJsonMedicalRecords();

	public List<Person> loadPersons();

	public List<FireStation> loadStations();

	public List<MedicalRecord> loadMedicalRecords();

}
