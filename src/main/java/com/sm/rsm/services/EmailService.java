package com.sm.rsm.services;

//Java Program to Illustrate Creation Of
//Service Interface
import com.sm.rsm.model.EmailDetails;

//Importing required classes

//Interface
public interface EmailService {

 // Method
 // To send a simple email
 String sendSimpleMail(EmailDetails details);

 // Method
 // To send an email with attachment
 String sendMailWithAttachment(EmailDetails details);
}