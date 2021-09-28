package com.spl.safetyNet.models;



import java.util.List;



public class MedicalRecord {
	private Person2 person;
	private List<String>medications ;
	private List<String> allergies ;
	public MedicalRecord() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MedicalRecord(Person2 person, List<String> medications, List<String> allergies) {
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
	public Person2 getPerson() {
		return person;
	}
	public void setPerson(Person2 person) {
		this.person = person;
	}
	public List<String> getMedications() {
		return medications;
	}
	public void setMedications(List<String> medications) {
		this.medications = medications;
	}
	public List<String> getAllergies() {
		return allergies;
	}
	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
	}
	
	
		
	
}
