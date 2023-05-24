package com.sm.rsm.cntrl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sm.rsm.dto.OrdersDto;
import com.sm.rsm.model.Food;
import com.sm.rsm.model.Orders;
import com.sm.rsm.model.Tables;
import com.sm.rsm.model.Users;
import com.sm.rsm.services.FoodService;
import com.sm.rsm.services.OrdersService;
import com.sm.rsm.services.TablesService;
import com.sm.rsm.services.UsersService;

import jakarta.validation.Valid;

@RestController
public class OrdersController {
	@Autowired
	OrdersService orderService;
	@Autowired
	TablesService tableService;
	@Autowired
	UsersService userService;
	@Autowired
	FoodService foodService;
	
	
	@PostMapping("/addOrder")
	public ResponseEntity<String> addOrder(@Valid @RequestBody OrdersDto ordersDto)
	{
		Orders orders= new Orders();
		
		Users users= new Users();
		users.setUids(ordersDto.getUserid());
		orders.setUser(users);
		
		List<Integer> listOfTables = ordersDto.getTableIds();
		List<Tables> list= new ArrayList<>();
		for(Integer tableid: listOfTables)
		{
			Tables table= new Tables();
			table.setTid(tableid);
			list.add(table);
		}
		orders.setTable(list);
		
		//need to work on this map thing
		
		Map<Integer,Integer> foodItems=ordersDto.getFoodItem();
		Map<Food,Integer> map= new HashMap<>();
		
		for(Map.Entry<Integer, Integer> entry : foodItems.entrySet()) {
			Food food = new Food();
			food.setFid(entry.getKey());
			map.put(food, entry.getValue());
		}
		orders.setFood(map);
		
/*		
		List<Integer> listOfFood = ordersDto.getFoodIds();
		List<Food> lists= new ArrayList<>();
		for(Integer foodid: listOfFood)
		{
			Food food = new Food();
			food.setFid(foodid);
			lists.add(food);
		}
		orders.setFood(lists);
*/		
		orders.setTotalPrice(ordersDto.getTotalPrice());
		
		orders.setDates( LocalDate.now().toString() );
		orderService.addOrders(orders);
		return new ResponseEntity<String>("Order added",HttpStatus.OK);
	}
	
	@GetMapping("/getOrderByUserId/{id}")
	public ResponseEntity<List<Orders>> getOrderByUserId(@PathVariable int id)
	{
		
		return new ResponseEntity<>(orderService.getOrdersByUserId(id),HttpStatus.OK);
	}
	@GetMapping("/getOrderByCurrentDate")
	public ResponseEntity<List<Orders>> getOrderByCurrentDate()
	{	
		return new ResponseEntity<>(orderService.getOrdersByDates(LocalDate.now().toString()),HttpStatus.OK);
	}
	
}
