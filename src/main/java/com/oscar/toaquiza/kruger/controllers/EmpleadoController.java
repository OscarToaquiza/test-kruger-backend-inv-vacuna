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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oscar.toaquiza.kruger.models.VacunaEmpleado;
import com.oscar.toaquiza.kruger.models.entity.Usuario;
import com.oscar.toaquiza.kruger.models.entity.UsuarioVacuna;
import com.oscar.toaquiza.kruger.models.entity.Vacuna;
import com.oscar.toaquiza.kruger.models.services.IUsuarioService;
import com.oscar.toaquiza.kruger.models.services.IVacunaService;

@RestController
@RequestMapping("/api")
public class EmpleadoController {

	public final static Long ROL_EMPLEADO = 2L;
	
	@Autowired
	private IUsuarioService usuarioService;
	@Autowired
	private IVacunaService vacunaService;
	
	/*
	 * Como Empleado requiero ingresar al sistema para visualizar y actualizar mi información.
	 */
	@Secured("ROLE_EMPLEADO")
	@PutMapping("/update/empleado/info/{id}")
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
			//Completar info.
			usuarioFind.setFechaNacimiento(usuario.getFechaNacimiento());
			usuarioFind.setDomicilio(usuario.getDomicilio());
			usuarioFind.setTelefono(usuario.getTelefono());
			
			usuarioUpdate = usuarioService.save(usuarioFind);
			
		} catch (DataAccessException e) {
			resp.put("msg", "Error en el servidor - " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Usuario>(usuarioUpdate, HttpStatus.OK);
	}
	
	/*
	 * Agregar lista vacunas
	 */
	@Secured("ROLE_EMPLEADO")
	@PutMapping(value="/update/vacunas/{id}")
	public  ResponseEntity<?> editar( @RequestBody List<VacunaEmpleado> listaVacunas , BindingResult result, @PathVariable Long id ) {
		
		Map<String,String> resp = new HashMap<>();
		
		Usuario usuarioUpdate = null;
		Usuario usuarioFind = usuarioService.findById(id);
		List<UsuarioVacuna> listaVacunasUsuario = new ArrayList<UsuarioVacuna>();
		
		if(usuarioFind == null) {
			resp.put("msg", "ID: "+ id + " No encontrado");
			return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);
		}	
		
		try {
			
			if(listaVacunas.isEmpty()) {
				resp.put("msg", "Lista de vacunas vacia");
				return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);
			}else {
				for (VacunaEmpleado vacunaEmpleado : listaVacunas) {
					Vacuna vacuna = vacunaService.findById(vacunaEmpleado.getId());
					if(vacuna == null) {
						resp.put("msg", "ID "+ vacunaEmpleado.getId() +"de vacuna No Válido");
					}else {
						UsuarioVacuna usuarioVacuna = new UsuarioVacuna();
						usuarioVacuna.setFechaVacuna(vacunaEmpleado.getFechaVacuna());
						usuarioVacuna.setNumeroDosis(vacunaEmpleado.getNumeroDosis());
						usuarioVacuna.setVacuna(vacuna);
						listaVacunasUsuario.add(usuarioVacuna);
					}
				}
				
				if( !resp.isEmpty() ) {
					return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);
				}
			}
			
			//Agregar lista de vacunas
			usuarioFind.setEstadoVacunacion(true);
			usuarioFind.setVacunas(listaVacunasUsuario);
			usuarioUpdate = usuarioService.save(usuarioFind);
			
		} catch (DataAccessException e) {
			resp.put("msg", "Error en el servidor - " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Usuario>(usuarioUpdate, HttpStatus.OK);
	}
	
}
