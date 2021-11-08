package org.escoladeltreball.proyectowiaw2.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "VisitaEntity")
@Table(name = "visita")
@NamedQueries(value = {
	    @NamedQuery(name = "findAllVisits", query = "SELECT v FROM VisitaEntity v"),
	    @NamedQuery(name = "findVisitaById", query = "SELECT v FROM VisitaEntity v where v.id = :id"),
	    @NamedQuery(name = "findVisitaByPaciente", query = "SELECT v FROM VisitaEntity v where v.paciente = :paciente"),
})
public class Visita implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private LocalDateTime fecha;
	
	private Integer habitacion;
	
	private String estado;
	
	private String observaciones;
	
	//Owner
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	private Doctor doctor;
	
	//Owner
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	private Paciente paciente;
	
	//Owner
	@ManyToOne(cascade={CascadeType.PERSIST}, fetch = FetchType.EAGER)
	private Recepcionista recepcionista;
	
	public Visita(){
		super();
	}

	public Visita(long id, LocalDateTime fecha, Integer habitacion, String estado, String observaciones, Doctor doctor,
			Paciente paciente, Recepcionista recepcionista) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.habitacion = habitacion;
		this.estado = estado;
		this.observaciones = observaciones;
		this.doctor = doctor;
		this.paciente = paciente;
		this.recepcionista = recepcionista;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Recepcionista getRecepcionista() {
		return recepcionista;
	}

	public void setRecepcionista(Recepcionista recepcionista) {
		this.recepcionista = recepcionista;
	}

	
	
	
	
}
