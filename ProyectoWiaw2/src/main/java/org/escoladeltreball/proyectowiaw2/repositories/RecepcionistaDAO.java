package org.escoladeltreball.proyectowiaw2.repositories;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.escoladeltreball.proyectowiaw2.entities.Autoridad;
import org.escoladeltreball.proyectowiaw2.entities.Contrato;
import org.escoladeltreball.proyectowiaw2.entities.Doctor;
import org.escoladeltreball.proyectowiaw2.entities.Empleado;
import org.escoladeltreball.proyectowiaw2.entities.Expediente;
import org.escoladeltreball.proyectowiaw2.entities.Paciente;
import org.escoladeltreball.proyectowiaw2.entities.Recepcionista;
import org.escoladeltreball.proyectowiaw2.entities.Visita;
//import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Repository
@Transactional
public class RecepcionistaDAO {
	
	@PersistenceContext
	protected EntityManager manager;

	@Autowired
	Environment env;
	
	private static final Logger logger = LoggerFactory.getLogger(RecepcionistaDAO.class);

	
	//Busca un recepcionista a partir del seu Dni
	public Recepcionista getRecep(String dni) {
		
		Query query = manager.createNamedQuery("findRecepByDni").setParameter("dni", dni);
		Recepcionista recepcionista = (Recepcionista)query.getSingleResult();
		
		return recepcionista;
	}
	
	//Busca un doctor a partir del seu Dni
	public Doctor getDoctorCabecera(String dni) {
		
		//Asignamos de dostor de cabecera al que tenga menos pacientes asignados 
		if(dni.equals("auto")){
			List<Doctor> doctoresCabecera = doctoresCabecera();
			
			Doctor doctor = doctoresCabecera.get(0);
			List<Paciente> pacientes;
			for(Doctor oneDoctor: doctoresCabecera){
				if(oneDoctor.getPacientes() != null){
					pacientes = oneDoctor.getPacientes();
					if(pacientes.size() < doctor.getPacientes().size()){
						doctor = oneDoctor;
					}
				}
			}
			return doctor;
		}
		//Asignamos un doctor de cabecera concreto
		else{
			Query query = manager.createNamedQuery("findDoctorByDni").setParameter("dni", dni);
			Doctor doctor = (Doctor)query.getSingleResult();
			return doctor;
		}
		
	}
	
	public Contrato getContrato(String dni) {
		
		Recepcionista recepcionista = getRecep(dni);
	
		return recepcionista.getContrato();
	}
	
	//Devuelve una lista con los doctores de cabecera
	public List<Doctor> doctoresCabecera(){
		Query queryCabecera = manager.createNamedQuery("findAllCabecera");
		
		List<Doctor> doctoresCabecera = queryCabecera.getResultList();
		
		return doctoresCabecera;
	}
	
	//Devuelve una lista con los doctores de cabecera
	public List<String> doctoresCabeceraDni(){
		Query queryCabecera = manager.createNamedQuery("findAllCabeceraDni");
		
		List<String> doctoresCabecera = queryCabecera.getResultList();
		
		return doctoresCabecera;
	}
	
	//Devuelve una lista con todos los doctores 
	public List<Doctor> doctores(){
		Query queryDoctores = manager.createNamedQuery("findAllDoctors");
		
		List<Doctor> doctores = queryDoctores.getResultList();
		
		return doctores;
	}

	//Devuelve una lista con todos los doctores 
	public List<Doctor> doctoresActivos(){
		Query queryDoctores = manager.createNamedQuery("findAllDoctorsActivos");
		
		List<Doctor> doctores = queryDoctores.getResultList();
		
		return doctores;
	}
	
	//Devuelve una lista con todos los pacientes 
	public List<Paciente> pacientes(){
		Query queryPacientes = manager.createNamedQuery("findAllPacientes");
		
		List<Paciente> pacientes= queryPacientes.getResultList();
		
		return pacientes;
	}
	
