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

import com.fr.register.exception.UserNotFoundException;
import com.fr.register.model.UserModel;
import com.fr.register.service.UserServiceI;

// Utilisateurs  Rest API /api/

@RequestMapping("/api")
@RestController
@Validated
public class UserControllerAPI {

	@Autowired
	UserServiceI userServiceI;


	// API : GET /users pour recupérer tous les utilisateurs
	@GetMapping(path = "/users", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<UserModel>> userList() {
		return new ResponseEntity<>(userServiceI.getAllUser(), HttpStatus.OK);
	}
	
	

	// API : GET /users/id Pour la récupération d'un utilisateur grace à son ID
	@GetMapping(path = "users/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserModel> findUserById(
			@PathVariable("id") @Pattern(regexp = "\\d+", message = "L'id de l'utilisateur doit être numérique") String id)
			throws UserNotFoundException {
		try {
			return new ResponseEntity<>(userServiceI.getUserById(Long.valueOf(id)), HttpStatus.OK);
		} catch (NoSuchElementException ex) {
			throw new UserNotFoundException();
		}
	}
	

	// API : POST /users pour la sauvegarde d'un utilisateur
	@PostMapping(path = "/users", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
	MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserModel> save(@Valid @RequestBody UserModel user) {

		UserModel u = userServiceI.saveUser(user);
		return new ResponseEntity<>(u, HttpStatus.CREATED);
	}
	

	// API : PUT /users/id Pour la modification d'un utilisateur grace à son ID
	@PutMapping(path = "/users/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserModel> update(@Valid @RequestBody UserModel user, @PathVariable Long id) {
		user.setId(id);
		UserModel u = userServiceI.saveUser(user);
		return new ResponseEntity<>(u, HttpStatus.OK);

	}

	// API : DELETE /users/id Pour la suppression d'un utilisateur grace à son ID
	@DeleteMapping(path = "users/{id}")
	public ResponseEntity<String> delete(
			@PathVariable("id") @Pattern(regexp = "\\d+", message = "L'id de l'utilisateur doit être numérique") String id)
			throws UserNotFoundException {

		userServiceI.deleteUserById(Long.valueOf(id));
		return new ResponseEntity<>("Utilisateur Supprimé avec succès", HttpStatus.OK);

	}

}
