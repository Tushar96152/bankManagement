package com.Banking.backend.service.serviceImp;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.Banking.backend.entity.BankAccount;
import com.Banking.backend.service.MyMailSender;

@Component
public class MyMailSenderImpl  implements MyMailSender{
      @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendWelcomeEmail(String to, String name) {
        String subject = "Welcome, " + name + "!";
        String body = "<html><body>"
                + "Dear " + name + ",<br><br>"
                + "Welcome to <strong>TechCode Bank</strong>!<br><br>"
                + "Your registration was successful. We are thrilled to have you as a part of our innovative banking community.<br>"
                + "You can now access your account, manage transactions, and explore our range of cutting-edge financial services.<br><br>"
                + "👉 <a href='https://www.techcode.cfd'>Visit TechCode Bank</a><br><br>"
                + "If you have any questions or need assistance, our customer support team is here to help you.<br><br>"
                + "Warm regards,<br>"
                + "TechCode Bank Team"
                + "</body></html>";
    
        // Use the common sendEmail method to send the email
        sendEmail(to, subject, body);
    }
    
    public File generateSecureAccountDetailsPdf(String name, String netBankingId,String netLoginPassword, String netBankingPassword, String maskedCardNumber, String tempPin, String dobStr) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dob = LocalDate.parse(dobStr, formatter);
        String pdfPassword = dob.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
    
        // Set page properties for a professional document
        Document document = new Document(PageSize.A4, 50, 50, 70, 50);
        File file = new File("TechCodeBank_Account_Details_" + name.replaceAll("\\s+", "_") + ".pdf");
    
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        writer.setEncryption(
            pdfPassword.getBytes(),
            null,
            PdfWriter.ALLOW_PRINTING,
            PdfWriter.ENCRYPTION_AES_128
        );
    
        document.open();
    
        // Add header with bank logo and information
        createHeader(document);
        
        // Add reference number and date
        addReferenceAndDate(document);
        
        // Customer greeting
        addCustomerGreeting(document, name);
        
        // Add account details section
        addAccountDetailsSection(document, netBankingId, netBankingPassword,netLoginPassword, maskedCardNumber, tempPin);
        
        // Security information - without password information
        addSecurityInformation(document);
        
        // Footer with bank contact information
        addFooter(document);
    
        document.close();
        
        // Generate separate password instructions and print them
        String passwordInstructions = "IMPORTANT: This PDF file is password-protected for your security.\n" +
                                     "To open the file, please use your date of birth in the format ddMMyyyy.\n" +
                                     "For example, if your date of birth is " + dobStr + ", the password would be " + 
                                     dobStr.replace("-", "").substring(0, 2) + dobStr.replace("-", "").substring(3, 5) + 
                                     dobStr.replace("-", "").substring(6) + ".";
        
        // Print the instructions to console or log them
        System.out.println(passwordInstructions);
        
        return file;
    }


private void createHeader(Document document) throws DocumentException, IOException {
    // Create a table for the header with logo and bank name
    PdfPTable headerTable = new PdfPTable(2);
    headerTable.setWidthPercentage(100);
    float[] columnWidths = {1f, 2f};
    headerTable.setWidths(columnWidths);
    
    // Add logo
    String logoPath = getClass().getClassLoader().getResource("images/techcodelogo.png").getPath();
    Image logo = Image.getInstance(logoPath);
    logo.scaleToFit(120, 60);
    
    PdfPCell logoCell = new PdfPCell();
    logoCell.addElement(logo);
    logoCell.setBorder(Rectangle.NO_BORDER);
    logoCell.setPaddingBottom(10f);
    headerTable.addCell(logoCell);
    
    // Add bank name and slogan
    Font bankNameFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, BaseColor.DARK_GRAY);
    Font sloganFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10, BaseColor.DARK_GRAY);
    
    Paragraph bankInfo = new Paragraph();
    bankInfo.add(new Chunk("TechCode Bank\n", bankNameFont));
    bankInfo.add(new Chunk("Banking for the Digital Future", sloganFont));
    
    PdfPCell bankInfoCell = new PdfPCell();
    bankInfoCell.addElement(bankInfo);
    bankInfoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    bankInfoCell.setBorder(Rectangle.NO_BORDER);
    headerTable.addCell(bankInfoCell);
    
    document.add(headerTable);
    
    // Add separator line
    LineSeparator lineSeparator = new LineSeparator(1, 100, new BaseColor(0, 102, 204), Element.ALIGN_CENTER, -5);
    document.add(new Chunk(lineSeparator));
    document.add(new Paragraph(" "));
}

