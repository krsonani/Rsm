package com.sm.rsm.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.sm.rsm.dao.CategoryDao;
import com.sm.rsm.model.Category;

@Repository
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	CategoryDao categoryDao;
	
	@Override
	public void addCategory(Category category) {
		categoryDao.save(category);
	}

	@Override
	public void deleteCategory(int id) {
		categoryDao.deleteById(id)
;
	}

	@Override
	public Category updateCategory(Category category) {
		categoryDao.save(category);
		return category;
	}

	@Override
	public List<Category> getAllCategory() {
		return categoryDao.findAll();
	}

	@Override
	public Category getCategoryById(int id) {
		return categoryDao.findById(id).get();
	}

	@Override
	public boolean getCategoryExistanceByName(String name) {
		Optional<Category> op= categoryDao.findByCname(name);
		
		if(op.isPresent())
		{
			return true;
		}
		return false;
	}

}