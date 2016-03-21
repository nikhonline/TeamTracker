package com.dao;

import java.util.List;

import com.dto.CustomUser;
import com.model.User;

/**
 * Only for the Authentication details
 */
public interface UserTransactionsDao {
	CustomUser getUserByUserName(String username);
	List<User> getAllUsers();
	Boolean createUser(User user);
	Boolean updateUser(User user);
	Boolean deleteUser(String username);
	Boolean changeUserPwd(String username, String pwd);
}
