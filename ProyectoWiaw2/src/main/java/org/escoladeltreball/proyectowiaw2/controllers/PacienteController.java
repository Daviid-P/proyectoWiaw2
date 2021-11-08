package org.escoladeltreball.proyectowiaw2.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.escoladeltreball.proyectowiaw2.AccessCheck;
import org.escoladeltreball.proyectowiaw2.entities.Autoridad;
import org.escoladeltreball.proyectowiaw2.entities.Doctor;
import org.escoladeltreball.proyectowiaw2.entities.Expediente;
import org.escoladeltreball.proyectowiaw2.entities.Paciente;
import org.escoladeltreball.proyectowiaw2.entities.Prueba;
import org.escoladeltreball.proyectowiaw2.entities.Usuario;
import org.escoladeltreball.proyectowiaw2.repositories.DirectorDAO;
import org.escoladeltreball.proyectowiaw2.repositories.DoctorDAO;
import org.escoladeltreball.proyectowiaw2.repositories.PacienteDAO;
import org.escoladeltreball.proyectowiaw2.repositories.RecepcionistaDAO;
import org.escoladeltreball.proyectowiaw2.repositories.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PacienteController {
	
	@Autowired
	UsuarioDAO usuarioDao;
	
	@Autowired
	RecepcionistaDAO recepcionistaDao;

	@Autowired
	PacienteDAO pacienteDao;
	
	@Autowired
	DoctorDAO doctorDao;
	
	@Autowired
	DirectorDAO directorDao;
	
	@Autowired
	AccessCheck accessCheck;
	
	//Muestra info del doctor de cabecera de un paciente
	@RequestMapping("/doctor")
	public String doctor(Locale locale, Model model, Principal principal){
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoPaciente(principal.getName());
			///////////////////////////////////////////////////
		} catch (AccessDeniedException e) {
			List<String> mensajes = new ArrayList<String>();
			model.addAttribute("error","Acceso no Permitido.");
			mensajes.add("Esta pagina es solo para pacientes.");
			model.addAttribute("mensajes",mensajes);
			return "mensaje";
		}
		
		Doctor doctor = pacienteDao.getDoctor(principal.getName());
		
		model.addAttribute("doctor", doctor);
		
		return "info-doctor";
	}
	//Muestra las visitas del paciente que las solicite
	@RequestMapping(value="/listPacienteVisitas", method = RequestMethod.GET)
	public String listVisitas(Locale locale, Model model, Principal principal) {
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoPaciente(principal.getName());
			///////////////////////////////////////////////////
		} catch (AccessDeniedException e) {
			List<String> mensajes = new ArrayList<String>();
			model.addAttribute("error","Acceso no Permitido.");
			mensajes.add("Esta pagina es solo para pacientes.");
			model.addAttribute("mensajes",mensajes);
			return "mensaje";
		}
		
		Paciente paciente = pacienteDao.getPaciente(principal.getName());
		
		model.addAttribute("visitas", paciente.getVisitas());
		
//		String role = "";
		Usuario usuario = usuarioDao.usuario(principal.getName());
//		List<Autoridad> autoridades = usuario.getAutoridades();
//		for(Autoridad autoridad: autoridades){			
//			if(autoridad.getAutoridad().equals("ROLE_DOCTOR")){
//				role = "ROLE_DOCTOR";
//			}
//			else if(autoridad.getAutoridad().equals("ROLE_RECEPCIONISTA")){
//				role = "ROLE_RECEPCIONISTA";
//			}
//		}
		
//		model.addAttribute("rol", role);
		
		return "visita-list";
	}
	
	//Muestra las pruebas del paciente que las solicite
	@RequestMapping(value="/listMyPruebas", method = RequestMethod.GET)
	public String listMyPruebas(Locale locale, Model model, Principal principal) {
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoPaciente(principal.getName());
			///////////////////////////////////////////////////
		} catch (AccessDeniedException e) {
			List<String> mensajes = new ArrayList<String>();
			model.addAttribute("error","Acceso no Permitido.");
			mensajes.add("Esta pagina es solo para pacientes.");
			model.addAttribute("mensajes",mensajes);
			return "mensaje";
		}
		
		Paciente paciente = pacienteDao.getPaciente(principal.getName());
		
//		List<String> roles = new ArrayList<String>();
		Usuario usuario = usuarioDao.usuario(principal.getName());
//		List<Autoridad> autoridades = usuario.getAutoridades();
//		for(Autoridad autoridad: autoridades){			
//			roles.add(autoridad.getAutoridad());
//		}
		
		List<Prueba> pruebas = paciente.getPruebas();
		
		model.addAttribute("pruebas", pruebas);
//		model.addAttribute("rols", roles);
		
		return "pruebas-list";
	}

	@RequestMapping("/expediente")
	public String expediente(Locale locale, Model model, Principal principal){
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoPaciente(principal.getName());
			///////////////////////////////////////////////////
		} catch (AccessDeniedException e) {
			List<String> mensajes = new ArrayList<String>();
			model.addAttribute("error","Acceso no Permitido.");
			mensajes.add("Esta pagina es solo para pacientes.");
			mensajes.add("Solo un paciente puede consultar esta url.");
			model.addAttribute("mensajes",mensajes);
			return "mensaje";
		}

		// Url directa para que un paciente vea su expediente
		
		Paciente paciente = recepcionistaDao.findPacienteByDni(principal.getName());
		Expediente expediente = paciente.getExpediente();
		
		model.addAttribute("expediente", expediente);
		model.addAttribute("paciente", paciente);
		
		return "info-expediente";	
	}

}
