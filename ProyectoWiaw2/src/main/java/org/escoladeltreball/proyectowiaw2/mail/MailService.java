package org.escoladeltreball.proyectowiaw2.mail;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public interface MailService extends Serializable {

	public void sendEmail(Mail mail);
}
