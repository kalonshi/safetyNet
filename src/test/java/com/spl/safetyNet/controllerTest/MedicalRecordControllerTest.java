package com.spl.safetyNet.controllerTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestParam;

import com.spl.safetyNet.controller.MedicalRecordController;
import com.spl.safetyNet.service.IMedicalRecordImpl;

@WebMvcTest(controllers = MedicalRecordController.class)
class MedicalRecordControllerTest {
	@Autowired
	private MockMvc mvc;

	@MockBean
	private IMedicalRecordImpl iMedicalRecordImpl;

	@Test
	public void updateMedicalRecordTest() throws Exception {
		String firstName = "John";
		String lastName = "Boyd";
		String medication = "aznol:350mg";
		String newMedication = "aspirin:200mg";
		mvc.perform(put("/medicalRecord/updateMedication?firstName=" + firstName + "&lastName=" + lastName
				+ "&medication=" + medication + "&newMedication=" + newMedication)).andExpect(status().isOk());

	}

	@Test
	public void updateAllergyTest() throws Exception {
		String firstName = "John";
		String lastName = "Boyd";
		String allergy = "nillacilan";
		String newAllergy = "peanuts";
		mvc.perform(put("/medicalRecord/updateAllergy?firstName=" + firstName + "&lastName=" + lastName + "&allergy="
				+ allergy + "&newAllergy=" + newAllergy)).andExpect(status().isOk());

	}

	@Test
	public void addMedicationTest() throws Exception {
		String firstName = "John";
		String lastName = "Boyd";
		String medication = "newMedication:350mg";
		mvc.perform(post("/medicalRecord/addMedication?firstName=" + firstName + "&lastName=" + lastName
				+ "&medication=" + medication)).andExpect(status().isOk());

	}

	@Test
	public void addAllergyTest() throws Exception {
		String firstName = "John";
		String lastName = "Boyd";
		String allergy = "peanuts";
		mvc.perform(post(
				"/medicalRecord/addAllergy?firstName=" + firstName + "&lastName=" + lastName + "&allergy=" + allergy))
				.andExpect(status().isOk());

	}

	@Test
	public void deleteMedicationTest() throws Exception {
		String firstName = "John";
		String lastName = "Boyd";
		String medication = "aznol:350mg";
		mvc.perform(delete("/medicalRecord/deleteMedication?firstName=" + firstName + "&lastName=" + lastName
				+ "&medication=" + medication)).andExpect(status().isOk());

	}

	@Test
	public void deleteAllergyTest() throws Exception {
		String firstName = "John";
		String lastName = "Boyd";
		String allergy = "nillacilan";
		mvc.perform(delete("/medicalRecord/deleteAllergy?firstName=" + firstName + "&lastName=" + lastName + "&allergy="
				+ allergy)).andExpect(status().isOk());

	}
}
