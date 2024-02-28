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

	/**
	 * Checks whether the provided user's credentials
	 * align with an existing stored {@link User}.
	 *
	 * @param user  The provided user credentials
	 * @return      The stored {@link User} instance,
	 *              or {@code null} if invalid
	 */
	User authenticateUser(User user);

    User updateUser(User user) throws IOException;

}
