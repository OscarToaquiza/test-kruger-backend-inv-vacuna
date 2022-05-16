package com.oscar.toaquiza.kruger.models.services;

import java.util.List;

import com.oscar.toaquiza.kruger.models.entity.Rol;
import com.oscar.toaquiza.kruger.models.entity.Usuario;

public interface IUsuarioService {
	
	public Usuario save (Usuario usuario);
	
	public List<Usuario> findAll();
	
	public List<Usuario> findByRoles(List<Rol> roles);
	
	public Usuario findById(Long id);
	
	public void delete(Long id);
	
	public List<Usuario> findByRolesInAndEstadoVacunacion(List<Rol> roles, Boolean estadoVacunacion);

}
