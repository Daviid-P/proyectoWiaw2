package org.escoladeltreball.proyectowiaw2.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@NamedQueries({
    @NamedQuery(name = "findAllReceps", query = "SELECT r FROM RecepcionistaEntity r"),
    @NamedQuery(name = "findRecepById", query = "SELECT r FROM RecepcionistaEntity r WHERE r.id = :id"),
    @NamedQuery(name = "findRecepByDni", query = "SELECT r FROM RecepcionistaEntity r WHERE r.dni = :dni"),
})

@Entity(name = "RecepcionistaEntity")
@Table(name = "recepcionista")
@PrimaryKeyJoinColumn(name = "id") //, referencedColumnName = "id"
public class Recepcionista extends Empleado implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//Inverse
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval=true, mappedBy= "recepcionista")
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Visita> visitas;
	
	public Recepcionista(){
		super();
	}

	public List<Visita> getVisitas() {
		return visitas;
	}

	public void setVisitas(List<Visita> visitas) {
		this.visitas = visitas;
	}
	
}
