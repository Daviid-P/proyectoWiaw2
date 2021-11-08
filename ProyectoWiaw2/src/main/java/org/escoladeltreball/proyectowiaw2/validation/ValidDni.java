package org.escoladeltreball.proyectowiaw2.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DniValidator.class)
@Documented
public @interface ValidDni {
	String message() default "Este DNI ya se encuentra registrado, seguro que es tu DNI?";
	 Class<?>[] groups() default {}; 
	 Class<? extends Payload>[] payload() default {};
}
