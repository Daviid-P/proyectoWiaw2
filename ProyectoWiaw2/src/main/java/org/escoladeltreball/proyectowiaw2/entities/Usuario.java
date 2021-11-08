	package org.escoladeltreball.proyectowiaw2.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity(name = "UsuarioEntity")
@Table(name  = "usuario")
@Inheritance(strategy=InheritanceType.JOINED)
@NamedQueries(value = {
    @NamedQuery(name = "findUserByDni", query = "SELECT u FROM UsuarioEntity u WHERE u.dni = :dni"),
    @NamedQuery(name = "findAllDnis", query = "SELECT u.dni FROM UsuarioEntity u"),
    @NamedQuery(name = "findAllEmails", query = "SELECT u.email FROM UsuarioEntity u"),
})
public abstract class Usuario{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(length=9,nullable=false, unique=true)
	private String dni;
	
	@Column(nullable=false)
	private String password;
	
	@Column(nullable=false)
	private boolean activo;
	
	@Column(nullable=false)
	private String nombre;
	
	@Column(nullable=false)
	private String apellidos;
	
	@Column(nullable=false)
	private LocalDate fecha_nacimiento;
	
	@Column(nullable=false)
	private String codigoPostal;
	
	@Column(nullable=false)
	private String direccion;
	
	@Column(nullable=true)
	private long telefono;
	
	@Column(nullable=true)
	private String email;
	
	//Inverse
	@Fetch(value = FetchMode.SUBSELECT)
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval=true, mappedBy= "usuario", fetch = FetchType.EAGER)
	private List<Autoridad> autoridades;
	
//	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, mappedBy = "usuario")
//	private PasswordResetToken passwordResetToken;
	
	
	public Usuario(){
		super();
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
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
	
	

	
	
	
}
