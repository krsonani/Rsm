package com.sm.rsm.services;

import com.sm.rsm.dto.EmailOtpDto;
import com.sm.rsm.dto.UsersDto;
//Java Program to Illustrate Creation Of
//Service Interface
import com.sm.rsm.model.EmailDetails;

import jakarta.validation.Valid;

//Importing required classes

//Interface
public interface EmailService {

 // Method
 // To send a simple email
 String sendSimpleMail(EmailDetails details);

 // Method
 // To send an email with attachment
 String sendMailWithAttachment(EmailDetails details);

boolean sendForgetMail(String email);

boolean approveForgetOtp(@Valid UsersDto usersDto);

boolean sendNewMail(String email);

boolean verifyNewUserOtp(UsersDto usersDto);
}