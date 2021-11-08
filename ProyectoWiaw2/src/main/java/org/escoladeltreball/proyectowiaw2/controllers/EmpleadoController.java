package org.escoladeltreball.proyectowiaw2.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.escoladeltreball.proyectowiaw2.AccessCheck;
import org.escoladeltreball.proyectowiaw2.entities.Contrato;
import org.escoladeltreball.proyectowiaw2.entities.Empleado;
import org.escoladeltreball.proyectowiaw2.repositories.DirectorDAO;
import org.escoladeltreball.proyectowiaw2.repositories.DoctorDAO;
import org.escoladeltreball.proyectowiaw2.repositories.EmpleadoDAO;
import org.escoladeltreball.proyectowiaw2.repositories.PacienteDAO;
import org.escoladeltreball.proyectowiaw2.repositories.RecepcionistaDAO;
import org.escoladeltreball.proyectowiaw2.repositories.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EmpleadoController {
	
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
	EmpleadoDAO empleadoDao;
	
	@Autowired
	AccessCheck accessCheck;
	
	//CONTRATO################################################################################################################################################
	
	//Permite consulta tu propio contrato
	@RequestMapping("/viewContrato")
	public String viewContrato(Locale locale, Model model, Principal principal){
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoEmpleado(principal.getName());
			///////////////////////////////////////////////////
		} catch (AccessDeniedException e) {
			List<String> mensajes = new ArrayList<String>();
			model.addAttribute("error","Acceso no Permitido.");
			mensajes.add("Esta pagina es solo para empleados.");
			mensajes.add("Solo un empleado puede ver su propio contrato.");
			model.addAttribute("mensajes",mensajes);
			return "mensaje";
		}
		
		//Obtenemos el usuario y su ROL
		String dni = principal.getName();
		
		Empleado emp = empleadoDao.getEmpleado(dni);
		
		Contrato contrato = emp.getContrato();
		
		model.addAttribute("empleado", emp);
		model.addAttribute("contrato", contrato);
		
		return "info-contrato";
		
	}

}
