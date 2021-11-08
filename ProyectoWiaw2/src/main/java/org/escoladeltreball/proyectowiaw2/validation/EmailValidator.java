package org.escoladeltreball.proyectowiaw2.validation;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String>{
	
	@PersistenceContext
	protected EntityManager manager;
	
	private Pattern pattern;
    private Matcher matcher;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$"; 
	
    @Override
	public void initialize(ValidEmail constraintAnnotation) {}
	
	
	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		return (uniqueEmail(email));
	}
	
//	//Controla que el formato del mail sea valido
//	private boolean validateEmail(String email) {
//        pattern = Pattern.compile(EMAIL_PATTERN);
//        matcher = pattern.matcher(email);
//        return matcher.matches();
//    }
	
	//Controla que el mail no este repetido
	private boolean uniqueEmail(String email) {
		Query queryEmails = manager.createNamedQuery("findAllEmails");
		
		List<String> emails = queryEmails.getResultList();
		
		for(String oneEmail: emails){
			if(email.equals(oneEmail)){
				return false;
			}
		}
		 manager.close();
		return true;
    }
	
}
