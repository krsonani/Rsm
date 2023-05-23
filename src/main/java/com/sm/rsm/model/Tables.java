package com.sm.rsm.model;

import org.antlr.v4.runtime.misc.NotNull;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Tables")
public class Tables {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int tid;
	private int capacity;
	private boolean isAvailable;
	
}
