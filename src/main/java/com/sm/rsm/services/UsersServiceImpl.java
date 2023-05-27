package com.sm.rsm.services;

import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;



import com.sm.rsm.dao.UsersDao;
import com.sm.rsm.dto.UsersDto;
import com.sm.rsm.model.Users;

@Repository
public class UsersServiceImpl implements UsersService {
	@Autowired
	private UsersDao userDao;
	@Autowired
	private PasswordEncoder encorder;

	@Override
	public void addUsers(Users user,UsersDto userD) {
		user.setEmail(userD.getEmail());
		user.setName(userD.getName());
 		user.setPassword(encorder.encode(userD.getPassword()));
 		user.setPhoneNum(userD.getPhoneNum());
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

	@Override
	public void updateUsers(Users user) {
		userDao.save(user);
		
	}

	@Override
	public Users getUsersByEmail(String email) {
		
		return userDao.findByEmail(email).get();
	}
	
	@Override
	public boolean existsByEmail(String email) {
		return userDao.existsByEmail(email);
	}

	@Override
	public Users fetchUsersById(int id) {
		
		return userDao.findById(id).get();
	}


}
