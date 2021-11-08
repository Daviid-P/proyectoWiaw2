package org.escoladeltreball.proyectowiaw2.controllers;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.escoladeltreball.proyectowiaw2.AccessCheck;
import org.escoladeltreball.proyectowiaw2.beans.ContratoBean;
import org.escoladeltreball.proyectowiaw2.beans.UsuarioBean;
import org.escoladeltreball.proyectowiaw2.entities.Autoridad;
import org.escoladeltreball.proyectowiaw2.entities.Contrato;
import org.escoladeltreball.proyectowiaw2.entities.Doctor;
import org.escoladeltreball.proyectowiaw2.entities.Empleado;
import org.escoladeltreball.proyectowiaw2.entities.Recepcionista;
import org.escoladeltreball.proyectowiaw2.entities.TipoPrueba;
import org.escoladeltreball.proyectowiaw2.entities.Usuario;
import org.escoladeltreball.proyectowiaw2.repositories.DirectorDAO;
import org.escoladeltreball.proyectowiaw2.repositories.EmpleadoDAO;
import org.escoladeltreball.proyectowiaw2.repositories.UsuarioDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DirectorController {
	
	private static final Logger logger = LoggerFactory.getLogger(DirectorController.class);
	
	@Autowired
	DirectorDAO directorDao;
	
	@Autowired
	UsuarioDAO usuarioDao;
	
	@Autowired
	EmpleadoDAO empleadoDao;
	
	@Autowired
	AccessCheck accessCheck;
	
	//Para formatear fechas
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	
	//AÑADIR EMPLEADOS################################################################################################################################
	
	//Cada vez que enviemos el formulario de registro de empleado, esta variable cogerá el valor de ese empleado para luego añadir el contrato
	private Doctor doctor = new Doctor();
	private Recepcionista recepcionista = new Recepcionista();
	private String empleadoActual;
	
	//Muestra el formulario para añadir doctor
	@RequestMapping(value = "/addDoctor", method = RequestMethod.GET)
	public String addDoctorForm(Locale locale, Model model, Principal principal) {
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoDirector(principal.getName());
			///////////////////////////////////////////////////
		} catch (AccessDeniedException e) {
			List<String> mensajes = new ArrayList<String>();
			model.addAttribute("error","Acceso no Permitido.");
			mensajes.add("Esta pagina es solo para directivos.");
			mensajes.add("Solo un directivo puede añadir nuevos empleados.");
			model.addAttribute("mensajes",mensajes);
			return "mensaje";
		}
		
		model.addAttribute(new UsuarioBean());
		
		return "add-doctor-form";
	}
	
	//Procesa los datos del formulario para añadir un doctor y redirige al formulario para añadir su contrato
	@PostMapping(value = "/addDoctor")
	public String addDoctor(Principal principal, Model model, @Valid UsuarioBean usuarioBean, BindingResult result,
			@RequestParam("nombre") String nombre,
			@RequestParam("apellidos") String apellidos,
			@RequestParam("dni") String dni,
			@RequestParam("email") String email,
			@RequestParam("telefono") long telefono,
			@RequestParam("especialidad") String especialidad,
			@RequestParam("direccion") String direccion,
			@RequestParam("codigoPostal") String codigopostal,
			@RequestParam("fechanacimiento") String fechanacimiento,
			@RequestParam("password") String password){
		
		if(result.hasErrors()){
			
			model.addAttribute(usuarioBean); //Aquí la clau la dedueix, no cal pasarli
			
			return "add-doctor-form";
		}
		
		LocalDate fecha_nacimiento = LocalDate.parse(fechanacimiento,dtf);
		
		doctor = directorDao.addDoctor(nombre, apellidos, dni, email, telefono, especialidad, direccion, 
				codigopostal, fecha_nacimiento, password);
		
		empleadoActual = "doc";
		
		model.addAttribute("name", nombre+""+apellidos);
		model.addAttribute("url","/addContrato");
		model.addAttribute(new ContratoBean());
		
		return "add-contrato-form";
	}
	
	//Muestra el formulatio para añadir recepcionista
	@RequestMapping(value = "/addRecepcionista", method = RequestMethod.GET)
	public String addRecepcionistaForm(Locale locale, Model model, Principal principal) {
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoDirector(principal.getName());
			///////////////////////////////////////////////////
		} catch (AccessDeniedException e) {
			List<String> mensajes = new ArrayList<String>();
			model.addAttribute("error","Acceso no Permitido.");
			mensajes.add("Esta pagina es solo para directivos.");
			mensajes.add("Solo un directivo puede añadir nuevos empleados.");
			model.addAttribute("mensajes",mensajes);
			return "mensaje";
		}
		model.addAttribute(new UsuarioBean());
		
		return "add-recepcionista-form";
	}
	
	//Procesa los datos del formulario para añadir un recepcionista y redirige al formulario para añadir su contrato
	@PostMapping(value = "/addRecepcionista")
	public String addRecepcionista(Principal principal, Model model, @Valid UsuarioBean usuarioBean, BindingResult result,
			@RequestParam("nombre") String nombre,
			@RequestParam("apellidos") String apellidos,
			@RequestParam("dni") String dni,
			@RequestParam("email") String email,
			@RequestParam("telefono") long telefono,
			@RequestParam("direccion") String direccion,
			@RequestParam("codigoPostal") String codigopostal,
			@RequestParam("fechanacimiento") String fechanacimiento,
			@RequestParam("password") String password){
		
		if(result.hasErrors()){
			
			model.addAttribute(usuarioBean); //Aquí la clau la dedueix, no cal pasarli
			
			return "add-recepcionista-form";
		}
		
		LocalDate fecha_nacimiento = LocalDate.parse(fechanacimiento,dtf);
		
		recepcionista = directorDao.addRecepcionista(nombre, apellidos, dni, email, telefono, direccion, 
				codigopostal, fecha_nacimiento, password);
		
		empleadoActual = "rec";
		
		model.addAttribute("name", nombre+" "+apellidos);
		model.addAttribute("url","/addContrato");
		model.addAttribute(new ContratoBean());
		
		return "add-contrato-form";
	}
	
	
	////Procesa los datos del formulario para añadir un contrato
	@PostMapping(value = "/addContrato")
	public String addContrato(Principal principal, Model model,  
			@RequestParam("inicio") String inicio,
			@RequestParam(value = "finContrato", required = false) String finContrato,
			@RequestParam("sueldo") int sueldo,
			@RequestParam("tipo") String tipo,
			@RequestParam("dias_vacaciones") int diasvacaciones){
		
		LocalDate inicioDate = LocalDate.parse(inicio,dtf);
		
		//Si no se ha introducio es que no tiene fecha de finalización
		if(finContrato == ""){
			//Dependiendo del empleado del que se trate mandaremos unos argumentos o otros a la fucnión
			if(empleadoActual.equals("rec")){
				directorDao.addContrato(inicioDate, sueldo, tipo, diasvacaciones, recepcionista, "ROLE_RECEPCIONISTA");	
			}
			else if(empleadoActual.equals("doc")){
				directorDao.addContrato(inicioDate, sueldo, tipo, diasvacaciones, doctor, "ROLE_DOCTOR");
			}
		}
		else{
			LocalDate finDate = LocalDate.parse(finContrato,dtf);
			//Dependiendo del empleado del que se trate mandaremos unos argumentos o otros a la fucnión
			if(empleadoActual.equals("rec")){
				directorDao.addContrato(inicioDate, finDate, sueldo, tipo, diasvacaciones, recepcionista, "ROLE_RECEPCIONISTA");	
			}
			else if(empleadoActual.equals("doc")){
				directorDao.addContrato(inicioDate, finDate, sueldo, tipo, diasvacaciones, doctor, "ROLE_DOCTOR");
			}
		}
		

		return "redirect:/dash";
	}
	
	//LISTAR EMPLEADOS######################################################################################################################
	
	@RequestMapping(value = "/listDoctors", method = RequestMethod.GET)
	public String listDoctors(Locale locale, Model model, Principal principal) {
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoEmpleado(principal.getName());
			///////////////////////////////////////////////////
		} catch (AccessDeniedException e) {
			List<String> mensajes = new ArrayList<String>();
			model.addAttribute("error","Acceso no Permitido.");
			mensajes.add("Esta pagina es solo para empleados.");
			mensajes.add("Si eres un paciente y necesitas información de algún doctor, consulta en recepción.");
			model.addAttribute("mensajes",mensajes);
			return "mensaje";
		}
		
		model.addAttribute("doctores", directorDao.listAllDoctors());
		
		Object[] totalDoctor = directorDao.sueldoTotal("doc");
		
		model.addAttribute("doctoresTotal", totalDoctor[0]);
		model.addAttribute("sueldoTotal", totalDoctor[1]);
		
		return "doctor-list";
	}
	
	@RequestMapping(value = "/updateContrato/{dni}", method = RequestMethod.GET)
	public String updateContratoForm(Model model, Principal principal,
			@PathVariable String dni) {
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoDirector(principal.getName());
			///////////////////////////////////////////////////
		} catch (AccessDeniedException e) {
			List<String> mensajes = new ArrayList<String>();
			model.addAttribute("error","Acceso no Permitido.");
			mensajes.add("Esta pagina es solo para directivos.");
			mensajes.add("Solo un directivo puede modificar un contrato.");
			model.addAttribute("mensajes",mensajes);
			return "mensaje";
		}
		
		Empleado empleado = empleadoDao.getEmpleado(dni);
		String nombre = empleado.getNombre()+" "+empleado.getApellidos();
		Contrato contrato = empleado.getContrato();
		
		model.addAttribute("name",nombre);
		model.addAttribute("url","/updateContrato/"+dni);
		model.addAttribute("contratoBean",contrato);
		
		return "add-contrato-form";
	}
	
	@RequestMapping(value = "/listRecepcionistas", method = RequestMethod.GET)
	public String listRecepcionistas(Locale locale, Model model, Principal principal) {
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoDirector(principal.getName());
			///////////////////////////////////////////////////
		} catch (AccessDeniedException e) {
			List<String> mensajes = new ArrayList<String>();
			model.addAttribute("error","Acceso no Permitido.");
			mensajes.add("Esta pagina es solo para directivos.");
			mensajes.add("Solo un directivo puede consultar los recepcionistas.");
			model.addAttribute("mensajes",mensajes);
			return "mensaje";
		}
		
		Object[] totalRec = directorDao.sueldoTotal("rec");
		
		model.addAttribute("recepTotal", totalRec[0]);
		model.addAttribute("sueldoTotal", totalRec[1]);
		
		model.addAttribute("recepcionistas", directorDao.listAllRecepcionistas());
		
		return "recep-list";
	}
	
	//MODIFICAR / ELIMINAR CONTRATOS######################################################################################################################
	
	@PostMapping(value = "/updateContrato/{dni}")
	public String updateContrato(@Valid @ModelAttribute("contratoBean") ContratoBean contratoBean, BindingResult result, Model model,
			@PathVariable String dni,
			@RequestParam("inicio") String inicio,
			@RequestParam(value = "finContrato", required = false) String finContrato) throws Exception {
		
		Usuario usuario = usuarioDao.usuario(dni);
		String nombre = usuario.getNombre()+" "+usuario.getApellidos();
				
		if(result.hasErrors()){
			
			model.addAttribute("name",nombre);
			model.addAttribute("url","/updateContrato/"+dni);
			model.addAttribute(contratoBean); //Aquí la clau la dedueix, no cal pasarli
			
			return "add-contrato-form";
		}
		
		//Si no se ha introducio es que no tiene fecha de finalización
		if(finContrato.isEmpty()){
			LocalDate finDate = LocalDate.parse(finContrato, dtf);
			contratoBean.setFinContrato(finDate);
		}
		LocalDate inicioDate= LocalDate.parse(inicio, dtf);
		contratoBean.setInicio(inicioDate);
		
		if (directorDao.updateContrato(contratoBean)) {
			return "redirect:/dash";
		} else {
			result.addError(new ObjectError("UpdateError", "Ha habido un error al actualizar el contrato."));
			model.addAttribute("name",nombre);
			model.addAttribute("url","/updateContrato/"+dni);
			model.addAttribute(contratoBean); //Aquí la clau la dedueix, no cal pasarli
			
			return "add-contrato-form";
		}
	}
	
	@RequestMapping(value = "/finContrato/{dni}", method = RequestMethod.GET)
	public String finContrato(Model model, Principal principal, 
			@PathVariable String dni) {
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoDirector(principal.getName());
			///////////////////////////////////////////////////
		} catch (AccessDeniedException e) {
			List<String> mensajes = new ArrayList<String>();
			model.addAttribute("error","Acceso no Permitido.");
			mensajes.add("Esta pagina es solo para directivos.");
			mensajes.add("Solo un directivo puede finalizar un contrato.");
			model.addAttribute("mensajes",mensajes);
			return "mensaje";
		}
		
		Empleado empleado = empleadoDao.getEmpleado(dni);
		
		directorDao.finContrato(empleado);
		
		
		return "redirect:/dash";
	}
	
	@RequestMapping(value = "/reContrato/{dni}", method = RequestMethod.GET)
	public String reContrato(Model model, Principal principal, 
			@PathVariable String dni) {
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoDirector(principal.getName());
			///////////////////////////////////////////////////
		} catch (AccessDeniedException e) {
			List<String> mensajes = new ArrayList<String>();
			model.addAttribute("error","Acceso no Permitido.");
			mensajes.add("Esta pagina es solo para directivos.");
			mensajes.add("Solo un directivo puede recontratar a un ex-empleado.");
			model.addAttribute("mensajes",mensajes);
			return "mensaje";
		}
		
		Empleado empleado = empleadoDao.getEmpleado(dni);
		
		directorDao.reContrato(empleado);
		
		return "redirect:/updateContrato/"+dni;
	}
	
	
	//AÑADIR/MODIFICAR PRUEBAS GENERALES##################################################################################################################
	
	//Muestra el formulario para añadir una nueva prueba
	@RequestMapping(value = "/addGeneralPrueba", method = RequestMethod.GET)
	public String addGeneralPruebaForm(Locale locale, Model model, Principal principal) {
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoDirector(principal.getName());
			///////////////////////////////////////////////////
		} catch (AccessDeniedException e) {
			List<String> mensajes = new ArrayList<String>();
			model.addAttribute("error","Acceso no Permitido.");
			mensajes.add("Esta pagina es solo para directivos.");
			mensajes.add("Solo un directivo puede añadir una nueva prueba general.");
			model.addAttribute("mensajes",mensajes);
			return "mensaje";
		}
		
		model.addAttribute("tipoPrueba", new TipoPrueba());
		model.addAttribute("url","/addGeneralPrueba");
		
		return "add-prueba-general";
	}
	
	
	//Modifica una prueba
	@RequestMapping(value = "/updatePruebaGeneral/{id}", method = RequestMethod.GET)
	public String updateGeneralPruebaForm(Locale locale, Model model, Principal principal,
			 @PathVariable long id) {
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoDirector(principal.getName());
			///////////////////////////////////////////////////
		} catch (AccessDeniedException e) {
			List<String> mensajes = new ArrayList<String>();
			model.addAttribute("error","Acceso no Permitido.");
			mensajes.add("Esta pagina es solo para directivos.");
			mensajes.add("Solo un directivo puede modificar una prueba general.");
			model.addAttribute("mensajes",mensajes);
			return "mensaje";
		}
		
		TipoPrueba tipoPrueba = usuarioDao.listOneTipoPruebas(id);
		
		model.addAttribute("tipoPrueba",tipoPrueba);
		model.addAttribute("url","/updateGeneralPrueba/"+id);
		
		return "add-prueba-general";
	}
	
	//Añade un nuevo tipo de prueba
	@PostMapping(value = "/addGeneralPrueba")
	public String addGeneralPrueba(Principal principal, Model model,
			TipoPrueba tipoPrueba){
		
		tipoPrueba.setDisponible(true);
		
		directorDao.addGeneralPrueba(tipoPrueba);
		
		return "dash";
		
	}
	
	//Modifica un tipo de prueba existente
	@PostMapping(value = "/updateGeneralPrueba/{id}")
	public String updateGeneralPrueba(Principal principal, Model model,
			TipoPrueba tipoPrueba,
			@PathVariable long id){
		
		TipoPrueba DBtipoPrueba = usuarioDao.listOneTipoPruebas(id);
		tipoPrueba.setDisponible(true);
		
		directorDao.updateGeneralPrueba(tipoPrueba, DBtipoPrueba);
		
		return "redirect:/listGeneralPruebas";
		
	}
	
	//Cancela una prueba
	@RequestMapping(value = "/cancPruebaGeneral/{id}", method = RequestMethod.GET)
	public String cancGeneralPrueba(Locale locale, Model model, Principal principal,
			 @PathVariable long id) {
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoDirector(principal.getName());
			///////////////////////////////////////////////////
		} catch (AccessDeniedException e) {
			List<String> mensajes = new ArrayList<String>();
			model.addAttribute("error","Acceso no Permitido.");
			mensajes.add("Esta pagina es solo para directivos.");
			mensajes.add("Solo un directivo puede cancelar una prueba general.");
			model.addAttribute("mensajes",mensajes);
			return "mensaje";
		}
		
		TipoPrueba tipoPrueba = usuarioDao.listOneTipoPruebas(id);
		
		directorDao.cancelGeneralPrueba(tipoPrueba);
		
		return "redirect:/listGeneralPruebas";
	}
	
	//Acepta una prueba cancelada
	@RequestMapping(value = "/acceptPruebaGeneral/{id}", method = RequestMethod.GET)
	public String acceptGeneralPrueba(Locale locale, Model model, Principal principal,
			 @PathVariable long id) {
		
		try {
			///////////////////////////////////////////////////
			accessCheck.metodoDirector(principal.getName());
			///////////////////////////////////////////////////
		} catch (AccessDeniedException e) {
			List<String> mensajes = new ArrayList<String>();
			model.addAttribute("error","Acceso no Permitido.");
			mensajes.add("Esta pagina es solo para directivos.");
			mensajes.add("Solo un directivo puede reaceptar una prueba general cancelada.");
			model.addAttribute("mensajes",mensajes);
			return "mensaje";
		}
		
		TipoPrueba tipoPrueba = usuarioDao.listOneTipoPruebas(id);
		
		directorDao.acceptGeneralPrueba(tipoPrueba);
		
		return "redirect:/listGeneralPruebas";
	}
	
	//LISTAR PRUEBAS GENERALES##################################################################################################################
	@RequestMapping(value = "/listGeneralPruebas", method = RequestMethod.GET)
	public String addlistGeneralPrueba(Locale locale, Model model, Principal principal) {
		
		///////////////////////////////////////////////////		
		accessCheck.metodoUsuario(principal.getName());
		///////////////////////////////////////////////////
		
		List<TipoPrueba> tiposPruebas = usuarioDao.listAllTipoPruebas();
		
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
//			else if(autoridad.getAutoridad().equals("ROLE_DIRECTOR")){
//				role = "ROLE_DIRECTOR";
//			}
//		}
		
		model.addAttribute("tiposPruebas",tiposPruebas);
//		model.addAttribute("rol", role);
		
		return "pruebas-generales-list";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
