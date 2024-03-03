package com.alchemyalley.api.controller;

import com.alchemyalley.api.model.User;
import com.alchemyalley.api.persistence.UserDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller responsible for requests beginning with /users
 * @author Group 2
 */
@RestController
@RequestMapping("users")
public class UsersController {

	private static final Logger LOG = Logger.getLogger(UsersController.class.getName());
	private final UserDAO userDAO;

	public UsersController(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@PostMapping("")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		LOG.info("POST /users " + user);

		try {
			User newUser = this.userDAO.createUser(user);
			return newUser != null ?
					new ResponseEntity<>(newUser, HttpStatus.CREATED) :
					new ResponseEntity<>(HttpStatus.CONFLICT);
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/auth")
	public ResponseEntity<User> authenticateUser(@RequestBody User user) {
		LOG.info("POST /users/auth " + user);

		User storedUser = this.userDAO.authenticateUser(user);
		return storedUser != null ?
				new ResponseEntity<>(storedUser, HttpStatus.OK) :
				new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("/{username}")
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		LOG.info("PUT /users " + user);
		
		try {
			User updatedUser = this.userDAO.updateUser(user);
			return updatedUser != null ?
					new ResponseEntity<>(updatedUser, HttpStatus.OK) :
					new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
