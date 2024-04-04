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
	 * Creates a new user to be stored.
	 * 
	 * @param user  The {@link User} to create
	 * @return      The response that may contain the newly created user
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
	 * Attempts to authenticate a {@link User} using its
	 * username and password by checking against its hash.
	 * 
	 * @param user  A {@link User} whose username and password
	 *              will be used as input
	 * @return      The response that may contain the stored user's data
	 */
	@PostMapping("/auth")
	public ResponseEntity<User> authenticateUser(@RequestBody User user) {
		LOG.info("POST /users/auth " + user);

		User storedUser = this.userDAO.authenticateUser(user);
		return storedUser != null ? new ResponseEntity<>(storedUser.removePassword(), HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Updates a {@link User} in the database.
	 * 
	 * @param user  The {@link User} to update
	 * @return      The response that may contain the updated user
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
	 * Attempts to proceed for checkout with a {@link User},
	 * unlocking any new products and decrementing the stock.
	 * 
	 * @param user          The {@link User} who is checking out
	 * @return              The response that may contain an unlocked {@link Product}
	 * @throws IOException  If there was an error updating products or the user
	 */
	@PostMapping("/checkout")
	public ResponseEntity<Product> doCraft(@RequestBody User user) throws IOException {
		LOG.info("POST /users/checkout " + user);

		int[] cart = user.getCart();
		Integer[] cartBoxed = Arrays.stream(cart).boxed().toArray(Integer[]::new);

		for (int i : cart) {
			Product temp = productDAO.getProduct(i);
			if (temp.getQuantity() < 1) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			} else {
				productDAO.updateProduct(temp.decrementStock());
			}
		}

		if (user.getCart().length != 2) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		Recipe recipe = craftingDAO.getRecipe(cartBoxed);
		if(recipe == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		Product result = productDAO.getProduct(recipe.getResult());

		user = user.clearCart().addToUnlocked(result.getId());
		userDAO.updateUser(user);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
