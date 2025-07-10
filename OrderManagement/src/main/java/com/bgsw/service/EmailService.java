package com.bgsw.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.bgsw.entity.Role;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    

    public void sendOtpEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your OTP for Password Reset");
        message.setText("Your One-Time Password (OTP) is: " + otp + "\n\nUse this to reset your password.");

        mailSender.send(message);
    }

}
