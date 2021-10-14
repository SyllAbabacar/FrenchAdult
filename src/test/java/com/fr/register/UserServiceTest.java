package com.fr.register;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.fr.register.entities.Gender;
import com.fr.register.exception.UserNotFoundException;
import com.fr.register.model.UserModel;
import com.fr.register.service.UserServiceI;

@SpringBootTest
public class UserServiceTest {

	@Autowired
	UserServiceI userServiceI;

	@Test
	@Order(1)
	public void saveUserTest() {
		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDate localDate = LocalDate.of(1992, 2, 19);
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		UserModel userModel = UserModel.builder().name("Frank Mendy").birthdate(date).countryOfResidence("France")
				.phoneNumber("+33 1 23 45 67 89").gender(Gender.F.getGenre()).build();

		UserModel userModel2 = userServiceI.saveUser(userModel);

		Assertions.assertThat(userModel.getCountryOfResidence()).isEqualTo("France");
	}

	@Test
	@Order(2)
	public void getUserByIdTest() {

		UserModel user = userServiceI.getUserById(1L);
		Assertions.assertThat(user).isNull();

	}

	@Test
	@Order(3)
	public void getAllUserTest() {

		List<UserModel> user = userServiceI.getAllUser();

		Assertions.assertThat(user.size()).isGreaterThan(0);

	}

	@Test
	@Order(4)
	@Rollback(value = false)
	public void updateUserByIdTest() {

		UserModel user = userServiceI.getUserById(1L);

		user.setName("Frank Gomis");

		UserModel userUpdated = userServiceI.saveUser(user);

		Assertions.assertThat(userUpdated.getName()).isEqualTo("Frank Gomis");

	}

	@Test
	@Order(5)
	@Rollback(value = false)
	public void deleteUserTest() throws UserNotFoundException {

		userServiceI.deleteUserById(1L);


		UserModel user1 = userServiceI.getUserByName("Frank Gomis");

		Assertions.assertThat(user1).isNull();
	}

}
