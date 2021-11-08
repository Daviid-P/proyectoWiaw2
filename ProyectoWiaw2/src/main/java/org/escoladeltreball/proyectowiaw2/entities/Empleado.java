package org.escoladeltreball.proyectowiaw2.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity(name = "EmpleadoEntity")
@Table(name  = "empleado")
@Inheritance(strategy=InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "id") //, referencedColumnName = "id"
@NamedQueries(value = {
		@NamedQuery(name = "findEmpByDni", query = "SELECT e FROM EmpleadoEntity e WHERE e.dni = :dni"),
		@NamedQuery(name = "findAllEmp", query = "SELECT e FROM EmpleadoEntity e"),
		@NamedQuery(name = "findAllEmpId", query = "SELECT e.id FROM EmpleadoEntity e"),
})
public abstract class Empleado extends Usuario{
	
	//Owner
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE},orphanRemoval=true , fetch = FetchType.EAGER)
	private Contrato contrato;

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}
	
}
