package com.spl.safetyNet.models;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class Person {
	private String firstName;
	private String lastName;
	private String phone;
	private String zip;
	private String address;
	private String city;
	private String email;
	private Date birthDate;
	private MedicalRecord medicalRecord;
	private Set<FireStation>fireStations;
	
	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Person(String firstName, String lastName, String phone, String zip, String address, String city,
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

	public Set<FireStation> getFireStation() {
		return fireStations;
	}

	public void setFireStation(Set<FireStation> fireStations) {
		this.fireStations = fireStations;
	}

	public Person(String firstName, String lastName, String phone, String zip, String address, String city,
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

	public void addFireStation(FireStation firestation) {
		fireStations.add(firestation);
				
	}
	
	
	public Boolean isMinor( ) {
		if (!this.birthDate.equals(null)) {
			Date inTime = new Date();
			if (((inTime.getDay()/365)-(this.birthDate.getDay()/365))<365*18){
				
				return true;
			}
		}
		return false;
	}
	public int age() {
		
		if (!this.birthDate.equals(null)) {
			Date inTime = new Date();
			int age=((inTime.getDay()/365)-(this.birthDate.getDay()/365));
		}
		
		return 0;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
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
		Person other = (Person) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}

	
	
}
