package com.sm.rsm.services;

import java.util.List;

import com.sm.rsm.model.Table;

public interface TableService {

	void addTable(Table table);
	void deleteTable(int tid);
	Table updateTable(Table table);
	List<Table> getAllTables();
	Table getTableById(int id);
	
}
