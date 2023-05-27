package com.sm.rsm.cntrl;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sm.rsm.dto.EmailOtpDto;
import com.sm.rsm.dto.UserLoginResponce;
import com.sm.rsm.dto.UsersDto;
import com.sm.rsm.model.Role;
import com.sm.rsm.model.Users;
import com.sm.rsm.services.EmailService;
import com.sm.rsm.services.JwtService;
import com.sm.rsm.services.UsersService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;


@RestController
@CrossOrigin(origins = {"*"})
public class UsersController {
	@Autowired
	private  UsersService userservice;

	@Autowired
	private PasswordEncoder encorder;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	Map<String,String> response=new HashMap<String,String>();
	
	@Secured({ "ROLE_CUSTOMER" })
	@GetMapping("/verifyNewUser/{email}")
	public ResponseEntity<?> verifyNewUser(@PathVariable String email)
	{
		
		if(emailService.sendNewMail(email)) {
			response.put("msg", "OTP sent");
			return new ResponseEntity<>(response ,HttpStatus.OK);
		}
		else {
			response.put("msg", "Email already Exist");
			response.put("status", "200");
			return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}
	}
	@Secured({ "ROLE_CUSTOMER" })
	@PostMapping("/verifyNewUserOtp")
	public ResponseEntity<?> verifyNewUserOtp(@RequestBody UsersDto userD)
	{	
		if(emailService.verifyNewUserOtp(userD))
		{	
			Users user = new Users();
	 		Role role= new Role();
	 		role.setRid(1);
	 		user.setRole(role);
			userservice.addUsers(user,userD);
			response.put("msg", "User Registered");
			response.put("status", "200");
			return new ResponseEntity<>(response ,HttpStatus.OK);
		}
		else {
			response.put("msg", "Invalid OTP");
			response.put("status", "400");
			return new ResponseEntity<>(response ,HttpStatus.BAD_REQUEST);
		}
				
	}
	//This has to be removed, it is only used for testing purpose...
	@PostMapping(value= {"/addUser"})
	public String addUser(@Valid @RequestBody UsersDto userD)
	{ 
		Users user = new Users();
 		Role role= new Role();
 		role.setRid(1);
 		user.setRole(role);
		userservice.addUsers(user,userD);
		System.out.println("hiiiiiiiii");
		return "success";	

	}
	@Secured({ "ROLE_MANAGER" })
	@PostMapping(value= {"/addManager"})
	public ResponseEntity<?> addManager(@Valid @RequestBody UsersDto userD)
	{ 	
		
		if(userservice.existsByEmail(userD.getEmail()))
			return new ResponseEntity<>(response ,HttpStatus.OK);
		else
		{	
			Users user = new Users();
	 		Role role= new Role();
	 		role.setRid(2);
	 		user.setRole(role);
			userservice.addUsers(user,userD);
			response.put("msg", "Manager Registered");
			response.put("status", "200");
			return new ResponseEntity<>(response ,HttpStatus.OK);
		}
	}
	@Secured({ "ROLE_CUSTOMER" })
	@PutMapping(value= {"/updateUser"})
	public Map<String,String> updateUser(@RequestBody Users users)
	{
		Map<String,String> map = new HashMap<>();
		Users user = userservice.getUsers(users.getUids());
		user.setName(users.getName());
		user.setPhoneNum(users.getPhoneNum());
		userservice.updateUsers(user);
		map.put("response", "success");
		return map;
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
	
	@Secured({ "ROLE_CUSTOMER" , "ROLE_MANAGER"})
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
	
	@Secured({ "ROLE_CUSTOMER" , "ROLE_MANAGER"})
	@GetMapping(value= {"/api/verifytoken"})
	public ResponseEntity<?>  verifyToken(HttpServletRequest request, HttpServletResponse response)
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
	
	@Secured("ROLE_CUSTOMER")
	@GetMapping(value= {"/checkEmailIsRegisteredOrNot/{email}"})
	public ResponseEntity<?> checkEmailAvailability(@PathVariable String email) {
		
		if(userservice.existsByEmail(email) == true) {
			
			response.put("msg", "found");
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		response.put("msg", "not found");
		return  new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> onMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
	
	@PutMapping("/updateForgetPasswordUser")
	public Map<String,String> updateForgetPasswordUser(@RequestBody UsersDto usersDto)
	{
		Users user= userservice.getUsersByEmail(usersDto.getEmail());
		user.setPassword( encorder.encode(usersDto.getPassword()) );
		Map<String,String> map = new HashMap<>();
		userservice.updateUsers(user);
		response.put("msg", "User Updated");
		response.put("status", "200");
		return map;
	}
	
	@GetMapping("/getUserById/{id}")
	public Users getUsersById(@PathVariable int id)
	{
		return userservice.fetchUsersById(id);
	}
	

}

