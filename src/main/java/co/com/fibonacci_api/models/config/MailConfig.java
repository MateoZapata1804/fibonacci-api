package co.com.fibonacci_api.models.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:email.properties")
public class MailConfig {

	@Value("${email.address}")
	private String mailSenderAddress;
	
	@Value("${email.password}")
	private String mailSenderPassword;
	
	
	public Properties getProperties() {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		return props;
	}
	
	public String getMailSenderAddress() {
		return mailSenderAddress;
	}

	public String getMailSenderPassword() {
		return mailSenderPassword;
	}
	
}
