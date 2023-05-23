package com.sm.rsm.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sm.rsm.dao.UsersDao;
import com.sm.rsm.model.Users;

@Service
public class UsersServiceImpl implements UsersService {
	
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
