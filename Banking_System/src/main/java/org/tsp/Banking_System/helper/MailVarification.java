package org.tsp.Banking_System.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.tsp.Banking_System.dto.Customer;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
@Service
public class MailVarification 
{
@Autowired
JavaMailSender mailsender;
//the above mail sender get this through dependency
public void sendmail(Customer customer)
{
	MimeMessage mimemessage=mailsender.createMimeMessage();
	MimeMessageHelper helper=new MimeMessageHelper(mimemessage);
	
		try 
		{
			helper.setFrom("prasanth8971@gmail.com");
		}
		catch (MessagingException e) 
		{
			e.printStackTrace();
		}
		try 
		{
			helper.setTo(customer.getEmail());
		}
		catch (MessagingException e) 
		{
			e.printStackTrace();
		}
	      try
	      {
			helper.setSubject("mail verification");
		  } 
	      catch (MessagingException e)
	      {
			e.printStackTrace();
		  }
	      try
	      {
			helper.setText("Your otp for email verification is"+customer.getOtp());
		  } 
	      catch (MessagingException e)
	      {
			e.printStackTrace();
		  }
	      mailsender.send(mimemessage);
}
}
