package com.sm.rsm.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Food {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int fId;
	private String fName;
	@Column(length = 3)
	private String fImage;
	
	private String description;
	private double price;
	private boolean isAvailable;
	@ManyToOne
	private Category category;
}
