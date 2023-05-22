package com.sm.rsm.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tables {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int tid;
	private int capacity;
	private boolean isavailable;
	
}
