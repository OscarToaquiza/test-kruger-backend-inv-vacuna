package com.oscar.toaquiza.kruger.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oscar.toaquiza.kruger.models.dao.IRolDao;
import com.oscar.toaquiza.kruger.models.entity.Rol;


@Service
public class RolServiceImplement implements IRolService{

	@Autowired
	private IRolDao rolDao;
	
	@Override
	public Rol findById(Long id) {
		return rolDao.findById(id).orElse(null);
	}

}
