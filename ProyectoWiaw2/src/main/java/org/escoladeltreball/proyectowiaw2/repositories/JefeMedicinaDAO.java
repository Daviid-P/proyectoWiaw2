package org.escoladeltreball.proyectowiaw2.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.escoladeltreball.proyectowiaw2.entities.Doctor;
import org.escoladeltreball.proyectowiaw2.entities.Paciente;
import org.escoladeltreball.proyectowiaw2.entities.TipoPrueba;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Repository
@Transactional
public class JefeMedicinaDAO {

	@PersistenceContext
	protected EntityManager manager;
	
	private Doctor getDoctor(String dni) {
		
		Query query = manager.createNamedQuery("getDoctor").setParameter("dni", dni);
		Doctor doctor = (Doctor)query.getSingleResult();
		
		return doctor;
	}
	
	public List<Paciente> getDatosPacientes() {
			
		return null;
	}
	
	public Boolean setAnalitica(String dni) {
		
		Doctor doctor = getDoctor(dni);
	
		return null;
	}
	
	public Boolean setResultado(String dni) {
		
		Doctor doctor = getDoctor(dni);
	
		return null;
	}

	public Boolean setPrueba(String dni, String nombre, String descripcion) {
		
		try {
			Doctor doctor = getDoctor(dni);
			
			TipoPrueba prueba = new TipoPrueba();
			
			prueba.setId(0L);
			prueba.setNombre(nombre);
			prueba.setDescripcion(descripcion);
			
			manager.persist(prueba);
			
			return true;
		} catch (Exception e) {
			return false;
		}
	
	}
	
	public Boolean addObservacion(String dni, Paciente paciente, String observacion) {
		
		Doctor doctor = getDoctor(dni);
	
		return null;
	}
}
