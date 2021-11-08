package org.escoladeltreball.proyectowiaw2.controllers;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.escoladeltreball.proyectowiaw2.entities.Autoridad;
import org.escoladeltreball.proyectowiaw2.entities.Usuario;
import org.escoladeltreball.proyectowiaw2.mail.Inbox;
import org.escoladeltreball.proyectowiaw2.mail.Mail;
import org.escoladeltreball.proyectowiaw2.mail.MailServiceImpl;
import org.escoladeltreball.proyectowiaw2.repositories.DirectorDAO;
import org.escoladeltreball.proyectowiaw2.repositories.PacienteDAO;
import org.escoladeltreball.proyectowiaw2.repositories.UsuarioDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InboxController {
	
	@Autowired
	UsuarioDAO usuarioDao;
	
	@Autowired
	PacienteDAO pacienteDao;
	
	@Autowired
	DirectorDAO directorDao;

	@Autowired
	MailServiceImpl mailService;

	@Autowired
	JavaMailSenderImpl mailSender;

	@Autowired
	Inbox inbox;
	
	@Autowired
	Environment env;
	
	private static final Logger logger = LoggerFactory.getLogger(InboxController.class);
	
	@RequestMapping(value = "/inbox", method = RequestMethod.GET)
	public String inbox(Locale locale, Model model, Principal principal, HttpSession session) {
		
		Usuario usuario = usuarioDao.usuario(principal.getName());
		
		if (session.getAttribute("password") == null) {
			model.addAttribute("url","/inbox");
			return "login";
		} else {
			
			String password = session.getAttribute("password").toString();
			
			inbox.setEmail(usuario.getEmail());
			inbox.setPassword(password);
			
			try {
				List<Mail> messages = inbox.read();
				model.addAttribute("messages", messages);
				
				List<String> emails = new ArrayList<String>();
				
				String role = "";
				List<Autoridad> autoridades = usuario.getAutoridades();
				for(Autoridad autoridad: autoridades){			
					if(autoridad.getAutoridad().equals("ROLE_PACIENTE")){
						role = "ROLE_PACIENTE";
					}
				}
				
				if (role.equals("ROLE_PACIENTE")) {
					emails.add(pacienteDao.getDoctor(usuario.getDni()).getEmail());
				} else {
					emails = directorDao.getAllEmails();
				}
				
				model.addAttribute("emails", emails);
				
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				if (e.getMessage().contains("Invalid credentials")) {
					List<String> errores = new ArrayList<String>();
					errores.add("Error al conectar al correo, revisa email y contraseña.");
					// Nuestra aplicacion es segura :D
					//errores.add("Tal vez necesites autorizar el acceso a <a href='https://myaccount.google.com/lesssecureapps'>aplicaciones menos seguras</a>.");
					errores.add("Si tienes habilitada la verificación en dos pasos necesitas generar una <a href='https://security.google.com/settings/security/apppasswords'>contraseña de aplicación</a>.");
					model.addAttribute("error",errores);
					// La contraseña podria estar mal, la borramos
					session.removeAttribute("password");
					model.addAttribute("url","/inbox");
					return "/login";
				} else if (e.getMessage().contains("Please log in via your web browser")) {
					List<String> errores = new ArrayList<String>();
					errores.add("Error al conectar al correo, revisa email y contraseña.");
					errores.add("Si tienes habilitada la verificación en dos pasos necesitas generar una <a href='https://security.google.com/settings/security/apppasswords'>contraseña de aplicación</a>.");
					errores.add("Si no tienes habilitada la verificación en dos pasos necesitas <a href=\"https://support.google.com/accounts/answer/6010255\">permitir que aplicaciones menos seguras accedan a tu cuenta</a>.");
					errores.add("Si no has podido solucionar el problema con los consejos anteriores, ve a https://www.google.com/accounts/DisplayUnlockCaptcha y sigue los pasos indicados en la página.");
					model.addAttribute("error",errores);
					// La contraseña podria estar mal, la borramos
					session.removeAttribute("password");
					model.addAttribute("url","/inbox");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return "inbox";
		}
	}
	
	@PostMapping(value = "/inbox")
	public String inboxPost(Locale locale, Model model, Principal principal, HttpSession session,
							@RequestParam("password") String password) {
		// Solo para guardar la contraseña como variable de sesion
		
		session.setAttribute("password", password);
		
		return "redirect:/inbox";
		
	}
	
	@PostMapping(value="/sendMail")
	public String sendMail(Model model, Principal principal, HttpSession session,
							@RequestParam("to") String to,
							@RequestParam("subject") String subject,
							@RequestParam("content") String content) {
		
		Usuario usuario = usuarioDao.usuario(principal.getName());
		
		mailSender.setUsername(usuario.getEmail());
		mailSender.setPassword(session.getAttribute("password").toString());
		
		mailService.setMailSender(mailSender);
		
		Mail mail = new Mail();
		mail.setMailFrom(usuario.getEmail());
		mail.setMailTo(to);
		// Añadimos al sujeto nuestra cabecera
		//System.out.println("FIND1 "+subject);
		mail.setMailSubject(subject);
		//System.out.println(mail.getMailSubject());
		mail.setMailContent(content);
		mail.setMailSendDate(new Date());
		
		mailService.sendEmail(mail);
		
		return "redirect:/inbox";
	}
	
	@PostMapping(value="/contactUs")
	public String contactUs(Model model, Principal principal,
							@RequestParam("name") String name,
							@RequestParam("email") String from,
							@RequestParam("message") String message) {
		
		// Correo usado para consultas
		mailSender.setUsername(env.getProperty("mail.smtp.email"));
		mailSender.setPassword(env.getProperty("mail.smtp.password"));
		
		mailService.setMailSender(mailSender);
		
		Mail mail = new Mail();
		// GMAIL no permite cambiar el campo FROM (Ahora mismo clinicasalutvital@gmail.com envia a clinicasalutvital@gmail.com)
		mail.setMailFrom(from);
		// Si pudieramos poner un FROM diferente esto no haria falta
		mail.setMailTo(env.getProperty("mail.smtp.email"));
		// Añadimos al sujeto nuestra cabecera
		mail.setMailSubject("Contact Us - "+name);
		mail.setMailContent(message);
		mail.setMailSendDate(new Date());
		
		mailService.sendEmail(mail);
		
		return "redirect:/";
	}
	
}
