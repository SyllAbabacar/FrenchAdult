package com.fr.register.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fr.register.config.TrackingExecutionTime;
import com.fr.register.dto.UserDto;
import com.fr.register.entities.User;
import com.fr.register.exception.UserNotFoundException;
import com.fr.register.model.UserModel;
import com.fr.register.repository.UserRepository;

@Service
public class UserServiceImpl implements UserServiceI {

	private UserRepository userRepository;
	
	UserDto dto = new UserDto();

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	private Validator validator;

	@Autowired
	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	

	@Override
	@TrackingExecutionTime
	public List<UserModel> getAllUser() {
		List<User> userList = userRepository.findAll();

		return dto.toModels(userList);
	}

	@Override
	@TrackingExecutionTime
	public UserModel getUserById(Long id) {
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isPresent()) {
			return dto.toModel(optionalUser.get());

		} else {
			return null;
		}
	}

	@Override
	@TrackingExecutionTime
	public UserModel saveUser(UserModel model) {
		User userEntity = dto.toEntity(model);
		Set<ConstraintViolation<User>> violations = validator.validate(userEntity);

		if (!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (ConstraintViolation<User> constraintViolation : violations) {
				sb.append(constraintViolation.getMessage());
			}
			throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
		}
		userEntity = userRepository.save(dto.toEntity(model));
		return dto.toModel(userEntity);

	}

	@Override
	@TrackingExecutionTime
	public UserModel updateUserById(Long id, UserModel user) throws UserNotFoundException {
		
		userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		
		user.setId(id);
		
		return dto.toModel(userRepository.save(dto.toEntity(user)));
	}

	@Override
	@TrackingExecutionTime
	public UserModel deleteUserById(Long id) throws UserNotFoundException {

		UserModel user = null;
		
		user = getUserById(id);
		
		if (null != user) {
			
			userRepository.deleteById(id);
			
			return user;
			
		} 
		else {
			
			return user;
		}

	}

	@Override
	public UserModel getUserByName(String name) {
		Optional<User> optionalUser = userRepository.findByName(name);
		if (optionalUser.isPresent()) {
			return dto.toModel(optionalUser.get());

		} 
		else {
			return null;
		}

	}

	@Override
	public UserModel getUserByPhoneNumber(String numberPhone) {
		Optional<User> optionalUser = userRepository.findByPhoneNumber(numberPhone);
		if (optionalUser.isPresent()) {
			return dto.toModel(optionalUser.get());

		} 
		else {
			return null;
		}

	}

}
