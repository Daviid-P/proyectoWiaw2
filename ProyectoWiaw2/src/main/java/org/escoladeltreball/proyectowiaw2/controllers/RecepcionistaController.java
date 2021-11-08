package org.escoladeltreball.proyectowiaw2.controllers;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.escoladeltreball.proyectowiaw2.AccessCheck;
import org.escoladeltreball.proyectowiaw2.beans.PacienteBean;
import org.escoladeltreball.proyectowiaw2.beans.VisitaBean;
import org.escoladeltreball.proyectowiaw2.entities.Autoridad;
import org.escoladeltreball.proyectowiaw2.entities.Doctor;
import org.escoladeltreball.proyectowiaw2.entities.Empleado;
import org.escoladeltreball.proyectowiaw2.entities.Expediente;
import org.escoladeltreball.proyectowiaw2.entities.Paciente;
import org.escoladeltreball.proyectowiaw2.entities.Recepcionista;
import org.escoladeltreball.proyectowiaw2.entities.Usuario;
import org.escoladeltreball.proyectowiaw2.entities.Visita;
import org.escoladeltreball.proyectowiaw2.repositories.DirectorDAO;
import org.escoladeltreball.proyectowiaw2.repositories.DoctorDAO;
import org.escoladeltreball.proyectowiaw2.repositories.EmpleadoDAO;
import org.escoladeltreball.proyectowiaw2.repositories.PacienteDAO;
import org.escoladeltreball.proyectowiaw2.repositories.RecepcionistaDAO;
import org.escoladeltreball.proyectowiaw2.repositories.UsuarioDAO;
//import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RecepcionistaController {
	
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
	
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	
	//Esta variable contendrá el valor de cada nuevo paciente que registremos, para asignarselo al expediente
	Paciente paciente = new Paciente();
	
	
	//PACIENTES##########################################################################################################################################
	
	//Muestra el formulario para añadir paciente
	@RequestMapping(value = "/addPaciente", method = RequestMethod.GET)
	public String addPacienteForm(Locale locale, Model model, Principal principal) {
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoRecepcionista(principal.getName());
			///////////////////////////////////////////////////
		} catch (AccessDeniedException e) {
			List<String> mensajes = new ArrayList<String>();
			model.addAttribute("error","Acceso no Permitido.");
			mensajes.add("Esta pagina es solo para recepcionistas.");
			mensajes.add("Solo un recepcionista puede añadir nuevos pacientes.");
			model.addAttribute("mensajes",mensajes);
			return "mensaje";
		}
		
		//Para realizar validación de paciente en servidor
		model.addAttribute(new PacienteBean());
		
		//Le pasaremos una lista con los doctores de cabecera de la cl��nica
		List<Doctor> doctoresCabecera = recepcionistaDao.doctoresCabecera();
		model.addAttribute("doctoresCabecera", doctoresCabecera);
			
		return "add-paciente-form";
	}
	
	//Procesa los datos del formulario para añadir un paciente y redirige al formulario para añadir su expediente
	@PostMapping(value = "/addPaciente")
	public String addPacienteEndPoint(Principal principal, Model model, @Valid PacienteBean pacienteBean, BindingResult result,
			@RequestParam("nombre") String nombre,
			@RequestParam("apellidos") String apellidos,
			@RequestParam("dni") String dni,
			@RequestParam("email") String email,
			@RequestParam("telefono") long telefono,
			@RequestParam("direccion") String direccion,
			@RequestParam("codigoPostal") String codigopostal,
			@RequestParam("fechanacimiento") String fechanacimiento,
			@RequestParam("password") String password,
			@RequestParam(value = "habitacion", required = false) int habitacion,
			@RequestParam("doctorCabeceraDni") String dniDoctorCabecera){
		
		if(result.hasErrors()){
			
			model.addAttribute(pacienteBean); //Aqui la clau la dedueix, no cal pasarli
			
			//Le pasaremos una lista con los doctores de cabecera de la clinica
			List<Doctor> doctoresCabecera = recepcionistaDao.doctoresCabecera();
			model.addAttribute("doctoresCabecera", doctoresCabecera);
			
			return "add-paciente-form";
		}
		
		//Busquem el doctor a partir del seu DNI
		Doctor doctorCabecera = recepcionistaDao.getDoctorCabecera(dniDoctorCabecera);
		
		LocalDate fecha_nacimiento = LocalDate.parse(fechanacimiento,dtf);
		
		//Añade un paciente con o sin habitación
		if(habitacion == 0){
			paciente = recepcionistaDao.addPaciente(nombre, apellidos, dni, email, telefono, direccion, codigopostal, fecha_nacimiento, password, doctorCabecera);
		}
		else{
			paciente = recepcionistaDao.addPaciente(nombre, apellidos, dni, email, telefono, direccion, codigopostal, fecha_nacimiento, password, doctorCabecera, habitacion);
		}
		
		return "add-expediente-form";
	}
	
	//NOT IMPLEMENTED:
	
	
	//Muestra el formulatio para añadir paciente que ya es empleado
