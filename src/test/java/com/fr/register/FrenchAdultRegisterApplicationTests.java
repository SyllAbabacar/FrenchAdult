package com.fr.register;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fr.register.entities.Gender;
import com.fr.register.model.UserModel;
import com.fr.register.service.UserServiceI;
import com.fr.register.web.UserControllerAPI;

@ExtendWith(MockitoExtension.class)
class FrenchAdultRegisterApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	
	@InjectMocks
	private UserControllerAPI controllerAPI ;

	@Mock
	UserServiceI userServiceI;
	
	
	List<UserModel> users  = null ;
	UserModel user1 = null ;
	UserModel user2 = null ;
	UserModel user3 = null ;
	
	@BeforeEach
    public void setUp() {
		
		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDate localDate = LocalDate.of(1992, 2, 19);
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		 user1 = new UserModel(1L, "Samba Ndiaye", date, "France", "+33 1 23 45 67 89", Gender.M.getGenre());

		LocalDate localDate1 = LocalDate.of(1996, 2, 10);
		Date date2 = Date.from(localDate1.atStartOfDay(defaultZoneId).toInstant());
		 user2 = new UserModel(1L, "Anta Diop", date2, "France", "+33 1 23 45 67 11", Gender.F.getGenre());

		LocalDate localDate3 = LocalDate.of(1990, 5, 10);
		Date date3 = Date.from(localDate3.atStartOfDay(defaultZoneId).toInstant());

		 user3 = new UserModel(1L, "Karim Mboup", date3, "France", "+33 1 54 45 67 89", Gender.M.getGenre());
		 users = new ArrayList<>(Arrays.asList(user1, user2, user3));
		   mockMvc = MockMvcBuilders.standaloneSetup(controllerAPI).build();


    }


	// Test de l' endpoint GET: /api/users pour la recupération des utilisateurs
	@Test
	public void testGetAllUsers() throws Exception {
		
		List<UserModel> users = new ArrayList<>(Arrays.asList(user1, user2, user3));
		
		Mockito.when(userServiceI.getAllUser()).thenReturn(users);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/users").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(users))).
                andDo(MockMvcResultHandlers.print());	
		
	    verify(userServiceI).getAllUser();
	    verify(userServiceI,times(1)).getAllUser();


		
	}

	// Test de l' endpoint GET: /api/users pour la recupération d'un utilisateur via  son ID
	@Test
	public void testGetUserById() throws Exception {
		
	    Mockito.when(userServiceI.getUserById(user1.getId())).thenReturn(user1);	
		mockMvc.perform(get("/api/users/1").
		           contentType(MediaType.APPLICATION_JSON).
		           content(asJsonString(user1))).
		           andExpect(MockMvcResultMatchers.status().isOk()).
		           andDo(MockMvcResultHandlers.print());
	}

	
	// Test de l' endpoint POST: /api/users pour la sauvegarde d'un nouveau utlisateurs
	@Test
	public void testPostUsers() throws Exception {
		
		
	    Mockito.when(userServiceI.saveUser(any())).thenReturn(user1);
		
		
		mockMvc.perform(post("/api/users").
		           contentType(MediaType.APPLICATION_JSON).
		           content(asJsonString(user1))).
		           andExpect(status().isCreated());

		verify(userServiceI,times(1)).saveUser(any());
	}

	// Test de l' endpoint PUT /api/users/id pour la mise a jour des données d'un utilisateur via son ID
	@Test
	public void testUpdateUsers() throws Exception {
		
		user1.setName("Ababacar Syll");
	    Mockito.when(userServiceI.updateUserById(user1.getId(),user1)).thenReturn(user1);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.put("/api/users/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(asJsonString(user1));

		mockMvc
				.perform(mockRequest)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("Ababacar Syll")));
		verify(userServiceI,times(1)).updateUserById(1L, user1);

	}
	
	
	

	// Test de l' endpoint DELETE /api/users/id pour la suppréssion d'un utilisateur via son ID
	@Test
	public void testDeleteUserById() throws Exception {
		
	    Mockito.when(userServiceI.deleteUserById(user1.getId())).thenReturn(user1);
		
		 mockMvc.perform(delete("/api/users/1")
			      .contentType(MediaType.APPLICATION_JSON)
			      .content(asJsonString(user1)))
			      .andExpect(MockMvcResultMatchers.status().isOk()).
			      andDo(MockMvcResultHandlers.print());

	}
	
	public static String asJsonString(final Object obj){
	    try{
	        return new ObjectMapper().writeValueAsString(obj);
	    }catch (Exception e){
	           throw new RuntimeException(e);
	      }
	}


}
