package com.fr.register;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fr.register.entities.Gender;
import com.fr.register.model.UserModel;

@SpringBootTest
@AutoConfigureMockMvc
class FrenchAdultRegisterApplicationTests {

	@Autowired
    private MockMvc mockMvc; 
	
	
	@Autowired
    ObjectMapper mapper;
	
	
	
	
	// Test de l' endpoint GET: /api/users pour  la recupération des utilisateurs
	@Test
    public void testGetUsers() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
	            .get("/api/users")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$[1].name", is("Modou Ndiaye")));
    }
	
	
	// Test de l' endpoint GET: /api/users pour  la recupération d'un utilisateur via son ID
		@Test
	    public void testGetUserById() throws Exception {
			mockMvc.perform(MockMvcRequestBuilders
		            .get("/api/users/1")
		            .contentType(MediaType.APPLICATION_JSON))
		            .andExpect(status().isOk())
		            .andExpect(jsonPath("$.name", is("Faty Syll")));
	    }
	
	
	// Test de l' endpoint POST: /api/users pour  la sauvegarde d'un nouveau utlisateurs
	@Test
    public void testPostUsers() throws Exception {	
		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDate localDate = LocalDate.of(1992,2,19);   
        Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		UserModel userModel = UserModel.builder()
				.name("Frank Mendy")
				.birthdate(date)
				.countryOfResidence("France")
				.phoneNumber("+33 1 23 45 67 89")
				.gender(Gender.F.getGenre())
				.build();
		
				
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/users")
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON)
	            .content(this.mapper.writeValueAsString(userModel));
		
		mockMvc.perform(mockRequest)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name", is("Frank Mendy")));
    }
	
	// Test de l' endpoint PUT /api/users/id pour  la mise a jour des données d'un utilisateur via son ID

	@Test
    public void testUpdateUsers() throws Exception {	
		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDate localDate = LocalDate.of(1992,2,19);   
        Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		UserModel userModel = UserModel.builder()
				.id(4L)
				.name("Frank Gomis")
				.birthdate(date)
				.countryOfResidence("France")
				.phoneNumber("+33 1 33 33 67 89")
				.gender(Gender.F.getGenre())
				.build();
				
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/users/4")
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON)
	            .content(this.mapper.writeValueAsString(userModel));
		
		mockMvc.perform(mockRequest)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is("Frank Gomis")));
    }
	
	
	
	// Test de l' endpoint DELETE /api/users/id pour la suppréssion d'un utilisateur
	// via son ID
	@Test
	public void testDeleteUserById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/4").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

}
