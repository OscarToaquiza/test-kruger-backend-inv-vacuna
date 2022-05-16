package com.oscar.toaquiza.kruger.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	@Size(min = 10, max = 10, message = "El número de cédula debe tener 10 digitos")
	private String cedula;

	@NotEmpty(message = "Nombre es un campo oblogatorio")
	@Column(nullable = false)
	private String nombre;

	@NotEmpty(message = "Apellidos es un campo oblogatorio")
	@Column(nullable = false)
	private String apellido;

	@Email(message = "Correo no válido")
	@Column(nullable = false)
	private String correo;

	@Column(name = "fecha_nacimiento")
	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;

	private String domicilio;

	private String telefono;

	private String username;

	private String password;

	@Column(name = "estado_vacunacion")
	@ColumnDefault("false")
	private boolean estadoVacunacion;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_rol", inverseJoinColumns = @JoinColumn(name = "rol_id"))
	private List<Rol> roles;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_usuario")
	private List<UsuarioVacuna> vacunas;
	
	public Usuario() {
		this.vacunas = new ArrayList<>();
	}
	
	
	
	public Usuario(Long id,String cedula,String nombre,	String apellido,String correo) {
		super();
		this.id = id;
		this.cedula = cedula;
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = correo;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEstadoVacunacion() {
		return estadoVacunacion;
	}

	public void setEstadoVacunacion(boolean estadoVacunacion) {
		this.estadoVacunacion = estadoVacunacion;
	}

	public List<Rol> getRoles() {
		return roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}

	public List<UsuarioVacuna> getVacunas() {
		return vacunas;
	}

	public void setVacunas(List<UsuarioVacuna> vacunas) {
		this.vacunas = vacunas;
	}
	
	public void generatedUsername() {
		
		String username = "";
		
		if(this.getApellido().contains(" ")) {
			username = this.getApellido().split(" ")[0];
		}else {
			username = this.getApellido();
		}
		
		username = username +"_";
		
		if(this.getNombre().contains(" ")) {
			username = username + this.getNombre().split(" ")[0];
		}else {
			username = this.getNombre();
		}
		
		this.setUsername(username);
	}
	


}
