package com.alchemyalley.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class User {

	static final String STRING_FORMAT = "User [username=%s, password=%s, admin=%b, unlocked=%s, cart=%s";

	@JsonProperty("username") private final String username;
	@JsonProperty("password") private final String password;
	@JsonProperty("admin") private final boolean admin;
	@JsonProperty("unlocked") private final int[] unlocked;
	@JsonProperty("cart") private final int[] cart;

	public User(@JsonProperty("username") String username, @JsonProperty("password") String password,
	            @JsonProperty("admin") boolean admin, @JsonProperty("unlocked") int[] unlocked,
	            @JsonProperty("cart") int[] cart) {
		this.username = username;
		this.password = password;
		this.admin = admin;
		this.unlocked = unlocked;
		this.cart = cart;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public boolean isAdmin() {
		return this.admin;
	}

	public int[] getUnlocked() {
		return this.unlocked;
	}

	public int[] getCart() {
		return this.cart;
	}

	@Override
	public String toString() {
		return String.format(STRING_FORMAT, this.username, this.password, this.admin,
				Arrays.toString(this.unlocked), Arrays.toString(this.cart));
	}

}
