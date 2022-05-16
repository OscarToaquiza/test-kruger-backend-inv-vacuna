package com.oscar.toaquiza.kruger.models.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oscar.toaquiza.kruger.models.dao.IUsuarioVacunaDao;
import com.oscar.toaquiza.kruger.models.entity.Vacuna;

@Service
public class UsuarioVacunaServiceImplement implements IUsuarioVacunaService {

	@Autowired
	private IUsuarioVacunaDao usuarioVacunaDao;

	@Override
	public List<Object[]> findUserByVacuna(Long idVacuna) {
		return usuarioVacunaDao.findUserByVacuna(idVacuna);
	}

	@Override
	public List<Object[]> findUserByFechaInicioFechaFin(Date fechaInicio, Date fechaFin) {
		return usuarioVacunaDao.findUserByFechaInicioFechaFin(fechaInicio, fechaFin);
	}
	


}
