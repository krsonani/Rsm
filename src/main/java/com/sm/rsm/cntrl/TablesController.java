package com.sm.rsm.cntrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sm.rsm.dto.OrdersDto;
import com.sm.rsm.dto.TablesDto;
import com.sm.rsm.model.Tables;
import com.sm.rsm.model.Users;
import com.sm.rsm.services.TablesService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = {"*"})
public class TablesController {
	Map<String,String> response = new HashMap<>();
	
	@Autowired
	private TablesService tablesService;
	
	@Secured({ "ROLE_MANAGER"})
	@PostMapping("/addTable")
	public ResponseEntity<?> addTable(@Valid @RequestBody TablesDto tablesdto){
		

		for(int i=0;i<tablesdto.getQuantity();i++)
		{
			
			Tables tables = new Tables();
			tables.setCapacity(tablesdto.getCapacity());
			tables.setAvailable(true);
			System.out.println(tables.toString());
			tablesService.addTable(tables);
		}
		response.put("msg", "Table Added");
		response.put("status", "200");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@Secured({ "ROLE_CUSTOMER" , "ROLE_MANAGER"})
	@GetMapping("/getAllTables")
	public ResponseEntity<List<Tables>> getAllTables(){
		
		return new ResponseEntity<>(tablesService.getAllTables(), HttpStatus.OK);
	}
	
	@Secured({ "ROLE_MANAGER"})
	@GetMapping("/removeTable/{id}")
	public ResponseEntity<?> removeTable(@PathVariable int id){
		
		tablesService.deleteTable(id);
		response.put("msg", "Table Removed");
		response.put("status", "200");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
//	@Secured({ "ROLE_MANAGER"})
	@PostMapping("/toggleTableStatus")
	public ResponseEntity<?> toggleTableStatus(@RequestBody List<Integer> tableIds){
		
//		List<Integer> tableIds = orderDto.getTableIds();
		System.out.println(tableIds);
		
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
			System.out.println(tables);
		}
		response.put("msg", "Table status altered");
		response.put("status", "200");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/addToWaitingListForAnySittingTable/{id}/{capacity}")
	public ResponseEntity<?> addToWaitingListForAnySittingTable(@PathVariable int id, @PathVariable int capacity){
		
		tablesService.addToWaitingListForAnySittingTable(id,capacity);
		
		response.put("msg", "Added user to a WQ");
		response.put("status", "200");
		return new ResponseEntity<>(response, HttpStatus.OK);
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
	

	//will keep hitting this API to check if the user has got any table or not----------------------------------------------------------------------------------
	@GetMapping("/isAnyTableVacantNow/{id}")
	public ResponseEntity<?> isAnyTableVacantNow(@PathVariable int id){
		
		System.out.println("i am being hit by frontend.");
		
		List<Integer> tableIds = tablesService.autoAssignedTableId(id);
		
		Map<String, List<Integer>> temp = new HashMap<>();
		
		if(tableIds != null) {
			
			temp.put("Table found", tableIds);
			return new ResponseEntity<>(temp,HttpStatus.OK);
		}
		else {
			
			//to get the waiting queue count for each table
			tableIds = new ArrayList<>();
			tableIds.add(tablesService.getUserWaitingQueueCount(id));
			
			temp.put("Table not found", tableIds );
			return new ResponseEntity<>(temp,HttpStatus.OK);
		}
	}
	
	@GetMapping("/getSurplusUsersList")
	public Map<Integer,Integer> getSurplusUsersList(){
		
		Map<Users,Integer> surplusUsers = tablesService.getAllSurplusUsers();
		Map<Integer,Integer> map = new HashMap<>();
		
		for(Map.Entry<Users, Integer> entry : surplusUsers.entrySet()) {
			map.put(entry.getKey().getUids(),entry.getValue());
		}
		
		System.out.println("Hii"+map.toString());
		if(map.size()!=0)
			return map;
		else
			return map;
	}
	
	@PostMapping("/handleTable/{uids}")
	public ResponseEntity<?> handleTable(@RequestBody List<Integer> tableIds, @PathVariable int uids)
	{
		System.out.println("UID :"+uids);
		System.out.println("TableIds :"+tableIds.toString());
		for(Integer tableId : tableIds)
		{
			Tables table=tablesService.getTableById(tableId);
			table.setAvailable(false);
			tablesService.updateTable(table);
		}
		tablesService.removeSurplusUserFromList(uids,tableIds);
		response.put("msg", "Table status altered");
		response.put("status", "200");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> onMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
}