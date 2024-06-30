package com.projeto.agenda.database.atendimento.controller;

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

import com.projeto.agenda.database.atendimento.entity.Atendimento;
import com.projeto.agenda.database.atendimento.services.AtendimentoServicos;

@RestController
@RequestMapping(path="/Appointments")
public class AtendimentoControlador {

	@Autowired
	private AtendimentoServicos atendimentoServicos;
	
	//POST METHODS
	@PostMapping(value = "/addAppointment")
	public HttpStatus insertAppointment(@RequestBody Atendimento appointment) {
		return atendimentoServicos.addAppointment(appointment) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
	}
			
	//PUT METHODS
	@PutMapping(value = "/updateAppointment")
	public HttpStatus updateAppointment(@RequestBody Atendimento appointment) {
		return atendimentoServicos.updateAppointment(appointment) ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST;
	}
	
	//GET METHODS
	@GetMapping(value= "/getAllAppointments", produces="application/json")
	public List<Atendimento> listar(){
		return atendimentoServicos.getAllAppointments();
	}
	
	@GetMapping(value= "/getAppointmentById/{id}", produces="application/json")
	public @ResponseBody Optional<Atendimento> getAppointmentById(@PathVariable Integer id){
		return atendimentoServicos.getById(id);
	}
	
	//DELETE METHODS
	@DeleteMapping(value = "/deleteAppointmentById/{id}")
	public HttpStatus deleteAppointmentById(@PathVariable Integer id) {
		atendimentoServicos.deleteAppointmentById(id);
		return HttpStatus.NO_CONTENT;
	}
	
}
