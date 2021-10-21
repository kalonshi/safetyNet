package com.spl.safetyNet.models;

import java.util.List;

public class MedicalRecord {
	private Person person;
	private List<String> medications;
	private List<String> allergies;

	public MedicalRecord() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MedicalRecord(Person person, List<String> medications, List<String> allergies) {
		super();
		this.person = person;
		this.medications = medications;
		this.allergies = allergies;
	}

	public MedicalRecord(List<String> medications, List<String> allergies) {
		super();
		this.medications = medications;
		this.allergies = allergies;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allergies == null) ? 0 : allergies.hashCode());
		result = prime * result + ((medications == null) ? 0 : medications.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MedicalRecord other = (MedicalRecord) obj;
		if (allergies == null) {
			if (other.allergies != null)
				return false;
		} else if (!allergies.equals(other.allergies))
			return false;
		if (medications == null) {
			if (other.medications != null)
				return false;
		} else if (!medications.equals(other.medications))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MedicalRecord [medications=" + medications + ", allergies=" + allergies + "]";
	}

}
