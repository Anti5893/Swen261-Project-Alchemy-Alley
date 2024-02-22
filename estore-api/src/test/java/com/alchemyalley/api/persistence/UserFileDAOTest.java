package com.alchemyalley.api.persistence;

import com.alchemyalley.api.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
		when(this.objectMapper.readValue(new File("doesnt_matter.txt"), User[].class)).thenReturn(new User[0]);
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
	public void testCreateUser() {
		// Setup
		User user = new User("Jack", "securePassword", true, new int[] { 1, 2, 3 }, new int[] { 1, 2 });

		// Invoke
		User actual = assertDoesNotThrow(() -> this.userFileDAO.createUser(user), "Unknown exception occurred");

		// Analyze
		assertNotNull(actual);
		assertEquals(user.getUsername(), actual.getUsername());
	}

	@Test
	public void testSaveException() throws IOException {
		// Setup
		User user = new User("Jack", "securePassword", true, new int[] { 1, 2, 3 }, new int[] { 1, 2 });
		doThrow(new IOException()).when(this.objectMapper).writeValue(any(File.class), any(User[].class));

		// Invoke & Analyze
		assertThrows(IOException.class, () -> this.userFileDAO.createUser(user), "IOException not thrown");
	}

}
