package com.oscar.toaquiza.kruger.models;

import java.util.Date;

public class VacunaEmpleado {
	
	private Long id;
	
	private Date fechaVacuna;
	
	private int numeroDosis;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaVacuna() {
		return fechaVacuna;
	}

	public void setFechaVacuna(Date fechaVacuna) {
		this.fechaVacuna = fechaVacuna;
	}

	public int getNumeroDosis() {
		return numeroDosis;
	}

	public void setNumeroDosis(int numeroDosis) {
		this.numeroDosis = numeroDosis;
	}
	
	
}
