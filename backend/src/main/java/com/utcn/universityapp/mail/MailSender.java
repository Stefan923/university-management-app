package com.utcn.universityapp.mail;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;

public class MailSender {

    private static final String EMAIL = "msi.university.no.reply@gmail.com";
    private static final String PASSWORD = "1234test";

    private static final String MAIL_SUBJECT = "MSI University - Confirm your account";
    private static final String MAIL_CONTENT = "MSI University - Confirm your account";
    private static final String MAIL_CONTENT_TYPE = "text/html";

    private static final Properties properties = new Properties();

    static {
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
    }

    public void sendEmailConfirmationMail(String receiver) throws MessagingException {
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, PASSWORD);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(EMAIL, false));

        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
        message.setSubject(MAIL_SUBJECT);
        message.setContent(MAIL_CONTENT, MAIL_CONTENT_TYPE);
        message.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(MAIL_CONTENT, MAIL_CONTENT_TYPE);

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        message.setContent(multipart);
        Transport.send(message);
    }

}
