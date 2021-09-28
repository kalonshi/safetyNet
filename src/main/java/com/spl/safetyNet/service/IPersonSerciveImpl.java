package com.spl.safetyNet.service;

import java.util.List;



import org.springframework.stereotype.Service;

import com.spl.safetyNet.models.Person2;
@Service
 class IPersonSerciveImpl implements IPerson{

	@Override
	public Person2 addPerson(String firstName, String lastName, String phone, String zip, String address, String city,
			String email) {
		// TODO Auto-generated method stub
		Person2 newPerson =new Person2( firstName, lastName, phone, zip,  address,  city,
				 email);
		return newPerson;
	}

	@Override
	public Person2 getPerson(String firstName, String lastName) {
		// TODO Auto-generated method stub
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
