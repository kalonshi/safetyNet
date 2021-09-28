package com.spl.safetyNet.service;

import java.util.List;

import com.spl.safetyNet.models.Person2;

public interface IPerson {
	public Person2 addPerson(String firstName, String lastName, String phone, String zip, String address, String city,
			String email);

	public Person2 getPerson(String firstName, String lastName);

	public void delete(String firstName, String lastName);
	
	public List<String> getListEmail(String city);

}
