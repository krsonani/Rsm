package com.sm.rsm.services;

import java.util.List;

import com.sm.rsm.model.Tables;

public interface TablesService {

	void addTable(Tables table);
	void deleteTable(int tid);
	Tables updateTable(Tables table);
	List<Tables> getAllTables();
	Tables getTableById(int id);
	
}
