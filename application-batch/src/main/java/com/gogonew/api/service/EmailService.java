package com.gogonew.api.service;

import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmailService {
	private final JavaMailSender mailSender;

	public void sendEmail(String from, String recipient, String subject, String text) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		message.setFrom(from);
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
		message.setSubject(subject);
		message.setText(text);
		message.setSentDate(new Date());

		mailSender.send(message);
	}
}
