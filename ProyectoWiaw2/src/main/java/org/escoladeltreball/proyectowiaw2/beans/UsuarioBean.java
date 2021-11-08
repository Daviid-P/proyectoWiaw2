package org.escoladeltreball.proyectowiaw2.beans;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.escoladeltreball.proyectowiaw2.entities.Autoridad;
import org.escoladeltreball.proyectowiaw2.validation.ValidDni;
import org.escoladeltreball.proyectowiaw2.validation.ValidEmail;

public class UsuarioBean {
	
	@ValidDni
	private String dni;
	
	private String password;
	
	private boolean activo;
	
	private String nombre;
	
	private String apellidos;
	
	private LocalDate fecha_nacimiento;
	
	private String codigoPostal;
	
	private String direccion;
	
	private long telefono;
	
	@ValidEmail
	private String email;
	
	//Inverse
	private List<Autoridad> autoridades = new ArrayList<Autoridad>();
	
	
	public UsuarioBean(){
		super();
	}


	public UsuarioBean(String dni, String password, boolean activo, String nombre, String apellidos,
			LocalDate fecha_nacimiento, String codigoPostal, String direccion, long telefono, String email,
			List<Autoridad> autoridades) {
		this.dni = dni;
		this.password = password;
		this.activo = activo;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fecha_nacimiento = fecha_nacimiento;
		this.codigoPostal = codigoPostal;
		this.direccion = direccion;
		this.telefono = telefono;
		this.email = email;
		this.autoridades = autoridades;
	}


	public String getDni() {
		return dni;
	}


	public void setDni(String dni) {
		this.dni = dni;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public boolean isActivo() {
		return activo;
	}


	public void setActivo(boolean activo) {
		this.activo = activo;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellidos() {
		return apellidos;
	}


	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}


	public LocalDate getFecha_nacimiento() {
		return fecha_nacimiento;
	}


	public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
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


	public long getTelefono() {
		return telefono;
	}


	public void setTelefono(long telefono) {
		this.telefono = telefono;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public List<Autoridad> getAutoridades() {
		return autoridades;
	}


	public void setAutoridades(List<Autoridad> autoridades) {
		this.autoridades = autoridades;
	}


	@Override
	public String toString() {
		return "UsuarioBean [dni=" + dni + ", password=" + password + ", activo=" + activo + ", nombre=" + nombre
				+ ", apellidos=" + apellidos + ", fecha_nacimiento=" + fecha_nacimiento + ", codigoPostal="
				+ codigoPostal + ", direccion=" + direccion + ", telefono=" + telefono + ", email=" + email
				+ ", autoridades=" + autoridades + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (activo ? 1231 : 1237);
		result = prime * result + ((apellidos == null) ? 0 : apellidos.hashCode());
		result = prime * result + ((autoridades == null) ? 0 : autoridades.hashCode());
		result = prime * result + ((codigoPostal == null) ? 0 : codigoPostal.hashCode());
		result = prime * result + ((direccion == null) ? 0 : direccion.hashCode());
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fecha_nacimiento == null) ? 0 : fecha_nacimiento.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + (int) (telefono ^ (telefono >>> 32));
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioBean other = (UsuarioBean) obj;
		if (activo != other.activo)
			return false;
		if (apellidos == null) {
			if (other.apellidos != null)
				return false;
		} else if (!apellidos.equals(other.apellidos))
			return false;
		if (autoridades == null) {
			if (other.autoridades != null)
				return false;
		} else if (!autoridades.equals(other.autoridades))
			return false;
		if (codigoPostal == null) {
			if (other.codigoPostal != null)
				return false;
		} else if (!codigoPostal.equals(other.codigoPostal))
			return false;
		if (direccion == null) {
			if (other.direccion != null)
				return false;
		} else if (!direccion.equals(other.direccion))
			return false;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (fecha_nacimiento == null) {
			if (other.fecha_nacimiento != null)
				return false;
		} else if (!fecha_nacimiento.equals(other.fecha_nacimiento))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (telefono != other.telefono)
			return false;
		return true;
	}
	
	
}
