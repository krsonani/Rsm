package com.sm.rsm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sm.rsm.model.Food;

public interface FoodDao extends JpaRepository<Food, Integer>{

}
