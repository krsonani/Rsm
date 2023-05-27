package com.sm.rsm.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sm.rsm.model.Orders;
import com.sm.rsm.model.Users;

public interface OrdersDao extends JpaRepository<Orders, Integer> {


	List<Orders> findByUser(Users user);
	List<Orders> findByDates(String date);
	Optional<List<Orders>>findByUserAndIsBillGenerated(Users user, boolean status);
	
//	@Query("UPDATE Orders o SET is_bill_generated=1 where u.oid:oid")
//	String updateBillStatus(@)
}
