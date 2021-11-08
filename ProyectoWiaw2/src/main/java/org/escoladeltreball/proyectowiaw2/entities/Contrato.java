package org.escoladeltreball.proyectowiaw2.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name = "ContratoEntity")
@Table(name  = "contrato")
@NamedQueries( value = {
		@NamedQuery(name = "findContratoById", query = "SELECT c FROM ContratoEntity c WHERE c.id = :id"),
})
public class Contrato implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable=false)
	private LocalDate inicio;
	
	private LocalDate fin;
	
	private LocalDate finContrato;
	
	@Column(nullable=false)
	private Integer sueldo;
	
	@Column(nullable=false)
	private String tipo;
	
	@Column(nullable=false)
	private Integer dias_vacaciones;
	
	//Inverse
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE}, mappedBy= "contrato")
	private Empleado empleado;
	
	public Contrato(){
		super();
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
