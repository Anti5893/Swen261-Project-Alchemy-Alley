package com.alchemyalley.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

/**
 * Represents a User entity.
 * @author Group 2
 */
public class User {

	static final String STRING_FORMAT = "User [username=%s, password=%s, admin=%b, unlocked=%s, cart=%s";

	@JsonProperty("username") private final String username;
	@JsonProperty("password") private final String password;
	@JsonProperty("admin") private boolean admin;
	@JsonProperty("unlocked") private int[] unlocked;
	@JsonProperty("cart") private int[] cart;

	/**
	 * Creates a {@code User} with its given attributes.
	 *
	 * @param username  The username of the user
	 * @param password  The password of the user
	 * @param admin     Whether the user is an admin or not
	 * @param unlocked  The product ids the user has unlocked
	 * @param cart      The product ids that are in the user's cart
	 */
	public User(@JsonProperty("username") String username, @JsonProperty("password") String password,
	            @JsonProperty("admin") boolean admin, @JsonProperty("unlocked") int[] unlocked,
	            @JsonProperty("cart") int[] cart) throws IllegalArgumentException {
		if(username.isEmpty()) throw new IllegalArgumentException("Username cannot be empty");
		this.username = username;
		this.password = password;
		this.admin = admin;
		this.unlocked = unlocked;
		this.cart = cart;
	}

	/**
	 * Gets the username of this user.
	 *
	 * @return  The username of the user
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Gets the password of this user.
	 *
	 * @return  The password of the user
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Gets whether this user is an admin or not.
	 *
	 * @return  The administrator flag of the user
	 */
	public boolean isAdmin() {
		return this.admin;
	}

	/**
	 * Gets all product ids that this user has unlocked.
	 *
	 * @return  An array of integers holding the unlocked product ids
	 */
	public int[] getUnlocked() {
		return this.unlocked;
	}

	/**
	 * Gets the product ids currently in this user's cart.
	 *
	 * @return  An array of integers holding the cart's product ids
	 */
	public int[] getCart() {
		return this.cart;
	}

	/**
	 * The string representation of a user.
	 *
	 * @return  Its string representation, including fields
	 */
	@Override
	public String toString() {
		return String.format(STRING_FORMAT, this.username, this.password, this.admin,
				Arrays.toString(this.unlocked), Arrays.toString(this.cart));
	}

}
