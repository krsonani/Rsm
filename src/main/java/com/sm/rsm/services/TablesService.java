package com.sm.rsm.services;

import java.util.List;
import java.util.Map;

import com.sm.rsm.model.Tables;
import com.sm.rsm.model.Users;

public interface TablesService {

	void addTable(Tables table);
	void deleteTable(int tid);
	Tables updateTable(Tables table);
	List<Tables> getAllTables();
	Tables getTableById(int id);
	
	boolean addToWaitingListForAnySittingTable(int id, int capacity);
//	void addToWaitingListForFourSittingTable(int id);
//	void addToWaitingListForEightSittingTable(int id);
	
	int autoAssignUserToTable(Tables table);
	List<Integer> autoAssignedTableId(int id);
	void displayAllStaticVariables();
	Map<Users,Integer> getAllSurplusUsers();
	void assigningWaitingUserToTable(int userId, int tableId);

	int getUserWaitingQueueCount(int id);

	void removeSurplusUserFromList(int uids,List<Integer> tableIds);

}