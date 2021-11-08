package org.escoladeltreball.proyectowiaw2.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service("mailService")
public class MailServiceImpl implements MailService {
 
	private JavaMailSender mailSender;

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
 
    @Override
    public void sendEmail(Mail mail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mail.getMailFrom());
        message.setTo(mail.getMailTo());
        if (mail.getMailReplyTo() != null) {
        	message.setReplyTo(mail.getMailReplyTo());
        } else {
        	message.setReplyTo(mail.getMailFrom());
        }
        message.setSubject(mail.getMailSubject());
        message.setText(mail.getMailContent());
        message.setSentDate(mail.getMailSendDate());
        mailSender.send(message);
    }
 
}