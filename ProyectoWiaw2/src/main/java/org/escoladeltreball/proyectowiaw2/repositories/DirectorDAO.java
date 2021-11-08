package org.escoladeltreball.proyectowiaw2.repositories;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.escoladeltreball.proyectowiaw2.beans.ContratoBean;
import org.escoladeltreball.proyectowiaw2.entities.Autoridad;
import org.escoladeltreball.proyectowiaw2.entities.Contrato;
import org.escoladeltreball.proyectowiaw2.entities.Director;
import org.escoladeltreball.proyectowiaw2.entities.Doctor;
import org.escoladeltreball.proyectowiaw2.entities.Empleado;
import org.escoladeltreball.proyectowiaw2.entities.Recepcionista;
import org.escoladeltreball.proyectowiaw2.entities.TipoPrueba;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Repository
@Transactional
public class DirectorDAO{
	
	@PersistenceContext
	protected EntityManager manager;
	
	private DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

	
	public Contrato findContratoById(long id) {
		
		Query query = manager.createNamedQuery("findContratoById").setParameter("id", id);
		Contrato contrato = (Contrato) query.getSingleResult();
		
		return contrato;
	}

	public Boolean updateContrato(ContratoBean contrato) {
		
		try {
			Contrato contratoBD = findContratoById(contrato.getId());
			
			contratoBD.setInicio(contrato.getInicio());
			contratoBD.setFinContrato(contrato.getFinContrato());
			contratoBD.setSueldo(contrato.getSueldo());
			contratoBD.setDias_vacaciones(contrato.getDias_vacaciones());
			contratoBD.setTipo(contrato.getTipo());
			
			manager.persist(contratoBD);
			
			return true;

		} catch (Exception e) {
			return false;
		}
	}
	
	public void finContrato(Empleado emp) {
		
		emp.setActivo(false);
		
		Contrato contrato = emp.getContrato();
		contrato.setFin(LocalDate.now());
		
		manager.merge(contrato);
		manager.flush();
		
	}
	
	public void reContrato(Empleado emp) {
		
		emp.setActivo(true);
		Contrato contrato = emp.getContrato();
		contrato.setFin(null);
		
		manager.merge(contrato);
		manager.flush();
		
	}

	
	
	//A��ADIR EMPLEADO/CONTRATO#############################################################################################################################
	
	//Funci��n que a��ade un director a la BBDD
	public void addDirector(){
		Director director = new Director();
		
		director.setDni("47752902A");
		director.setPassword(new BCryptPasswordEncoder().encode("kevin15"));
		director.setActivo(true);
		director.setNombre("Kevin");
		director.setApellidos("Ramos Lopez");
		director.setFecha_nacimiento(LocalDate.parse("1993-10-18"));
		director.setTelefono(669618162);
		director.setEmail("kevinramos456@gmail.com");
		director.setDireccion("c/ triola 9 11 2-2");
		director.setCodigoPostal("08770");
		
		Autoridad autoridad = new Autoridad();
		autoridad.setAutoridad("ROLE_DIRECTOR");
		autoridad.setUsuario(director);
		
		List<Autoridad> autoridades = new ArrayList<Autoridad>();
		autoridades.add(autoridad);
		
		director.setAutoridades(autoridades);
		
		manager.persist(director);
		manager.flush();
	}

	//FUnci��n que a��ade un contrato en la BBDD CON FECHA DE FIN
	public Boolean addContrato(LocalDate inicio, LocalDate finContrato, int sueldo, String tipo, int diasvacaciones, Empleado emp, String role){
		
		//Creamos el contrato
		Contrato contrato = new Contrato();
		contrato.setInicio(inicio);
		contrato.setFinContrato(finContrato);
		contrato.setSueldo(sueldo);
		contrato.setTipo(tipo);
		contrato.setDias_vacaciones(diasvacaciones);
		
		//Asignamos el contrato al doctor
		emp.setContrato(contrato);
		
		//Creamos la autoridad y le asignamos el doctor
		Autoridad autoridad = new Autoridad();
		autoridad.setAutoridad(role);
		autoridad.setUsuario(emp);
		
		//manager.persist(doc);
		manager.persist(autoridad);
		manager.flush();
		
		return true;
	}
	
	//Funci��n que a��ade un contrato en la BBDD SIN FECHA DE FIN
	public Boolean addContrato(LocalDate inicio, int sueldo, String tipo, int diasvacaciones, Empleado emp, String role){
		
		//Creamos el contrato
		Contrato contrato = new Contrato();
		contrato.setInicio(inicio);
		contrato.setSueldo(sueldo);
		contrato.setTipo(tipo);
		contrato.setDias_vacaciones(diasvacaciones);
		
		//Asignamos el contrato al doctor
		emp.setContrato(contrato);
		
		//Creamos la autoridad y le asignamos el doctor
		Autoridad autoridad = new Autoridad();
		autoridad.setAutoridad(role);
		autoridad.setUsuario(emp);
		
		//manager.persist(doc);
		manager.persist(autoridad);
		manager.flush();
		
		return true;
	}
	