	//Devuelve una lista con todos los pacientes 
	public List<Paciente> pacientesActivos(){
		Query queryPacientes = manager.createNamedQuery("findAllPacientesActivos");
		
		List<Paciente> pacientes= queryPacientes.getResultList();
		
		return pacientes;
	}
	
	
	//A��ade un paciente con Habitaci��n asignada
	public Paciente addPaciente(String nombre, String apellidos, String dni, String email, long telefono, 
			String direccion, String codigopostal, LocalDate fechanacimiento, String password, Doctor doctorCabecera, int habitacion ) {
		
			Paciente paciente = new Paciente();
			paciente.setDni(dni);
			paciente.setPassword(new BCryptPasswordEncoder().encode(password));
			paciente.setActivo(true);
			paciente.setNombre(nombre);
			paciente.setApellidos(apellidos);
			paciente.setFecha_nacimiento(fechanacimiento);
			paciente.setTelefono(telefono);
			paciente.setEmail(email);
			paciente.setDireccion(direccion);
			paciente.setCodigoPostal(codigopostal);
			paciente.setDoctorCabecera(doctorCabecera);
			paciente.setHabitacion(habitacion);
			
						
			return paciente;
	}
	//A��ade un paciente sin Habitaci��n asignada
	public Paciente addPaciente(String nombre, String apellidos, String dni, String email, long telefono, 
			String direccion, String codigopostal, LocalDate fechanacimiento, String password, Doctor doctorCabecera) {
		
			Paciente paciente = new Paciente();
			paciente.setDni(dni);
			paciente.setPassword(new BCryptPasswordEncoder().encode(password));
			paciente.setActivo(true);
			paciente.setNombre(nombre);
			paciente.setApellidos(apellidos);
			paciente.setFecha_nacimiento(fechanacimiento);
			paciente.setTelefono(telefono);
			paciente.setEmail(email);
			paciente.setDireccion(direccion);
			paciente.setCodigoPostal(codigopostal);
			paciente.setDoctorCabecera(doctorCabecera);
			
						
			return paciente;
	}
	
	//NOT IMPLEMENTED:
	//Añade un paciente ya registrado como empleado con habitación asignada
//	public Paciente addEmpPaciente(String dni, Doctor doctorCabecera, int habitacion, Empleado emp) {
//		
//		Paciente paciente = new Paciente();
//		paciente.setId(emp.getId());
//		paciente.setDni(emp.getDni());
//		paciente.setPassword(emp.getPassword());
//		paciente.setActivo(true);
//		paciente.setNombre(emp.getNombre());
//		paciente.setApellidos(emp.getApellidos());
//		paciente.setFecha_nacimiento(emp.getFecha_nacimiento());
//		paciente.setTelefono(emp.getTelefono());
//		paciente.setEmail(emp.getEmail());
//		paciente.setDireccion(emp.getDireccion());
//		paciente.setCodigoPostal(emp.getCodigoPostal());
//		
//		paciente.setDoctorCabecera(doctorCabecera);
//		paciente.setHabitacion(habitacion);
//						
//		return paciente;
//	}
	
	
	
	//Añade un nuevo expediente en la BBDD
	public Boolean addExpedienteEmp(char genero, String ocupacion, String alergias, String alimentacion, String habitos, String antecedentesMedicos,
		int hospitalizaciones, String medicacion, int peso, Paciente paciente){
		
		//Creamos el Expediente
		Expediente expediente = new Expediente();
		expediente.setGenero(genero);
		expediente.setOcupacion(ocupacion);
		expediente.setAlergias(alergias);
		expediente.setAlimentacion(alimentacion);
		expediente.setHabitos(habitos);
		expediente.setAntecedentesMedicos(antecedentesMedicos);
		expediente.setHospitalizaciones(hospitalizaciones);
		expediente.setMedicacion(medicacion);
		expediente.setPeso(peso);
		
		//Asignamos el expediente al paciente
		paciente.setExpediente(expediente);
		
		//Creamos la autoridad y le asignamos el paciente
		Autoridad autoridad = new Autoridad();
		autoridad.setAutoridad("ROLE_PACIENTE");
		autoridad.setUsuario(paciente);
		
		//Insert Expediente
		String sqlStringExpediente = "INSERT INTO expediente(alergias, alimentacion, antecedentesmedicos, genero, habitos, hospitalizaciones, medicacion, ocupacion, peso) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		manager.createNativeQuery(sqlStringExpediente)
		.setParameter(1, expediente.getAlergias())
		.setParameter(2, expediente.getAlimentacion())
		.setParameter(3, expediente.getAntecedentesMedicos())
		.setParameter(4, expediente.getGenero())
		.setParameter(5, expediente.getHabitos())
		.setParameter(6, expediente.getHospitalizaciones())
		.setParameter(7, expediente.getMedicacion())
		.setParameter(8, expediente.getOcupacion())
		.setParameter(9, expediente.getPeso())
		.executeUpdate();
		
		Query queryLastExpediente = manager.createNamedQuery("findLastExp");
		
		Long idExpediente = (Long)queryLastExpediente.getSingleResult();
		
		System.out.println(idExpediente);
		
		//Insert Paciente
		String sqlStringPaciente = "INSERT INTO paciente(habitacion, id, doctorcabecera_id, expediente_id) VALUES(?, ?, ?, ?)";
		
		manager.createNativeQuery(sqlStringPaciente)
		.setParameter(1, paciente.getHabitacion())
		.setParameter(2, paciente.getId())
		.setParameter(3, paciente.getDoctorCabecera().getId())
		.setParameter(4, idExpediente)
		.executeUpdate();
		
		//Insert Autoridad
		String sqlStringAutoridad = "INSERT INTO autoridad(autoridad, usuario_id) VALUES(?, ?)";
		
		manager.createNativeQuery(sqlStringAutoridad)
		.setParameter(1, "ROLE_PACIENTE")
		.setParameter(2, paciente.getId())
		.executeUpdate();
			
		return true;
	}
	
