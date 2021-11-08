package org.escoladeltreball.proyectowiaw2.beans;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.escoladeltreball.proyectowiaw2.entities.Empleado;
import org.springframework.format.annotation.DateTimeFormat;

public class ContratoBean {
	
	private long id;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate inicio;
	
	private LocalDate fin;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate finContrato;
	
	private Integer sueldo;
	
	private String tipo;
	
	private Integer dias_vacaciones;
	
	private Empleado empleado;
	
	public ContratoBean(){
		super();
	}
	
	public LocalDate dtfConvert(String finContrato){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate finDate = LocalDate.parse(finContrato,dtf);
		
		return finDate;
	}
	
	public ContratoBean(long id, String inicio, LocalDate fin, String finContrato, Integer sueldo, String tipo,
			Integer dias_vacaciones, Empleado empleado) {
		super();
		this.id = id;
		this.inicio = dtfConvert(inicio);
		this.fin = fin;
		this.finContrato = dtfConvert(finContrato);
		this.sueldo = sueldo;
		this.tipo = tipo;
		this.dias_vacaciones = dias_vacaciones;
		this.empleado = empleado;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getInicio() {
		return inicio;
	}

	public void setInicio(LocalDate inicio) {
		this.inicio = inicio;
	}

	public LocalDate getFin() {
		return fin;
	}

	public void setFin(LocalDate fin) {
		this.fin = fin;
	}

	public LocalDate getFinContrato() {
		return finContrato;
	}

	public void setFinContrato(LocalDate finContrato) {
		this.finContrato = finContrato;
	}

	public Integer getSueldo() {
		return sueldo;
	}

	public void setSueldo(Integer sueldo) {
		this.sueldo = sueldo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getDias_vacaciones() {
		return dias_vacaciones;
	}

	public void setDias_vacaciones(Integer dias_vacaciones) {
		this.dias_vacaciones = dias_vacaciones;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	
	
	
	
	
}
