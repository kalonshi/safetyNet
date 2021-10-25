package com.spl.safetyNet.Views;


import java.util.List;



public class InfoPerson {
	private String firstName;
	private String lastName;
	private String address;
	private String zip;
	private String city;
	private int age;
	private String email;
	private List<String> medications;
	private List<String> allergies;
	
	public InfoPerson() {
	}

	public InfoPerson(String firstName, String lastName, String address, String zip, String city, int age, String email,
			List<String> medications, List<String> allergies) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.zip = zip;
		this.city = city;
		this.age = age;
		this.email = email;
		this.medications = medications;
		this.allergies = allergies;
	}

	public String getFirstName() {
		return firstName;
	}

	/*
	 * public void setFirstName(String firstName) { this.firstName = firstName; }
	 */

	public String getLastName() {
		return lastName;
	}

	/*
	 * public void setLastName(String lastName) { this.lastName = lastName; }
	 */

	public String getAddress() {
		return address;
	}

	/*
	 * public void setAddress(String address) { this.address = address; }
	 */

	public String getZip() {
		return zip;
	}

	/*
	 * public void setZip(String zip) { this.zip = zip; }
	 */

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getAge() {
		return age;
	}
	/*
	 * public void setAge(int age) { this.age = age; }
	 */

	public String getEmail() {
		return email;
	}
	/*
	 * public void setEmail(String email) { this.email = email; }
	 */

	public List<String> getMedications() {
		return medications;
	}

	/*
	 * public void setMedications(List<String> medications) { this.medications =
	 * medications; }
	 */

	public List<String> getAllergies() {
		return allergies;
	}

	/*
	 * public void setAllergies(List<String> allergies) { this.allergies =
	 * allergies; }
	 */
	
	
}
