package com.sm.rsm.services;

import java.util.List;
import java.util.Optional;

import com.sm.rsm.model.Food;

public interface FoodService {
	public void addFood(Food food);
	public void deleteFood(int id);
	public Food updateFood(Food food);
	public List<Food> getAllFood(Food food);
	public Optional<Food> getFoodById(int id);

}
