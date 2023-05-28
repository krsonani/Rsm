package com.sm.rsm.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sm.rsm.model.Category;

public interface CategoryDao extends JpaRepository<Category, Integer> {
	
	Optional<Category> findByCname(String name);

}