	//Añade un nuevo expediente en la BBDD
	public Boolean addExpediente(char genero, String ocupacion, String alergias, String alimentacion, String habitos, String antecedentesMedicos,
		int hospitalizaciones, String medicacion, int peso, Paciente paciente){
		
		//Creamos el Expediente
		Expediente expediente = new Expediente();
		expediente.setGenero(genero);
		expediente.setOcupacion(ocupacion);
		expediente.setAlergias(alergias);
		expediente.setAlimentacion(alimentacion);
		expediente.setHabitos(habitos);
		expediente.setAntecedentesMedicos(antecedentesMedicos);
		expediente.setHospitalizaciones(hospitalizaciones);
		expediente.setMedicacion(medicacion);
		expediente.setPeso(peso);
		
		//Asignamos el expediente al paciente
		paciente.setExpediente(expediente);
		
		//Creamos la autoridad y le asignamos el paciente
		Autoridad autoridad = new Autoridad();
		autoridad.setAutoridad("ROLE_PACIENTE");
		autoridad.setUsuario(paciente);
		
		manager.merge(autoridad);
		manager.flush();
		
		return true;
	}
	
	//Da de alta un paciente
	public void altaPaciente(Paciente paciente){
		
		paciente.setActivo(false);
		
		manager.merge(paciente);
		manager.flush();
	}
	
	//Da de baja un paciente
	public void bajaPaciente(Paciente paciente){
		
		paciente.setActivo(true);
		
		manager.merge(paciente);
		manager.flush();
	}
	
	//Añade una nueva visita en la BBDD
	public Boolean addVisita(String doctorDni, String pacienteDni, LocalDateTime fecha, int habitacion, Recepcionista recepcionista){
		
		//Buscamos doctor y paciente por DNI
		Query queryDoctor = manager.createNamedQuery("findDoctorByDni").setParameter("dni", doctorDni);
		Doctor doctor = (Doctor)queryDoctor.getSingleResult();
		
		Query queryPaciente = manager.createNamedQuery("findPacienteByDni").setParameter("dni", pacienteDni);
		Paciente paciente = (Paciente)queryPaciente.getSingleResult();
		
		//Creamos la nueva visita
		Visita visita = new Visita();
		visita.setDoctor(doctor);
		visita.setPaciente(paciente);
		visita.setFecha(fecha);
		visita.setHabitacion(habitacion);
		visita.setRecepcionista(recepcionista);
		visita.setEstado("Pendiente");
		
				
		manager.merge(visita);
		manager.flush();
		
		return true;
	}
	
