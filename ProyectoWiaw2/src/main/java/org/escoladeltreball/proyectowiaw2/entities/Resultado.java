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
import javax.persistence.Table;

@NamedQueries({
    @NamedQuery(name = "findAllResultados", query = "SELECT r FROM ResultadoEntity r"),
    @NamedQuery(name = "findResultadoById", query = "SELECT r FROM ResultadoEntity r WHERE r.id = :id"),
})
@Entity(name = "ResultadoEntity")
@Table(name  = "resultado")
public class Resultado implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable=false)
	private int resultado;
	
	@Column(nullable=false)
	private String comentarios;
	
	//Owner
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	private Prueba prueba;
	
	
	public Resultado(){
		super();
	}


	public Resultado(long id, int resultado, String comentarios, Prueba prueba) {
		super();
		this.id = id;
		this.resultado = resultado;
		this.comentarios = comentarios;
		this.prueba = prueba;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public int getResultado() {
		return resultado;
	}


	public void setResultado(int resultado) {
		this.resultado = resultado;
	}


	public String getComentarios() {
		return comentarios;
	}


	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}


	public Prueba getPrueba() {
		return prueba;
	}


	public void setPrueba(Prueba prueba) {
		this.prueba = prueba;
	}

	

}
