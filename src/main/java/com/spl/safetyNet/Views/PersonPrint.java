package com.spl.safetyNet.Views;

import com.spl.safetyNet.models.Person;

public class PersonPrint  {
	private String firstName;
	private String lastName;
	private String zip;
	private String address;
	private String city;
	private String phone;

	public PersonPrint(String firstName, String lastName, String address, String city, String zip, String phone) {
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
		
	}

	public String getFirstName() {
		return firstName;
	}

	
	public String getLastName() {
		return lastName;
	}

	
	public String getPhone() {
		return phone;
	}

	
	public String getZip() {
		return zip;
	}

	
	public String getAddress() {
		return address;
	}

	
	public String getCity() {
		return city;
	}

	}
