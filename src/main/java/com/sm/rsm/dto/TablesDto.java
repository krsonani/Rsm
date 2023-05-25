package com.sm.rsm.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TablesDto {
   
	@Min(value=1, message = "quantity muse be greater then zero")
	private int quantity;
	
	@Min(value = 2, message = "Capacity must be a positive value")
	private int capacity;
		
}
