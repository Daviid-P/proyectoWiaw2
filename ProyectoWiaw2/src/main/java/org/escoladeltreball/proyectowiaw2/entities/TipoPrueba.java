package org.escoladeltreball.proyectowiaw2.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@NamedQueries({
    @NamedQuery(name = "findAllTipoPruebas", query = "SELECT t FROM TipoPruebaEntity t"),
    @NamedQuery(name = "findTipoPruebaById", query = "SELECT t FROM TipoPruebaEntity t WHERE t.id = :id"),
    @NamedQuery(name = "findTipoPruebaByNombre", query = "SELECT t FROM TipoPruebaEntity t WHERE t.nombre = :nombre"),
})
@Entity(name = "TipoPruebaEntity")
@Table(name  = "tipoprueba")
public class TipoPrueba implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(length=32,nullable=false, unique=true)
	private String nombre;
	
	@Column(length=512,nullable=false)
	private String descripcion;
	
	@Column(nullable=false)
	private boolean disponible;
	
	//Inverse orphanRemoval=true, 
	@Fetch(value = FetchMode.SUBSELECT)
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, mappedBy= "tipo")
	private List<Prueba> pruebas;

	public TipoPrueba() {
		super();
	}

	public TipoPrueba(long id, String name, String descripcion, boolean disponible, List<Prueba> pruebas) {
		this.id = id;
		this.nombre = name;
		this.descripcion = descripcion;
		this.disponible = disponible;
		this.pruebas = pruebas;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Prueba> getPruebas() {
		return pruebas;
	}

	public void setPruebas(List<Prueba> pruebas) {
		this.pruebas = pruebas;
	}

	public boolean getDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}
	
	
	
	
	
	
}

