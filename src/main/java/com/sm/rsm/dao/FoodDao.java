package com.sm.rsm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sm.rsm.model.Category;
import com.sm.rsm.model.Food;

public interface FoodDao extends JpaRepository<Food, Integer>{

	List<Food> findByCategory(Category category);
	
}
