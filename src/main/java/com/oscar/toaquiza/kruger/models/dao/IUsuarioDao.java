package com.oscar.toaquiza.kruger.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.oscar.toaquiza.kruger.models.entity.Rol;
import com.oscar.toaquiza.kruger.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {
	
	public Usuario findByUsername(String username);
	
	public List<Usuario> findByRolesIn(List<Rol> roles);
	
	public List<Usuario> findByRolesInAndEstadoVacunacion(List<Rol> roles, Boolean estadoVacunacion);
	
}
