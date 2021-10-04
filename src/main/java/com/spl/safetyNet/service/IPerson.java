package com.spl.safetyNet.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.spl.safetyNet.models.Person;

public interface IPerson {
	public Person addPerson(String firstName, String lastName, String phone, String zip, String address, String city,
			String email, Date birthDate);

	public Person getPerson(String firstName, String lastName);

	public Set<Person> getListPersons(String firstName, String lastName);

	public void delete(String firstName, String lastName);

	public Set<Person> getResidentsByAddress(String address);

	public Set<String> getListEmail(String city);

	public Set<Person> getfamilyMenbers(String lastName);

	public Set<Person> getMinorsByAddress(String address);

	public Set<Person> getAdultsByAddress(String address);

	public int getNumberAdultsByAddress(String address);

	public int getNumberMinorsByAddress(String address);

	public Set<String> printlistPersons(String firstName, String lastName);

	public Set<String> printlistPersonByadress(String adress);

	public Set<String> printlistMinorsByAddress(String adress);
	
	public Set<String> getPersonInfo(String firstName, String lastName);
}
