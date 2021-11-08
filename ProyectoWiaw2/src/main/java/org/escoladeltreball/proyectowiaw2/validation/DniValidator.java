package org.escoladeltreball.proyectowiaw2.validation;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DniValidator implements ConstraintValidator<ValidDni, String>{
	
	@PersistenceContext
	protected EntityManager manager;
	
	private Pattern pattern;
    private Matcher matcher;
    
	@Override
	public void initialize(ValidDni constraintAnnotation) {}
	
	@Override
	public boolean isValid(String dni, ConstraintValidatorContext context) {
		return (uniqueDni(dni));
	}
	
	//Controla que el DNI no este repetido
	private boolean uniqueDni(String dni) {
		Query queryDnis = manager.createNamedQuery("findAllDnis");
		
		List<String> dnis = queryDnis.getResultList();
		
		for(String oneDni: dnis){
			if(dni.equals(oneDni)){
				return false;
			}
		}
		
		manager.close();
		return true;
    }
}
