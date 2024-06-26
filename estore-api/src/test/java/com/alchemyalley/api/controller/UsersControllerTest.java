package com.alchemyalley.api.controller;

import com.alchemyalley.api.model.User;
import com.alchemyalley.api.model.ElementType;
import com.alchemyalley.api.model.Product;
import com.alchemyalley.api.model.Recipe;
import com.alchemyalley.api.persistence.ProductDAO;
import com.alchemyalley.api.persistence.UserDAO;
import com.alchemyalley.api.persistence.CraftingDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests the UsersController class.
 * @author Group 2
 */
@Tag("Controller-tier")
public class UsersControllerTest {

	private UserDAO userDAO;
	private UsersController usersController;
	private ProductDAO productDAO;
	private CraftingDAO craftingDAO;

	/**
	 * Creates a mock {@link UserDAO} class and injects it into
	 * a {@link UsersController} before every test is run.
	 */
	@BeforeEach
	public void setupUsersController() {
		this.userDAO = mock(UserDAO.class);
		this.craftingDAO = mock(CraftingDAO.class);
		this.productDAO = mock(ProductDAO.class);
		this.usersController = new UsersController(this.userDAO, this.productDAO, this.craftingDAO);
	}

	@Test
	public void testCreateUser() throws IOException {
		// Setup
		User user = new User("Jack", "securePassword", true, new int[] { 1, 2, 3}, new int[] { 1, 2 }).removePassword();
		when(this.userDAO.createUser(user)).thenReturn(user);

		// Invoke
		ResponseEntity<User> response = this.usersController.createUser(user);

		// Analyze
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(user, response.getBody());
	}

	@Test
	public void testCreateUserFailed() throws IOException {
		// Setup
		User user = new User("Jack", "securePassword", true, new int[] { 1, 2, 3}, new int[] { 1, 2 });
		when(this.userDAO.createUser(user)).thenReturn(null);

		// Invoke
		ResponseEntity<User> response = this.usersController.createUser(user);

		// Analyze
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
	}

	@Test
	public void testCreateUserHandleException() throws IOException {
		// Setup
		User user = new User("Jack", "securePassword", true, new int[] { 1, 2, 3}, new int[] { 1, 2 }).removePassword();
		doThrow(new IOException()).when(this.userDAO).createUser(user);

		// Invoke
		ResponseEntity<User> response = this.usersController.createUser(user);

		// Analyze
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void testAuthenticateUser() {
		// Setup
		User user = new User("Jack", "securePassword", true, new int[] { 1, 2, 3}, new int[] { 1, 2 }).removePassword();
		when(this.userDAO.authenticateUser(user)).thenReturn(user);

		// Invoke
		ResponseEntity<User> response = this.usersController.authenticateUser(user);

		// Analyze
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(user, response.getBody());
	}

	@Test
	public void testAuthenticateUserFailed() {
		// Setup
		User user = new User("Jack", "securePassword", true, new int[] { 1, 2, 3}, new int[] { 1, 2 });
		when(this.userDAO.authenticateUser(user)).thenReturn(null);

		// Invoke
		ResponseEntity<User> response = this.usersController.authenticateUser(user);

		// Analyze
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}

	@Test
	public void testUpdateUser() throws IOException{
		// Setup
		User updatedUser = new User("JackFTW", "securePassword", false, new int[] { 1 }, new int[] { 1, 2 }).removePassword();
		when(this.userDAO.updateUser(updatedUser)).thenReturn(updatedUser);

		// Invoke
		ResponseEntity<User> response = this.usersController.updateUser(updatedUser);

		// Analyze
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(updatedUser, response.getBody());
	}

	@Test
	public void testUpdateUserFailsWhenUserNotFound() throws IOException {
		// Setup
		User userToUpdate = new User("NonExistentUser", "password", false, new int[0], new int[0]);
		when(userDAO.updateUser(userToUpdate)).thenReturn(null);

		// Invoke
		ResponseEntity<User> response = usersController.updateUser(userToUpdate);

		// Analyze
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody());
	}

