package com.telegroup_ltd.vehicle_reservation.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class Notification {

    private final JavaMailSender mailSender;

    @Autowired
    public Notification(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendMail(String from,String to, String subject, String body){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
        System.out.println("Mail sent!");
    }

    @Async
    public void sendInvite(String email, String token) {
        SimpleMailMessage message=new SimpleMailMessage();
        message.setSubject("Vehicle Reservation - Registracija");
        message.setFrom("dejan.gajic.mdp@gmail.com ");
        message.setTo(email);
        String builder = "Ovo je poziv za registraciju na Vehicle Reservation" +
                "\n" +
                "Molimo kliknite na sljedeći link da biste se registrovali: " +
                "https://localhost:8443?q=reg&t=" + token +
                "\n" +
                "Poziv za registraciju važi 24 časa";
        message.setText(builder);
        mailSender.send(message);
    }
}
