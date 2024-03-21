package com.alchemyalley.api.persistence;

import com.alchemyalley.api.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests the UserFileDAO class.
 * @author Group 2
 */
@Tag("Persistence-tier")
public class UserFileDAOTest {

	private ObjectMapper objectMapper;
	private UserFileDAO userFileDAO;

	@BeforeEach
	public void setupUserFileDAO() throws IOException {
		this.objectMapper = mock(ObjectMapper.class);
		when(this.objectMapper.readValue(new File("doesnt_matter.txt"), User[].class)).thenReturn(new User[] {
				new User("Jack", "$2a$10$5XZoZ1Tx0G83U/Mwbro3M.UvGo/0WGhjPIzsbk1KJHwsWi57vi7v2", true, new int[] { 1, 2, 3 }, new int[] { 1, 2 })
		});
		this.userFileDAO = new UserFileDAO("doesnt_matter.txt", this.objectMapper);
	}

	@Test
	public void testConstructorException() throws IOException {
		// Setup
		doThrow(new IOException()).when(this.objectMapper).readValue(new File("doesnt_matter.txt"), User[].class);

		// Invoke & Analyze
		assertThrows(IOException.class, () -> new UserFileDAO("doesnt_matter.txt", this.objectMapper), "IOException not thrown");
	}

	@Test
	public void testSaveException() throws IOException {
		// Setup
		User user = new User("Jacky", "securePassword123", true, new int[] { 1, 2, 3 }, new int[] { 1, 2 });
		doThrow(new IOException()).when(this.objectMapper).writeValue(any(File.class), any(User[].class));

		// Invoke & Analyze
		assertThrows(IOException.class, () -> this.userFileDAO.createUser(user), "IOException not thrown");
	}

	@Test
	public void testCreateUser() {
		// Setup
		User user = new User("Jacky", "securePassword123", false, new int[0], new int[0]);

		// Invoke
		User actual = assertDoesNotThrow(() -> this.userFileDAO.createUser(user), "Unknown exception occurred");

		// Analyze
		assertNotNull(actual);
		assertEquals(user, actual);
	}

	@Test
	public void testCreateFailed() {
		// Setup
		User user = new User("Jack", "securePassword123", true, new int[] { 1, 2, 3 }, new int[] { 1, 2 });

		// Invoke
		User actual = assertDoesNotThrow(() -> this.userFileDAO.createUser(user), "Unknown exception occurred");

		// Analyze
		assertNull(actual);
	}

	@Test
	public void testAuthenticateUser() {
		// Setup
		User user = new User("Jack", "securePassword123", false, null, null);

		// Invoke
		User actual = this.userFileDAO.authenticateUser(user);

		// Analyze
		assertNotNull(actual);
		assertEquals(user.getUsername(), actual.getUsername());
	}

	@Test
	public void testAuthenticateUserFailedUsername() {
		// Setup
		User user = new User("Jacky", "securePassword123", false, null, null);

		// Invoke
		User actual = this.userFileDAO.authenticateUser(user);

		// Analyze
		assertNull(actual);
	}

	@Test
	public void testAuthenticateUserFailedPassword() {
		// Setup
		User user = new User("Jack", "wrongPassword", false, null, null);

		// Invoke
		User actual = this.userFileDAO.authenticateUser(user);

		// Analyze
		assertNull(actual);
	}

	@Test
	public void testUpdateUserSuccess() throws IOException {
		// Setup
   		User user = new User("Jack", null, true, new int[] { 4, 5, 6 }, new int[] { 3, 4 });

    	// Invoke
   		User result = userFileDAO.updateUser(user);

   		// Analyze
    	assertNotNull(result);
		assertEquals(user, result);
	}

	@Test
	public void testUpdateUserNotFound() throws IOException {
		// Setup
		User nonExistentUser = new User("NonExistentUser", null, false, new int[0], new int[0]);

		// Invoke
		User result = userFileDAO.updateUser(nonExistentUser);

		// Analyze
		assertNull(result, "Update operation should return null for non-existent user.");
	}

}
