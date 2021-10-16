package com.fr.register.service;

import java.util.List;

import com.fr.register.exception.UserNotFoundException;
import com.fr.register.model.UserModel;

public interface UserServiceI {
	
	public List<UserModel> getAllUser() ;
	
	public UserModel getUserById(Long id) ;
	
	public UserModel saveUser(UserModel model) ;
	
	public UserModel deleteUserById(Long id) throws UserNotFoundException ;

	public UserModel getUserByName(String name);

	UserModel updateUserById(Long id, UserModel user) throws UserNotFoundException;

}
