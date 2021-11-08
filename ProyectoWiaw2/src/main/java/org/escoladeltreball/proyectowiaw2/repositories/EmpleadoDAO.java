package org.escoladeltreball.proyectowiaw2.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.escoladeltreball.proyectowiaw2.entities.Doctor;
import org.escoladeltreball.proyectowiaw2.entities.Empleado;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Repository
@Transactional
public class EmpleadoDAO {
	
	@PersistenceContext
	protected EntityManager manager;
	
	//Obtiene un empleado a partir de un dni
	public Empleado getEmpleado(String dni) {
		
		Query query = manager.createNamedQuery("findEmpByDni").setParameter("dni", dni);
		Empleado empleado = (Empleado)query.getSingleResult();
		
		return empleado;
	}
	
	//Obtiene una lista con todos los empleados
	public List<Empleado> getAllEmpleados() {
		
		Query query = manager.createNamedQuery("findAllEmp");
		List<Empleado> empleados = query.getResultList();
		
		return empleados;
	}
	
	//Comprueva si un dni pertenece a algun empleado
	public Boolean checkEmpExist(String dni) {
		
		try {
			Query query = manager.createNamedQuery("findEmpByDni").setParameter("dni", dni);
			Empleado empleado = (Empleado)query.getSingleResult();
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
}
