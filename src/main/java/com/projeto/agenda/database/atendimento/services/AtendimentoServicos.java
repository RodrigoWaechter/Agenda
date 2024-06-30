package com.projeto.agenda.database.atendimento.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.agenda.database.atendimento.entity.Atendimento;
import com.projeto.agenda.database.atendimento.repository.AtendimentoRepositorio;

@Service
public class AtendimentoServicos {
	
	@Autowired
	private AtendimentoRepositorio atendimentoRepositorio;
	
	//POST - INSERT APPOINTMENT
	@Transactional
	public boolean addAppointment(Atendimento appointment) {
		return atendimentoRepositorio.save(appointment) != null;
	}
	
	//PUT - UPDATE APPOINTMENT
	@Transactional
	public boolean updateAppointment(Atendimento appointment) {
		return atendimentoRepositorio.save(appointment) != null;
	}
	
	//GET - SELECT ALL
	@Transactional
	public List<Atendimento> getAllAppointments(){
		return (List<Atendimento>) atendimentoRepositorio.findAll();
	}
	
	//GET - SELECT BY ID
	@Transactional
	public Optional<Atendimento> getById(Integer id){
		return atendimentoRepositorio.findById(id);
	}
	
	//DELETE - DELETE BY ID
	@Transactional
	public void deleteAppointmentById(Integer id) {
		atendimentoRepositorio.deleteById(id);
	}
	
}
