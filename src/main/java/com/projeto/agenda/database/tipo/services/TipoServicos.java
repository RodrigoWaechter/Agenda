package com.projeto.agenda.database.tipo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.agenda.database.tipo.entity.Tipo;
import com.projeto.agenda.database.tipo.repository.TipoRepositorio;

@Service
public class TipoServicos {

	@Autowired
	private TipoRepositorio tipoRepositorio;
	
	//POST - INSERT TYPE
	@Transactional
	public boolean addType(Tipo type) {
		return tipoRepositorio.save(type) != null;
	}
	
	//PUT - UPDATE TYPE
	@Transactional
	public boolean updateType(Tipo type) {
		return tipoRepositorio.save(type) != null;
	}
	
	//GET - SELECT ALL
	@Transactional
	public List<Tipo> getAllTypes() {
		return (List<Tipo>) tipoRepositorio.findAll();
	}  
	
	//GET - SELECT BY ID
	@Transactional
	public Optional<Tipo> getById(Integer id) {
		return tipoRepositorio.findById(id);
	}
	
	//DELETE = DELETE BY ID
	@Transactional
	public void deleteTypeById(Integer id) {
		tipoRepositorio.deleteById(id);
	}
	
}
