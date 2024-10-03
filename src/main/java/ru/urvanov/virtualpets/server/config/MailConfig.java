package ru.urvanov.virtualpets.server.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {
    
//    JavaMailSender в Spring Boot создаётся автоматически. Этот код не
//    нужен
//    @Bean
//    public JavaMailSender mailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        Properties properties = new Properties();
//        properties.setProperty("mail.smtp.auth", Boolean.TRUE.toString());
//        mailSender.setHost(null);
//        mailSender.setJavaMailProperties(properties);
//        return mailSender;
//    }
//    
    @Bean
    public SimpleMailMessage templateMessage(
            @Value("${virtualpets-server-springboot.mail.from}")
            String mailFrom,
            @Value("${virtualpets-server-springboot.mail.subject}")
            String mailSubject) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(null);
        simpleMailMessage.setSubject(mailSubject);
        return simpleMailMessage;
    }
    
    

}
