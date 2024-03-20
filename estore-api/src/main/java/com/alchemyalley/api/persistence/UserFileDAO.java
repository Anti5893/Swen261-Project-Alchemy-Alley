package com.alchemyalley.api.persistence;

import com.alchemyalley.api.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of {@link UserDAO} that uses JSON.
 * @author Group 2
 */
@Component
public class UserFileDAO implements UserDAO {

	private final Map<String, User> users;
	private final String fileName;
	private final ObjectMapper objectMapper;

	/**
	 * Creates an instance of this DAO over a JSON file.
	 *
	 * @param fileName      The file to load.
	 * @param objectMapper  The object mapper.
	 * @throws IOException  If there is an error reading from disk
	 */
	public UserFileDAO(@Value("${users.file}") String fileName, ObjectMapper objectMapper) throws IOException {
		this.users = new HashMap<>();
		this.fileName = fileName;
		this.objectMapper = objectMapper;
		load();
	}

	/**
	 * Loads the JSON file on disk and stores it in a map.
	 *
	 * @throws IOException  If there is an error reading from disk
	 */
	private void load() throws IOException {
		User[] users = this.objectMapper.readValue(new File(this.fileName), User[].class);

		for(User user : users) {
			this.users.put(user.getUsername(), user);
		}
	}

	/**
	 * Saves the map to disk as a JSON file.
	 *
	 * @throws IOException  If there is an error saving to disk
	 */
	private void save() throws IOException {
		User[] users = this.users.values().toArray(new User[0]);
		this.objectMapper.writeValue(new File(this.fileName), users);
	}

	public User createUser(User user) throws IOException {
		synchronized(this.users) {
			if(this.users.containsKey(user.getUsername())) return null;

			String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
			User newUser = new User(user.getUsername(), hashed, false, new int[0], new int[0]);
			this.users.put(user.getUsername(), newUser);
			save();

			return newUser;
		}
	}

	public User authenticateUser(User user) {
		User storedUser = this.users.getOrDefault(user.getUsername(), null);
		if(storedUser == null) return null;

		return BCrypt.checkpw(user.getPassword(), storedUser.getPassword()) ? storedUser : null;
	}

	/**
	 * Updates the given user.
	 * @param user: user that is being altered to new data
	 * @return The updated User.
	 */
	public User updateUser(User user) throws IOException {
		synchronized(this.users) {
			User storedUser = this.users.getOrDefault(user.getUsername(), null);
			if(storedUser == null) return null;
			User updatedUser = new User(storedUser.getUsername(), storedUser.getPassword(), storedUser.isAdmin(), user.getUnlocked(), user.getCart());
			this.users.put(storedUser.getUsername(), updatedUser);
			save();
			return updatedUser;
		}
	}

}
