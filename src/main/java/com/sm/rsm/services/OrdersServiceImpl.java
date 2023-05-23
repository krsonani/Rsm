package com.sm.rsm.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.sm.rsm.dao.OrdersDao;
import com.sm.rsm.model.Orders;
import com.sm.rsm.model.Users;

@Repository
public class OrdersServiceImpl implements OrdersService {
	@Autowired
	OrdersDao ordersDao;
	@Autowired
	UsersService usersService;

	@Override
	public void addOrders(Orders order) {
		ordersDao.save(order);
	}

	@Override
	public List<Orders> getAllOrders() {
		return ordersDao.findAll();
	}

	@Override
	public List<Orders> getOrdersByUserId(int id) {
		Users user=usersService.getUsers(id);
		return ordersDao.findByUser(user);
	}

	@Override
	public Optional<Orders> getOrdersById(int id) {
		// TODO Auto-generated method stub
		return ordersDao.findById(id);
	}

	@Override
	public List<Orders> getOrdersByDates(String date) {
		// TODO Auto-generated method stub
		return ordersDao.findByDates(date);
	}

}
