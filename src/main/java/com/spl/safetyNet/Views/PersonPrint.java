package com.spl.safetyNet.Views;

import com.spl.safetyNet.models.Person;

public class PersonPrint implements Comparable{
	private String firstName;
	private String lastName;
	
	private String zip;
	private String address;
	private String city;
	private String phone;
	public PersonPrint(String firstName, String lastName,  String address, String city,String zip, String phone
			) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.zip = zip;
		this.address = address;
		this.city = city;
		
	}
	public PersonPrint() {
		super();
		// TODO Auto-generated constructor stub
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
	public String getPhone() {
		return phone;
	}

	/*
	 * public void setPhone(String phone) { this.phone = phone; }
	 */
	public String getZip() {
		return zip;
	}

	/*
	 * public void setZip(String zip) { this.zip = zip; }
	 */
	public String getAddress() {
		return address;
	}

	/*
	 * public void setAddress(String address) { this.address = address; }
	 */
	public String getCity() {
		return city;
	}

	/*
	 * public void setCity(String city) { this.city = city; }
	 */
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
	PersonPrint person= (PersonPrint) o;
	
		return getAddress().compareTo(person.getAddress());
	}

	
}
