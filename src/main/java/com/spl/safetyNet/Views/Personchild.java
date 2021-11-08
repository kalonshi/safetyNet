package com.spl.safetyNet.Views;

import java.util.List;

public class Personchild {
	public String firstName;
	public String lastName;
	public int age;
	public List<Family> familyMenber;

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public List<Family> getFamilyMenber() {
		return familyMenber;
	}

	public void addMenber(Family family) {
		this.familyMenber.add(family);
	}

	public void setFamilyMenber(List<Family> familyMenbers) {
		this.familyMenber = familyMenbers;
	}

	public Personchild() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Personchild(String firstName, String lastName, int age) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
	}

}
