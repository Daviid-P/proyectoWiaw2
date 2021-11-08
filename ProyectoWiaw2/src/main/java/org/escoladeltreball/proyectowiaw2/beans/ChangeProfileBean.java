package org.escoladeltreball.proyectowiaw2.beans;

import org.escoladeltreball.proyectowiaw2.validation.ValidEmail;

public class ChangeProfileBean {
	
	private String codigoPostal;
	
	private String direccion;
	
	private String telefono;
	
	@ValidEmail
	private String email;

	public ChangeProfileBean() {
		super();
	}

	public ChangeProfileBean(String codigoPostal, String direccion, String telefono, String email) {
		this.codigoPostal = codigoPostal;
		this.direccion = direccion;
		this.telefono = telefono;
		this.email = email;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
}
