package com.spl.safetyNet.controllerTest;

import static org.junit.jupiter.api.Assertions.*;

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
    

}
