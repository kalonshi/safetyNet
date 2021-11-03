package com.spl.safetyNet.controllerTest;

import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

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
	String stationNumber="1";
    mvc.perform(get("/phoneAlert?firestation="+stationNumber))
        .andExpect(status().isOk());
}

@Test
public void testPhoneAlertWithBadRequest() throws Exception {
	
    mvc.perform(get("/phoneAlert"))
        .andExpect(status().isBadRequest());
}



}
