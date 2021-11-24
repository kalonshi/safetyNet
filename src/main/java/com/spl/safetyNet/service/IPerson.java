package com.spl.safetyNet.service;

import java.util.List;

import com.spl.safetyNet.Views.InfoPerson;
import com.spl.safetyNet.Views.ListPerson;
import com.spl.safetyNet.Views.PersonEmail;
import com.spl.safetyNet.Views.Personchild;
import com.spl.safetyNet.models.Person;

public interface IPerson {
	public Person updatePersonPhone(String firstName, String lastName, String updatedPhone);

	public Person updatePersonEmail(String firstName, String lastName, String updatedEmail);

	public Person updatePersonAdresse(String firstName, String lastName, String updatedAddress);

	public InfoPerson getInfoPerson(String firstName, String lastName);

	public Person getPerson(String firstName, String lastName);

	public List<InfoPerson> listSelectedPersonByFirstNameAndLasrtNameAndRelatives(String firstName, String lastName);

	public boolean delete(String firstName, String lastName);

	public List<Person> getResidentsByAddress(String address);

	public List<PersonEmail> listEmail(String city);

	public List<Person> getfamilyMenbers(String lastName);

	public List<Personchild> printlistMinorsByAddress(String adress);

	public ListPerson listPersonsLinkToSelectedStation(String fireStationNumber);

	public Person addPerson(String firstName, String lastName);
}
