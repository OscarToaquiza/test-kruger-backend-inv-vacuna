package com.oscar.toaquiza.kruger.models.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.oscar.toaquiza.kruger.models.dao.IUsuarioDao;
import com.oscar.toaquiza.kruger.models.entity.Rol;
import com.oscar.toaquiza.kruger.models.entity.Usuario;

@Service
public class UsuarioServiceImplement implements IUsuarioService, UserDetailsService {
	
	private Logger log = LoggerFactory.getLogger(UsuarioServiceImplement.class);
	
	@Autowired
	private IUsuarioDao usuarioDao;

	@Override
	public List<Usuario> findAll() {
		return (List<Usuario>) usuarioDao.findAll();
	}

	@Override
	public Usuario save(Usuario usuario) {
		return usuarioDao.save(usuario);
	}

	@Override
	public Usuario findById(Long id) {
		return usuarioDao.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		usuarioDao.deleteById(id);
	}
	
	@Override
	public List<Usuario> findByRoles(List<Rol> roles) {
		return usuarioDao.findByRolesIn(roles);
	}
	
	/**
	 * UserDetailsService
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioDao.findByUsername(username);
		
		if(usuario == null) {
			log.error("No existe usuario");
			new UsernameNotFoundException("No existe usuario");
		}
		
		List<GrantedAuthority> roles = usuario.getRoles().stream()
								.map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
								.collect(Collectors.toList());
		
		return new User(username, usuario.getPassword(), true, true, true, true, roles);
	}

	@Override
	public List<Usuario> findByRolesInAndEstadoVacunacion(List<Rol> roles, Boolean estadoVacunacion) {
		return usuarioDao.findByRolesInAndEstadoVacunacion(roles, estadoVacunacion);
	}



	

}
