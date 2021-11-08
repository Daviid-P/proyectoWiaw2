package org.escoladeltreball.proyectowiaw2.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@NamedQueries({
    @NamedQuery(name = "findAllPacientes", query = "SELECT s FROM PacienteEntity s"),
    @NamedQuery(name = "findAllPacientesActivos", query = "SELECT s FROM PacienteEntity s where s.activo = true"),
    @NamedQuery(name = "findPacienteById", query = "SELECT s FROM PacienteEntity s WHERE s.id = :id"),
    @NamedQuery(name = "findPacienteByDni", query = "SELECT s FROM PacienteEntity s WHERE s.dni = :dni"),
})

@Entity(name = "PacienteEntity")
@Table(name = "paciente")
@PrimaryKeyJoinColumn(name = "id") //, referencedColumnName = "id"
public class Paciente extends Usuario implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer habitacion;
	
	//Owner
	@ManyToOne(cascade={CascadeType.PERSIST}, fetch = FetchType.EAGER)
	private Doctor doctorCabecera;
	
	//Inverse
	@Fetch(value = FetchMode.SUBSELECT)
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval=true, mappedBy= "paciente", fetch = FetchType.EAGER)
	private List<Visita> visitas;
		
	//Inverse
	@Fetch(value = FetchMode.SUBSELECT)
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},orphanRemoval=true, mappedBy= "paciente", fetch = FetchType.EAGER)
	private List<Prueba> pruebas;
	
	//Owner
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE},orphanRemoval=true , fetch = FetchType.EAGER)
	private Expediente expediente;

	public Paciente(){
		super();
	}

	public Paciente(Integer habitacion, Doctor doctorCabecera, List<Visita> visitas, List<Prueba> pruebas,
			Expediente expediente) {
		this.habitacion = habitacion;
		this.doctorCabecera = doctorCabecera;
		this.visitas = visitas;
		this.pruebas = pruebas;
		this.expediente = expediente;
	}

	public Integer getHabitacion() {
		return habitacion;
	}

	public void setHabitacion(Integer habitacion) {
		this.habitacion = habitacion;
	}

	public Doctor getDoctorCabecera() {
		return doctorCabecera;
	}

	public void setDoctorCabecera(Doctor doctorCabecera) {
		this.doctorCabecera = doctorCabecera;
	}

	public List<Visita> getVisitas() {
		return visitas;
	}

	public void setVisitas(List<Visita> visitas) {
		this.visitas = visitas;
	}

	public List<Prueba> getPruebas() {
		return pruebas;
	}

	public void setPruebas(List<Prueba> pruebas) {
		this.pruebas = pruebas;
	}

	public Expediente getExpediente() {
		return expediente;
	}

	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}


	
}
