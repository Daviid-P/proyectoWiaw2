package org.escoladeltreball.proyectowiaw2.controllers;

import java.security.Principal;
import java.util.Locale;

import javax.validation.Valid;

import org.escoladeltreball.proyectowiaw2.AccessCheck;
import org.escoladeltreball.proyectowiaw2.beans.ChangeProfileBean;
import org.escoladeltreball.proyectowiaw2.entities.Usuario;
import org.escoladeltreball.proyectowiaw2.repositories.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioController {
	
	@Autowired
	UsuarioDAO usuarioDao;
	
	@Autowired
	AccessCheck accessCheck;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	
	//Muestra la información de perfil de un usuario
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String addPacienteForm(Locale locale, Model model, Principal principal) {
		
		
		//Para realizar validación de usuario en servidor
		model.addAttribute(new ChangeProfileBean());
				
		Usuario usuario = usuarioDao.usuario(principal.getName());
		model.addAttribute("usuario", usuario);
		
		return "perfil";	
	}
	
	//Procesa los datos de modificación de perfil de un usuario
	//isPasswordValid() http://stackoverflow.com/questions/24115991/grails-spring-security-how-to-compare-passwords
	@PostMapping(value = "/changeProfile")
	public String changeProfile(Principal principal, Model model, @Valid ChangeProfileBean changeProfileBean, BindingResult result, RedirectAttributes redirectAttributes,
			@RequestParam(value= "email", required = false)  String email,
			@RequestParam(value= "telefono", required = false)  String telefono,
			@RequestParam(value= "direccion", required = false)  String direccion,
			@RequestParam(value= "codigoPostal", required = false)  String codigoPostal,
			@RequestParam(value= "password", required = false)  String oldPassword,
			@RequestParam(value= "newPassword", required = false)  String newPassword){
		
		Usuario usuario = usuarioDao.usuario(principal.getName());
		model.addAttribute("usuario", usuario);
		
		//Validación en servidor
		if(result.hasErrors()){
			
			redirectAttributes.addFlashAttribute(changeProfileBean);
			
			return "redirect:/profile";
		}
		
		
		if(!email.equals("")){
			usuario.setEmail(email);
		}
		
		//Comprueba que las contraseñas coincidan y si es así la modifica
		if(!oldPassword.equals("") && !newPassword.equals("")){
			String dbPassword = usuario.getPassword();

			if(passwordEncoder.matches(oldPassword, dbPassword)){
				usuario.setPassword(passwordEncoder.encode(newPassword));
			}
			else{
				redirectAttributes.addFlashAttribute("errPass", "La contraseña introducida no es correcta");
			}
			
		} 	
		
		if(!direccion.equals("")){
			usuario.setDireccion(direccion);
		}
		
		if(!codigoPostal.equals("")){
			usuario.setCodigoPostal(codigoPostal);
		}
		
		if(!telefono.equals("")){
			long newTelefono = Long.parseLong(telefono, 10);
			usuario.setTelefono(newTelefono);
		}
		
		usuarioDao.save(usuario);
		
		//return "perfil";
		return "redirect:/profile";
	}
	
	//Muestra la información de perfil de un usuario
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public String info(Locale locale, Model model, Principal principal) {
		
		return "info-faq";	
	}
}
