package com.Banking.backend.service.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.Banking.backend.service.MyMailSender;

@Component
public class MyMailSenderImpl  implements MyMailSender{
      @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendWelcomeEmail(String to, String name) {
        String subject = "Welcome, " + name + "!";
        String body = "Dear " + name + ",\n\n"
            + "Welcome to SanjeevniCare Bank!\n\n"
            + "Your registration was successful. We are delighted to have you join our trusted banking family.\n"
            + "You can now access your account, manage transactions, and explore our wide range of financial services.\n\n"
            + "If you have any questions or need assistance, feel free to contact our customer support team.\n\n"
            + "Warm regards,\n"
            + "SanjeevniCare Bank Team";


        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your-email@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
        System.out.println("✅ Welcome email sent to " + to);
    }

}
