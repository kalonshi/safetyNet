package com.spl.safetyNet.Views;

import java.util.ArrayList;
import java.util.List;

public class ListPerson {
	private List<PersonPrint> contactsList = new ArrayList<PersonPrint>();
	private int nbMinors;
	private int nbAdults;

	public ListPerson() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*
	 * public ListPerson(List<PersonPrint> listToPrint, int nbMinors, int nbAdults)
	 * { super(); this.contactsList = listToPrint; this.nbMinors = nbMinors;
	 * this.nbAdults = nbAdults; }
	 */

	public List<PersonPrint> getContactsList() {
		return contactsList;
	}

	public void setContactsList(List<PersonPrint> listPersonsLinkTest) {
		this.contactsList = listPersonsLinkTest;
	}

	public int getNbMinors() {
		return nbMinors;
	}

	public void setNbMinors(int nbMinors) {
		this.nbMinors = nbMinors;
	}

	public int getNbAdults() {
		return nbAdults;
	}

	public void setNbAdults(int nbAdults) {
		this.nbAdults = nbAdults;
	}

}
