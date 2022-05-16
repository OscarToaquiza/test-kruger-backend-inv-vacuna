package com.oscar.toaquiza.kruger.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oscar.toaquiza.kruger.models.entity.Rol;
import com.oscar.toaquiza.kruger.models.entity.Usuario;
import com.oscar.toaquiza.kruger.models.services.IRolService;
import com.oscar.toaquiza.kruger.models.services.IUsuarioService;
import com.oscar.toaquiza.kruger.models.services.IUsuarioVacunaService;

/**
 * Como Administrador requiere registrar, editar, listar y eliminar a los empleados.
a. Registrar la siguiente información del empleado.
- Cédula.
- Nombres.
- Apellidos.
- Correo electrónico.
b. Los campos deben contener validaciones de acuerdo al tipo de dato:
- Todos los campos son requeridos.
- Cédula válida. (Incluir un valor numérico y único de 10 dígitos)
- Correo electrónico válido.
- Nombres y apellidos no deben contener números o caracteres especiales.
c. Al dar de alta un empleado se debe generar un usuario y contraseña para el empleado
 * @author Oscar
 *
 */

@RestController
@RequestMapping("/api")
public class UsuarioController {

	public final static Long ROL_EMPLEADO = 2L;
	
	@Autowired
	private IUsuarioService usuarioService;
	@Autowired
	private IRolService rolService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	/**
	 * Crear usuarios, solo los Administradores pueden crear usuarios
	 * El username se crea: primer apellido guin bajo nombre
	 * El password es el numero de cedula encirptado
	 * @param usuario
	 * @param result
	 * @return
	 */
	@Secured("ROLE_ADMIN")
	@PostMapping("/create/empleado")
	public ResponseEntity<?> registrar( @Valid @RequestBody Usuario usuario, BindingResult result){
		
		Usuario usuarioNew = null; 
		Map<String,String> resp = new HashMap<>();
		
		if(result.hasErrors()) {
			for(  FieldError  e : result.getFieldErrors()) {
				resp.put(e.getField(),e.getDefaultMessage() );
			}
			return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
		}

		try {	
			
			usuario.generatedUsername();
			usuario.setPassword(passwordEncoder.encode(usuario.getCedula()));
			usuario.setRoles(getListaRolEmpleado());
			usuarioNew = usuarioService.save(usuario);
			
		} catch (DataAccessException e) {
			resp.put("msg", "Error en el servidor - " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Usuario>(usuarioNew, HttpStatus.OK);
	}
	
	
	/*
	 * 
	 */
	@Secured("ROLE_ADMIN")
	@PutMapping("/update/empleado/{id}")
	public  ResponseEntity<?> editar( @Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id ) {
		
		Map<String,String> resp = new HashMap<>();
		
		if(result.hasErrors()) {
			for(  FieldError  e : result.getFieldErrors()) {
				resp.put(e.getField(),e.getDefaultMessage() );
			}
			return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
		}
		
		Usuario usuarioUpdate = null;
		Usuario usuarioFind = usuarioService.findById(id);
		
		if(usuarioFind == null) {
			resp.put("msg", "ID: "+ id + " No encontrado");
			return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);
		}
		
		try {
			
			usuarioFind.setApellido(usuario.getApellido());
			usuarioFind.setNombre(usuario.getNombre());
			usuarioFind.setCedula(usuario.getCedula());
			usuarioFind.setCorreo(usuario.getCorreo());
			
			usuarioUpdate = usuarioService.save(usuarioFind);
			
		} catch (DataAccessException e) {
			resp.put("msg", "Error en el servidor - " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Usuario>(usuarioUpdate, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/listar/empleados")
	public List<Usuario> listar(){
		return usuarioService.findByRoles(getListaRolEmpleado());
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/delete/empleado/{id}")
	public  ResponseEntity<?>  eliminar( @PathVariable Long id ) {
		
		Map<String,String> resp = new HashMap<>();
		try {
			usuarioService.delete(id);
			resp.put("msg", "Usuario " + id +" eliminado.");
		} catch (DataAccessException e) {
			resp.put("msg", "Error en el servidor - " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
		
	}
	
	/**
	 * Metodos complementarios
	 * 
	 */
	
	public Rol getRolEmpleado() {
		return rolService.findById(ROL_EMPLEADO);
	}
	
	public List<Rol> getListaRolEmpleado(){
		List<Rol> roles = new ArrayList<Rol>();
		roles.add(getRolEmpleado());
		return roles;
	}
	
}
