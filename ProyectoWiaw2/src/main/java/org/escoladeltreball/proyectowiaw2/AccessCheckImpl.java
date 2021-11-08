package org.escoladeltreball.proyectowiaw2;

import java.util.logging.Logger;
import org.springframework.stereotype.Service;

@Service
public class AccessCheckImpl implements AccessCheck {
	
	Logger logger = Logger.getLogger(AccessCheck.class.getName());

	@Override
	public void metodoPaciente(String dni) {
		// TODO Auto-generated method stub
		logger.warning("Metodo Paciente: "+dni);
	}

	@Override
	public void metodoRecepcionista(String dni) {
		// TODO Auto-generated method stub
		logger.warning("Metodo Recepcionista: "+dni);
	}

	@Override
	public void metodoDoctor(String dni) {
		// TODO Auto-generated method stub
		logger.warning("Metodo Doctor: "+dni);
	}

	@Override
	public void metodoDirector(String dni) {
		// TODO Auto-generated method stub
		logger.warning("Metodo Director: "+dni);
	}

	@Override
	public void metodoEmpleado(String dni) {
		// TODO Auto-generated method stub
		logger.warning("Metodo Empleado: "+dni);
	}
	
	@Override
	public void metodoUsuario(String dni) {
		// TODO Auto-generated method stub
		logger.warning("Metodo Usuario: "+dni);
	}
	
	@Override
	public void metodoOficina(String dni) {
		// TODO Auto-generated method stub
		logger.warning("Metodo Oficina: "+dni);
	}
	
	@Override
	public void metodoMedicina(String dni) {
		// TODO Auto-generated method stub
		logger.warning("Metodo Medicina: "+dni);
	}

}
