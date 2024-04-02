package com.alchemyalley.api.controller;

import com.alchemyalley.api.model.Product;
import com.alchemyalley.api.model.Recipe;
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
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.alchemyalley.api.persistence.CraftingDAO;
import com.alchemyalley.api.persistence.ProductDAO;

/**
 * Controller responsible for requests beginning with /users
 * 
 * @author Group 2
 */
@RestController
@RequestMapping("users")
public class UsersController {

	private static final Logger LOG = Logger.getLogger(UsersController.class.getName());
	private final UserDAO userDAO;
	private final ProductDAO productDAO;
	private final CraftingDAO craftingDAO;

	public UsersController(UserDAO userDAO, ProductDAO productDAO, CraftingDAO craftingDAO) {
		this.userDAO = userDAO;
		this.productDAO = productDAO;
		this.craftingDAO = craftingDAO;
	}

	/**
	 * Creates a user, returns user with password stripped
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping("")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		LOG.info("POST /users " + user);

		try {
			User newUser = this.userDAO.createUser(user);
			return newUser != null ? new ResponseEntity<>(newUser.removePassword(), HttpStatus.CREATED)
					: new ResponseEntity<>(HttpStatus.CONFLICT);
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Authenticates a user, removes password and returns authenticated user
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping("/auth")
	public ResponseEntity<User> authenticateUser(@RequestBody User user) {
		LOG.info("POST /users/auth " + user);

		User storedUser = this.userDAO.authenticateUser(user);
		return storedUser != null ? new ResponseEntity<>(storedUser.removePassword(), HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Updates a user with the same name
	 * 
	 * @param user
	 * @return
	 */
	@PutMapping("")
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		LOG.info("PUT /users " + user);

		try {
			User updatedUser = this.userDAO.updateUser(user);
			return updatedUser != null ? new ResponseEntity<>(updatedUser.removePassword(), HttpStatus.OK)
					: new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Handles the checkout process for a user, unlocking new products and
	 * decrementing stock
	 * 
	 * @param user
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/checkout")
	public ResponseEntity<Product> doCraft(@RequestBody User user) throws IOException {
		LOG.info("POST /users/checkout " + user);

		if (user.getCart().length != 2) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		int[] cart = user.getCart();
		Integer[] cartBoxed = Arrays.stream(cart).boxed().toArray(Integer[]::new);

		Recipe recipe = craftingDAO.getRecipe(cartBoxed);
		if(recipe == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		Product result = productDAO.getProduct(recipe.getResult());

		for (int i : cart) {
			Product temp = productDAO.getProduct(i);
			if (temp.getQuantity() < 1) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			} else {
				productDAO.updateProduct(temp.decrementStock());
			}
		}

		user = user.clearCart().addToUnlocked(result.getId());
		userDAO.updateUser(user);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