	//Función que comprueva si el doctor i habitación están libres a la hora indicada
	public String validVisita(String doctorDni, String pacienteDni, LocalDateTime fecha, int habitacion){
		
		//Comprovamos si el doctor está disponible a esa hora
		Query queryDoctor = manager.createNamedQuery("findDoctorByDni").setParameter("dni", doctorDni);
		Doctor doctor = (Doctor)queryDoctor.getSingleResult();
		
		List<Visita> visitas = doctor.getVisitas();
		for(Visita oneVisita: visitas){
			if(oneVisita.getFecha().equals(fecha) && oneVisita.getEstado().equals("Pendiente")){
				return "El doctor seleccionado no está disponible en esa hora";
			}
		}
		
		//Comprovamos si el paciente está disponible a esa hora
		Query queryPaciente = manager.createNamedQuery("findPacienteByDni").setParameter("dni", pacienteDni);
		Paciente paciente = (Paciente)queryPaciente.getSingleResult();
		
		List<Visita> visitasPaciente = paciente.getVisitas();
		for(Visita oneVisita: visitasPaciente){
			if(oneVisita.getFecha().equals(fecha) && oneVisita.getEstado().equals("Pendiente")){
				return "El paciente seleccionado no está disponible en esa hora";
			}
		}
		
		//Comprovamos si la habitación está disponible		
		Query queryVisita = manager.createNamedQuery("findAllVisits");
		List<Visita> allVisitas = queryVisita.getResultList();
		for(Visita oneVisita: allVisitas){
			if(oneVisita.getFecha().equals(fecha) && oneVisita.getHabitacion() == habitacion && oneVisita.getEstado().equals("Pendiente")){
				return "La habitación seleccionada no está disponible en esa hora";
			}
		}		
		
		return "OK";
		
	}
	
	//Devuelve una lista con todas las visitas
	public List<Visita> findAllVisitas(){
		Query queryVisita = manager.createNamedQuery("findAllVisits");
		List<Visita> allVisitas = queryVisita.getResultList();
	
		return allVisitas;
	}
	
	//Devuelve una visita concreta
	public Visita findOneVisita(long id){
		Query queryVisita = manager.createNamedQuery("findVisitaById").setParameter("id", id);
		Visita visita = (Visita)queryVisita.getSingleResult();
	
		return visita;
	}
	
	//Elimina una visita del la BBDD
	public void cancVisita(long id){
		
		Visita visita = findOneVisita(id);
		
		visita.setEstado("Cancelada");
		
		manager.merge(visita);
		manager.flush();
		
	}
	
	//Devuelve una lista con todos los pacientes
	public List<Paciente> findAllPacientes(){
		Query queryPaciente = manager.createNamedQuery("findAllPacientes");
		List<Paciente> allPacientes = queryPaciente.getResultList();
	
		return allPacientes;
	}
	
	//Devuelve paciente a partir de su dni
	public Paciente findPacienteByDni(String dni){
		Query queryPaciente = manager.createNamedQuery("findPacienteByDni").setParameter("dni", dni);
		Paciente paciente = (Paciente)queryPaciente.getSingleResult();
	
		return paciente;
	}
	
	//Devuelve un doctor a partir de su dni
	public Doctor findDoctorByDni(String dni){
		Query queryDoctor = manager.createNamedQuery("findDoctorByDni").setParameter("dni", dni);
		Doctor doctor = (Doctor)queryDoctor.getSingleResult();
		
		return doctor;
	}
	
	// Modifica el doctor de cabecera de un paciente
	public String modDoctorCabecera(Paciente paciente, Doctor doctor) {
		paciente.setDoctorCabecera(doctor);
//		
		manager.merge(paciente);
		manager.flush();
		
		//try {
//			System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOKKKKKKKKKKKKKKKKKKKKk");
//			manager.merge(paciente);
//			manager.flush();
//		} catch (Exception e) {
//			System.out.println("FAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAILLLL");
//			return "{error:1,errorMsg:"+e.getMessage()+"}";
//		}
//		
//		return "{error:0,doctorDni:"+doctor.getDni()+"}";
		
		return "OK";
	}
	
