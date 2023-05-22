package com.sm.rsm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sm.rsm.model.Users;

public interface UsersDao extends JpaRepository<Users,Integer> {

}
