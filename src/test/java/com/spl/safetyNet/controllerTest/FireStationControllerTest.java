package com.spl.safetyNet.controllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.spl.safetyNet.controller.FireStationController;
import com.spl.safetyNet.service.IFirestationImpl;

@WebMvcTest(controllers = FireStationController.class)
class FireStationControllerTest {
	@Autowired
	private MockMvc mvc;

	@MockBean
	private IFirestationImpl iFirestationImpl;

	@Test
	public void testPhoneAlert() throws Exception {
		String stationNumber = "1";
		mvc.perform(get("/phoneAlert?firestation=" + stationNumber)).andExpect(status().isOk());
	}

	@Test
	public void testPhoneAlertWithBadRequest() throws Exception {

		mvc.perform(get("/phoneAlert")).andExpect(status().isBadRequest());
	}

	@Test
	public void testFire() throws Exception {
		String address = "1509 Culver St";

		mvc.perform(get("/fire?address=" + address)).andExpect(status().isOk());

	}

	@Test
	public void flood() throws Exception {
		/* List<String> stations = new ArrayList<String>() ; */
		/*
		 * stations.add("1"); stations.add("2");
		 */
		/*
		 * String[] stations= ["1","2"]; List<String> stations2= ("1","2");
		 */
		String[] stations3 = { "1", "2" };

		/* String[] stations4= ["1","2"]; */

		mvc.perform(get("/flood/stations?stations=" + stations3)).andExpect(status().isOk());
// http://localhost:8080/flood/stations?stations=<a list of station_numbers>
		/*
		 * mvc.perform(get("/flood/stations?stations="+"1")).andExpect(status().isOk());
		 */
	}

	@Test
	public void addFireStationTest() throws Exception {
		String fireStationNumber = "15";
		String address = "17 av jules Guedes";
		mvc.perform(post("/fireStation/add?fireStationNumber=" + fireStationNumber + "&address=" + address))
				.andExpect(status().isOk());

	}

	@Test
	public void deleteFireStationTest() throws Exception {
		String fireStationNumber = "1";
		mvc.perform(delete("/fireStation/delete?fireStationNumber=" + fireStationNumber)).andExpect(status().isOk());

	}

	@Test
	public void updateFireStationNumberTest() throws Exception {
		String fireStationNumber = "1";
		String newStationNumber = "14";
		mvc.perform(put("/fireStation/updateNumber?fireStationNumber=" + fireStationNumber + "&newStationNumber="
				+ newStationNumber)).andExpect(status().isOk());

	}

	@Test
	public void deleteAddressFireStationTest() throws Exception {
		String address = "951 LoneTree Rd";
		mvc.perform(delete("/fireStation/address/delete?address=" + address)).andExpect(status().isOk());

	}

}
