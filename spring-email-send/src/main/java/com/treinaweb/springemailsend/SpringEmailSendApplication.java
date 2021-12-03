package com.treinaweb.springemailsend;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import com.treinaweb.springemailsend.services.EmailSenderServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class SpringEmailSendApplication {

	@Autowired
	private EmailSenderServices emailSenderServices;

	public static void main(String[] args) {
		SpringApplication.run(SpringEmailSendApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void triggerEmail() throws MessagingException {
		// emailSenderServices.sendSimpleEmail("hofaseb383@suggerin.com", "Email de
		// teste", "Email enviado pelo Spring");

		// emailSenderServices.sendSimpleEmailWithAttachment(
		// "hofaseb383@suggerin.com",
		// "Email de Teste",
		// "Email com anexo enviado pelo Spring",
		// "MeuCV.pdf");

		Map<String, Object> props = new HashMap<>();

		props.put("userName", "Gilberto");

		emailSenderServices.sendSimpleEmailWithTemplate("hofaseb383@suggerin.com", "Email de teste", props);
	}

}