	//FUnci��n que a��ade un doctor en la BBDD
	public Doctor addDoctor(String nombre, String apellidos, String dni, String email, long telefono, 
			String especialidad, String direccion, String codigopostal, LocalDate fechanacimiento, String password ) {
		
			Doctor doc = new Doctor();
			doc.setDni(dni);
			doc.setPassword(new BCryptPasswordEncoder().encode(password));
			doc.setActivo(true);
			doc.setNombre(nombre);
			doc.setApellidos(apellidos);
			doc.setFecha_nacimiento(fechanacimiento);
			doc.setTelefono(telefono);
			doc.setEmail(email);
			doc.setDireccion(direccion);
			doc.setCodigoPostal(codigopostal);
			doc.setEspecialidad(especialidad);
			
			return doc;
	}
	
	//FUnci��n que a��ade un recepcionista en la BBDD
	public Recepcionista addRecepcionista(String nombre, String apellidos, String dni, String email, long telefono, 
			String direccion, String codigopostal, LocalDate fechanacimiento, String password ) {
		
			Recepcionista rec = new Recepcionista();
			rec.setDni(dni);
			rec.setPassword(new BCryptPasswordEncoder().encode(password));
			rec.setActivo(true);
			rec.setNombre(nombre);
			rec.setApellidos(apellidos);
			rec.setFecha_nacimiento(fechanacimiento);
			rec.setTelefono(telefono);
			rec.setEmail(email);
			rec.setDireccion(direccion);
			rec.setCodigoPostal(codigopostal);
			//doc.setContrato(contrato);
			
						
			return rec;
	}
	
	//LIST###############################################################################################################################
	public List<Doctor> listAllDoctors() {
		
		Query query = manager.createNamedQuery("findAllDoctors");
		
		List<Doctor> doctores = query.getResultList();
		
		return doctores;
	}
	
	public List<Recepcionista> listAllRecepcionistas() {
		Query query = manager.createNamedQuery("findAllReceps");
	
		List<Recepcionista> recepcionistas = query.getResultList();
	
		return recepcionistas;
	}
	
	//A��ADIR/MODIFICAR PRUEBA GENERAL##################################################################################################################
	
	//Funci��n que a��ade una prueba en la BBDD
	public void addGeneralPrueba(TipoPrueba tipoPrueba) {
		
		manager.merge(tipoPrueba);
		manager.flush();
	
	}
	
	//Funci��n que modifica una prueba en la BBDD
	public void updateGeneralPrueba(TipoPrueba tipoPrueba, TipoPrueba DBtipoPrueba) {
		
		DBtipoPrueba.setNombre(tipoPrueba.getNombre());
		DBtipoPrueba.setDescripcion(tipoPrueba.getDescripcion());
		
		manager.merge(tipoPrueba);
		manager.flush();
	
	}
	
	//Funci��n que cancela una prueba en la BBDD
	public void cancelGeneralPrueba(TipoPrueba tipoPrueba) {
		
		tipoPrueba.setDisponible(false);
		
		manager.merge(tipoPrueba);
		manager.flush();
	
	}
	
	//Funci��n que recepta una prueba cancelada en la BBDD
	public void acceptGeneralPrueba(TipoPrueba tipoPrueba) {
		
		tipoPrueba.setDisponible(true);
		
		manager.merge(tipoPrueba);
		manager.flush();
	
	}
	
	
	//MAIL####################################################################################################################################
	
	public List<String> getAllEmails() {
		List<String> emails = new ArrayList<String>();
		
		List<Doctor> doctors = listAllDoctors();
		List<Recepcionista> recepcionistas = listAllRecepcionistas();
		
		System.out.println(emails.size());
		
		for (int i = 0; i < doctors.size(); i++) {
			emails.add(doctors.get(i).getEmail());
		}
		
		System.out.println(emails.size());
		
		for (int i = 0; i < recepcionistas.size(); i++) {
			emails.add(recepcionistas.get(i).getEmail());
		}
		
		System.out.println(emails.size());
		
		Query query = manager.createNamedQuery("findDirector");
		
		Director director = (Director)query.getSingleResult();
		
		emails.add(director.getEmail());
		
		return emails;
	}
	
	//Deveuelve el n��mero de doctores activos y su sueldo
	public Object[] sueldoTotal(String rol) {
		
		float sueldoTotal = 0;
		int empleadosTotal = 0;
		
		if(rol.equals("doc")){
			List<Doctor> doctores = listAllDoctors();
			
			for(Doctor doctor: doctores){
				if(doctor.isActivo()){
					sueldoTotal += doctor.getContrato().getSueldo();
					empleadosTotal ++;
				}
			}
		}
		else if(rol.equals("rec")){
			List<Recepcionista> recepcionistas = listAllRecepcionistas();
			
			for(Recepcionista rec: recepcionistas){
				if(rec.isActivo()){
					sueldoTotal += rec.getContrato().getSueldo();
					empleadosTotal ++;
				}
			}
		}
		
		Object[] values = new Object[2];
	    values[0] = empleadosTotal;
	    values[1] = sueldoTotal;
	    return values;
	}

}