private void addReferenceAndDate(Document document) throws DocumentException {
    Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
    
    // Create a table for reference number and date
    PdfPTable refTable = new PdfPTable(2);
    refTable.setWidthPercentage(100);
    
    // Generate a reference number
    String refNumber = "TCB" + String.format("%08d", new Random().nextInt(10000000));
    
    // Add reference number
    PdfPCell refCell = new PdfPCell(new Paragraph("Reference: " + refNumber, normalFont));
    refCell.setBorder(Rectangle.NO_BORDER);
    refCell.setHorizontalAlignment(Element.ALIGN_LEFT);
    refTable.addCell(refCell);
    
    // Add date
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMMM yyyy");
    PdfPCell dateCell = new PdfPCell(new Paragraph("Date: " + LocalDate.now().format(dtf), normalFont));
    dateCell.setBorder(Rectangle.NO_BORDER);
    dateCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    refTable.addCell(dateCell);
    
    document.add(refTable);
    document.add(new Paragraph(" "));
}

private void addCustomerGreeting(Document document, String name) throws DocumentException {
    Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 11);
    Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
    
    // Add greeting
    document.add(new Paragraph("Dear " + name + ",", boldFont));
    document.add(new Paragraph(" "));
    
    Paragraph welcomePara = new Paragraph();
    welcomePara.add(new Chunk("Thank you for choosing ", normalFont));
    welcomePara.add(new Chunk("TechCode Bank", boldFont));
    welcomePara.add(new Chunk(" for your banking needs. We are pleased to provide you with your new account information. Your account has been successfully created and is now ready for use.", normalFont));
    document.add(welcomePara);
    
    document.add(new Paragraph(" "));
    document.add(new Paragraph("Please find your secure access credentials below:", normalFont));
    document.add(new Paragraph(" "));
}

private void addAccountDetailsSection(Document document, String netBankingId, String netBankingPassword,String netLoginPassword, String maskedCardNumber, String tempPin) throws DocumentException {
    Font sectionTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, new BaseColor(0, 102, 204));
    Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
    Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 11);
    
    document.add(new Paragraph("ACCOUNT ACCESS DETAILS", sectionTitleFont));
    document.add(new Paragraph(" "));
    
    // Create a table for account details with styled cells
    PdfPTable detailsTable = new PdfPTable(2);
    detailsTable.setWidthPercentage(90);
    detailsTable.setHorizontalAlignment(Element.ALIGN_CENTER);
    detailsTable.setSpacingBefore(5f);
    detailsTable.setSpacingAfter(5f);
    
    float[] colWidths = {1.5f, 2f};
    detailsTable.setWidths(colWidths);
    
    // Style the table
    BaseColor headerBgColor = new BaseColor(240, 240, 240);
    BaseColor borderColor = new BaseColor(200, 200, 200);
    
    // Add the details rows with consistent styling
    addStyledRow(detailsTable, "NetBanking ID", netBankingId, labelFont, valueFont, headerBgColor, borderColor);
    addStyledRow(detailsTable, "Temporary Login Password", netLoginPassword, labelFont, valueFont, headerBgColor, borderColor);
    addStyledRow(detailsTable, "Temporary Transaction Password", netBankingPassword, labelFont, valueFont, headerBgColor, borderColor);
    addStyledRow(detailsTable, "Debit Card Number", maskedCardNumber, labelFont, valueFont, headerBgColor, borderColor);
    addStyledRow(detailsTable, "Temporary Card PIN", tempPin, labelFont, valueFont, headerBgColor, borderColor);
    
    
    document.add(detailsTable);
    document.add(new Paragraph(" "));
}

private void addStyledRow(PdfPTable table, String label, String value, Font labelFont, Font valueFont, BaseColor bgColor, BaseColor borderColor) {
    // Label cell
    PdfPCell labelCell = new PdfPCell(new Paragraph(label, labelFont));
    labelCell.setBackgroundColor(bgColor);
    labelCell.setBorderColor(borderColor);
    labelCell.setPadding(8f);
    labelCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    table.addCell(labelCell);
    
    // Value cell
    PdfPCell valueCell = new PdfPCell(new Paragraph(value, valueFont));
    valueCell.setBorderColor(borderColor);
    valueCell.setPadding(8f);
    valueCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    table.addCell(valueCell);
}

