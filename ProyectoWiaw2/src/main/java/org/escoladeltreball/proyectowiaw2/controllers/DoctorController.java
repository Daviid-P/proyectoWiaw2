package org.escoladeltreball.proyectowiaw2.controllers;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.escoladeltreball.proyectowiaw2.AccessCheck;
import org.escoladeltreball.proyectowiaw2.entities.Autoridad;
import org.escoladeltreball.proyectowiaw2.entities.Doctor;
import org.escoladeltreball.proyectowiaw2.entities.Paciente;
import org.escoladeltreball.proyectowiaw2.entities.Prueba;
import org.escoladeltreball.proyectowiaw2.entities.Resultado;
import org.escoladeltreball.proyectowiaw2.entities.TipoPrueba;
import org.escoladeltreball.proyectowiaw2.entities.Usuario;
import org.escoladeltreball.proyectowiaw2.entities.Visita;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DoctorController {
	
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
	
	//VISITAS################################################################################################################################################
	
	//Lista las visitas de el doctor que realiza la consulta
	@RequestMapping(value="/listMyVisitas", method = RequestMethod.GET)
	public String listMyVisitas(Locale locale, Model model, Principal principal) {
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoDoctor(principal.getName());
			///////////////////////////////////////////////////
		} catch (AccessDeniedException e) {
			List<String> mensajes = new ArrayList<String>();
			model.addAttribute("error","Acceso no Permitido.");
			mensajes.add("Esta pagina es solo para doctores.");
			mensajes.add("Solo un doctor puede consultar sus propias visitas.");
			model.addAttribute("mensajes",mensajes);
			return "mensaje";
		}
		
//		String role = "";
		Doctor doctor = doctorDao.getDoctor(principal.getName());
//		List<Autoridad> autoridades = doctor.getAutoridades();
//		for(Autoridad autoridad: autoridades){			
//			if(autoridad.getAutoridad().equals("ROLE_DOCTOR")){
//				role = "ROLE_DOCTOR";
//			}
//			else if(autoridad.getAutoridad().equals("ROLE_RECEPCIONISTA")){
//				role = "ROLE_RECEPCIONISTA";
//			}
//		}
//		
//		model.addAttribute("rol", role);
		
		//Pasamos las visitas que tiene programadas este doctor
		model.addAttribute("visitas",doctor.getVisitas());
		
		return "visita-list";
	}
	
	//Una vez realizada una visita permite finalizar esta e introducir las observaciones
	@RequestMapping("/finVisita/{id}")
	public String delVisita(Locale locale, Model model, Principal principal,
			@PathVariable("id") long id){
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoDoctor(principal.getName());
			///////////////////////////////////////////////////
		} catch (AccessDeniedException e) {
			List<String> mensajes = new ArrayList<String>();
			model.addAttribute("error","Acceso no Permitido.");
			mensajes.add("Esta pagina es solo para doctores.");
			mensajes.add("Solo un doctor puede finalizar una visita.");
			model.addAttribute("mensajes",mensajes);
			return "mensaje";
		}		
		Visita visita = recepcionistaDao.findOneVisita(id);
		
		model.addAttribute("paciente", visita.getPaciente());
		model.addAttribute("doctor", visita.getDoctor());
		model.addAttribute("recepcionista", visita.getRecepcionista());
		model.addAttribute("visita", visita);
		model.addAttribute("fin", "yes");
		
		return "info-visita";
		
		//return "redirect:/listVisitas";
		
	}
	
	//A��ade observaciones a una visita
	@PostMapping(value = "/addObservaciones/{id}")
	public String addContrato(Principal principal, Model model,  
			@PathVariable("id") long id,
			@RequestParam("observaciones") String observaciones){
		
		Visita visita = recepcionistaDao.findOneVisita(id);
		
		model.addAttribute("paciente", visita.getPaciente());
		model.addAttribute("doctor", visita.getDoctor());
		model.addAttribute("recepcionista", visita.getRecepcionista());
		model.addAttribute("visita", visita);
		
		
		//Solo modificamos las visitas que se encuentran pendientes y que sean propias
		if(!visita.getEstado().equals("Pendiente") || !visita.getDoctor().getDni().equals(principal.getName())){
			model.addAttribute("notPermit", "Esta acción no está permitida, solo puedes añadir observaciones en visitas pendientes y propias");
			
			model.addAttribute("fin", "no");
			
			return "info-visita";
		}
		else{
			visita.setObservaciones(observaciones);
			visita.setEstado("Finalizada");
			
			model.addAttribute("fin", "no");
			
			doctorDao.mergeVisita(visita);
			
			return "info-visita";
		}
		
	}
	
	//PRUEBAS################################################################################################################################################
	
	//Lista todas las pruebas
	@RequestMapping(value="/listPruebas", method = RequestMethod.GET)
	public String listPruebas(Locale locale, Model model, Principal principal) {
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoMedicina(principal.getName());
			///////////////////////////////////////////////////
		} catch (AccessDeniedException e) {
			List<String> mensajes = new ArrayList<String>();
			model.addAttribute("error","Acceso no Permitido.");
			mensajes.add("Esta pagina es solo para doctores o directores.");
			mensajes.add("Solo un doctor o directivo pueden consultar todas las pruebas realizadas.");
			model.addAttribute("mensajes",mensajes);
			return "mensaje";
		}
		
