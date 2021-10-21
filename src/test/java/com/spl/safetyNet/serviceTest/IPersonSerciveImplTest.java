package com.spl.safetyNet.serviceTest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;


import com.spl.safetyNet.models.Person;
import com.spl.safetyNet.service.IPersonSerciveImpl;

public class IPersonSerciveImplTest {
	private static IPersonSerciveImpl iPersonSerciveImpl;
	private Person person;
	@BeforeAll
	private static void setUp() {
		iPersonSerciveImpl=new IPersonSerciveImpl(); 
	}
	
	@BeforeEach
	private void setUpPerTest() {
		person = new Person();
	}
}
