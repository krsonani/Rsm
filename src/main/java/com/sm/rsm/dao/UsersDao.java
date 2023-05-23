package com.sm.rsm.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sm.rsm.model.Users;

public interface UsersDao extends JpaRepository<Users,Integer> {

	Optional<Users> findByEmail(String email);
	
	public boolean existsByEmail(String email);

}
