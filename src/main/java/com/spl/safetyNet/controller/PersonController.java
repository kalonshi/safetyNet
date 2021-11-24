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

		return iPersonImpl.listPersonsLinkToSelectedStation(stationNumber);

	}

	// http://localhost:8080/childAlert?address=<address>
	@GetMapping("/childAlert")
	@ResponseBody
	public List<Personchild> getListOfMinorAndRelative(@RequestParam String address) {
		/* return null; */

		logger.info("Entering url :http://localhost:8080/childAlert?address=" + address);

		return iPersonImpl.printlistMinorsByAddress(address);
	}

	// http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
	@GetMapping("/personInfo")
	@ResponseBody
	public List<InfoPerson> getPersonInfo(@RequestParam String firstName, @RequestParam String lastName) {

		logger.info(
				"Entering url :http://localhost:8080/personInfo?firstName= " + firstName + " &lastName= " + lastName);

		if (!firstName.isEmpty() || !lastName.isEmpty()) {
			return iPersonImpl.listSelectedPersonByFirstNameAndLasrtNameAndRelatives(firstName, lastName);
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
	public boolean deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
		logger.info("delete Person by FirstName and LastName" + firstName + " " + lastName);
		return iPersonImpl.delete(firstName, lastName);

	}

	@GetMapping("/person")

	@ResponseBody
	public Person person(@RequestParam String firstName, @RequestParam String lastName) {

		logger.info("Entering url :person?firstName=" + firstName + "& lastName= " + lastName);

		return iPersonImpl.getPerson(firstName, lastName);

	}

	@PostMapping("/person/add")
	@ResponseBody
	public Person addPerson(@RequestParam String firstName, @RequestParam String lastName) {
		logger.info("add Person : firstname :" + firstName + " lastName : " + lastName);

		Person newPerson = iPersonImpl.addPerson(firstName, lastName);

		return newPerson;

	}

	@PutMapping("person/updateLastName")

	@ResponseBody
	public Person updatePersonLastName(@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String updatedLastName) {

		logger.info("update Person :" + firstName + " " + lastName);
		Person updatePersonRecord = iPersonImpl.updatePersonLaststName(firstName, lastName, updatedLastName);

		return updatePersonRecord;

	}

	@PutMapping("person/updateFirstName")

	@ResponseBody
	public Person updatePersonFirstName(@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String updatedFirstName) {

		logger.info("update Person :" + firstName + " " + lastName);
		Person updatePersonRecord = iPersonImpl.updatePersonFirstName(firstName, lastName, updatedFirstName);

		return updatePersonRecord;

	}

	@PutMapping("person/updatePhone")
	@ResponseBody
	public Person updatePersonPhone(@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String updatedPhone) {

		logger.info("update Person :" + firstName + " " + lastName);
		Person updatePersonRecord = iPersonImpl.updatePersonPhone(firstName, lastName, updatedPhone);

		return updatePersonRecord;

	}

	@PutMapping("person/updateEmail")
	@ResponseBody
	public Person updatePersonEmail(@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String updatedEmail) {

		logger.info("update Person :" + firstName + " " + lastName);
		Person updatePersonRecord = iPersonImpl.updatePersonEmail(firstName, lastName, updatedEmail);

		return updatePersonRecord;

	}

	@PutMapping("person/updateAddress")

	@ResponseBody
	public Person updatePerson(@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String updatedAddress

	) {

		logger.info("update Person :" + firstName + " " + lastName);
		Person updatePersonRecord = iPersonImpl.updatePersonAdresse(firstName, lastName, updatedAddress);

		return updatePersonRecord;

	}

}
