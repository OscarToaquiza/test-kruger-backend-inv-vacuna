package com.oscar.toaquiza.kruger.models.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.oscar.toaquiza.kruger.models.entity.UsuarioVacuna;
import com.oscar.toaquiza.kruger.models.entity.Vacuna;

public interface IUsuarioVacunaDao extends CrudRepository<UsuarioVacuna, Long> {
	
    @Query(value = "select distinct u.id, u.nombre, u.apellido , u.cedula , u.correo \r\n"
    		+ "from usuario_vacuna uv \r\n"
    		+ "left join usuario u on uv.id_usuario = u.id \r\n"
    		+ "where uv.id_vacuna = :idVacuna", nativeQuery = true)
    List<Object[]> findUserByVacuna(@Param("idVacuna") Long idVacuna);
    
    @Query(value = "select distinct u.id, u.nombre, u.apellido , u.cedula , u.correo \r\n"
    		+ "from usuario_vacuna uv \r\n"
    		+ "left join usuario u on uv.id_usuario = u.id \r\n"
    		+ "where uv.fecha_vacunacion >= :fechaInicio and uv.fecha_vacunacion < :fechaFin", nativeQuery = true)
    List<Object[]> findUserByFechaInicioFechaFin(@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin  );
	
}
