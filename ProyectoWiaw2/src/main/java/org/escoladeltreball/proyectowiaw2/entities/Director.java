package org.escoladeltreball.proyectowiaw2.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity(name = "DirectorEntity")
@Table(name  = "director")
@PrimaryKeyJoinColumn(name = "director_id", referencedColumnName = "id")
@NamedQueries(value = {
	    @NamedQuery(name = "findDirector", query = "SELECT d FROM DirectorEntity d"),
	})
public class Director extends Usuario implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public Director(){
		super();
	}

}
