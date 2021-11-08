package org.escoladeltreball.proyectowiaw2.entities;

import java.io.Serializable;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@NamedQueries(value = {
	    @NamedQuery(name = "findAutoridad", query = "SELECT a FROM AutoridadEntity a WHERE a.usuario = :usuario"),
})
@Entity(name = "AutoridadEntity")
@Table(name  = "autoridad")
public class Autoridad implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable=false)
	private String autoridad;
	
	//Owner
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	private Usuario usuario;
	
	public Autoridad(){
		super();
	}
	
	public Autoridad(long id, String autoridad, Usuario usuario) {
		this.id = id;
		this.autoridad = autoridad;
		this.usuario = usuario;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAutoridad() {
		return autoridad;
	}

	public void setAutoridad(String autoridad) {
		this.autoridad = autoridad;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	
	

}
