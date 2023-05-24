package com.sm.rsm.cntrl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sm.rsm.dto.TablesDto;
import com.sm.rsm.model.Tables;
import com.sm.rsm.model.Users;
import com.sm.rsm.services.TablesService;

import jakarta.validation.Valid;

@RestController
public class TablesController {
	
	@Autowired
	private TablesService tablesService;
	
	@PostMapping("/addTable")
	public ResponseEntity<String> addTable(@Valid @RequestBody TablesDto tablesdto){
		
		Tables tables = new Tables();
		tables.setCapacity(tablesdto.getCapacity());
		tables.setAvailable(true);
		System.out.println(tables.toString());
		tablesService.addTable(tables);
		
		return new ResponseEntity<>("Table added", HttpStatus.OK);
	}
	
	@GetMapping("/getAllTables")
	public ResponseEntity<List<Tables>> getAllTables(){
		
		return new ResponseEntity<>(tablesService.getAllTables(), HttpStatus.OK);
	}
	
	@GetMapping("/removeTable/{id}")
	public ResponseEntity<String> removeTable(@PathVariable int id){
		
		tablesService.deleteTable(id)
;
		return new ResponseEntity<>("Table removed",HttpStatus.OK);
	}
	
	@PostMapping("/toggleTableStatus")
	public ResponseEntity<String> toggleTableStatus(@RequestBody List<Integer> tableIds){
		
		for(Integer id : tableIds) {
			Tables tables = tablesService.getTableById(id);
			
			tables.setAvailable(!tables.isAvailable());
			
			//call the customer from waiting queue once table becomes free - TRUE
			if(tables.isAvailable()) {
				
				// here user will be assigned a table so need to toggle the table status
				int userId = tablesService.autoAssignUserToTable(tables);
				
				if(userId!=0) {
					tables.setAvailable(false);
					tablesService.assigningWaitingUserToTable(userId, id);
				}	
			}
			
			tablesService.displayAllStaticVariables();
			
			tablesService.updateTable(tables);
		}
		
		return new ResponseEntity<>("Table status altered", HttpStatus.OK);
	}
	
	@GetMapping("/addToWaitingListForAnySittingTable/{id}/{capacity}")
	public ResponseEntity<String> addToWaitingListForAnySittingTable(@PathVariable int id, @PathVariable int capacity){
		
		tablesService.addToWaitingListForAnySittingTable(id,capacity);
		
		return new ResponseEntity<>("Added user to a WQ", HttpStatus.OK);
	}
	
//	@GetMapping("/addToWaitingListForFourSittingTable/{id}")
//	public ResponseEntity<String> addToWaitingListForFourSittingTable(@PathVariable int id){
//		
//		tablesService.addToWaitingListForFourSittingTable(id)
;
//		
//		return new ResponseEntity<>("Added to 4 sitting WQ", HttpStatus.OK);
//	}
//	
//	@GetMapping("/addToWaitingListForEightSittingTable/{id}")
//	public ResponseEntity<String> addToWaitingListForEightSittingTable(@PathVariable int id){
//		
//		tablesService.addToWaitingListForEightSittingTable(id)
;
//		
//		return new ResponseEntity<>("Added to 8 sitting WQ", HttpStatus.OK);
//	}
	

	//will keep hitting this API to check if the user has got any table or not
	@GetMapping("/isAnyTableVacantNow/{id}")
	public ResponseEntity<List<Integer>> isAnyTableVacantNow(@PathVariable int id){
		
		List<Integer> tableIds = tablesService.autoAssignedTableId(id);
		
		if(tableIds != null)
			return new ResponseEntity<>(tableIds,HttpStatus.OK);
		else
			return new ResponseEntity<>(null,HttpStatus.OK);
	}
	
	@GetMapping("/getSurplusUsersList")
	public ResponseEntity<Map<Users,Integer>> getSurplusUsersList(){
		
		Map<Users,Integer> surplusUsers = tablesService.getAllSurplusUsers();
		
		if(surplusUsers!=null)
			return new ResponseEntity<>(surplusUsers,HttpStatus.OK);
		else
			return new ResponseEntity<>(null,HttpStatus.OK);
	}
}