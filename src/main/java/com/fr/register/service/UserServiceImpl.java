package com.fr.register.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fr.register.dto.UserDto;
import com.fr.register.entities.User;
import com.fr.register.exception.UserNotFoundException;
import com.fr.register.model.UserModel;
import com.fr.register.repository.UserRepository;

@Service
public class UserServiceImpl implements UserServiceI {

	private UserRepository userRepository;

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	UserDto dto = new UserDto();

	@Override
	public List<UserModel> getAllUser() {
		List<User> userList = userRepository.findAll();

		return dto.toModels(userList);
	}

	@Override
	public UserModel getUserById(Long id) {
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isPresent()) {
			return dto.toModel(optionalUser.get());

		} else {
			return null;
		}
	}

	@Override
	public UserModel saveUser(UserModel model) {
		User u = null;
		try {
			u = userRepository.save(dto.toEntity(model));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto.toModel(u);
	}

	@Override
	public UserModel updateUserById(Long id, UserModel user) throws UserNotFoundException {
		userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		user.setId(id);
		return dto.toModel(userRepository.save(dto.toEntity(user)));
	}

	@Override
	public UserModel deleteUserById(Long id) throws UserNotFoundException {

		UserModel user = null;
		user = getUserById(id);
		if (null != user) {
			userRepository.deleteById(id);
			return user;
		} else {
			return user;
		}

	}

	@Override
	public UserModel getUserByName(String name) {
		Optional<User> optionalUser = userRepository.findByName(name);
		if (optionalUser.isPresent()) {
			return dto.toModel(optionalUser.get());

		} else {
			return null;
		}

	}

}
