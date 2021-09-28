package com.spl.safetyNet.models;

import java.util.Date;
import java.util.List;

public class Person2 {
	private String firstName;
	private String lastName;
	private String phone;
	private String zip;
	private String address;
	private String city;
	private String email;
	private Date birthDate;
	private MedicalRecord medicalRecord;
	private List<FireStation>fireStation;
	
	public Person2() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Person2(String firstName, String lastName, String phone, String zip, String address, String city,
			String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.zip = zip;
		this.address = address;
		this.city = city;
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public MedicalRecord getMedicalRecord() {
		return medicalRecord;
	}

	public void setMedicalRecord(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
	}

	public List<FireStation> getFireStation() {
		return fireStation;
	}

	public void setFireStation(List<FireStation> fireStation) {
		this.fireStation = fireStation;
	}

	public Person2(String firstName, String lastName, String phone, String zip, String address, String city,
			String email, Date birthDate) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.zip = zip;
		this.address = address;
		this.city = city;
		this.email = email;
		this.birthDate = birthDate;
	}

	
	
}