//	@RequestMapping(value = "/addEmpPaciente", method = RequestMethod.GET)
//	public String addEmpPacienteForm(Locale locale, Model model, Principal principal) {
//		
//		//Le pasaremos una lista con los doctores de cabecera de la clínica
//		List<Doctor> doctoresCabecera = recepcionistaDao.doctoresCabecera();
//		model.addAttribute("doctoresCabecera", doctoresCabecera);
//			
//		return "add-emp-paciente-form";
//	}
	//Procesa los datos del formulario para añadir un paciente y redirige al formulario para añadir su expediente
//	@PostMapping(value = "/addEmpPaciente")
//	public String addEmpPacienteEndPoint(Principal principal, Model model,
//			@RequestParam("dni") String dni,
//			@RequestParam(value= "habitacion", required = false) int habitacion,
//			@RequestParam("doctorCabeceraDni") String dniDoctorCabecera){
//		
//		//Busquem el doctor a partir del seu DNI
//		Doctor doctorCabecera = recepcionistaDao.getDoctorCabecera(dniDoctorCabecera);
//		
//		Empleado emp = empleadoDao.getEmpleado(dni);
//		
//		//Añade un paciente con o sin habitación
//		if(habitacion == 0){
//			paciente = recepcionistaDao.addEmpPaciente(dniDoctorCabecera, doctorCabecera, 0, emp);
//		}
//		else{
//			paciente = recepcionistaDao.addEmpPaciente(dniDoctorCabecera, doctorCabecera, habitacion, emp);
//		}
//		
//		return "add-expediente-form";
//	}
	
	////Procesa los datos del formulario para a��adir un expediente
	@PostMapping(value = "/addExpediente")
	public String addExpediente(Principal principal, Model model,  
			@RequestParam("genero") char genero,
			@RequestParam("ocupacion") String ocupacion,
			@RequestParam("alergias") String alergias,
			@RequestParam("alimentacion") String alimentacion,
			@RequestParam("habitos") String habitos,
			@RequestParam("peso") int peso,
			@RequestParam("hospitalizaciones") int hospitalizaciones,
			@RequestParam("antecedentesMedicos") String antecedentesMedicos,
			@RequestParam("medicacion") String medicacion){
		
		//Comprovamos si el paciente ya es un empleado
		List<Empleado> empleados = empleadoDao.getAllEmpleados();
		if(empleadoDao.checkEmpExist(paciente.getDni())){
			recepcionistaDao.addExpedienteEmp(genero, ocupacion, alergias, alimentacion, habitos, antecedentesMedicos, hospitalizaciones, medicacion, peso, paciente);
		}
		else{
			recepcionistaDao.addExpediente(genero, ocupacion, alergias, alimentacion, habitos, antecedentesMedicos, hospitalizaciones, medicacion, peso, paciente);
		}
		
		return "redirect:/dash";
	}
	
	//Muestra un listado con todos los pacientes
	@RequestMapping(value="/listPacientes", method = RequestMethod.GET)
	public String listAllPacientes(Locale locale, Model model, Principal principal) {
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoEmpleado(principal.getName());
			///////////////////////////////////////////////////
		} catch (AccessDeniedException e) {
			List<String> mensajes = new ArrayList<String>();
			model.addAttribute("error","Acceso no Permitido.");
			mensajes.add("Esta pagina es solo para empleados.");
			mensajes.add("Si eres un paciente, no puedes consultar información de otros pacientes, nos tomamos vuestra privacidad muy en serio.");
			model.addAttribute("mensajes",mensajes);
			return "mensaje";
		}
		
		//Le pasaremos una lista con el dni de los doctores de cabecera de la clinica
		List<String> doctoresCabecera = recepcionistaDao.doctoresCabeceraDni();
		model.addAttribute("doctoresCabeceraDni", doctoresCabecera);
		
//		String role = "";
//		Usuario usuario = usuarioDao.usuario(principal.getName());
//		List<Autoridad> autoridades = usuario.getAutoridades();
//		for(Autoridad autoridad: autoridades){			
//			if(autoridad.getAutoridad().equals("ROLE_DOCTOR")){
//				role = "ROLE_DOCTOR";
//			}
//			else if(autoridad.getAutoridad().equals("ROLE_RECEPCIONISTA")){
//				role = "ROLE_RECEPCIONISTA";
//			}
//		}				
		
		model.addAttribute("pacientes",recepcionistaDao.findAllPacientes());
