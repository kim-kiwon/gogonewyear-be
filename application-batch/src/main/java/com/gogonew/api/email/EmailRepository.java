package com.gogonew.api.email;

import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;

import lombok.Getter;

@Getter
@Repository
public class EmailRepository {
	private final JavaMailSender mailSender;
	private final String fromAddress;

	public EmailRepository(JavaMailSender mailSender, @Value("${spring.mail.username") String fromAddress) {
		this.mailSender = mailSender;
		this.fromAddress = fromAddress;
	}

	public void sendEmail(String recipient, String subject, String text) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		message.setFrom(fromAddress);
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
		message.setSubject(subject);
		message.setText(text);
		message.setSentDate(new Date());

		mailSender.send(message);
	}
}
