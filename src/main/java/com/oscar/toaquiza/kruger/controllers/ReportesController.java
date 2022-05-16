package com.oscar.toaquiza.kruger.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oscar.toaquiza.kruger.models.RangoFechas;
import com.oscar.toaquiza.kruger.models.entity.Rol;
import com.oscar.toaquiza.kruger.models.entity.Usuario;
import com.oscar.toaquiza.kruger.models.services.IRolService;
import com.oscar.toaquiza.kruger.models.services.IUsuarioService;
import com.oscar.toaquiza.kruger.models.services.IUsuarioVacunaService;


/**
 * a. Filtrar por estado de vacunación.
 * b. Filtrar por tipo de vacuna.
 * c. Filtrar por rango de fecha de vacunación.

 * @author Oscar
 *
 */
@RestController
@RequestMapping("/api/report")
public class ReportesController {
	
	@Autowired
	private IUsuarioService usuarioService;
	@Autowired
	private IRolService rolService;
	@Autowired
	private IUsuarioVacunaService usuarioVacunaService;
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/empleados/vacunados")
	public ResponseEntity<?> empleadorVacunadas(){
		
		Map<String,String> resp = new HashMap<>();
		
		try {

			 List<Usuario> empleados = usuarioService.findByRolesInAndEstadoVacunacion( getListaRolEmpleado() ,true);
			
			if( empleados.isEmpty() ) {
				resp.put("msg", "No existen empleados vacunados");
				return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);
			}
			
			return new ResponseEntity<List<Usuario>>(empleados, HttpStatus.OK);
		} catch (Exception e) {
			resp.put("msg", "Error en el servidor - " + e.getMessage());
			return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/empleados/vacunados/{id}")
	public ResponseEntity<?> empleadosVacunadosVacuna( @PathVariable Long id ){
		
		Map<String,String> resp = new HashMap<>();
		List<Usuario> listaEmpleados = new ArrayList<>();
		
		try {
			
			  List<Object[]> empleados = usuarioVacunaService.findUserByVacuna(id);
			  
			
			if( empleados.isEmpty() ) {
				resp.put("msg", "No existen empleados vacunados con la vacuna con id " + id);
				return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);
			}
			
			for (Object[] objects : empleados) {
				
				Usuario empleado = new Usuario( Long.parseLong(objects[0].toString()), 
						objects[1].toString(),objects[2].toString(),
						objects[3].toString(),objects[4].toString());
				listaEmpleados.add(empleado);
			}
			
			return new ResponseEntity<List<Usuario>>(listaEmpleados, HttpStatus.OK);
		} catch (Exception e) {
			resp.put("msg", "Error en el servidor - " + e.getMessage());
			return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/empleados/vacunados")
	public ResponseEntity<?> empleadosVacunadosRangoFecha( @RequestBody RangoFechas fechas ){
		
		Map<String,String> resp = new HashMap<>();
		List<Usuario> listaEmpleados = new ArrayList<>();
		
		
		try {
			
			if( fechas.getFechaInicio() == null && fechas.getFechaFin() == null ) {
				resp.put("msg", "No se ha enviado fechas de busqueda");
				return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);
			}
			
			if(fechas.getFechaFin() == null) {
				fechas.setFechaFin( new Date() );
			}
			
			  List<Object[]> empleados = usuarioVacunaService.findUserByFechaInicioFechaFin(fechas.getFechaInicio(), fechas.getFechaFin());
			
			 for (Object[] objects : empleados) {
					
					Usuario empleado = new Usuario( Long.parseLong(objects[0].toString()), 
							objects[1].toString(),objects[2].toString(),
							objects[3].toString(),objects[4].toString());
					listaEmpleados.add(empleado);
				}
			 
			return new ResponseEntity<List<Usuario>>(listaEmpleados, HttpStatus.OK);
		} catch (Exception e) {
			resp.put("msg", "Error en el servidor - " + e.getMessage());
			return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	/**
	 * Metodos complementarios
	 * 
	 */
	
	public Rol getRolEmpleado() {
		return rolService.findById(UsuarioController.ROL_EMPLEADO);
	}
	
	public List<Rol> getListaRolEmpleado(){
		List<Rol> roles = new ArrayList<Rol>();
		roles.add(getRolEmpleado());
		return roles;
	}
	
}