//		List<String> roles = new ArrayList<String>();
		Usuario usuario = usuarioDao.usuario(principal.getName());
//		List<Autoridad> autoridades = usuario.getAutoridades();
//		for(Autoridad autoridad: autoridades){			
//			roles.add(autoridad.getAutoridad());
//		}
		
//		model.addAttribute("rols", roles);
		
		List<Prueba> pruebas = doctorDao.getAllPruebas();
		
		model.addAttribute("pruebas", pruebas);
		
		return "pruebas-list";
	}
	
	//Lista las pruebas de un paciente
	@RequestMapping(value="/listPruebas/{dni}", method = RequestMethod.GET)
	public String listPruebasOfPacient(Locale locale, Model model, Principal principal,
			@PathVariable("dni") String dni) {
		
		///////////////////////////////////////////////////		
		accessCheck.metodoUsuario(principal.getName());
		///////////////////////////////////////////////////
		
//		List<String> roles = new ArrayList<String>();
		Usuario usuario = usuarioDao.usuario(principal.getName());
//		List<Autoridad> autoridades = usuario.getAutoridades();
//		for(Autoridad autoridad: autoridades){			
//			roles.add(autoridad.getAutoridad());
//		}
		
//		model.addAttribute("rols", roles);
		
		Paciente paciente = pacienteDao.getPaciente(dni);
		
		List<Prueba> pruebas = doctorDao.getAllPruebasOfPacient(paciente);
		
		model.addAttribute("pruebas", pruebas);
		
		return "pruebas-list";
	}
	
	//Lista las pruebas de un tipo de prueba concreto
	@RequestMapping(value="/pruebasDe/{id}", method = RequestMethod.GET)
	public String listPruebasOfTipoPrueba(Locale locale, Model model, Principal principal,
			@PathVariable("id") long id) {
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoMedicina(principal.getName());
			///////////////////////////////////////////////////
		} catch (AccessDeniedException e) {
			List<String> mensajes = new ArrayList<String>();
			model.addAttribute("error","Acceso no Permitido.");
			mensajes.add("Esta pagina es solo para empleados.");
			mensajes.add("Solo un empleado puede consultar las pruebas de un paciente.");
			model.addAttribute("mensajes",mensajes);
			return "mensaje";
		}
		
//		List<String> roles = new ArrayList<String>();
		Usuario usuario = usuarioDao.usuario(principal.getName());
//		List<Autoridad> autoridades = usuario.getAutoridades();
//		for(Autoridad autoridad: autoridades){			
//			roles.add(autoridad.getAutoridad());
//		}
		
