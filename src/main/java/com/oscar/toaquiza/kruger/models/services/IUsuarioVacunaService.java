package com.oscar.toaquiza.kruger.models.services;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

public interface IUsuarioVacunaService {
	
	List<Object[]> findUserByVacuna(@Param("idVacuna") Long idVacuna);
	List<Object[]> findUserByFechaInicioFechaFin(@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin  );
}
