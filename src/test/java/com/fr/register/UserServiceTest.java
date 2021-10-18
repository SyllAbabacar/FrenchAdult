package com.fr.register;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Validator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.fr.register.dto.UserDto;
import com.fr.register.entities.Gender;
import com.fr.register.entities.User;
import com.fr.register.exception.UserNotFoundException;
import com.fr.register.model.UserModel;
import com.fr.register.repository.UserRepository;
import com.fr.register.service.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Autowired
	@InjectMocks
	 UserServiceImpl userService;
	
	@Mock
	UserRepository repository ;
	
	@Mock
    Validator validator;
	
	
	UserDto dto = new UserDto() ;
	
	UserModel user1 ;

	List<UserModel> users ;
	
	@BeforeEach
    public void setUp() {
		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDate localDate = LocalDate.of(1992, 2, 19);
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		 user1 = new UserModel(1L, "Samba Ndiaye", date, "France", "+33 1 23 45 67 89", Gender.M.getGenre());

		LocalDate localDate1 = LocalDate.of(1996, 2, 10);
		Date date2 = Date.from(localDate1.atStartOfDay(defaultZoneId).toInstant());
		UserModel user2 = new UserModel(1L, "Anta Diop", date2, "France", "+33 1 23 45 67 11", Gender.F.getGenre());

		LocalDate localDate3 = LocalDate.of(1990, 5, 10);
		Date date3 = Date.from(localDate3.atStartOfDay(defaultZoneId).toInstant());

		UserModel user3 = new UserModel(1L, "Karim Mboup", date3, "France", "+33 1 54 45 67 89", Gender.M.getGenre());
		users = new ArrayList<>(Arrays.asList(user1, user2, user3));

    }
	
	
	
	@Test
	public void saveUserTest() {
		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDate localDate = LocalDate.of(1992, 2, 19);
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		UserModel userModel = UserModel.builder()
				.id(1L)
				.name("Frank Mendy").birthdate(date)
				.countryOfResidence("France")
				.phoneNumber("+33 1 23 45 67 89")
				.gender(Gender.F.getGenre()).build();

		User user = dto.toEntity(userModel)  ;
		Mockito.when(repository.save(any())).thenReturn(user);
		UserModel userModel2 = userService.saveUser(userModel);
		Assertions.assertThat(userModel2.getName()).isEqualTo("Frank Mendy");
		verify(repository,times(1)).save((any()));
	}

	@Test
	public void getUserByIdTest() {

		User user = dto.toEntity(user1);
		Mockito.when(repository.findById(1L)).thenReturn(Optional.of(user));

		assertThat(userService.getUserById(user.getId()).getId()).isEqualTo(user.getId());

	}

	@Test
	public void getAllUserTest() {

		List<User> list = dto.toEntities(users);
		Mockito.when(repository.findAll()).thenReturn(list);

		List<UserModel> listUsers = userService.getAllUser();

		assertEquals(list.size(), listUsers.size());
		Assertions.assertThat(listUsers.size()).isGreaterThan(0);

		verify(repository, times(1)).findAll();

	}

	
	@Test
	public void updateUserByIdTest() throws UserNotFoundException {
		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDate localDate = LocalDate.of(1992, 2, 19);
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		UserModel userModel = UserModel
				.builder()
				.id(1L).name("Frank Mendy")
				.birthdate(date)
				.countryOfResidence("France")
				.phoneNumber("+33 1 23 45 67 89")
				.gender(Gender.F.getGenre())
				.build();

		User user = dto.toEntity(userModel);
		when(repository.findById(user.getId())).thenReturn(Optional.of(user));
		Mockito.when(repository.save(user)).thenReturn(user);

		UserModel userUpdate = userService.updateUserById(1L, userModel);
		verify(repository, times(1)).findById(user.getId());
		verify(repository, times(1)).save(user);

		Assertions.assertThat(userUpdate.getId()).isEqualTo(user.getId());

	}

	@Test
	public void deleteUserTest() throws UserNotFoundException {

		User user = dto.toEntity(user1);
		Mockito.when(repository.findById(1L)).thenReturn(Optional.of(user));

		UserModel deleteUser = userService.deleteUserById(user.getId());
		assertThat(deleteUser).isNotNull();
		verify(repository).deleteById(user.getId());
	}
	 

}