//		model.addAttribute("rol",role);
		
		return "paciente-list";
	}
	
	//Modifica el doctor de cabecera de un paciente
	@RequestMapping(value = "/modDoctorCabecera", method = RequestMethod.POST)
	public @ResponseBody
	void modDoctorCabeceraForm(Locale locale, Model model, Principal principal, 
			@RequestParam("pk") long idPaciente,
			@RequestParam("value") String dniDoctor) {
		
		///////////////////////////////////////////////////		
		accessCheck.metodoRecepcionista(principal.getName());
		///////////////////////////////////////////////////
		
		Paciente paciente = pacienteDao.getPacienteById(idPaciente);
		Doctor doctor = doctorDao.getDoctor(dniDoctor);
		
		recepcionistaDao.modDoctorCabecera(paciente, doctor);
	}
	
	//Da de baja a un paciente (lo desactva en la BBDD)
	@RequestMapping(value="/baja/{dni}", method = RequestMethod.GET)
	public String altaPaciente(Locale locale, Model model, Principal principal,
			@PathVariable("dni") String dni) {
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoRecepcionista(principal.getName());
			///////////////////////////////////////////////////
		} catch (AccessDeniedException e) {
			List<String> mensajes = new ArrayList<String>();
			model.addAttribute("error","Acceso no Permitido.");
			mensajes.add("Esta pagina es solo para recepcionistas.");
			mensajes.add("Solo un recepcionista puede dar de baja a un paciente de la clínica.");
			model.addAttribute("mensajes",mensajes);
			return "mensaje";
		}
		
		Paciente paciente = pacienteDao.getPaciente(dni);
		
		boolean empleado = false;
		
		for (Autoridad autoridad : paciente.getAutoridades()) {
			if (autoridad.getAutoridad().matches("ROLE_(RECEPCIONISTA|DOCTOR|DIRECTOR)")) {
				empleado = true;
			}
		}
		
		//Si el paciente es además un empleado, no le da de baja en la BBDD		
		if (!empleado) {
			recepcionistaDao.altaPaciente(paciente);
		}
				
		return "redirect:/listPacientes";
	}
	
	//Da de alta a un paciente (lo vuelve a activar en la BBDD)
	@RequestMapping(value="/alta/{dni}", method = RequestMethod.GET)
	public String bajaPaciente(Locale locale, Model model, Principal principal,
			@PathVariable("dni") String dni) {
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoRecepcionista(principal.getName());
			///////////////////////////////////////////////////
		} catch (AccessDeniedException e) {
			List<String> mensajes = new ArrayList<String>();
			model.addAttribute("error","Acceso no Permitido.");
			mensajes.add("Esta pagina es solo para recepcionistas.");
			mensajes.add("Solo un recepcionista puede dar de alta a un ex-paciente de la clínica.");
			model.addAttribute("mensajes",mensajes);
			return "mensaje";
		}
		
		Paciente paciente = pacienteDao.getPaciente(dni);
		
		recepcionistaDao.bajaPaciente(paciente);
		
		return "redirect:/listPacientes";
	}
	
	
	//VISITAS##########################################################################################################################################
	
	//Muestra el formulario para añadir visita
	@RequestMapping(value = "/addVisita", method = RequestMethod.GET)
	public String addVisitaForm(Locale locale, Model model, Principal principal) {
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoRecepcionista(principal.getName());
			///////////////////////////////////////////////////
		} catch (AccessDeniedException e) {
			List<String> mensajes = new ArrayList<String>();
			model.addAttribute("error","Acceso no Permitido.");
			mensajes.add("Esta pagina es solo para recepcionistas.");
			mensajes.add("Solo un recepcionista puede programar nuevas visitas.");
			model.addAttribute("mensajes",mensajes);
			return "mensaje";
		}
		
		//Para realizar validación de visita en servid
		model.addAttribute(new VisitaBean());

		//Le pasaremos una lista con los doctores de la clinica
		List<Doctor> doctores = recepcionistaDao.doctoresActivos();
		model.addAttribute("doctores", doctores);
		
		//Le pasaremos una lista con los pacientes
		List<Paciente> pacientes = recepcionistaDao.pacientesActivos();
		model.addAttribute("pacientes", pacientes);
			
		return "add-visita-form";
	}
	
	//Añade una nueva visita en la BBDD
	@PostMapping(value = "/addVisita")
	public String addVisita(Locale locale, Model model, Principal principal, @Valid VisitaBean visitabean, BindingResult result){
		if(result.hasErrors()){
			
			model.addAttribute(visitabean);
			
			//Le pasaremos una lista con los doctores de la clinica
			List<Doctor> doctores = recepcionistaDao.doctores();
			model.addAttribute("doctores", doctores);
			
			//Le pasaremos una lista con los pacientes
			List<Paciente> pacientes = recepcionistaDao.pacientes();
			model.addAttribute("pacientes", pacientes);
				
			return "add-visita-form";
		}
		
		String dni = principal.getName();
		Recepcionista recepcionista = recepcionistaDao.getRecep(dni);
		
		String doctorDni = visitabean.getDoctorDni();
		String pacienteDni = visitabean.getPacienteDni();
		LocalDateTime fecha = visitabean.getFecha();
		int habitacion = visitabean.getHabitacion();
		
		//Comprovem si el doctor i habitacio estan disponibles
		String valid = recepcionistaDao.validVisita(doctorDni, pacienteDni, fecha, habitacion);
		if(!valid.equals("OK")){
			model.addAttribute(visitabean);
			
			model.addAttribute("errorVisita", valid);
			model.addAttribute("classError", "alert alert-danger");
			
			//Le pasaremos una lista con los doctores de la clinica
			List<Doctor> doctores = recepcionistaDao.doctores();
			model.addAttribute("doctores", doctores);
			
			//Le pasaremos una lista con los pacientes
			List<Paciente> pacientes = recepcionistaDao.pacientes();
			model.addAttribute("pacientes", pacientes);
			return "add-visita-form";
		}
		
		recepcionistaDao.addVisita(doctorDni, pacienteDni, fecha, habitacion, recepcionista);
		
			
		return "redirect:/dash";
	}
		
	@RequestMapping(value="/listVisitas", method = RequestMethod.GET)
	public String listVisitas(Locale locale, Model model, Principal principal) {
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoRecepcionista(principal.getName());
			///////////////////////////////////////////////////
		} catch (AccessDeniedException e) {
			List<String> mensajes = new ArrayList<String>();
			model.addAttribute("error","Acceso no Permitido.");
			mensajes.add("Esta pagina es solo para recepcionistas.");
			mensajes.add("Solo un recepcionista puede ver el listado con todas las visitas.");
			model.addAttribute("mensajes",mensajes);
			return "mensaje";
		}
		
		model.addAttribute("visitas",recepcionistaDao.findAllVisitas());
		
