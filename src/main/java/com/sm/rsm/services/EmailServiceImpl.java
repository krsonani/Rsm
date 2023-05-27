package com.sm.rsm.services;

import com.sm.rsm.dto.EmailOtpDto;
import com.sm.rsm.dto.UsersDto;
//Importing required classes
import com.sm.rsm.model.EmailDetails;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;

//Annotation
@Service
//Implementing EmailService interface
public class EmailServiceImpl implements EmailService {
	
	@Autowired UsersService userService;

	@Autowired private JavaMailSender javaMailSender;

	static Map<String,String> emailOtpMap= new HashMap<>();
	static Map<String,String> emailNewUser= new HashMap<>();
	
	@Value("${spring.mail.username}") private String sender;

	// Method 1
	// To send a simple email
	public String sendSimpleMail(EmailDetails details)
	{

		// Try block to check for exceptions
		try {

			// Creating a simple mail message
			SimpleMailMessage mailMessage
				= new SimpleMailMessage();

			// Setting up necessary details
			mailMessage.setFrom(sender);
			mailMessage.setTo(details.getRecipient());
			mailMessage.setText(details.getMsgBody());
			mailMessage.setSubject(details.getSubject());

			// Sending the mail
			javaMailSender.send(mailMessage);
			System.out.println("before success");
			return "Mail Sent Successfully...";
		}

		// Catch block to handle the exceptions
		catch (Exception e) {
			e.printStackTrace();
			return "Error while Sending Mail";
		}
	}

	// Method 2
	// To send an email with attachment
	public String
	sendMailWithAttachment(EmailDetails details)
	{
		// Creating a mime message
		MimeMessage mimeMessage
			= javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;

		try {

			// Setting multipart as true for attachments to
			// be send
			mimeMessageHelper
				= new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(sender);
			mimeMessageHelper.setTo(details.getRecipient());
			mimeMessageHelper.setText(details.getMsgBody());
			mimeMessageHelper.setSubject(
				details.getSubject());

			// Adding the attachment
			FileSystemResource file
				= new FileSystemResource(
					new File(details.getAttachment()));

			mimeMessageHelper.addAttachment(
				file.getFilename(), file);

			// Sending the mail
			javaMailSender.send(mimeMessage);
			return "Mail sent Successfully";
		}

		// Catch block to handle MessagingException
		catch (MessagingException e) {

			// Display message when exception occurred
			return "Error while sending mail!!!";
		}
	}

	@Override
	public boolean sendForgetMail(String email) {
		if(userService.existsByEmail(email))
		{
			EmailDetails details = new EmailDetails();
			details.setRecipient(email);
			String otp=getRandomNumberString();
			details.setMsgBody(otp);
			details.setSubject("Confirm OTP:");
			sendSimpleMail(details);
			emailOtpMap.put(email, otp);
			return true;
		}
		else
			return false;
	}
	public static String getRandomNumberString() {
	    // It will generate 6 digit random Number.
	    // from 0 to 999999
	    Random rnd = new Random();
	    int number = rnd.nextInt(999999);

	    // this will convert any number sequence into 6 character.
	    return String.format("%06d", number);
	}

	@Override
	public boolean approveForgetOtp(@Valid UsersDto usersDto) {
		if(emailOtpMap.get(usersDto.getEmail()).equals(usersDto.getOtp()))
		{
			emailOtpMap.remove(usersDto.getEmail());
			return true;
		}
		else 
			return false;
	}

	@Override
	public boolean sendNewMail(String email) {
		if(userService.existsByEmail(email))
			return false;
		else
		{
			EmailDetails details = new EmailDetails();
			details.setRecipient(email);
			String otp=getRandomNumberString();
			details.setMsgBody(otp);
			details.setSubject("Confirm OTP:");
			sendSimpleMail(details);
			
			emailNewUser.put(email, otp);
			return true;
		}
	}

	@Override
	public boolean verifyNewUserOtp(UsersDto usersDto) {
		if(emailNewUser.get(usersDto.getEmail()).equals(usersDto.getOtp()))
		{
			emailNewUser.remove(usersDto.getEmail());
			return true;
		}
		else 
			return false;
	}
}