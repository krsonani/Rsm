package com.sm.rsm.model;

import java.util.List;
import java.util.Map;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

	@Id
	@SequenceGenerator(name = "hibernate_orders", sequenceName = "hibernate_id_orders", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_orders")
	private int oid;
	@ManyToOne
	@JoinColumn(name = "userid")
	private Users user;
<<<<<<< HEAD
	@ManyToMany()
=======
	@ManyToMany
>>>>>>> 3c76efcbcf49f6481ab3a9f8d004c3501264ef5d
	private List<Food> foodList;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private Map<Integer,Integer> foodMap;

	private double totalPrice;
	private String dates;
	private boolean isBillGenerated;
}
