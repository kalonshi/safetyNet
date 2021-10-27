package com.spl.safetyNet.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import com.spl.safetyNet.Views.InfoPerson;
import com.spl.safetyNet.Views.ListPerson;
import com.spl.safetyNet.Views.PersonEmail;
import com.spl.safetyNet.Views.PersonFire;
import com.spl.safetyNet.Views.PersonPrint;
import com.spl.safetyNet.Views.Personchild;
import com.spl.safetyNet.models.Person;

public interface IPerson {
	public Person addPerson(String firstName, String lastName, String phone, String zip, String address, String city,
			String email, Date birthDate);

	public Person updatePerson(String firstName, String lastName, String phone, String zip, String address, String city,
			String email);

	public InfoPerson getInfoPerson(String firstName, String lastName);

	public Person getPerson(String firstName, String lastName);

	public List<InfoPerson> getListInfoPerson(String firstName, String lastName);

	public boolean delete(String firstName, String lastName);

	public List<Person> getResidentsByAddress(String address);

	/* public List<PersonFire> listResidentsByAddress(String address); */

	public List<PersonEmail> listEmail(String city);

	public List<Person> getfamilyMenbers(String lastName);

	/* public List<Person> getMinorsByAddress(String address); */

	/* public List<Person> getAdultsByAddress(String address); */

	/* public List<Person> printlistPersonByadress(String adress); */

	public List<Personchild> printlistMinorsByAddress(String adress);

	public ListPerson listPersonsLinkToStationSelected(String fireStationNumber);
}
