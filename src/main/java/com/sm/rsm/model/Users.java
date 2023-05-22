package com.sm.rsm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int uid;
	private String name;
	private String email;
	private String phoneNub;
	private Role role;
	
	

}
