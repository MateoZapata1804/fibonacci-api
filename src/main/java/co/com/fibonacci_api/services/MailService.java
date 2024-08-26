package co.com.fibonacci_api.services;

import java.text.MessageFormat;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import co.com.fibonacci_api.DTO.MailDTO;
import co.com.fibonacci_api.models.MailAddressModel;
import co.com.fibonacci_api.models.config.MailConfig;
import co.com.fibonacci_api.models.http.RequestResponse;
import co.com.fibonacci_api.repositories.MailRepository;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {

	@Autowired
	MailRepository mailRepo;
	@Autowired
	MailConfig mailConfig;
	@Autowired
	TemplateEngine templateEngine;
	
	public RequestResponse sendMail(MailDTO mailData) {
		
        try {
        	
        	JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            
            mailSender.setJavaMailProperties(mailConfig.getProperties());
            mailSender.setUsername(mailConfig.getMailSenderAddress());
            mailSender.setPassword(mailConfig.getMailSenderPassword());
            
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                    
            Context context = new Context();
            context.setVariable("message", mailData.getContent());
            String html = templateEngine.process("fibonacci-seq-mail", context);
            
            helper.setTo(mailData.getTo());
            helper.setSubject(mailData.getSubject());
            helper.setText(html, true);
            
            mailSender.send(mimeMessage);
            
            String outputMsg = MessageFormat.format("Enviado a: {0}, Remitente: {1}", 
            		mailData.getTo(), 
            		mailConfig.getMailSenderAddress());
            
            return new RequestResponse(true, "Correo enviado exitosamente", "", outputMsg);
            
		} catch (Exception e) {
			return new RequestResponse(false, "No fue posible enviar el correo", "", e.toString());
		}
	}
	
	public List<MailAddressModel> getParametrizedMails() {
		return mailRepo.findAllByDisabled(false);
	}
	
	public List<String> getParametrizedMailAddresses() {
		return mailRepo.findEnabledEmailAddresses();
	}
}
