package com.projeto.agenda.database.tipo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.agenda.database.tipo.entity.Tipo;
import com.projeto.agenda.database.tipo.services.TipoServicos;

@RestController
@RequestMapping(path="/Types")
public class TipoControlador {

	@Autowired
	private TipoServicos tipoServicos;
	
	//POST METHODS
	@PostMapping(value = "/addType")
	public HttpStatus insertType(@RequestBody Tipo type) {
		return tipoServicos.addType(type) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
	}
		
	//PUT METHODS
	@PutMapping(value = "/updateType")
	public HttpStatus updateType(@RequestBody Tipo type) {
		return tipoServicos.updateType(type) ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST;
	}
	
	//GET METHODS
	@GetMapping(value= "/getAllTypes", produces="application/json")
	public List<Tipo> listar(){
		return tipoServicos.getAllTypes();
	}
	
	@GetMapping(value= "/getTypeById/{id}", produces="application/json")
	public @ResponseBody Optional<Tipo> getTypeById(@PathVariable Integer id){
		return tipoServicos.getById(id);
	}
	
	//DELETE METHODS
	@DeleteMapping(value = "/deleteTypeById/{id}")
	public HttpStatus deleteTypeById(@PathVariable Integer id) {
		tipoServicos.deleteTypeById(id);
		return HttpStatus.NO_CONTENT;
	}
}