	@Test
	public void testUpdateUserException() throws IOException {
		// Setup
		User userToUpdate = new User("Jack", "securePassword", true, new int[] {1, 2, 3}, new int[] {1, 2}).removePassword();
		doThrow(IOException.class).when(userDAO).updateUser(userToUpdate);

		// Invoke
		ResponseEntity<User> response = usersController.updateUser(userToUpdate);

		// Analyze
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertNull(response.getBody());
	}
	@Test
	public void testDoCraftGood() throws IOException {
		// Setup
		User user = new User("Jack", "securePassword", true, new int[] {1, 2}, new int[] {1, 2});
		when(productDAO.getProduct(1)).thenReturn(new Product(1, "Fire", ElementType.FIRE, 10.0, 1, "url"));
		when(productDAO.getProduct(2)).thenReturn(new Product(2, "Fire", ElementType.FIRE, 10.0, 1, "url"));
		when(productDAO.getProduct(3)).thenReturn(new Product(3, "Water", ElementType.WATER, 10.0, 1, "url"));
		when(craftingDAO.getRecipe(new Integer[]{1, 2})).thenReturn(new Recipe(new Integer[]{1, 2}, 3));
		when(userDAO.updateUser(user.clearCart())).thenReturn(user.clearCart());

		// Invoke
		ResponseEntity<Product> response = usersController.doCraft(user);

		// Analyze
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(new Product(3, "Water", ElementType.WATER, 10.0, 1, "url"), response.getBody());
	}

	@Test
	public void testDoCraftBad() throws IOException {
		// Setup
		User user = new User("Jack", "securePassword", true, new int[] {1, 2}, new int[] {1, 2});
		when(productDAO.getProduct(1)).thenReturn(new Product(1, "Fire", ElementType.FIRE, 10.0, 1, "url"));
		when(productDAO.getProduct(2)).thenReturn(new Product(2, "Fire", ElementType.FIRE, 10.0, 1, "url"));
		when(craftingDAO.getRecipe(new Integer[]{1, 2})).thenReturn(null);

		// Invoke
		ResponseEntity<Product> response = usersController.doCraft(user);

		// Analyze
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody());
	}

	@Test
	public void testDoCraftOutOfStock() throws IOException {
		// Setup
		User user = new User("Jack", "securePassword", true, new int[] {1, 2}, new int[] {1, 2});
		when(productDAO.getProduct(1)).thenReturn(new Product(1, "Fire", ElementType.FIRE, 10.0, 0, "url"));
		when(productDAO.getProduct(2)).thenReturn(new Product(2, "Fire", ElementType.FIRE, 10.0, 1, "url"));
		when(productDAO.getProduct(3)).thenReturn(null);
		when(craftingDAO.getRecipe(new Integer[]{1, 2})).thenReturn(new Recipe(new Integer[]{1, 2}, 3));
		doThrow(IOException.class).when(userDAO).updateUser(user);

		// Invoke
		ResponseEntity<Product> response = usersController.doCraft(user);

		// Analyze
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertNull(response.getBody());
	}

	@Test
	public void testDoCraftTooFewItems() throws IOException {
		// Setup
		User user = new User("Jack", "securePassword", true, new int[] {1, 2}, new int[] {1});
		when(productDAO.getProduct(1)).thenReturn(new Product(1, "Fire", ElementType.FIRE, 10.0, 1, "url"));
		when(productDAO.getProduct(2)).thenReturn(new Product(2, "Fire", ElementType.FIRE, 10.0, 1, "url"));
		when(productDAO.getProduct(3)).thenReturn(null);
		when(craftingDAO.getRecipe(new Integer[]{1, 2})).thenReturn(new Recipe(new Integer[]{1, 2}, 3));
		doThrow(IOException.class).when(userDAO).updateUser(user);

		// Invoke
		ResponseEntity<Product> response = usersController.doCraft(user);

		// Analyze
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertNull(response.getBody());
	}

}