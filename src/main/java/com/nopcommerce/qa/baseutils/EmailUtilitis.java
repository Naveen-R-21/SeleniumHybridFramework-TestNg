package com.nopcommerce.qa.baseutils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import java.io.File;
import java.util.Properties;

public class EmailUtilitis {

    public static void sendEmailWithAttachment(String Email, String Password, String toEmail, String subject, String body, String attachmentPath) {
        // Replace with your email and password
        final String username = Email;
        final String password = Password;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("https.protocols", "TLSv1.2,TLSv1.3"); 

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);

            // Create a multipart message
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Attach the file
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(attachmentPath);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(new File(attachmentPath).getName());
            multipart.addBodyPart(messageBodyPart);

            // Set the complete message parts
            message.setContent(multipart);

            // Send the message
            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

 
}
