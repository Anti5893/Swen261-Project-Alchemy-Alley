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
	public void testRemovePassword() {
		// Setup
		String username = "Jack";
		String password = "securePassword";
		boolean admin = true;
		int[] unlocked = { 1, 2, 3 };
		int[] cart = { 1, 2 };
		User user = new User(username, password, admin, unlocked, cart);

		// Invoke
		User newUser = user.removePassword();

		// Analyze
		assertEquals(user.getUsername(), newUser.getUsername());
		assertNull(newUser.getPassword());
		assertEquals(user.isAdmin(), newUser.isAdmin());
		assertEquals(user.getUnlocked(), newUser.getUnlocked());
		assertEquals(user.getCart(), newUser.getCart());
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
	public void testEquals() {
		// Setup
		User user1 = new User("Jack", "securePassword", false, new int[] { 1, 2, 3 }, new int[] { 1, 2 });
		User user2 = new User("Jack", "otherPassword", false, new int[] { 1, 2, 3 }, new int[] { 1, 2 });

		// Invoke & Analyze
		assertEquals(user1, user2);
	}

	@Test
	public void testNotEquals() {
		// Setup
		User user1 = new User("Jack", "securePassword", false, new int[] { 1, 2, 3 }, new int[] { 1, 2 });
		User user2 = new User("Admin", "Admin123", true, new int[0], new int[0]);

		// Invoke & Analyze
		assertNotEquals(user1, user2);
	}

	@Test
	public void testNotEqualsOtherObject() {
		// Setup
		User user = new User("Jack", "securePassword", false, new int[] { 1, 2, 3 }, new int[] { 1, 2 });
		Object object = new Object();

		// Invoke & Analyze
		assertNotEquals(user, object);
	}
	@Test
	public void testClearCart() {
		// Setup
		User user = new User("Jack", "securePassword", false, new int[] { 1, 2, 3 }, new int[] { 1, 2 });
		
		// Invoke
		user = user.clearCart();

		// Analyze
		assertEquals(2, user.getCart().length);
		assertNotEquals(new int[] { 1, 2 }, user.getCart());
	}
	@Test
	public void testaddToUnlockedGood() {
		// Setup
		User user = new User("Jack", "securePassword", false, new int[] { 1, 2, 3 }, new int[] { 1, 2 });
		
		// Invoke
		user = user.addToUnlocked(4);

		// Analyze
		assertEquals(4, user.getUnlocked().length);
		assertNotEquals(new int[] { 1, 2, 3 }, user.getUnlocked());
		assertArrayEquals(new int[] {1, 2, 3, 4 }, user.getUnlocked());
	}
	@Test
	public void testaddToUnlockedDuplicate() {
		// Setup
		User user = new User("Jack", "securePassword", false, new int[] { 1, 2, 3 }, new int[] { 1, 2 });
		
		// Invoke
		user = user.addToUnlocked(3);

		// Analyze
		assertEquals(3, user.getUnlocked().length);
		assertArrayEquals(new int[] {1, 2, 3}, user.getUnlocked());
	}
}
