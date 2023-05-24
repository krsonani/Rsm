package com.sm.rsm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sm.rsm.model.Orders;
import com.sm.rsm.model.Users;

public interface OrdersDao extends JpaRepository<Orders, Integer> {


	List<Orders> findByUser(Users user);
	List<Orders> findByDates(String date);
}
