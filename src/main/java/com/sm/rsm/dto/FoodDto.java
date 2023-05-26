package com.sm.rsm.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodDto {

	private int fid;
	
	@NotBlank(message = "Food name is required")
	private String fname;
	
	@NotBlank(message = "Image is required")
	private String fimage;
	
	@NotBlank(message = "Description is required")
	private String description;
	
	@Positive(message = "Price must be a positive value")
	private double price;
	
	private int categoryId;
	
}
