package com.gogonew.api.email;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

import java.io.IOException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
class EmailRepositoryTest {
	@Mock
	private JavaMailSender javaMailSender;
	private EmailRepository emailRepository;
	@Captor
	private ArgumentCaptor<MimeMessage> mimeMessageCaptor;

	@BeforeEach
	private void beforeAll() {
		this.emailRepository = new EmailRepository(this.javaMailSender, "sendUser@gmail.com");
	}

	@Test
	void sendEmail_메일_내용_검증_성공_테스트() throws MessagingException, IOException {
		// given
		String recipient = "reciveUser@gmail.com";
		String subject = "Test Subject";
		String text = "Test Message";

		// 생성되는 메시지 값 검증을 위해 mocking
		Session session = Session.getDefaultInstance(System.getProperties(), null);
		MimeMessage mimeMessage = new MimeMessage(session);
		given(javaMailSender.createMimeMessage()).willReturn(mimeMessage);

		// when
		emailRepository.sendEmail(recipient, subject, text);

		// then
		verify(javaMailSender).send(mimeMessageCaptor.capture());

		MimeMessage capturedMimeMessage = mimeMessageCaptor.getValue();
		assertEquals(emailRepository.getFromAddress(), capturedMimeMessage.getFrom()[0].toString());
		assertEquals(recipient, capturedMimeMessage.getRecipients(Message.RecipientType.TO)[0].toString());
		assertEquals(subject, capturedMimeMessage.getSubject());
		assertEquals(text, capturedMimeMessage.getContent().toString());
		assertNotNull(capturedMimeMessage.getSentDate());
	}
}