package org.escoladeltreball.proyectowiaw2.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.escoladeltreball.proyectowiaw2.entities.Prueba;
import org.escoladeltreball.proyectowiaw2.entities.Resultado;
import org.escoladeltreball.proyectowiaw2.entities.TipoPrueba;
import org.escoladeltreball.proyectowiaw2.entities.Contrato;
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
public class DoctorDAO {

	
	@PersistenceContext
	protected EntityManager manager;
	
	//Obtiene un doctor a partir de un dni
	public Doctor getDoctor(String dni) {
		
		Query query = manager.createNamedQuery("findDoctorByDni").setParameter("dni", dni);
		Doctor doctor = (Doctor)query.getSingleResult();
		
		return doctor;
	}
	
	//Obtiene una lista con todas las pruebas
	public List<Prueba> getAllPruebas() {
		
		Query query = manager.createNamedQuery("findAllPruebas");
		List<Prueba> pruebas = query.getResultList();
		
		return pruebas;
	}
	
	//Obtiene una lista con las pruebas de un paciente
	public List<Prueba> getAllPruebasOfPacient(Paciente paciente) {
		
		Query query = manager.createNamedQuery("findPruebaByPaciente").setParameter("paciente", paciente);
		List<Prueba> pruebas = query.getResultList();
		
		return pruebas;
	}
	
	//Obtiene una lista con las pruebas de un tipo de prueba
	public List<Prueba> getAllPruebasOfTipoPrueba(TipoPrueba tipo) {
		
		Query query = manager.createNamedQuery("findPruebaByTipo").setParameter("tipo", tipo);
		List<Prueba> pruebas = query.getResultList();
		
		return pruebas;
	}
	
	//Obtiene una prueba por Id
	public Resultado getResultadoById(long id) {
		
		Query query = manager.createNamedQuery("findResultadoById").setParameter("id", id);
		Resultado resultado = (Resultado)query.getSingleResult();
		
		return resultado;
	}
	
	//Obtiene un resultado por Id
	public Prueba getPruebaById(long id) {
		
		Query query = manager.createNamedQuery("findPruebaById").setParameter("id", id);
		Prueba prueba = (Prueba)query.getSingleResult();
		
		return prueba;
	}
	
	//Obtiene un Tipo de prueba por id
	public TipoPrueba getTipoPruebaById(long id) {
		
		Query query = manager.createNamedQuery("findTipoPruebaById").setParameter("id", id);
		TipoPrueba tipoPrueba = (TipoPrueba)query.getSingleResult();
		
		return tipoPrueba;
	}
	
	//Merge de una visita
	public void mergeVisita(Visita visita) {
		
		manager.merge(visita);
		manager.flush();
	}
	
	//Merge de una prueba
	public void mergePrueba(Prueba prueba) {
		
		manager.merge(prueba);
		manager.flush();
	}
	
	//Merge de un resultado
	public void mergeResultado(Resultado resultado) {
		
		manager.merge(resultado);
		manager.flush();
	}
	
	
	
	
}
