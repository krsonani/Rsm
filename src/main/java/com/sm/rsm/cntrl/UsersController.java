package com.sm.rsm.cntrl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sm.rsm.dto.UserLoginResponce;
import com.sm.rsm.model.Users;
import com.sm.rsm.services.JwtService;
import com.sm.rsm.services.UsersServices;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@CrossOrigin(origins = {"*"})
public class UsersController {
	@Autowired
	private  UsersServices userservice;

	@Autowired
	private PasswordEncoder encorder;
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping(value= {"/addUser"})
	public String addUser(@RequestBody Users user)
	{ 
		user.setPassword(encorder.encode(user.getPassword()));
		userservice.addUsers(user);
		return "success";	

	}
	@PutMapping(value= {"/updateUser"})
	public String updateUser(@RequestBody Users user)
	{
		user.setPassword(encorder.encode(user.getPassword()));
		System.out.println(user);
		userservice.updateUsers(user);
		return "success";

	}
	
	@PostMapping(value= {"/getUser"})
	public ResponseEntity<?> getUserDetails(@RequestBody Users user) 
	{
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
				user.getEmail(), user.getPassword());
		System.out.println(authToken);
		try {
			// authenticate the credentials
			Authentication authenticatedDetails = authenticationManager.authenticate(authToken);
			Users userAuth = userservice.getUsersByEmail(user.getEmail());
			// => auth succcess
		//System.out.println(authenticatedDetails);
			
			System.out.println(userAuth.getEmail()+" "+userAuth.getPassword());
			return ResponseEntity
					.ok(new UserLoginResponce(userAuth,jwtService.genrateToken(user.getEmail())));
		} catch (Exception e) { 
			System.out.println("hi");// lab work : replace this by a method in global exc handler
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}
	
	@GetMapping(value= {"/getSession"})
	public Users getSession(HttpServletRequest request )
	{
		String token=null;
		String username=null;
		Users usersession=null;
		String authHeader= request.getHeader("Authorization");
		System.out.println(authHeader);
		if(authHeader!=null && authHeader.startsWith("Bearer "))
		{
			System.out.println("hello");
			token=authHeader.substring(7);
			username =jwtService.extractUsername(token);
		}

		if(username!=null)
		{
			usersession = userservice.getUsersByEmail(username);
		}
		return usersession;
	}
	
	@GetMapping(value= {"/api/verifytoken"})
	public ResponseEntity<?>  varifyToken(HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println("hi");
		String token=null;
		String authHeader= request.getHeader("Authorization");
		System.out.println(authHeader);
		if(authHeader!=null && authHeader.startsWith("Bearer "))
		{
			System.out.println("hello");
			token=authHeader.substring(7);	
		}
	     boolean bool=	jwtService.isTokenExpired(token);
	     System.err.println(bool);
		 if(!bool)
		 {
			 return ResponseEntity.ok(null);
		 }else
		 {
			 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		 }
		
	}
	
	@GetMapping(value= {"/checkEmailIsRegisteredOrNot/{email}"})
	public String checkEmailAvailability(@PathVariable String email) {
		
		if(userservice.existsByEmail(email) == true) {
			
			return "found";
		}
		return "not found";
		
	}
	
	@GetMapping(value= {"/"})
	public String checking()
	{
		return "hello";
	}
	

}
