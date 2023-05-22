package com.sm.rsm.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
	private List<Food> food;
	private double totalprice;
	private Date date;
}
