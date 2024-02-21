package com.alchemyalley.api.persistence;

import com.alchemyalley.api.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserFileDAO implements UserDAO {

	private final Map<String, User> users;
	private final String fileName;
	private final ObjectMapper objectMapper;

	public UserFileDAO(@Value("${users.file}") String fileName, ObjectMapper objectMapper) throws IOException {
		this.users = new HashMap<>();
		this.fileName = fileName;
		this.objectMapper = objectMapper;
		load();
	}

	private void load() throws IOException {
		User[] users = this.objectMapper.readValue(new File(this.fileName), User[].class);

		for(User user : users) {
			this.users.put(user.getUsername(), user);
		}
	}

	private void save() throws IOException {
		User[] users = this.users.values().toArray(new User[0]);
		this.objectMapper.writeValue(new File(this.fileName), users);
	}

	public User createUser(User user) throws IOException {
		if(this.users.containsKey(user.getUsername())) return null;

		User newUser = new User(user.getUsername(), user.getPassword(), false, new int[] {}, new int[] {});
		this.users.put(user.getUsername(), newUser);
		save();

		return newUser;
	}

}
