package com.sm.rsm.services;

import java.util.List;

import com.sm.rsm.model.Users;

public interface UsersServices {
	
	void addUsers(Users user);
	void deleteUsers(int uid);
	List<Users> getAllUsers();
	Users getUsers(int uid);
	void updateUsers(Users user);
	Users getUsersByEmail(String email);
	boolean existsByEmail(String email);
}
