package com.oscar.toaquiza.kruger.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oscar.toaquiza.kruger.models.dao.IVacunaDao;
import com.oscar.toaquiza.kruger.models.entity.Vacuna;

@Service
public class VacunaServiceImplement implements IVacunaService {

	@Autowired
	private IVacunaDao vacunaDao;
	
	@Override
	public Vacuna findById(Long id) {
		return vacunaDao.findById(id).orElse(null);
	}

}
