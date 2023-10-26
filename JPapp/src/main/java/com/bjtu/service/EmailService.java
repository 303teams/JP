package com.bjtu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
//@Component
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        System.out.println(3);
        SimpleMailMessage message = new SimpleMailMessage();
        System.out.println(4);
        message.setFrom("247492078@qq.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        System.out.println(5);
        javaMailSender.send(message);
        System.out.println(6);

    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.qq.com");
        mailSender.setPort(587);

        mailSender.setUsername("247492078@qq.com");
        mailSender.setPassword("ozkrrntfwnqxbgff");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    public static void main(String[] args) {

        EmailService es = new EmailService();
        es.sendSimpleMessage("2461172547@qq.com","code:","1234");
    }

}