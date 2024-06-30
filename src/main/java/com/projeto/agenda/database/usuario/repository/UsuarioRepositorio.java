package com.projeto.agenda.database.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.agenda.database.usuario.entity.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer>{

	//GET
	Usuario findByCpf(String cpf);
	Usuario findByNome(String nome);
		
	//DELETE
	void deleteByCpf(String cpf);
	
}
