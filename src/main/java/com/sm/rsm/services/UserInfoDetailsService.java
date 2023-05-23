package com.sm.rsm.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sm.rsm.dao.UsersDao;
import com.sm.rsm.model.Users;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserInfoDetailsService implements UserDetailsService {

	@Autowired
	private UsersDao userdao;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
	   
		Users user = userdao.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("Invalid Email id"));
		System.out.println(user.getEmail()+" "+user.getRole());
		return	new UserInfoUserDetails(user);
		
		 
	}

}