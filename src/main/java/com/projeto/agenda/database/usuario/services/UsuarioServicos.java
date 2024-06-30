package com.projeto.agenda.database.usuario.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.agenda.database.usuario.entity.Usuario;
import com.projeto.agenda.database.usuario.repository.UsuarioRepositorio;

@Service
public class UsuarioServicos {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	//POST - INSERT USER
	@Transactional
	public boolean addUser(Usuario user) {
		return usuarioRepositorio.save(user) != null;
	}
	
	//PUT - UPDATE USER
	@Transactional
	public boolean updateUser(Usuario user) {
		return usuarioRepositorio.save(user) != null;
	}
	
	//GET - SELECT ALL
	@Transactional
	public List<Usuario> getAllUsers() {
		return (List<Usuario>) usuarioRepositorio.findAll();
	} 
	
	//GET - SELECT BY ID
	@Transactional
	public Optional<Usuario> getById(Integer id) {
		return usuarioRepositorio.findById(id);
	}
	
	//GET - SELECT BY CPF
	@Transactional
	public Usuario getByCpf(String cpf) {
		return usuarioRepositorio.findByCpf(cpf);
	}
	
	//GET - SELECT BY NAME
	@Transactional
	public Usuario getByName(String nome) {
		return usuarioRepositorio.findByNome(nome);
	}
	
	//DELETE - DELETE BY ID
	@Transactional
	public void deleteUserById(Integer id) {
		usuarioRepositorio.deleteById(id);
	}
	
	//DELETE - DELETE BY CPF
	@Transactional
	public void deleteUserByCpf(String cpf) {
		usuarioRepositorio.deleteByCpf(cpf);
	}
	
}
