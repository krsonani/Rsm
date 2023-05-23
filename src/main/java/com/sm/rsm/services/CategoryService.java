package com.sm.rsm.services;

import java.util.List;
import java.util.Optional;

import com.sm.rsm.model.Category;

public interface CategoryService {
	public void addCategory(Category category);
	public void deleteCategory(int id);
	public Category updateCategory(Category category);
	public List<Category> getAllCategory();
	public Category getCategoryById(int id);
	
}
