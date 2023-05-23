package com.sm.rsm.dto;

import org.springframework.format.annotation.NumberFormat;

import com.sm.rsm.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
	private String fName;
	
	@NotBlank(message = "Image is required")
	private String fImage;
	
	@NotBlank(message = "Description is required")
	private String description;
	
	@Positive(message = "Price must be a positive value")
	private double price;
	
	@NotNull(message = "Availability status is required")
	private boolean isAvailable;
	
	@NotNull(message = "Category is required")
	private Category category;
	
	
}
