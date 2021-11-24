package com.spl.safetyNet.models;

import java.util.List;

public class MedicalRecord {
	private Person person;
	private List<String> medications;
	private List<String> allergies;

	public MedicalRecord(Person person, List<String> medications, List<String> allergies) {
		super();
		this.person = person;
		this.medications = medications;
		this.allergies = allergies;
	}

	public MedicalRecord() {
		super();

	}

	public MedicalRecord(List<String> medications, List<String> allergies) {
		super();
		this.medications = medications;
		this.allergies = allergies;
	}

	public MedicalRecord(Person person) {
		super();
		this.person = person;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public List<String> getMedications() {
		return medications;
	}

	public void setMedications(List<String> medications) {
		this.medications = medications;
	}

	public MedicalRecord addMedication(String medication) {
		medications.add(medication);

		return this;
	}

	public List<String> getAllergies() {
		return allergies;
	}

	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
	}

	public MedicalRecord addAllergie(String allergy) {
		allergies.add(allergy);

		return this;
	}

	@Override
	public String toString() {
		return "MedicalRecord [medications=" + medications + ", allergies=" + allergies + "]";
	}

}