//		model.addAttribute("rols", roles);
		
		List<Prueba> pruebas = doctorDao.getAllPruebasOfTipoPrueba(doctorDao.getTipoPruebaById(id));
		
		model.addAttribute("pruebas", pruebas);
		
		return "pruebas-list";
	}
	
	
	//Muestra el formulario para a��adir una nueva prueba de un paciente
	@RequestMapping(value="/addPrueba", method = RequestMethod.GET)
	public String addPrueba(Locale locale, Model model, Principal principal) {
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoDoctor(principal.getName());
			///////////////////////////////////////////////////
		} catch (AccessDeniedException e) {
			List<String> mensajes = new ArrayList<String>();
			model.addAttribute("error","Acceso no Permitido.");
			mensajes.add("Esta pagina es solo para doctores.");
			mensajes.add("Solo un doctor puede añadir nuevas pruebas sobre un paciente.");
			model.addAttribute("mensajes",mensajes);
			return "mensaje";
		}
		
		List<Paciente> pacientes = pacienteDao.getPacientes();
		
		List<TipoPrueba> tiposPruebas = usuarioDao.listAllTipoPruebas();
		
		model.addAttribute("pacientes", pacientes);
		model.addAttribute("tiposPruebas", tiposPruebas);
		
		return "add-prueba-form";
	}
	
	//A��ade una nueva prueba de un paciente en la BBDD
	@PostMapping(value = "/addPrueba")
	public String addPrueba(Principal principal, Model model,  
			@RequestParam("dniPaciente") String dni,
			@RequestParam("idTipoPrueba") long idTipoPrueba,
			@RequestParam("observaciones") String observaciones){
		
		Paciente paciente = pacienteDao.getPaciente(dni);
		TipoPrueba tipoPrueba = usuarioDao.listOneTipoPruebas(idTipoPrueba);
		
		Prueba prueba = new Prueba();
		
		prueba.setFecha(LocalDate.now());
		prueba.setPaciente(paciente);
		prueba.setObservaciones(observaciones);
		prueba.setTipo(tipoPrueba);
		
		doctorDao.mergePrueba(prueba);
		
		return "redirect:/dash";
	}
	
	//Muestra información de una prueba concreta
	@RequestMapping(value="/prueba/{id}", method = RequestMethod.GET)
	public String listOnePrueba(Locale locale, Model model, Principal principal,
			@PathVariable("id") long id) {
		
		///////////////////////////////////////////////////		
		accessCheck.metodoUsuario(principal.getName());
		///////////////////////////////////////////////////
		
//		List<String> roles = new ArrayList<String>();
		Usuario usuario = usuarioDao.usuario(principal.getName());
//		List<Autoridad> autoridades = usuario.getAutoridades();
//		for(Autoridad autoridad: autoridades){			
//			roles.add(autoridad.getAutoridad());
//		}
		
//		model.addAttribute("rols", roles);
		
		Prueba prueba = doctorDao.getPruebaById(id);

		model.addAttribute("prueba", prueba);
		model.addAttribute("addResult", false);
		
		return "info-prueba";
	}
	
	//RESULTADOS################################################################################################################################################
	
	//Muestra el formulario para a��adir un nuevo resultado
	@RequestMapping(value="/addResultado/{id}", method = RequestMethod.GET)
	public String addResultadoForm(Principal principal, Model model,  
			@PathVariable("id") long id){
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoDoctor(principal.getName());
			///////////////////////////////////////////////////
		} catch (AccessDeniedException e) {
			List<String> mensajes = new ArrayList<String>();
			model.addAttribute("error","Acceso no Permitido.");
			mensajes.add("Esta pagina es solo para doctores.");
			mensajes.add("Solo un doctor puede añadir nuevos resultados sobre una prueba.");
			model.addAttribute("mensajes",mensajes);
			return "mensaje";
		}
		
		Prueba prueba = doctorDao.getPruebaById(id);
		
		model.addAttribute("prueba", prueba);
		model.addAttribute("addResult", true);
		
		return "info-prueba";
	}
	
	//Muestra el formulario para modificar un resultado existente
	@RequestMapping(value="/modResultado/{id}", method = RequestMethod.GET)
	public String modResultadoForm(Principal principal, Model model,  
			@PathVariable("id") long id){
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoDoctor(principal.getName());
			///////////////////////////////////////////////////
		} catch (AccessDeniedException e) {
			List<String> mensajes = new ArrayList<String>();
			model.addAttribute("error","Acceso no Permitido.");
			mensajes.add("Esta pagina es solo para doctores.");
			mensajes.add("Solo un doctor puede modificar resultados sobre una prueba.");
			model.addAttribute("mensajes",mensajes);
			return "mensaje";
		}
		
		Resultado resultado = doctorDao.getResultadoById(id);
		
		model.addAttribute("prueba", resultado.getPrueba());
		model.addAttribute("existResult", resultado);
		
		model.addAttribute("modResult", true);
		
		return "info-prueba";
	}
	
	//A��ade una nuevo resultado de una prueba en la BBDD
	@PostMapping(value = "/addResultado/{id}")
	public String addResultado(Principal principal, Model model,  
			@RequestParam("resultado") int resultado,
			@RequestParam("comentarios") String comentarios,
			@PathVariable("id") long id){
		
		Prueba prueba = doctorDao.getPruebaById(id);
		
		Resultado newResultado = new Resultado();
				
		newResultado.setPrueba(prueba);
		newResultado.setResultado(resultado);
		newResultado.setComentarios(comentarios);
		
		doctorDao.mergeResultado(newResultado);
		
		
		return "redirect:/prueba/"+id;
	}
	
	//Modifica un resultado existente de una prueba en la BBDD
	@PostMapping(value = "/modResultado/{id}")
	public String modResultado(Principal principal, Model model,  
			@RequestParam("resultado") int resultado,
			@RequestParam("comentarios") String comentarios,
			@PathVariable("id") long id){
		
		Resultado newResultado = doctorDao.getResultadoById(id);
				
		newResultado.setResultado(resultado);
		newResultado.setComentarios(comentarios);
		
		doctorDao.mergeResultado(newResultado);
		
		return "redirect:/prueba/"+newResultado.getPrueba().getId();
	}

	
	
	
	
	
	
	
	
	
	
	

}
