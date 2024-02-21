package com.alchemyalley.api.persistence;

import com.alchemyalley.api.model.User;

import java.io.IOException;

public interface UserDAO {

	User createUser(User user) throws IOException;

}