	// Crea pdf para descargar
	public boolean createPDF(String dni) {
		
		boolean result;
		Paciente paciente;
		
		try {
			paciente = findPacienteByDni(dni);
		} catch (NoResultException e) {
			return false;
		}
		
		Expediente expediente = paciente.getExpediente();
		
		if (expediente != null) {
			try {
				
				Path expedientesDir = Paths.get(env.getProperty("app.expedientesDir"));
				//System.out.println("Destino: "+expedientesDir.toString());
				//System.out.println("Output: "+expedientesDir.toFile().exists());
				if (!expedientesDir.toFile().exists()) {
					expedientesDir = Paths.get(env.getProperty("app.expedientesDir2"));
				}
				
				File destino = new File(expedientesDir+"/"+paciente.getDni()+".pdf");
				//System.out.println("Destino: "+destino.toString());
				try {
					PDDocument pdf = PDDocument.load(new File(expedientesDir+"/"+"ExpedienteTemplate.pdf"));
					AccessPermission ap = new AccessPermission();
					ap.setCanModify(false);
					ap.setReadOnly();
					
					PDAcroForm acroForm = pdf.getDocumentCatalog().getAcroForm();
					
					PDDocumentInformation info = new PDDocumentInformation();
					info.setAuthor("Hospital Salut Vital");
					info.setCreationDate(Calendar.getInstance());
					info.setCreator("Hospital Salut Vital");
					info.setTitle("Expediente Medico - "+paciente.getNombre()+" "+paciente.getApellidos()+"("+paciente.getDni()+")");
					info.setSubject(paciente.getDni());
					
					pdf.setDocumentInformation(info);
					
					if (acroForm != null) {
						
						List<PDField> fields = acroForm.getFields();
						
						PDTextField fieldNombre = (PDTextField) acroForm.getField( "nombreInput" );
		                fieldNombre.setValue(paciente.getNombre()+" "+paciente.getApellidos());
		                fieldNombre.setReadOnly(true);
		                
		                PDTextField fieldDni = (PDTextField) acroForm.getField( "dniInput" );
		                fieldDni.setValue(paciente.getDni());
		                fieldDni.setReadOnly(true);
		                
		                PDTextField fieldFecha = (PDTextField) acroForm.getField( "fechaNacimientoInput" );
		                fieldFecha.setValue(paciente.getFecha_nacimiento().toString());
		                fieldFecha.setReadOnly(true);
		                
		                PDTextField fieldTelefono = (PDTextField) acroForm.getField( "telefonoInput" );
		                fieldTelefono.setValue(Objects.toString(paciente.getTelefono(), "Unknown"));
		                fieldTelefono.setReadOnly(true);
		                
		                PDTextField fieldDireccion = (PDTextField) acroForm.getField( "direccionInput" );
		                fieldDireccion.setValue(paciente.getDireccion());
		                fieldDireccion.setReadOnly(true);
		                
		                PDTextField fieldGenero = (PDTextField) acroForm.getField( "generoInput" );
		                if (expediente.getGenero() == 'H') {
		                	fieldGenero.setValue("Hombre");
						} else if (expediente.getGenero() == 'M') {
							fieldGenero.setValue("Mujer");
						} else {
							fieldGenero.setValue("Otro");
						}
		                fieldGenero.setReadOnly(true);
		                
		                PDTextField fieldOcupacion = (PDTextField) acroForm.getField( "ocupacionInput" );
		                fieldOcupacion.setValue(expediente.getOcupacion());
		                fieldOcupacion.setReadOnly(true);
		                
		                PDTextField fieldHospitalizaciones = (PDTextField) acroForm.getField( "hospitalizacionesInput" );
		                fieldHospitalizaciones.setValue(Objects.toString(expediente.getHospitalizaciones()));
		                fieldHospitalizaciones.setReadOnly(true);
		                
		                PDTextField fieldPeso = (PDTextField) acroForm.getField( "pesoInput" );
		                fieldPeso.setValue(Objects.toString(expediente.getPeso()));
		                fieldPeso.setReadOnly(true);
		                
		                PDTextField fieldAlergias = (PDTextField) acroForm.getField( "alergiasInput" );
		                fieldAlergias.setValue(expediente.getAlergias());
		                fieldAlergias.setReadOnly(true);
		                
		                PDTextField fieldAlimentacion = (PDTextField) acroForm.getField( "alimentacionInput" );
		                fieldAlimentacion.setValue(expediente.getAlimentacion());
		                fieldAlimentacion.setReadOnly(true);
		                
		                PDTextField fieldHabitos = (PDTextField) acroForm.getField( "habitosInput" );
		                fieldHabitos.setValue(expediente.getHabitos());
		                fieldHabitos.setReadOnly(true);
		                
		                PDTextField fieldAntecedentes = (PDTextField) acroForm.getField( "antecedentesInput" );
		                fieldAntecedentes.setValue(expediente.getAntecedentesMedicos());
		                fieldAntecedentes.setReadOnly(true);
		                
		                PDTextField fieldMedicacion = (PDTextField) acroForm.getField( "medicacionInput" );
		                fieldMedicacion.setValue(expediente.getMedicacion());
		                fieldMedicacion.setReadOnly(true);
		                
		                
		            }
					
					//StandardProtectionPolicy spp = new StandardProtectionPolicy(UUID.randomUUID().toString(), "", ap);
					
					//pdf.protect(spp);
					pdf.save(destino);
	                pdf.close();
	                
	                result = true;
					
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					result = false;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					result = false;
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				result = false;
			}
		} else {
			result = false;
		}
		
		return result;
	}
	
}
