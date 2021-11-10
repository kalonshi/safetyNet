package com.spl.safetyNet.controller;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spl.safetyNet.Views.InfoPerson;
import com.spl.safetyNet.Views.ListPerson;
import com.spl.safetyNet.Views.PersonEmail;

import com.spl.safetyNet.Views.Personchild;
import com.spl.safetyNet.models.Person;
import com.spl.safetyNet.service.IPersonSerciveImpl;
import org.apache.logging.log4j.LogManager;

@RestController
public class PersonController {
	@Autowired
	private IPersonSerciveImpl iPersonImpl;

	
	 private static final Logger logger = LogManager.getLogger(PersonController.class);
	  
	 	@ResponseBody
	@RequestMapping(path = "/")
	public String home(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		String host = request.getServerName();
		// Spring Boot >= 2.0.0.M7
		String endpointBasePath = "/actuator";
		StringBuilder sb = new StringBuilder();
		sb.append("<h2>Spring Boot Actuator</h2>");
		sb.append("<ul>");

		// http://localhost:8090/actuator
		String url = "http://" + host + ":8080" + contextPath + endpointBasePath;
		sb.append("<li><a href='" + url + "'>" + url + "</a></li>");
		sb.append("</ul>");
		return sb.toString();
	}

	// http://localhost:8080/firestation?stationNumber=<station_number>

	@GetMapping("/firestation")

	@ResponseBody
	public ListPerson getListPersonLinkedWithStation(@RequestParam String stationNumber) {

		logger.info("Entering url :firestation?stationNumber=" + stationNumber);

		return iPersonImpl.listPersonsLinkToStationSelected(stationNumber);

	}

	// http://localhost:8080/childAlert?address=<address>
	@GetMapping("/childAlert")
	@ResponseBody
	public List<Personchild> getListOfMinorAndRelative(@RequestParam String address) {
		/* return null; */

		logger.info("Entering url :http://localhost:8080/childAlert?address=" + address);

		return iPersonImpl.printlistMinorsByAddress(address);
	}

	// Test A verifier
	// http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
	@GetMapping("/personInfo")
	@ResponseBody
	public List<InfoPerson> getPersonInfo(@RequestParam String firstName, @RequestParam String lastName) {

		logger.info(
				"Entering url :http://localhost:8080/personInfo?firstName= " + firstName + " &lastName= " + lastName);

		if (!firstName.isEmpty() || !lastName.isEmpty()) {
			return iPersonImpl.getListInfoPerson(firstName, lastName);
		}

		logger.error("firstName.isEmpty()||!lastName.isEmpty()");
		return null;
	}

	// http://localhost:8080/communityEmail?city OK

	@GetMapping("/communityEmail")
	@ResponseBody
	public List<PersonEmail> getListEmailCitizens(@RequestParam String city) {
		logger.info("entering http://localhost:8080/communityEmail?" + city);
		List<PersonEmail> list = iPersonImpl.listEmail(city);

		return list;
	}

	@DeleteMapping("/person/delete")
	@ResponseBody
	public void deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
		logger.info("delete Person by FirstName and LastName" + firstName + " " + lastName);
		iPersonImpl.delete(firstName, lastName);

	}

	@PostMapping("/person/add")
	@ResponseBody
	public Person add(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String phone,
			@RequestParam String zip, @RequestParam String address, @RequestParam String city,
			@RequestParam String email, @RequestParam Date birthDate) {
		logger.info("add Person :" + firstName + " " + lastName);

		Person newPerson = iPersonImpl.addPerson(firstName, lastName, phone, zip, address, city, email, birthDate);

		return newPerson;

	}

	@PutMapping("person/update")
	@ResponseBody
	public Person updatePerson(@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String newPhone, @RequestParam String newZip, @RequestParam String newAddress,
			@RequestParam String newCity, @RequestParam String newEmail, @RequestParam Date newBirthDate) {

		logger.info("update Person :" + firstName + " " + lastName);
		Person updatePersonRecord = iPersonImpl.updatePerson(firstName, lastName, newPhone, newZip, newAddress, newCity,
				newEmail);

		return updatePersonRecord;

	}

}
