package org.escoladeltreball.proyectowiaw2.beans;

import java.time.LocalDateTime;

import org.escoladeltreball.proyectowiaw2.entities.Recepcionista;
import org.springframework.format.annotation.DateTimeFormat;

public class VisitaBean {

	private long id;
	
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
	private LocalDateTime fecha;
	
	private int habitacion;
	
	private String observaciones;
	
	private String doctorDni;
	
	private String pacienteDni;
	
	public String intToString(int i){
		return Integer.toString(i);
	}
	
	public VisitaBean(){
		super();
	}

	public VisitaBean(long id, LocalDateTime fecha, int habitacion, String observaciones, String doctorDni, String pacienteDni) {
		this.id = id;
		this.fecha = fecha;
		this.habitacion = habitacion;
		this.observaciones = observaciones;
		this.doctorDni = doctorDni;
		this.pacienteDni = pacienteDni;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public Integer getHabitacion() {
		return habitacion;
	}

	public void setHabitacion(Integer habitacion) {
		this.habitacion = habitacion;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getDoctorDni() {
		return doctorDni;
	}

	public void setDoctorDni(String doctorDni) {
		this.doctorDni = doctorDni;
	}

	public String getPacienteDni() {
		return pacienteDni;
	}

	public void setPacienteDni(String pacienteDni) {
		this.pacienteDni = pacienteDni;
	}

}
