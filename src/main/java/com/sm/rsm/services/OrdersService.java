package com.sm.rsm.services;

import java.util.List;
import java.util.Optional;

import com.sm.rsm.model.Orders;
import com.sm.rsm.model.Users;

public interface OrdersService {
	public void addOrders(Orders order);
	public List<Orders> getAllOrders();
	public List<Orders> getOrdersByUserId(int id);
	public Optional<Orders> getOrdersById(int id);
	public List<Orders> getOrdersByDates(String date);
	List<Orders> findByUserAndIsBillGenerated(Users user, boolean status);
}
