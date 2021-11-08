package org.escoladeltreball.proyectowiaw2.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

import org.escoladeltreball.proyectowiaw2.entities.Autoridad;
import org.escoladeltreball.proyectowiaw2.entities.Usuario;
import org.escoladeltreball.proyectowiaw2.repositories.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	
	@Autowired
	UsuarioDAO usuarioDao;
	
	@RequestMapping(value = "/dash", method = RequestMethod.GET)
	public String logged(Locale locale, Model model, Principal principal) {
		
		//Obtenemos el usuario y su ROL
//		Boolean paciente = false;
//		Boolean doctor = false;
//		Boolean recepcionista = false;
//		Boolean director = false;
		
		Usuario usuario = usuarioDao.usuario(principal.getName());
//		List<Autoridad> autoridades = usuario.getAutoridades();
//		for(Autoridad autoridad: autoridades){			
//			if(autoridad.getAutoridad().equals("ROLE_PACIENTE")){
//				paciente = true;
//			}
//			else if(autoridad.getAutoridad().equals("ROLE_DOCTOR")){
//				doctor = true;
//			}
//			else if(autoridad.getAutoridad().equals("ROLE_RECEPCIONISTA")){
//				recepcionista = true;
//			}
//			else if(autoridad.getAutoridad().equals("ROLE_DIRECTOR")){
//				director = true;
//			}
//		}
//		
//		model.addAttribute("paciente", paciente);
//		model.addAttribute("doctor", doctor);
//		model.addAttribute("recepcionista", recepcionista);
//		model.addAttribute("director", director);
		
		String nombre = usuario.getNombre()+" "+usuario.getApellidos();
		model.addAttribute("name", nombre);
		
		return "dash";
		
	}
		
}
