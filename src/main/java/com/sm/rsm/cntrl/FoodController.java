package com.sm.rsm.cntrl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sm.rsm.dao.FoodDao;
import com.sm.rsm.dto.FoodDto;
import com.sm.rsm.model.Category;
import com.sm.rsm.model.Food;
import com.sm.rsm.services.CategoryService;
import com.sm.rsm.services.FoodService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/food")
public class FoodController {

	@Autowired
	FoodService foodService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	FoodDao foodDao;
	
	@PostMapping("/addFood")
	public ResponseEntity<Object> addFood(@Valid @RequestBody FoodDto foodDto){
		
		Food food = new Food();
		food.setFName(foodDto.getFname());
		food.setFImage(foodDto.getFimage());
		food.setDescription(foodDto.getDescription());
		food.setPrice(foodDto.getPrice());
		food.setPrice(foodDto.getPrice());
		food.setAvailable(true);
		
		Category category = categoryService.getCategoryById(foodDto.getCategoryId());
		
		food.setCategory(category);
		foodService.addFood(food);
		
		return new ResponseEntity<>("Food Added Successfully\n" + food, HttpStatus.OK);
	}
	
	@GetMapping("/getAllFood")
	public ResponseEntity<Object> getAllFood(){
		return new ResponseEntity<>(foodService.getAllFood(), HttpStatus.OK);
	}
	
	@PutMapping("/updateFood/{fid}")
	public ResponseEntity<String> updateFood(@PathVariable int fid, @Valid @RequestBody FoodDto foodDto){
		
		Food food = foodService.getFoodById(fid);
		food.setFName(foodDto.getFname());
		food.setFImage(foodDto.getFimage());
		food.setDescription(foodDto.getDescription());
		food.setPrice(foodDto.getPrice());
		food.setPrice(foodDto.getPrice());
//		food.setAvailable(foodDto.isAvailable());
		
		Category category = categoryService.getCategoryById(foodDto.getCategoryId());
		
		food.setCategory(category);
		foodService.updateFood(food);
		
		return new ResponseEntity<>("Food updated", HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteFood/{id}")
	public ResponseEntity<Object> deleteFood(@PathVariable int fid){
		foodService.deleteFood(fid);
		return new ResponseEntity<>("Food Deleted", HttpStatus.OK);
	}
	
	@GetMapping("/getFoodByCategory/{cid}")
	public ResponseEntity<Object> findFoodByCategoryId(@PathVariable int cid){
		
		Category category = new Category();
		category.setCid(cid);
		
		return new ResponseEntity<>(foodDao.findByCategory(category), HttpStatus.OK);
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
