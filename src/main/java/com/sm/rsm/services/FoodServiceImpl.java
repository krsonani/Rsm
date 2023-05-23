package com.sm.rsm.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sm.rsm.dao.FoodDao;
import com.sm.rsm.model.Food;

@Repository
public class FoodServiceImpl implements FoodService {
	@Autowired
	FoodDao foodDao;
	
	@Override
	public void addFood(Food food)
	{
		foodDao.save(food);
	}
	@Override
	public void deleteFood(int id)
	{
		foodDao.deleteById(id);
	}
	@Override
	public Food updateFood(Food food)
	{
		foodDao.save(food);
		return food;
	}
	@Override
	public List<Food> getAllFood(Food food)
	{
		return foodDao.findAll();
	}
	public Optional<Food> getFoodById(int id)
	{
		return foodDao.findById(id);
	}
	
}
