package com.sm.rsm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.sm.rsm.dao.TableDao;
import com.sm.rsm.model.Table;

public class TableServiceImpl implements TableService {

	@Autowired
	private TableDao tableDao;
	
	@Override
	public void addTable(Table table) {
		tableDao.save(table);
	}

	@Override
	public void deleteTable(int tid) {
		tableDao.deleteById(tid);
	}

	@Override
	public Table updateTable(Table table) {
		return tableDao.save(table);
	}

	@Override
	public List<Table> getAllTables() {
		return tableDao.findAll();
	}

	@Override
	public Table getTableById(int id) {
		return tableDao.getReferenceById(id);
	}

}
