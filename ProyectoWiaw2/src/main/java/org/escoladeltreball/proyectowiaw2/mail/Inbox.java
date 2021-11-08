package org.escoladeltreball.proyectowiaw2.mail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

public class Inbox {
	
	private String email;
	private String password;
	private String host;
	private String port;
	
	private Store store;
 
    public Inbox() {}
    
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
    
    private Boolean connect() throws MessagingException {
    	Properties props = new Properties();
    	
    	props.setProperty("mail.imaps.host", this.host);
    	props.setProperty("mail.imaps.port", this.port);
    	
        Session session = Session.getDefaultInstance(props, null);
		
        try {
			this.store = session.getStore("imaps");
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			return false;
		}
		
		this.store.connect(this.host, this.email, this.password);
        
        return true;
    }

	public List<Mail> read() throws MessagingException, IOException {		
		if (connect()){
			Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            
            SearchTerm search = new SubjectTerm("Clinica Salut Vital -");
            
            Message[] messages = inbox.search(search);
            List<Mail> mails = new ArrayList<Mail>();
            
            for (int i = 0; i < messages.length; i++) {
            	// Copiamos los mensajes a una lista nueva
            	// Si no, al cerrar folder no podemos leerlos
            	Mail mail = new Mail(messages[i]);
            	mails.add(mail);
            	
//            	mail.setMailFrom(InternetAddress.toString(messages[i].getFrom()));
//            	
//            	try {
//					mail.setMailContent(messages[i]);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					mail.setMailContent("Error leyendo email.");
//				}
//            	mail.setMailSubject(messages[i].getSubject());
//            	mail.setMailSendDate(messages[i].getSentDate());
			}
            
            inbox.close(true);
            store.close();
            
            return mails;
		}
		return null;
    }
}