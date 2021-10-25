package com.spl.safetyNet.Views;



import java.util.List;

import com.spl.safetyNet.models.MedicalRecord;

public class PersonFire {
	private String firstName;
	private String lastName;
	private String phone;
	private int age;
	// Test**************
	private List<String> medications;
	private List<String> allergies;
	
	
	
	
	public PersonFire(String firstName, String lastName, String phone, int age, List<String> medications,
			List<String> allergies) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.age = age;
		this.medications = medications;
		this.allergies = allergies;
	}




	//
	public PersonFire() {
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




	public int getAge() {
		return age;
	}



	/*
	 * public void setAge(int age) { this.age = age; }
	 */



	public List<String> getMedications() {
		return medications;
	}




	/*
	 * public void setMedications(List<String> medications) { this.medications =
	 * medications; }
	 */




	public List<String> getAllergies() {
		return allergies;
	}



	/*
	 * public void setAllergies(List<String> allergies) { this.allergies =
	 * allergies; }
	 */
	
}
