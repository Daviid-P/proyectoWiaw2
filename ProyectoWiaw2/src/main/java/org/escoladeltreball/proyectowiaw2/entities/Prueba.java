package org.escoladeltreball.proyectowiaw2.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;



@NamedQueries({
    @NamedQuery(name = "findAllPruebas", query = "SELECT p FROM PruebaEntity p"),
    @NamedQuery(name = "findPruebaById", query = "SELECT p FROM PruebaEntity p WHERE p.id = :id"),
    @NamedQuery(name = "findPruebaByPaciente", query = "SELECT p FROM PruebaEntity p WHERE p.paciente = :paciente"),
    @NamedQuery(name = "findPruebaByTipo", query = "SELECT p FROM PruebaEntity p WHERE p.tipo = :tipo"),
})

@Entity(name = "PruebaEntity")
@Table(name  = "prueba")
public class Prueba implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable=false)
	private LocalDate fecha;
	
	@Column(length=512, nullable=false)
	private String observaciones;
	
	//Owner
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	private Paciente paciente;
	
	//Inverse
	@Fetch(value = FetchMode.JOIN)
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval=true, mappedBy= "prueba")
	private List<Resultado> resultados;
	
	//Owner
	@ManyToOne(cascade={CascadeType.PERSIST}, fetch = FetchType.EAGER)
	private TipoPrueba tipo;
	
	public Prueba(){
		super();
	}

	public Prueba(long id, LocalDate fecha, String observaciones, Paciente paciente, List<Resultado> resultados, TipoPrueba tipo) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.observaciones = observaciones;
		this.paciente = paciente;
		this.resultados = resultados;
		this.tipo = tipo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public List<Resultado> getResultados() {
		return resultados;
	}

	public void setResultados(List<Resultado> resultados) {
		this.resultados = resultados;
	}

	public TipoPrueba getTipo() {
		return tipo;
	}

	public void setTipo(TipoPrueba tipo) {
		this.tipo = tipo;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	
}
