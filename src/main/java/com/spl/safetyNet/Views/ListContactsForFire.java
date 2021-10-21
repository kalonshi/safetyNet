package com.spl.safetyNet.Views;

import java.util.List;


public class ListContactsForFire {
	private String address;
	private String  stationNumber;
private List<PersonFire> listcontactsFire;
public ListContactsForFire() {
	super();
	// TODO Auto-generated constructor stub
}
public ListContactsForFire(String address, String stationNumber, List<PersonFire> listcontactsFire) {
	super();
	this.address = address;
	this.stationNumber = stationNumber;
	this.listcontactsFire = listcontactsFire;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getStationNumber() {
	return stationNumber;
}
public void setStationNumber(String stationNumber) {
	this.stationNumber = stationNumber;
}
public List<PersonFire> getListcontactsFire() {
	return listcontactsFire;
}
public void setListcontactsFire(List<PersonFire> listcontactsFire) {
	this.listcontactsFire = listcontactsFire;
}

}
