package com.fr.register.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.fr.register.dto.UserDto;
import com.fr.register.exception.UserNotFoundException;
import com.fr.register.model.UserModel;
import com.fr.register.repository.UserRepository;

@Service
public class UserServiceImpl implements UserServiceI{

	@Autowired
	UserRepository userRepository ;
	
	@Autowired
	UserDto dto ;
	
	@Override
	public List<UserModel> getAllUser() {
		return dto.toModels(userRepository.findAll());
	}

	@Override
	public UserModel getUserByid(Long id) {
		return dto.toModel(userRepository.findById(id).get());
	}

	@Override
	public UserModel saveUser(UserModel model) {
		return dto.toModel(userRepository.save(dto.toEntity(model)));
	}

	@Override
	public void deleteUserById(Long id) throws UserNotFoundException {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException ex) {
			throw new UserNotFoundException();
		}
		
	}

}