// Modified method to remove password information
private void addSecurityInformation(Document document) throws DocumentException {
    Font securityTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, new BaseColor(0, 102, 204));
    Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 11);
    Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
    Font alertFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, BaseColor.RED);
    
    document.add(new Paragraph("IMPORTANT SECURITY INFORMATION", securityTitleFont));
    document.add(new Paragraph(" "));
    
    // Create a table for the security box
    PdfPTable securityTable = new PdfPTable(1);
    securityTable.setWidthPercentage(90);
    securityTable.setHorizontalAlignment(Element.ALIGN_CENTER);
    securityTable.setSpacingBefore(5f);
    securityTable.setSpacingAfter(10f);
    
    PdfPCell securityCell = new PdfPCell();
    securityCell.setBorderColor(new BaseColor(200, 200, 200));
    securityCell.setPadding(10f);
    
    Paragraph securityPara = new Paragraph();
    // Removed the password information that was previously here
    
    securityPara.add(new Chunk("Please note: ", alertFont));
    securityPara.add(new Chunk("You must change your temporary password and PIN during your first login.\n\n", normalFont));
    
    securityPara.add(new Chunk("Security Recommendations:\n", boldFont));
    securityPara.add(new Chunk("• Do not share your credentials with anyone.\n", normalFont));
    securityPara.add(new Chunk("• Choose a strong password with a mix of letters, numbers, and special characters.\n", normalFont));
    securityPara.add(new Chunk("• Never respond to emails or calls asking for your banking credentials.\n", normalFont));
    securityPara.add(new Chunk("• Always log out after completing your banking session.", normalFont));
    
    securityCell.addElement(securityPara);
    securityTable.addCell(securityCell);
    
    document.add(securityTable);
    document.add(new Paragraph(" "));
}

private void addFooter(Document document) throws DocumentException {
    Font footerTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
    Font footerFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
    
    // Add sign-off
    Paragraph signOff = new Paragraph();
    signOff.add(new Chunk("Thank you for banking with us.\n\n", footerFont));
    signOff.add(new Chunk("Warm Regards,\n", footerFont));
    signOff.add(new Chunk("TechCode Bank Team", footerTitleFont));
    document.add(signOff);
    
    document.add(new Paragraph(" "));
    
    // Add contact information
    LineSeparator lineSeparator = new LineSeparator(1, 100, new BaseColor(0, 102, 204), Element.ALIGN_CENTER, -5);
    document.add(new Chunk(lineSeparator));
    
    PdfPTable footerTable = new PdfPTable(3);
    footerTable.setWidthPercentage(100);
    
    // Customer Care
    PdfPCell customerCareCell = new PdfPCell();
    customerCareCell.setBorder(Rectangle.NO_BORDER);
    Paragraph customerCare = new Paragraph();
    customerCare.add(new Chunk("Customer Care:\n", footerTitleFont));
    customerCare.add(new Chunk("1800-123-4567\nsupport@techcodebank.com", footerFont));
    customerCareCell.addElement(customerCare);
    footerTable.addCell(customerCareCell);
    
    // Website
    PdfPCell websiteCell = new PdfPCell();
    websiteCell.setBorder(Rectangle.NO_BORDER);
    websiteCell.setHorizontalAlignment(Element.ALIGN_CENTER);
    Paragraph website = new Paragraph();
    website.add(new Chunk("Visit Us Online:\n", footerTitleFont));
    website.add(new Chunk("www.techcodebank.com\nMobile App: TechCode Banking", footerFont));
    websiteCell.addElement(website);
    footerTable.addCell(websiteCell);
    
    // Head Office
    PdfPCell addressCell = new PdfPCell();
    addressCell.setBorder(Rectangle.NO_BORDER);
    addressCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    Paragraph address = new Paragraph();
    address.add(new Chunk("Head Office:\n", footerTitleFont));
    address.add(new Chunk("TechCode Tower, 123 Digital Avenue\nFintech District, TC-12345", footerFont));
    addressCell.addElement(address);
    footerTable.addCell(addressCell);
    
    document.add(footerTable);
}
    


