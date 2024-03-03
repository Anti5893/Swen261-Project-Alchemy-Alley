package com.alchemyalley.api.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the User class.
 * @author Group 2
 */
@Tag("Model-tier")
public class UserTest {

	@Test
	public void testConstructor() {
		// Setup
		String username = "Jack";
		String password = "securePassword";
		boolean admin = true;
		int[] unlocked = { 1, 2, 3 };
		int[] cart = { 1, 2 };

		// Invoke
		User user = new User(username, password, admin, unlocked, cart);

		// Analyze
		assertEquals(username, user.getUsername());
		assertEquals(password, user.getPassword());
		assertEquals(admin, user.isAdmin());
		assertEquals(unlocked, user.getUnlocked());
		assertEquals(cart, user.getCart());
	}

	@Test
	public void testConstructorException() {
		// Setup
		String username = "";
		String password = "securePassword";
		boolean admin = true;
		int[] unlocked = { 1, 2, 3 };
		int[] cart = { 1, 2 };

		// Invoke & Analyze
		assertThrows(IllegalArgumentException.class,
				() -> new User(username, password, admin, unlocked, cart),
				"IllegalArgumentException not thrown");
	}

	@Test
	public void testToString() {
		// Setup
		String username = "Jack";
		String password = "securePassword";
		boolean admin = true;
		int[] unlocked = { 1, 2, 3 };
		int[] cart = { 1, 2 };

		User user = new User(username, password, admin, unlocked, cart);
		String expected = String.format(User.STRING_FORMAT, username, password, admin,
				Arrays.toString(unlocked), Arrays.toString(cart));

		// Invoke
		String actual = user.toString();

		// Analyze
		assertEquals(expected, actual);
	}

	@Test
	public void testSetCart()
	{
		String username = "Jack";
		String password = "securePassword";
		boolean admin = true;
		int[] unlocked = { 1, 2, 3 };
		int[] cart = { 1, 2 };

		User user = new User(username, password, admin, unlocked, cart);
		int[] newCart = {1,2,3};
		user.setCart(newCart);
		String expected = String.format(User.STRING_FORMAT, username, password, admin,
		Arrays.toString(unlocked), Arrays.toString(newCart));

		// Invoke
		String actual = user.toString();

		// Analyze
		assertEquals(expected, actual);
	}

	@Test
	public void testUpdateuser()
	{
		//Setup
		String username = "Jack";
		String password = "securePassword";
		boolean admin = true;
		int[] unlocked = { 1, 2, 3 };
		int[] cart = { 1, 2 };

		User user = new User(username, password, admin, unlocked, cart);
		int[] newCart = {1,2,3};
		int[] newUnlocked = {1};
		user.updatedUser(false,newCart,newUnlocked );
		String expected = String.format(User.STRING_FORMAT, username, password, false,
		Arrays.toString(newUnlocked), Arrays.toString(newCart));

		// Invoke
		String actual = user.toString();

		// Analyze
		assertEquals(expected, actual);
	}

}
