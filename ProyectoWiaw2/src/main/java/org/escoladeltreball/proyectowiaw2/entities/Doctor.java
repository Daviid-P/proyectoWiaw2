package org.escoladeltreball.proyectowiaw2.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity(name = "DoctorEntity")
@Table(name = "doctor")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
@NamedQueries(value = {
	    @NamedQuery(name = "findAllDoctors", query = "SELECT d FROM DoctorEntity d"),
	    @NamedQuery(name = "findAllDoctorsActivos", query = "SELECT d FROM DoctorEntity d where d.activo = true"),
	    @NamedQuery(name = "findAllCabecera", query = "SELECT d FROM DoctorEntity d where d.especialidad = 'cabecera' and d.activo = true"),
	    @NamedQuery(name = "findAllCabeceraDni", query = "SELECT d.dni FROM DoctorEntity d where d.especialidad = 'cabecera' and d.activo = true"),
	    @NamedQuery(name = "findDoctorByDni", query = "SELECT d FROM DoctorEntity d where d.dni = :dni"),
	    @NamedQuery(name = "findDoctorById", query = "SELECT d FROM DoctorEntity d where d.id = :id"),
	})
public class Doctor extends Empleado implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(nullable=false)
	private String especialidad;
	
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},orphanRemoval=true, mappedBy= "doctorCabecera", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Paciente> pacientes;
	
	
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},orphanRemoval=true, mappedBy= "doctor",  fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Visita> visitas;
	
	public Doctor(){
		super();
	}

	public Doctor(String especialidad, List<Paciente> pacientes, List<Visita> visitas) {
		this.especialidad = especialidad;
		this.pacientes = pacientes;
		this.visitas = visitas;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public List<Paciente> getPacientes() {
		return pacientes;
	}

	public void setPacientes(List<Paciente> pacientes) {
		this.pacientes = pacientes;
	}

	public List<Visita> getVisitas() {
		return visitas;
	}

	public void setVisitas(List<Visita> visitas) {
		this.visitas = visitas;
	}

	
	
}
