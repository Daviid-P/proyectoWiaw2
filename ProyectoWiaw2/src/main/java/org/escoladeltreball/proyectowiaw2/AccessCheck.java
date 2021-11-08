package org.escoladeltreball.proyectowiaw2;

import org.springframework.security.access.annotation.Secured;

public interface AccessCheck {

	@Secured({"ROLE_PACIENTE"})
	void metodoPaciente(String dni);

	@Secured({"ROLE_RECEPCIONISTA"})
	void metodoRecepcionista(String dni);

	@Secured({"ROLE_DOCTOR"})
	void metodoDoctor(String dni);
	
	@Secured({"ROLE_DIRECTOR"})
	void metodoDirector(String dni);
	
	@Secured({"ROLE_RECEPCIONISTA","ROLE_DOCTOR","ROLE_DIRECTOR"})
	void metodoEmpleado(String dni);
	
	@Secured({"ROLE_RECEPCIONISTA","ROLE_DOCTOR","ROLE_DIRECTOR", "ROLE_PACIENTE"})
	void metodoUsuario(String dni);
	
	@Secured({"ROLE_DIRECTOR", "ROLE_RECEPCIONISTA"})
	void metodoOficina(String dni);
	
	@Secured({"ROLE_DIRECTOR","ROLE_DOCTOR"})
	void metodoMedicina(String dni);
}
