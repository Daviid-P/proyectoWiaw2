package org.escoladeltreball.proyectowiaw2.mail;

import java.io.IOException;
import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;

import org.jsoup.Jsoup;

public class Mail {
	 
    private String mailFrom;
 
    private String mailTo;
    
    private String mailReplyTo;
 
    private String mailCc;
 
    private String mailBcc;
 
    private String mailSubject;
 
    private String mailContent;
 
    private String contentType;
    
    private Date sendDate;
 
    public Mail() {
        this.contentType = "text/plain";
    }

	public Mail(Message message) throws MessagingException, IOException {
        this.contentType = "text/plain";
		setMailContent(message);
		setMailFrom(InternetAddress.toString(message.getFrom()));
		setMailSubject(message.getSubject());
		setMailTo(InternetAddress.toString(message.getReplyTo()));
		setMailSendDate(message.getSentDate());
    }
 
    public String getContentType() {
        return contentType;
    }
 
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
 
    public String getMailBcc() {
        return mailBcc;
    }
 
    public void setMailBcc(String mailBcc) {
        this.mailBcc = mailBcc;
    }
 
    public String getMailCc() {
        return mailCc;
    }
 
    public void setMailCc(String mailCc) {
        this.mailCc = mailCc;
    }
 
    public String getMailFrom() {
        return mailFrom;
    }
 
    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }
    
    public String getMailReplyTo() {
        return mailReplyTo;
    }
 
    public void setMailReplyTo(String mailReplyTo) {
        this.mailReplyTo = mailReplyTo;
    }
 
    public String getMailSubject() {
        return mailSubject;
    }
    
    public String getShortSubject() {
    	String shortSubject = mailSubject.substring(22);
        return shortSubject;
    }
    
    public String getReplySubject() {
    	String subject = getShortSubject();
    	String nextSubject;
    	
    	if (subject.matches("RE([0-9]+)?: (.*)")) {
			if (subject.matches("RE[0-9]+: (.*)")) {
				String current = subject.substring(2,subject.indexOf(':'));
				String next = String.valueOf(Integer.parseInt(current)+1);
				nextSubject = subject.replace(current,next);
			} else {
				nextSubject = subject.replace("RE: ", "RE2: ");
			}
		} else {
			nextSubject = "RE: "+subject;
		}
    	return nextSubject;
		
	}
 
    public void setMailSubject(String mailSubject) {
    	if (mailSubject.startsWith("Clinica Salut Vital - ")) {
    		this.mailSubject = mailSubject;
		} else {
			this.mailSubject = "Clinica Salut Vital - " + mailSubject;
		}
    	
    }
 
    public String getMailTo() {
        return mailTo;
    }
 
    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }
 
    public void setMailSendDate(Date sendDate) {
    	this.sendDate = sendDate;
    }
    
    public Date getMailSendDate() {
        return sendDate;
    }
 
    public String getMailContent() {
    	
        return mailContent;
    }

	public String getMailContentPretty() {
		
	    return Jsoup.parse(mailContent).text();
	}
	
	public String getMailContentPreview() {
		
		String preview = getMailContentPretty();
		
		if (preview.length() > 50) {
			preview = preview.substring(0, 50)+"(...)";
		}
		
		return preview;
	}
 
    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }
    
    public void setMailContent(Object mailContent) throws MessagingException, IOException {
    	this.mailContent = getText((Part)mailContent);
    }
    
    private String getText(Part p) throws MessagingException, IOException {
		if (p.isMimeType("text/*")) {
			String s = (String)p.getContent();
			return s;
		}
		
		if (p.isMimeType("multipart/alternative")) {
			// prefer html text over plain text
			Multipart mp = (Multipart)p.getContent();
			String text = null;
			for (int i = 0; i < mp.getCount(); i++) {
				Part bp = mp.getBodyPart(i);
				if (bp.isMimeType("text/plain")) {
					if (text == null)
						text = getText(bp);
					continue;
				} else if (bp.isMimeType("text/html")) {
					String s = getText(bp);
					if (s != null)
						return s;
				} else {
					return getText(bp);
				}
			}
			return text;
		} else if (p.isMimeType("multipart/*")) {
			Multipart mp = (Multipart)p.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
			    String s = getText(mp.getBodyPart(i));
			    if (s != null)
			        return s;
			}
		}
		
		return "Error Leyendo Mensaje";
	}
 
    @Override
    public String toString() {
        StringBuilder lBuilder = new StringBuilder();
        lBuilder.append("Mail From:- ").append(getMailFrom());
        lBuilder.append("Mail To:- ").append(getMailTo());
        lBuilder.append("Mail Cc:- ").append(getMailCc());
        lBuilder.append("Mail Bcc:- ").append(getMailBcc());
        lBuilder.append("Mail Subject:- ").append(getMailSubject());
        lBuilder.append("Mail Send Date:- ").append(getMailSendDate());
        lBuilder.append("Mail Content:- ").append(getMailContent());
        return lBuilder.toString();
    }
 
}