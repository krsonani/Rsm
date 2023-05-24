package com.sm.rsm.model;

import java.util.List;
import java.util.Map;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int oid;
	@ManyToOne
	@JoinColumn(name = "userid")
	private Users user;
	@ManyToMany
	private List<Tables> table;
	@ManyToMany
	private Map<Food,Integer> food;
	private double totalPrice;
	private String dates;
}
