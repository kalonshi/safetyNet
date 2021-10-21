package com.spl.safetyNet.modelTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import com.spl.safetyNet.models.Person;

class PersonTest {
private static Person person;

@BeforeEach
private void setUpPerTest() {
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
	String dateBirth="01/01/2000";
	Date date;
	try {
		date = simpleDateFormat.parse(dateBirth);
		person = new Person( "firstName", "lastName", " phone", "zip", "address", " city",
				" email", date);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
	@Test
	public void calculateAgeTest() {
		
		assertEquals(21,person.age());
		
	}
	@Test
	public void calculateMinorTest() {
		
		assertEquals(false,person.isMinor());
		
	}
}
