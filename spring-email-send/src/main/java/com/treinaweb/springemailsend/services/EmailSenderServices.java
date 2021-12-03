package com.treinaweb.springemailsend.services;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailSenderServices {

   @Autowired
   private JavaMailSender mailSender;

   @Autowired
   private ResourceLoader resourceLoader;

   @Autowired
   private TemplateEngine templateEngine;

   private org.springframework.core.io.Resource resource;

   public EmailSenderServices(JavaMailSender mailSender) {
      this.mailSender = mailSender;
   }

   public org.springframework.core.io.Resource getResource() {
      return resource;
   }

   public void setResource(org.springframework.core.io.Resource resource) {
      this.resource = resource;
   }

   public void sendSimpleEmail(String to, String subject, String body) {
      SimpleMailMessage message = new SimpleMailMessage();

      message.setFrom("gilbertoaleite@gmail.com");
      message.setTo(to);
      message.setSubject(subject);
      message.setText(body);

      mailSender.send(message);

   }

   public void sendSimpleEmailWithAttachment(String to, String subject, String body, String attachment)
         throws MessagingException {
      MimeMessage mimeMessage = mailSender.createMimeMessage();

      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

      mimeMessageHelper.setFrom("gilberto@gmail.com");
      mimeMessageHelper.setTo(to);
      mimeMessageHelper.setSubject(subject);
      mimeMessageHelper.setText(body);

      Resource resource = resourceLoader.getResource(attachment);

      mimeMessageHelper.addAttachment(resource.getFilename(), resource);

      mailSender.send(mimeMessage);
   }

   public void sendSimpleEmailWithTemplate(String to, String subject, Map<String, Object> props)
         throws MessagingException {
      MimeMessage mimeMessage = mailSender.createMimeMessage();

      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

      Context context = new Context();
      context.setVariables(props);

      String html = templateEngine.process("welcome.html", context);

      mimeMessageHelper.setFrom("gilberto@gmail.com");
      mimeMessageHelper.setTo(to);
      mimeMessageHelper.setSubject(subject);
      mimeMessageHelper.setText(html, true);

      mailSender.send(mimeMessage);

   }
}
