package com.fr.register.service;

import java.util.List;

import com.fr.register.exception.UserNotFoundException;
import com.fr.register.model.UserModel;

public interface UserServiceI {
	
	public List<UserModel> getAllUser() ;
	
	public UserModel getUserByid(Long id) ;
	
	public UserModel saveUser(UserModel model) ;
	
	public void deleteUserById(Long id) throws UserNotFoundException ;

}