//		String role = "";
//		Usuario usuario = usuarioDao.usuario(principal.getName());
//		List<Autoridad> autoridades = usuario.getAutoridades();
//		for(Autoridad autoridad: autoridades){			
//			if(autoridad.getAutoridad().equals("ROLE_DOCTOR")){
//				role = "ROLE_DOCTOR";
//			}
//			else if(autoridad.getAutoridad().equals("ROLE_RECEPCIONISTA")){
//				role = "ROLE_RECEPCIONISTA";
//			}
//			else if(autoridad.getAutoridad().equals("ROLE_PACIENTE")){
//				role = "ROLE_PACIENTE";
//			}
//		}
		
		// Cambiado a Security Dialect (sec:authorize="hasRole()")
		
//		model.addAttribute("rol", "ROLE_RECEPCIONISTA");
		
		return "visita-list";
	}
	
	@RequestMapping(value="/visitasPaciente/{dni}", method = RequestMethod.GET)
	public String listVisitasPaciente(Locale locale, Model model, Principal principal,
			@PathVariable("dni") String dni) {
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoRecepcionista(principal.getName());
			///////////////////////////////////////////////////
		} catch (AccessDeniedException e) {
			List<String> mensajes = new ArrayList<String>();
			model.addAttribute("error","Acceso no Permitido.");
			mensajes.add("Esta pagina es solo para recepcionistas.");
			mensajes.add("Solo un recepcionista puede listar las visitas de un paciente.");
			model.addAttribute("mensajes",mensajes);
			return "mensaje";
		}
		
		model.addAttribute("visitas", pacienteDao.getPaciente(dni).getVisitas());
		
		// Cambiado a Security Dialect (sec:authorize="hasRole()")
		
