package com.sm.rsm.cntrl;

import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sm.rsm.dto.CategoryDto;
import com.sm.rsm.model.Category;
import com.sm.rsm.services.CategoryService;

import jakarta.validation.Valid;

@RestController
public class CategoryController {
	@Autowired
	CategoryService categoryService;
	
	@PostMapping("/addCategory")
	public ResponseEntity<String> addCategory(@Valid @RequestBody CategoryDto categoryDto)
	{
		System.out.println("inside");
		Category category= new Category();
		category.setCName(categoryDto.getCname()) ;
		System.out.println(category.toString());
		categoryService.addCategory(category);
		return new ResponseEntity<>("Category added",HttpStatus.OK);
	}
	
	@DeleteMapping("/removeCategory/{id}")
	public ResponseEntity<String> removeCategory(@PathVariable int id)
	{
		categoryService.deleteCategory(id);
		return new ResponseEntity<>("Category Removed",HttpStatus.OK);
	}
	@GetMapping("/getAllCategory")
	public ResponseEntity<List<Category>> getAllCategory()
	{
		return new ResponseEntity<>(categoryService.getAllCategory(),HttpStatus.OK);
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
