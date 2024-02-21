package com.alchemyalley.api.controller;

import com.alchemyalley.api.model.User;
import com.alchemyalley.api.persistence.UserDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Logger;

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

}
