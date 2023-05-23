package com.sm.rsm.cntrl;

import java.util.List;

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
		
		tablesService.deleteTable(id);
		return new ResponseEntity<>("Table removed",HttpStatus.OK);
	}
	
	@GetMapping("/toggleTableStatus/{id}")
	public ResponseEntity<String> toggleTableStatus(@PathVariable int id){
		
		Tables tables = tablesService.getTableById(id);
		tables.setAvailable(!tables.isAvailable());
		tablesService.updateTable(tables);
		
		return new ResponseEntity<>("Table status altered", HttpStatus.OK);
	}
}
