package com.sm.rsm.cntrl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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
@CrossOrigin(origins = {"*"})
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
	public ResponseEntity<Map<String,String>> addOrder(@Valid @RequestBody OrdersDto ordersDto)
	{
		Map<String,String> response = new HashMap<>();
		
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
		Map<Integer,Integer> foodItems=ordersDto.getFoodItem();
		Map<Integer,Integer> map= new LinkedHashMap<>();
		List<Food> foodList = new ArrayList<>();
		
		for(Map.Entry<Integer, Integer> entry : foodItems.entrySet()) {
			Food food = new Food();
			food.setFid(entry.getKey());
			foodList.add(food);
			map.put(food.getFid(), entry.getValue());
		}

		
		orders.setFoodList(foodList);
		orders.setFoodMap(map);
		orders.setTotalPrice(ordersDto.getTotalPrice());
		
		orders.setDates( LocalDate.now().toString() );
		
		System.out.println(orders);
		
		orderService.addOrders(orders);
		response.put("response", "Order added");
		return new ResponseEntity<>(response,HttpStatus.OK);
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
	
	@GetMapping("/updateBillStatus/{uids}")
	public ResponseEntity<Map<String,String>> updateBillStatus(@PathVariable int uids)
	{
		System.out.println("hitting api");
		
		Map<String,String> response = new HashMap<>();
		
		Users users = userService.getUsers(uids);
		
		List<Orders> orders = orderService.findByUserAndIsBillGenerated(users, false);
		for(int i=0; i<orders.size(); i++) {
			Orders order = orders.get(i);
			order.setBillGenerated(true);
			System.out.println(order);
			orderService.addOrders(order);
		}
		
		response.put("response", "Payment Done Bill Status updated");
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> onMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
	
}
