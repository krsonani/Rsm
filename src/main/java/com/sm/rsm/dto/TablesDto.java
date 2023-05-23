package com.sm.rsm.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TablesDto {

	private int tid;
	
	@Min(value = 2, message = "Capacity must be a positive value")
	private int capacity;
	
//	@NotNull(message = "Availability status is required")
//	private boolean isAvailable;
//	
}
