package com.projeto.agenda.database.usuario.controller;

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

import com.projeto.agenda.database.usuario.entity.Usuario;
import com.projeto.agenda.database.usuario.services.UsuarioServicos;

@RestController
@RequestMapping(path="/Users")
public class UsuarioControlador {

	@Autowired
	private UsuarioServicos usuarioServicos;
	
	//POST METHODS
	@PostMapping(value = "/addUser")
	public HttpStatus insertUser(@RequestBody Usuario user) {
		return usuarioServicos.addUser(user) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
	}
	
	//PUT METHODS
	@PutMapping(value = "/updateUser")
	public HttpStatus updateUser(@RequestBody Usuario user) {
		return usuarioServicos.updateUser(user) ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST;
	}
	
	//GET METHODS
	@GetMapping(value= "/getAllUsers", produces="application/json")
	public List<Usuario> listar(){
		return usuarioServicos.getAllUsers();
	}
	
	@GetMapping(value= "/getUserById/{id}", produces="application/json")
	public @ResponseBody Optional<Usuario> getUserById(@PathVariable Integer id){
		return usuarioServicos.getById(id);
	}
	
	@GetMapping(value= "/getUserByCpf/{cpf}", produces="application/json")
	public @ResponseBody Usuario getUserByCpf(@PathVariable String cpf){
		return usuarioServicos.getByCpf(cpf);
	}
	
	@GetMapping(value= "/getUserByName/{name}", produces="application/json")
	public @ResponseBody Usuario getUserByName(@PathVariable String name){
		return usuarioServicos.getByName(name);
	}
	
	//DELETE METHODS
	@DeleteMapping(value = "/deleteUserById/{id}")
	public HttpStatus deleteUserById(@PathVariable Integer id) {
		usuarioServicos.deleteUserById(id);
		return HttpStatus.NO_CONTENT;
	}
	
	@DeleteMapping(value = "/deleteUserByCpf/{cpf}")
	public HttpStatus deleteUserByCpf(@PathVariable String cpf) {
		usuarioServicos.deleteUserByCpf(cpf);
		return HttpStatus.NO_CONTENT;
	}
	
}