//		String role = "";
//		Usuario usuario = usuarioDao.usuario(principal.getName());
//		List<Autoridad> autoridades = usuario.getAutoridades();
//		for(Autoridad autoridad: autoridades){			
//			if(autoridad.getAutoridad().equals("ROLE_DOCTOR")){
//				role = "ROLE_DOCTOR";
//			}
//			else if(autoridad.getAutoridad().equals("ROLE_RECEPCIONISTA")){
//				role = "ROLE_RECEPCIONISTA";
//			}
//			else if(autoridad.getAutoridad().equals("ROLE_PACIENTE")){
//				role = "ROLE_PACIENTE";
//			}
//		}
//		
//		model.addAttribute("rol", role);
		
		return "visita-list";
	}
	
	//Retorna la información de una visita concreta por id
	@RequestMapping("/visita/{id}")
	public String oneVisita(Locale locale, Model model, Principal principal,
			@PathVariable("id") long id){
		
		///////////////////////////////////////////////////		
		accessCheck.metodoUsuario(principal.getName());
		///////////////////////////////////////////////////
		
		// Cambiado a Security Dialect (sec:authorize="hasRole()")
		
//		String role = "";
//		Usuario usuario = usuarioDao.usuario(principal.getName());
//		List<Autoridad> autoridades = usuario.getAutoridades();
//		for(Autoridad autoridad: autoridades){			
//			if(autoridad.getAutoridad().equals("ROLE_DOCTOR")){
//				role = "ROLE_DOCTOR";
//			}
//			else if(autoridad.getAutoridad().equals("ROLE_RECEPCIONISTA")){
//				role = "ROLE_RECEPCIONISTA";
//			}
//			else if(autoridad.getAutoridad().equals("ROLE_PACIENTE")){
//				role = "ROLE_PACIENTE";
//			}
//		}
//		
//		model.addAttribute("rol", role);
		
		Visita visita = recepcionistaDao.findOneVisita(id);
		
		model.addAttribute("paciente", visita.getPaciente());
		model.addAttribute("doctor", visita.getDoctor());
		model.addAttribute("recepcionista", visita.getRecepcionista());
		model.addAttribute("visita", visita);
		model.addAttribute("fin", "no");
		
		return "info-visita";
		
	}
	
	@RequestMapping("/delVisita/{id}")
	public String delVisita(Locale locale, Model model, Principal principal,
			@PathVariable("id") long id){
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoRecepcionista(principal.getName());
			///////////////////////////////////////////////////
		} catch (AccessDeniedException e) {
			List<String> mensajes = new ArrayList<String>();
			model.addAttribute("error","Acceso no Permitido.");
			mensajes.add("Esta pagina es solo para recepcionistas.");
			mensajes.add("Solo un recepcionista puede cancelar una visita.");
			model.addAttribute("mensajes",mensajes);
			return "mensaje";
		}
		
		recepcionistaDao.cancVisita(id);
		//model.addAttribute("visitas",recepcionistaDao.findAllVisitas());
		
		return "redirect:/listVisitas";
		
	}	
	
	
	//Devuelce información sobre un doctor a partir de su dni
	@RequestMapping("/doctor/{dni}")
	public String doctorVar(Locale locale, Model model, Principal principal,
			@PathVariable("dni") String dni){
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoEmpleado(principal.getName());
			///////////////////////////////////////////////////
		} catch (AccessDeniedException e) {
			List<String> mensajes = new ArrayList<String>();
			model.addAttribute("error","Acceso no Permitido.");
			mensajes.add("Esta pagina es solo para empleados.");
			mensajes.add("Si eres un paciente y quieres consultar los datos de tu doctor de cabecera puedes hacerlo desde tu dashboard.");
			model.addAttribute("mensajes",mensajes);
			return "mensaje";
		}
		
		Doctor doctor = doctorDao.getDoctor(dni);
		
		model.addAttribute("doctor", doctor);

		return "info-doctor";		
	}
	
	//Devuelve el expediente de un paciente a partir de su dni
	@RequestMapping("/expediente/{dni}")
	public String expedienteVar(Locale locale, Model model, Principal principal,
			@PathVariable("dni") String dni){
		
		// Url para empleados
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoEmpleado(principal.getName());
			///////////////////////////////////////////////////
		} catch (AccessDeniedException e) {
			List<String> mensajes = new ArrayList<String>();
			model.addAttribute("error","Acceso no Permitido.");
			mensajes.add("Esta pagina es solo para empleados.");
			mensajes.add("Si eres un paciente y quieres consultar los datos de tu expediente puedes hacerlo desde tu dashboard.");
			model.addAttribute("mensajes",mensajes);
			return "mensaje";
		}
		
		Paciente paciente = recepcionistaDao.findPacienteByDni(dni);
		Expediente expediente = paciente.getExpediente();
		
		model.addAttribute("expediente", expediente);
		model.addAttribute("paciente", paciente);
//		model.addAttribute("emp", ".");
		
		return "info-expediente";		
	}
}
