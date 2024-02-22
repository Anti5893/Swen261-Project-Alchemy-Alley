package com.alchemyalley.api.persistence;

import com.alchemyalley.api.model.User;

import java.io.IOException;

/**
 * Interface to interact with user data.
 * @author Group 2
 */
public interface UserDAO {

	/**
	 * Creates a new {@link User}, and saves it to disk
	 * as long as the username is not already taken.
	 *
	 * @param user          The {@link User} containing the new username and password
	 * @return              The created {@link User} instance, or {@code null} if taken
	 * @throws IOException  If there is an error saving
	 */
	User createUser(User user) throws IOException;

}
