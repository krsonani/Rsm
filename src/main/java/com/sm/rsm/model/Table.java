package com.sm.rsm.model;

import org.antlr.v4.runtime.misc.NotNull;

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
public class Table {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int tid;
	private int capacity;
	private boolean isAvailable;
	
}
