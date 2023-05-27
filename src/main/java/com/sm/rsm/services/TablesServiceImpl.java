package com.sm.rsm.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sm.rsm.dao.TablesDao;
import com.sm.rsm.dao.UsersDao;
import com.sm.rsm.model.Tables;
import com.sm.rsm.model.Users;

@Repository
public class TablesServiceImpl implements TablesService {

	public static Queue<Integer> waitingListForTwoSittingTable = new LinkedList<>();
	public static Queue<Integer> waitingListForFourSittingTable = new LinkedList<>();
	public static Queue<Integer> waitingListForEightSittingTable = new LinkedList<>();
	public static Map<Integer, List<Integer>> userAutoAssignedTableId = new LinkedHashMap<>();
	public static Map<Users, Integer> waitingListForSurplusUsers = new LinkedHashMap<>();
	
	@Autowired
	private TablesDao tableDao;
	
	@Autowired
	private UsersDao usersDao;
	
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
		return tableDao.getReferenceById(id)
;
	}

	@Override
	public void addToWaitingListForAnySittingTable(int id, int capacity) {
		
		if(capacity<=2)
			waitingListForTwoSittingTable.add(id)
;
		else if(capacity<=4)
			waitingListForFourSittingTable.add(id)
;
		else if(capacity<=8)
			waitingListForEightSittingTable.add(id)
;
		else
			waitingListForSurplusUsers.put(usersDao.getReferenceById(id)
, capacity);
			System.out.println(waitingListForSurplusUsers);
	}

//	@Override
//	public void addToWaitingListForFourSittingTable(int id) {
//		
//		waitingListForFourSittingTable.add(id)
;
//	}
//
//	@Override
//	public void addToWaitingListForEightSittingTable(int id) {
//		
//		waitingListForEightSittingTable.add(id)
;
//	}

	@Override
	public int autoAssignUserToTable(Tables table) {
		
		if(table.getCapacity()==2) {
			if(!waitingListForTwoSittingTable.isEmpty()) {
				return waitingListForTwoSittingTable.poll();
			}
		}
		else if(table.getCapacity()==4){
			if(!waitingListForFourSittingTable.isEmpty()) {
				return waitingListForFourSittingTable.poll();
			}
		}
		else {
			if(!waitingListForEightSittingTable.isEmpty()) {
				return waitingListForEightSittingTable.poll();
			}
		}	
		return 0;
	}
	
	@Override
	public void assigningWaitingUserToTable(int userId, int tableId) {
		
		List<Integer> list = new ArrayList<>();
		
		if(userAutoAssignedTableId.containsKey(userId)) {
			
			list.addAll(userAutoAssignedTableId.get(userId));
			list.add(tableId);
			
			userAutoAssignedTableId.put(userId, list);
		}
		else {
			list.add(tableId);
			userAutoAssignedTableId.put(userId, list);
		}
	}

	@Override
	public List<Integer> autoAssignedTableId(int id) {
		
		if(!userAutoAssignedTableId.isEmpty()) {
			
			if(userAutoAssignedTableId.containsKey(id)) {
				
				return userAutoAssignedTableId.remove(id);
			}
		}
		
		return null;
	}
	
	@Override
	public int getUserWaitingQueueCount(int id) {
		
		if(waitingListForTwoSittingTable.contains(id)) {
			return waitingListForTwoSittingTable.size();
		}
		else if(waitingListForFourSittingTable.contains(id)) {
			return waitingListForFourSittingTable.size();
		}
		else
			return waitingListForEightSittingTable.size();
	}
	
	@Override
	public Map<Users,Integer> getAllSurplusUsers(){
		
		return waitingListForSurplusUsers;
	}

	@Override
	public void displayAllStaticVariables() {
		System.out.println(waitingListForTwoSittingTable);
		System.out.println(waitingListForFourSittingTable);
		System.out.println(waitingListForEightSittingTable);
		System.out.println(userAutoAssignedTableId);
		System.out.println(waitingListForSurplusUsers);
	}

	@Override
	public void removeSurplusUserFromList(int uids,List<Integer> tableIds) {
		waitingListForSurplusUsers.remove(usersDao.getReferenceById(uids));
		userAutoAssignedTableId.put(uids, tableIds);
		System.out.println("-------------------Removed------------------------------");
		System.out.println(waitingListForSurplusUsers);
		System.out.println(userAutoAssignedTableId);
	}	
}