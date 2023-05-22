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
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int fid;
	private String fname;
	@Column(length = 1000)
	private String fimage;
	@Column(length = 1000)
	private String description;
	private double price;
	private boolean isavailable;
	@ManyToOne
	private Category category;
}
