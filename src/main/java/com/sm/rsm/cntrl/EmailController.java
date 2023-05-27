
package com.sm.rsm.cntrl;
//Java Program to Create Rest Controller that
//Defines various API for Sending Mail


import java.util.HashMap;
import java.util.Map;

//Importing required classes

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sm.rsm.dto.EmailOtpDto;
import com.sm.rsm.dto.UsersDto;
import com.sm.rsm.model.EmailDetails;
import com.sm.rsm.services.EmailService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

//Annotation
@RestController
//Class
@CrossOrigin(origins = {"*"})
public class EmailController {
	@Autowired private EmailService emailService;
	Map<String,String> response=new HashMap<String,String>();

	// Sending a simple Email
	@PostMapping("/sendMail")
	public String sendMail(@RequestBody EmailDetails details)
	{
		String status
			= emailService.sendSimpleMail(details);
		return status;
	}

	// Sending email with attachment
	@PostMapping("/sendMailWithAttachment")
	public String sendMailWithAttachment(
		@RequestBody EmailDetails details)
	{
		String status
			= emailService.sendMailWithAttachment(details);

		return status;
	}
	
	@GetMapping("/forgetPassword/{email}")
	public ResponseEntity<?> forgetPassword(@Email @PathVariable String email)
	{
		if(emailService.sendForgetMail(email)) {
			response.put("msg", "OTP sent");
			response.put("status", "200");
			return new ResponseEntity<>(response ,HttpStatus.OK);
		}
		else {
			response.put("msg", "Please Register first");
			response.put("status", "400");
			return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/confirmOtp")
	public ResponseEntity<?> confirmOtp( @RequestBody UsersDto usersDto)
	{
		System.out.println(usersDto);
		if(emailService.approveForgetOtp(usersDto)) {
			response.put("msg", "Create New Password");
			response.put("status", "200");
			return new ResponseEntity<>(response ,HttpStatus.OK);
		}
		else {
			response.put("msg", "Invalid OTP");
			response.put("status", "400");
			return new ResponseEntity<>(response ,HttpStatus.BAD_REQUEST);
		}
	}

}
