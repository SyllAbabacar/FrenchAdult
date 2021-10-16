package com.fr.register;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fr.register.entities.Gender;
import com.fr.register.entities.User;
import com.fr.register.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTest {

	@Autowired
	UserRepository userRepository ;
	
	@Test
    @Order(1)
	public void saveUserTest() {
		LocalDate localDate = LocalDate.of(1992,2,19);   
		User user = User.builder()
				.name("Frank Mendy")
				.birthdate(java.sql.Date.valueOf(localDate))
				.countryOfResidence("France")
				.phoneNumber("+33 1 23 45 67 89")
				.gender(Gender.F)
				.build();
		User u = userRepository.save(user) ;
		
		 Assertions.assertThat(u.getName()).isEqualTo("Frank Mendy") ;
	}
	
	@Test
    @Order(2)
    public void getUserTest(){

        User user = userRepository.findById(1L).get();

        Assertions.assertThat(user.getId()).isEqualTo(1L);

    }
	
	@Test
    @Order(3)
    public void getAllOfUserTest(){

        List<User> User = userRepository.findAll();

        Assertions.assertThat(User.size()).isGreaterThan(0);

    }
	
	
	@Test
    @Order(4)
    @Rollback(value = false)
    public void updateUserByIdTest(){

        User user = userRepository.findById(1L).get();

        user.setName("Frank Gomis");

        User userUpdated =  userRepository.save(user);

        Assertions.assertThat(userUpdated.getName()).isEqualTo("Frank Gomis");

    }
	
	
	@Test
    @Order(5)
    @Rollback(value = false)
    public void deleteUserByIdTest(){

        userRepository.deleteById(1L);
        User user1 = null;
        Optional<User> optionalUser = userRepository.findByName("Frank Gomis");
        
        if(optionalUser.isPresent()){
            user1 = optionalUser.get();
        }
        Assertions.assertThat(user1).isNull();
    }
	
	
}
