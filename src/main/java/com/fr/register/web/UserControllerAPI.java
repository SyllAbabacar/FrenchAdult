package com.fr.register.web;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fr.register.exception.UserInvalidRequestException;
import com.fr.register.exception.UserNotFoundException;
import com.fr.register.model.UserModel;
import com.fr.register.service.UserServiceI;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

// Utilisateurs  Rest API /api/

@Api(description ="API for CRUD operations on users.")
@RequestMapping("/api")
@RestController
@Validated
public class UserControllerAPI {

	@Autowired
	UserServiceI userServiceI;


	@ApiOperation(value = "To retrieve all users")
	@GetMapping(path = "/users", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<UserModel>> userList() {
		return new ResponseEntity<>(userServiceI.getAllUser(), HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "To retrieve user with a ID")
	@GetMapping(path = "users/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserModel> findUserById(
			@PathVariable("id") @Pattern(regexp = "\\d+", message = "User ID must be numeric") String id)
			throws UserNotFoundException {
		try {
			return new ResponseEntity<>(userServiceI.getUserById(Long.valueOf(id)), HttpStatus.OK);
		} catch (NoSuchElementException ex) {
			throw new UserNotFoundException();
		}
	}
	

	@ApiOperation(value = "To register a user")
	@PostMapping(path = "/users", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
	MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserModel> save(@Valid @RequestBody UserModel user) {

		UserModel u = userServiceI.saveUser(user);
		return new ResponseEntity<>(u, HttpStatus.CREATED);
	}
	

	@ApiOperation(value = "To update user with a ID")
	@PutMapping(path = "/users/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserModel> update(@Valid @RequestBody UserModel user, @PathVariable Long id) throws UserNotFoundException {
		if (user == null || user.getId() == null) {
	        throw new UserInvalidRequestException("UserModel or ID must not be null!");
	    }
		
		user.setId(id);
		UserModel u = userServiceI.updateUserById(id,user);
		return new ResponseEntity<>(u, HttpStatus.OK);

	}

	@ApiOperation(value = "To Delete user with a ID")
	@DeleteMapping(path = "users/{id}")
	public ResponseEntity<String> delete(
			@PathVariable("id") @Pattern(regexp = "\\d+", message = "User ID must be numeric") String id)
			throws UserNotFoundException {
			UserModel user = userServiceI.deleteUserById(Long.valueOf(id));
			if(null != user ) {
				return new ResponseEntity<>("User successfully Deleted", HttpStatus.OK);
			}else {
				return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

			}


	}

}
