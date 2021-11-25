package com.spl.safetyNet.controllerTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.spl.safetyNet.controller.MedicalRecordController;
import com.spl.safetyNet.service.IMedicalRecordImpl;

@WebMvcTest(controllers = MedicalRecordController.class)
class MedicalRecordControllerTest {
	@Autowired
	private MockMvc mvc;

	@MockBean
	private IMedicalRecordImpl iMedicalRecordImpl;


	@Test
	public void testAddMedicalRecord() throws Exception {
		String firstName = "John";
		String lastName = "Boyd";
		mvc.perform(post("http://localhost:8080/medicalRecord/add?firstName=" + firstName + "&lastName=" + lastName)).andExpect(status().isOk());
	}

	@Test
	public void testDeleteMedicalRecord() throws Exception {
		String firstName = "John";
		String lastName = "Boyd";
		mvc.perform(delete("http://localhost:8080/medicalRecord/delete?firstName=" + firstName + "&lastName=" + lastName)).andExpect(status().isOk());
	}

	

}
