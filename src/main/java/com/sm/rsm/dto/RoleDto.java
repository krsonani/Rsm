package com.sm.rsm.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

	private int rid;
	
	@NotBlank(message = "Role Type is required")
	private String type; 
	
}
