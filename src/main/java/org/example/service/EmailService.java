//package org.example.service;
//
//import javax.mail.*;
//import javax.mail.internet.*;
//import java.util.Properties;
//
//public class EmailService {
//
//    public void sendEmail(String to, String subject, String body) {
//        // Настройки сервера почты
//        final String smtpHost = "smtp.gmail.com"; // Или адрес SMTP-сервера вашего почтового провайдера
//        final String fromEmail = "culinaryexchangemail@gmail.com"; // Ваш email
//        final String fromPassword = "culinaryexchange12345"; // Ваш пароль
//
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", smtpHost);
//        props.put("mail.smtp.port", "587"); // Порт SMTP-сервера
//
//        Session session = Session.getInstance(props,
//                new javax.mail.Authenticator() {
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(fromEmail, fromPassword);
//                    }
//                });
//
//        try {
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(fromEmail));
//            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
//            message.setSubject(subject);
//            message.setText(body);
//
//            Transport.send(message);
//
//            System.out.println("Письмо успешно отправлено.");
//        } catch (MessagingException e) {
//            System.err.println("Ошибка отправки письма: " + e.getMessage());
//        }
//    }
//}

