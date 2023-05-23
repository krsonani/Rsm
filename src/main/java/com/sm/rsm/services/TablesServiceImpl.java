package com.sm.rsm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sm.rsm.dao.TablesDao;
import com.sm.rsm.model.Tables;

@Repository
public class TablesServiceImpl implements TablesService {

	@Autowired
	private TablesDao tableDao;
	
	@Override
	public void addTable(Tables table) {
		tableDao.save(table);
	}

	@Override
	public void deleteTable(int tid) {
		tableDao.deleteById(tid);
	}

	@Override
	public Tables updateTable(Tables table) {
		return tableDao.save(table);
	}

	@Override
	public List<Tables> getAllTables() {
		return tableDao.findAll();
	}

	@Override
	public Tables getTableById(int id) {
		return tableDao.getReferenceById(id);
	}

}
