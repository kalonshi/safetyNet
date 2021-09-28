package com.spl.safetyNet.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spl.safetyNet.models.Person2;
@Service
 class IPersonSerciveImpl implements IPerson{
	@Autowired 
	private JsonFileData3 jSonFile;
	@Override
	public Person2 addPerson(String firstName, String lastName, String phone, String zip, String address, String city,
			String email) {
		// TODO Auto-generated method stub
		Person2 newPerson =new Person2( firstName, lastName, phone, zip,  address,  city,
				 email);
		try {
			jSonFile.loadJsonPersons().add(newPerson);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newPerson;
	}

	@Override
	public Person2 getPerson(String firstName, String lastName) {
		// TODO Auto-generated method stub
		
		List<Person2> personList;
		try {
			personList = jSonFile.loadJsonPersons();
			for(Person2 p :personList) {
				if ((p.getFirstName().equals(firstName))&&(p.getLastName().equals(lastName))) 
					return p;
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		
		return null;
	}

	@Override
	public void delete(String firstName, String lastName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getListEmail(String city) {
		// TODO Auto-generated method stub
		return null;
	}

}
