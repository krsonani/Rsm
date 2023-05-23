package com.sm.rsm.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sm.rsm.dao.CategoryDao;
import com.sm.rsm.model.Category;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	CategoryDao categoryDao;
	
	@Override
	public void addCategory(Category category) {
		categoryDao.save(category);
	}

	@Override
	public void deleteCategory(int id) {
		categoryDao.deleteById(id);
	}

	@Override
	public Category updateCategory(Category category) {
		categoryDao.save(category);
		return category;
	}

	@Override
	public List<Category> getAllCategory(Category category) {
		return categoryDao.findAll();
	}

	@Override
	public Optional<Category> getCategoryById(int id) {
		return categoryDao.findById(id);
	}

}
