package com.example.emailsend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;

@RestController
public class SendEmailController {

    @Autowired
    private JavaMailSender javaMailSender;


    @PostMapping("/sendSimpleMail")
    public  String sendSimpleMain(@RequestParam String reciever, @RequestParam String message, @RequestParam  String subject){

        SimpleMailMessage msg= new SimpleMailMessage();

        msg.setTo(reciever);

        msg.setSubject(subject);

        msg.setText(message);

        javaMailSender.send(msg);

        return "Send message successfully";

    }

    @PostMapping("/sendAttachMail")
    public String sendAttachMail(@RequestParam String sender, @RequestParam String reciever, @RequestParam String msg, @RequestParam MultipartFile file, @RequestParam String subject) throws MessagingException, IOException {

        MimeMessage message= javaMailSender.createMimeMessage();

        MimeMessageHelper helper= new MimeMessageHelper(message,true);

        helper.setFrom(sender);

        helper.setTo(reciever);

        helper.setSubject(subject);

        helper.setText(String.format(msg));

        File file1 = File.createTempFile("file", "." + file.getContentType().split("/")[1]);

        file.transferTo(file1);

        helper.addAttachment(file1.getName(), file1);

        javaMailSender.send(message);

        return "Send Message Successfully";



    }

}
