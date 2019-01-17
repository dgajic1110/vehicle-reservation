package com.telegroup_ltd.vehicle_reservation.util;

import com.telegroup_ltd.vehicle_reservation.common.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Component
public class Notification {

    @Value("${mail.sender}")
    private String mailSender;

    @Value("${mail.password}")
    private String mailPassword;

    @Value("${mail.host}")
    private String mailHost;

    @Value("${mail.port}")
    private String mailPort;

    @Value("${mail.auth}")
    private String mailAuth;

    @Value("${mail.socketFactory.port}")
    private String mailSocketFactoryPort;

    @Value("${mail.socketFactory.class}")
    private String mailSocketFactoryClass;

    @Async
    public void sendMail(String email, String subject, String body) throws BadRequestException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", mailHost);
        properties.put("mail.smtp.port", mailPort);
        properties.put("mail.smtp.auth", mailAuth);
        properties.put("mail.smtp.socketFactory.port", mailSocketFactoryPort);
        properties.put("mail.smtp.socketFactory.class", mailSocketFactoryClass);

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailSender, mailPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailSender, "Vehicle Reservation"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new BadRequestException("Recipient mail not found.");
        }
    }

    @Async
    public void sendInvite(String email, String token) throws BadRequestException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", mailHost);
        properties.put("mail.smtp.port", mailPort);
        properties.put("mail.smtp.auth", mailAuth);
        properties.put("mail.smtp.socketFactory.port", mailSocketFactoryPort);
        properties.put("mail.smtp.socketFactory.class", mailSocketFactoryClass);

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailSender, mailPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailSender, "Vehicle Reservation"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Vehicle Reservation - Registracija");

            String builder = "Ova poruka služi za registraciju na Vehicle Reservation. " +
                    "Molimo kliknite na sljedeći link da biste se registrovali: " +
                    "http://localhost:8090 . Nakon klika na dugme registracija, potrebno je da unesete token." +
                    " Vaš token je " + token + " . Ovaj poziv za registraciju važi 24 časa.";

            message.setText(builder);

            Transport.send(message);

        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new BadRequestException("Recipient mail not found.");
        }
    }
}
