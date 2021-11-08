package org.escoladeltreball.proyectowiaw2.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@NamedQueries(value = {
	@NamedQuery(name = "findLastExp", query = "SELECT MAX(e.id) FROM ExpedienteEntity e"),
})
@Entity(name = "ExpedienteEntity")
@Table(name  = "expediente")
public class Expediente implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable=false)
	private char genero;
	
	@Column(nullable=false)
	private String ocupacion;
	
	private String alergias;
	
	@Column(nullable=false)
	private String alimentacion;
	
	private String habitos;
	
	@Column(length=4112, nullable=false)
	private String antecedentesMedicos;
	
	private int hospitalizaciones;
	
	@Column(length=1024, nullable=false)
	private String medicacion;
	
	@Column(nullable=false)
	private Integer peso;
	
	//Inverse
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE}, mappedBy= "expediente")
	private Paciente paciente;
	
	public Expediente(){
		super();
	}

	public Expediente(long id, char genero, String ocupacion, String alergias, String alimentacion, String habitos,
			String antecedentesMedicos, int hospitalizaciones, String medicacion, Integer peso, Paciente paciente) {
		super();
		this.id = id;
		this.genero = genero;
		this.ocupacion = ocupacion;
		this.alergias = alergias;
		this.alimentacion = alimentacion;
		this.habitos = habitos;
		this.antecedentesMedicos = antecedentesMedicos;
		this.hospitalizaciones = hospitalizaciones;
		this.medicacion = medicacion;
		this.peso = peso;
		this.paciente = paciente;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public char getGenero() {
		return genero;
	}

	public void setGenero(char genero) {
		this.genero = genero;
	}

	public String getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(String ocupacion) {
		this.ocupacion = ocupacion;
	}

	public String getAlergias() {
		return alergias;
	}

	public void setAlergias(String alergias) {
		this.alergias = alergias;
	}

	public String getAlimentacion() {
		return alimentacion;
	}

	public void setAlimentacion(String alimentacion) {
		this.alimentacion = alimentacion;
	}

	public String getHabitos() {
		return habitos;
	}

	public void setHabitos(String habitos) {
		this.habitos = habitos;
	}

	public String getAntecedentesMedicos() {
		return antecedentesMedicos;
	}

	public void setAntecedentesMedicos(String antecedentesMedicos) {
		this.antecedentesMedicos = antecedentesMedicos;
	}

	public int getHospitalizaciones() {
		return hospitalizaciones;
	}

	public void setHospitalizaciones(int hospitalizaciones) {
		this.hospitalizaciones = hospitalizaciones;
	}

	public String getMedicacion() {
		return medicacion;
	}

	public void setMedicacion(String medicacion) {
		this.medicacion = medicacion;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
}
