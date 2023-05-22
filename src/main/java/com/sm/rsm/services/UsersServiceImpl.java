package com.sm.rsm.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.sm.rsm.dao.UsersDao;
import com.sm.rsm.model.Users;

public class UsersServiceImpl implements UsersServices {
	
	@Autowired
	private UsersDao userDao;

	@Override
	public void addUsers(Users user) {
		userDao.save(user);
		
	}

	@Override
	public void deleteUsers(int uid) {
		userDao.deleteById(uid);
		
	}

	@Override
	public Users getUsers(int uid) {
		Optional<Users> op=userDao.findById(uid);
		return op.get();
	}

	@Override
	public List<Users> getAllUsers() {
		
		return userDao.findAll();
	}



}
