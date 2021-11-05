package com.spl.safetyNet.controllerTest;
/*
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.spl.safetyNet.controller.PersonController;
import com.spl.safetyNet.service.IPersonSerciveImpl;
*/

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

import com.spl.safetyNet.controller.PersonController;
import com.spl.safetyNet.service.IPersonSerciveImpl;




@WebMvcTest(controllers = PersonController.class)
class PersonControllerTest {

	@Autowired
    private MockMvc mvc;

    @MockBean
    private IPersonSerciveImpl iPersonSerciveImpl;
    
    @Test
    public void getAccurators() throws Exception {
    	
        mvc.perform(get("/"))
            .andExpect(status().isOk());
    }
    @Test
    public void testGetEmails() throws Exception {
    	String city="Culver";
        mvc.perform(get("/communityEmail?city="+city))
            .andExpect(status().isOk());
    }
    @Test
    public void testGetEmailsWithBadRequest() throws Exception {
    	
        mvc.perform(get("/communityEmail"))
            .andExpect(status().isBadRequest());
    }
    @Test
    public void testChildAlert() throws Exception {
    	String address="1509 Culver St";
        mvc.perform(get("/childAlert?address="+address))
            .andExpect(status().isOk());
    }
    @Test
    public void testChildAlertWithBadRequest() throws Exception {
    	
        mvc.perform(get("/childAlert"))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    public void testpersonInfo() throws Exception {
    	
		
       String firstName="John";
    		   String lastName="Boyd";
    	mvc.perform(get("/personInfo?firstName="+firstName+"&lastName="+lastName))
            .andExpect(status().isOk());
    }
    
    @Test
    public void testpersonInfoWithBadRequest() throws Exception {
    	
		
     
    	mvc.perform(get("/personInfo"))
            .andExpect(status().isBadRequest());
    }
	/*
	 * @Test public void givenFirstNameAndLastNameReturnInfo() throws ParseException
	 * {
	 * 
	 * String firstName="John"; String lastName="Boyd"; String phone="841-874-6512";
	 * String zip="97451"; String address="1509 Culver St"; String city="Culver";
	 * String email="jaboyd@email.com";
	 * 
	 * SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy"); Date
	 * birthDate = simpleDateFormat.parse("06/12/2018"); String uri ="/personInfo";
	 * 
	 * 
	 * }
	 * 
	 * 
	 * @Test public void addPerson() throws ParseException { String
	 * firstName="Patrice"; String lastName="LASSEY"; String phone="06 58 59 56 32";
	 * String zip="92330"; String address="17 rue jules vernes"; String
	 * city="Nogent sur marne"; String email="gjcvfg@gmail.com";
	 * 
	 * SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy"); Date
	 * birthDate = simpleDateFormat.parse("06/12/2018");
	 * 
	 * }
	 * 
	 * 
	 * @Test void givenCityReturnListEmail() throws Exception { String uri =
	 * "/communityEmail"; MvcResult mvcResult =
	 * mvc.perform(MockMvcRequestBuilders.get(uri)
	 * .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn(); int status =
	 * mvcResult.getResponse().getStatus(); assertEquals(200, status); }
	 * 
	 * @Test void givenStationNumberReturnListcontacts() throws Exception { String
	 * uri = "/firestation" ; MvcResult mvcResult =
	 * mvc.perform(MockMvcRequestBuilders.get(uri)
	 * .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn(); int status =
	 * mvcResult.getResponse().getStatus(); assertEquals(200, status); }
	 * 
	 * @Test void givenAddressReturnListofMinorsWithRelative() throws Exception {
	 * 
	 * String uri = "/childAlert"; MvcResult mvcResult =
	 * mvc.perform(MockMvcRequestBuilders.get(uri)
	 * .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn(); int status =
	 * mvcResult.getResponse().getStatus(); assertEquals(200, status); }
	 */
}
