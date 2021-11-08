package org.escoladeltreball.proyectowiaw2.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.escoladeltreball.proyectowiaw2.entities.Prueba;
import org.escoladeltreball.proyectowiaw2.entities.Doctor;
import org.escoladeltreball.proyectowiaw2.entities.Expediente;
import org.escoladeltreball.proyectowiaw2.entities.Paciente;
import org.escoladeltreball.proyectowiaw2.entities.Visita;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Repository
@Transactional
public class PacienteDAO {
	
	@PersistenceContext
	protected EntityManager manager;
	
	public Paciente getPaciente(String dni) {
		
		Query query = manager.createNamedQuery("findPacienteByDni").setParameter("dni", dni);
		Paciente paciente = (Paciente)query.getSingleResult();
		
		return paciente;
	}
	
	//Obtiene un paciente a partir de su id
	public Paciente getPacienteById(long id) {
		
		Query query = manager.createNamedQuery("findPacienteById").setParameter("id", id);
		Paciente paciente = (Paciente)query.getSingleResult();
		
		return paciente;
	}
	
	public List<Paciente> getPacientes() {
		
		Query query = manager.createNamedQuery("findAllPacientes");
		List<Paciente> pacientes = query.getResultList();
		
		return pacientes;
	}
	
	public Expediente getExpMed(String dni) {
		
		Paciente paciente = getPaciente(dni);
		
		return paciente.getExpediente();
	}

	public Doctor getDoctor(String dni) {
	
		Paciente paciente = getPaciente(dni);
	
		return paciente.getDoctorCabecera();
	}
	
	public List<Visita> getVisitas(String dni) {
		
		Paciente paciente = getPaciente(dni);
		
		return paciente.getVisitas();
	}

	public List<Prueba> getPruebas(String dni) {
	
		Paciente paciente = getPaciente(dni);
	
		return paciente.getPruebas();
	}
	
	public Boolean sendEmail(String dni, String mensaje) {
		
		Paciente paciente = getPaciente(dni);
		
		Doctor doctor = paciente.getDoctorCabecera();
		
		String docEmail = doctor.getEmail();
		
		return true;
	}
	
}
