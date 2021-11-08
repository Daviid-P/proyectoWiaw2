package org.escoladeltreball.proyectowiaw2.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.escoladeltreball.proyectowiaw2.entities.PasswordResetToken;
import org.escoladeltreball.proyectowiaw2.entities.Usuario;
import org.escoladeltreball.proyectowiaw2.mail.Mail;
import org.escoladeltreball.proyectowiaw2.mail.MailServiceImpl;
import org.escoladeltreball.proyectowiaw2.repositories.PasswordResetTokenDAO;
import org.escoladeltreball.proyectowiaw2.repositories.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResetPasswordController {
	
	@Autowired
	UsuarioDAO usuariodao;
	
	@Autowired
	PasswordResetTokenDAO passwordResetTokenDao;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	MailServiceImpl mailService;
	
	@Autowired
	JavaMailSenderImpl mailSender;
	
	@RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
	public String resetPassword(Locale locale, Model model,
										@RequestParam(value = "dni", required = false) String dni,
										@RequestParam(value = "token", required = false) String token) {
		
	    if (dni != null && token != null) {
	    	// Existe dni y token, el usuario ha hecho click en el enlace
	    	if (passwordResetTokenDao.validate(token,dni)) {
	    		// Todo bien
	    		Usuario usuario = usuariodao.usuario(dni);
	    		// Autenticamos al usuario 
	    	    Authentication auth = new UsernamePasswordAuthenticationToken(usuario, 
	    	    															  null, 
	    	    															  Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_AUTH")));
	    	    SecurityContextHolder.getContext().setAuthentication(auth);
	    	    
	    	    return "changePassword";
			} else {
				List<String> mensajes = new ArrayList<String>();
				mensajes.add("Solicita otro enlace para restablecer la contraseña");
				
				model.addAttribute("error","Enlace Expirado.");
				model.addAttribute("mensajes", mensajes);
				return "mensaje";
			}
		} else {
			// No existen dni y token el usuario quiere restablecer la contraseña
			return "forgot";
		}
	}
	
	@PostMapping(value = "/resetPassword")
	public String resetPasswordPost(Principal principal, Model model,
										 @RequestParam("dni") String dni){
		
		try {
			List<String> mensajes = new ArrayList<String>();
			
			Usuario usuario = usuariodao.usuario(dni);
			String token = UUID.randomUUID().toString();
			// Guarda Token en base de datos
			usuariodao.saveResetToken(token, usuario);
			
			// Envia correo
			mailSender.setUsername("daviidcou@gmail.com");
			mailSender.setPassword("shktrtedmexzmwyl");
			
			mailService.setMailSender(mailSender);
			
			Mail mail = new Mail();
			mail.setMailFrom("daviidcou@gmail.com");
			mail.setMailTo(usuario.getEmail());
			// Añadimos al sujeto nuestra cabecera
			mail.setMailSubject("Restablecer Contraseña");
			mail.setMailContent("Haz click en el siguiente enlace para restablecer tu contraseña:\nhttp://localhost:8080/proyectowiaw2/resetPassword?dni="+dni+"&token="+token);
			mail.setMailSendDate(new Date());
			
			mailService.sendEmail(mail);
			
			mensajes.add("Te hemos enviado un enlace para restablecer tu contraseña.");
			mensajes.add("Si no lo puedes ver revisa tu bandeja de SPAM.");
			
			model.addAttribute("info","Correo enviado.");
			model.addAttribute("mensajes", mensajes);
		} catch (Exception e) {
			System.out.println("Error buscando DNI");
			List<String> errores = new ArrayList<String>();
			errores.add("Revisa que el DNI este bien escrito.");
			errores.add("Usuario no encontrado");
			
			model.addAttribute("errores",errores);
			return "forgot";
		}
		
		return "mensaje";
	}
	
	@PostMapping(value = "/changePassword")
	public String changePassword(Model model,
			 					 @RequestParam("password1") String password) {
		
		Usuario usuario = (Usuario)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		System.out.println("new password: "+password);
		
		usuario.setPassword(passwordEncoder.encode(password));
		
		usuariodao.save(usuario);
		
		List<String> mensajes = new ArrayList<String>();
		mensajes.add("Se ha cambiado tu contraseña, <a th:href='@{/login}'>inicia sesion</a> con tu nueva contraseña");
		
		model.addAttribute("info","Contraseña Actualizada.");
		model.addAttribute("mensajes", mensajes);
		return "mensaje";
		
	}
}
