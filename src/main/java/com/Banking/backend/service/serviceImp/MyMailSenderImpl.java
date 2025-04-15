package com.Banking.backend.service.serviceImp;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.mail.internet.MimeMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
    
    public File generateSecureAccountDetailsPdf(String name, String netBankingId, String maskedCardNumber, String tempPin, String dobStr) throws Exception {
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    LocalDate dob = LocalDate.parse(dobStr, formatter);
    String pdfPassword = dob.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
    
    
        Document document = new Document();
    File file = new File("Account_Details_" + name + ".pdf");

    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
    writer.setEncryption(
        pdfPassword.getBytes(),
        null,
        PdfWriter.ALLOW_PRINTING,
        PdfWriter.ENCRYPTION_AES_128
    );

    document.open();

    Font headingFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
    Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

    document.add(new Paragraph("TechBank Account Details", headingFont));
    document.add(new Paragraph(" "));

    document.add(new Paragraph("Dear " + name + ",", normalFont));
    document.add(new Paragraph("Welcome to TechBank! Below are your secure account details.", normalFont));
    document.add(new Paragraph(" "));
    document.add(new Paragraph("🆔 NetBanking ID: " + netBankingId, normalFont));
    document.add(new Paragraph("💳 Debit Card Number: " + maskedCardNumber, normalFont));
    document.add(new Paragraph("🔐 Temporary Debit Card PIN: " + tempPin, normalFont));
    document.add(new Paragraph(" "));
    document.add(new Paragraph("Please log in and change your PIN/password immediately.", normalFont));
    document.add(new Paragraph(" "));
    document.add(new Paragraph("Best Regards,\nTechBank Team", normalFont));

    document.close();
    return file;
}

public void sendBankAccountEmailWithPDF(String to, String name, String netBankingId, String cardNumber, String pin, String dob) throws Exception {
    String maskedCard = "XXXX-XXXX-XXXX-" + cardNumber.substring(cardNumber.length() - 4);
    File pdfFile = generateSecureAccountDetailsPdf(name, netBankingId, maskedCard, pin, dob);

    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);
    
    helper.setTo(to);
    helper.setSubject("🎉 Welcome to TechBank - Account Created");
    
    String emailBody = "Dear " + name + ",\n\n"
            + "Welcome to TechBank! Your account has been successfully created.\n"
            + "We've attached a secure PDF with your login and card details.\n\n"
            + "📌 Use your **Date of Birth in DDMMYYYY format** to open the PDF.\n"
            + "For example, if your DOB is 5th July 1999 → 05071999\n\n"
            + "For security, please change your password/PIN after logging in.\n\n"
            + "Warm regards,\nTechBank Team";

    helper.setText(emailBody);
    helper.addAttachment(pdfFile.getName(), pdfFile);

    mailSender.send(message);
    System.out.println("✅ Email with secure PDF sent to " + to);
}

}
