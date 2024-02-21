package com.alchemyalley.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class User {

	static final String STRING_FORMAT = "User [id=%d, username=%s, password=%s, admin=%b, unlocked=%s";

	@JsonProperty("id") private final int id;
	@JsonProperty("username") private final String username;
	@JsonProperty("password") private final String password;
	@JsonProperty("admin") private final boolean admin;
	@JsonProperty("unlocked") private final int[] unlocked;

	public User(@JsonProperty("id") int id, @JsonProperty("username") String username,
	            @JsonProperty("password") String password, @JsonProperty("admin") boolean admin,
	            @JsonProperty("unlocked") int[] unlocked) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.admin = admin;
		this.unlocked = unlocked;
	}

	public int getId() {
		return this.id;
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

	@Override
	public String toString() {
		return String.format(STRING_FORMAT, this.id, this.username, this.password, this.admin, Arrays.toString(unlocked));
	}

}