public void sendBankAccountEmailWithPDF(String to, String name, String netBankingId,String netLoginPassword,String netBankingPassword, String cardNumber, String pin, String dob) throws Exception {
    String maskedCard = "XXXX-XXXX-XXXX-" + cardNumber.substring(cardNumber.length() - 4);
    File pdfFile = generateSecureAccountDetailsPdf(name, netBankingId,netBankingPassword,netLoginPassword
    , maskedCard, pin, dob);

    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);
    
    helper.setTo(to);
    helper.setSubject("🎉 Welcome to TechCodeBank - Account Created");
    
    String emailBody = "Dear " + name + ",\n\n"
            + "Welcome to TechCodeBank! Your account has been successfully created.\n"
            + "We've attached a secure PDF with your login and card details.\n\n"
            + "📌 Use your **Date of Birth in DDMMYYYY format** to open the PDF.\n"
            + "For example, if your DOB is 5th July 1999 → 05071999\n\n"
            + "For security, please change your password/PIN after logging in.\n\n"
            + "Warm regards,\nTechCodeBank Team";

    helper.setText(emailBody);
    helper.addAttachment(pdfFile.getName(), pdfFile);

    mailSender.send(message);
    System.out.println("✅ Email with secure PDF sent to " + to);
}

public void sendSenderTransferNotification(String email, String userName, double amount, String receiverAcc, double newBalance) {
    String subject = "Money Transfer Successful - TechCode Bank";
    String body = "<html><body>"
            + "Dear " + userName + ",<br><br>"
            + "You have successfully transferred <strong>₹" + amount + "</strong> to Account Number <strong>" + receiverAcc + "</strong>.<br><br>"
            + "<strong>New Balance:</strong> ₹" + newBalance + "<br>"
            + "<strong>Transaction Time:</strong> " + LocalDateTime.now() + "<br><br>"
            + "Thank you for using TechCode Bank.<br><br>"
            + "Warm regards,<br>TechCode Bank Team"
            + "</body></html>";
    sendEmail(email, subject, body);
}

public void sendReceiverTransferNotification(String email, String userName, double amount, String senderAcc, double newBalance) {
    String subject = "Money Received - TechCode Bank";
    String body = "<html><body>"
            + "Dear " + userName + ",<br><br>"
            + "You have received <strong>₹" + amount + "</strong> from Account Number <strong>" + senderAcc + "</strong>.<br><br>"
            + "<strong>New Balance:</strong> ₹" + newBalance + "<br>"
            + "<strong>Transaction Time:</strong> " + LocalDateTime.now() + "<br><br>"
            + "Thank you for banking with us!<br><br>"
            + "Warm regards,<br>TechCode Bank Team"
            + "</body></html>";
    sendEmail(email, subject, body);
}





public void sendEmail(String to, String subject, String htmlBody) {
    try {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        helper.setFrom("official@techcode.cfd"); // ✅ MUST MATCH your SMTP username
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true); // true = HTML content

        mailSender.send(message);
        System.out.println("Email sent successfully to " + to);

    } catch (MessagingException | MailException e) {
        System.err.println("Failed to send email: " + e.getMessage());
        throw new RuntimeException("Failed to send email", e);
    }
}


public void sendDepositNotification(BankAccount bankAccount, BigDecimal depositAmount, BigDecimal newBalance) {
    String userEmail = bankAccount.getUser().getEmail();  // Assuming BankAccount has a userEmail field
    String subject = "Deposit Confirmation - TechCode Bank";
    String body = "<html><body>"
            + "Dear " + bankAccount.getUser().getName() + ",<br><br>"
            + "We are pleased to inform you that a deposit of <strong>" + depositAmount + "</strong> has been successfully credited to your account.<br><br>"
            + "<strong>New Balance:</strong> " + newBalance + "<br>"
            + "<strong>Transaction Date:</strong> " + LocalDateTime.now() + "<br><br>"
            + "Thank you for banking with us!<br><br>"
            + "Warm regards,<br>"
            + "TechCode Bank Team"
            + "</body></html>";

    sendEmail(userEmail, subject, body);
}

public void sendWithdrawalNotification(BankAccount bankAccount, BigDecimal withdrawalAmount, BigDecimal newBalance) {
    String userEmail = bankAccount.getUser().getEmail();; 
    String subject = "Withdrawal Confirmation - TechCode Bank";
    String body = "<html><body>"
            + "Dear " +  bankAccount.getUser().getName() + ",<br><br>"
            + "We are writing to inform you that a withdrawal of <strong>" + withdrawalAmount + "</strong> was successfully processed from your account.<br><br>"
            + "<strong>New Balance:</strong> " + newBalance + "<br>"
            + "<strong>Transaction Date:</strong> " + LocalDateTime.now() + "<br><br>"
            + "If you did not authorize this transaction or have any concerns, please contact us immediately.<br><br>"
            + "Thank you for banking with us!<br><br>"
            + "Warm regards,<br>"
            + "TechCode Bank Team"
            + "</body></html>";

    sendEmail(userEmail, subject, body);
}

}
