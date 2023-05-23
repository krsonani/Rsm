package com.sm.rsm.dto;

import com.sm.rsm.model.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponce {

	private Users user;
	private String jwtToken;
	

}
