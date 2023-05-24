package com.sm.rsm.cntrl;
//Java Program to Create Rest Controller that
//Defines various API for Sending Mail

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//Importing required classes

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sm.rsm.dto.EmailOtpDto;
import com.sm.rsm.dto.UsersDto;
import com.sm.rsm.model.EmailDetails;
import com.sm.rsm.services.EmailService;
import com.sm.rsm.services.UsersService;

import jakarta.validation.Valid;

//Annotation
@RestController
//Class
public class EmailController {
	@Autowired private EmailService emailService;

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
	@PostMapping("/forgetPassword")
	public ResponseEntity<String> forgetPassword(@RequestBody EmailOtpDto emailOtpDto)
	{
		if(emailService.sendForgetMail(emailOtpDto))
			return new ResponseEntity<>("OTP Sent",HttpStatus.OK);
		else
			return new ResponseEntity<>("Invalid OTP",HttpStatus.BAD_REQUEST);
			
	}
	@PostMapping("/confirmOtp")
	public ResponseEntity<String> confirmOtp(@Valid @RequestBody EmailOtpDto emailOtpDto)
	{
		if(emailService.approveForgetOtp(emailOtpDto))
			return new ResponseEntity<>("Create new Password",HttpStatus.OK);
		else
			return new ResponseEntity<>("Invalid OTP",HttpStatus.BAD_REQUEST);
		
	}

}
