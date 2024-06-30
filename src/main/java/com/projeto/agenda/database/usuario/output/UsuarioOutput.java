package com.projeto.agenda.database.usuario.output;

import com.projeto.agenda.database.usuario.entity.Usuario;

public class UsuarioOutput {
	
	public String retornarDados(Usuario user) {
		return (
				"\nId: " + user.getId_usuario() + 
				"\nNome: " + user.getNome() +
				"\nCPF: " + user.getCpf() + 
				"\nData de Nascimento: " + user.getDataNascToString() + 
				"\nAltura: " + user.getAltura() + 
				"\nPeso: " + user.getPeso() +
				"\nTelefone: " + user.getTelefone() +
				"\nEmail: " + user.getEmail() +
				"\nEndereço: " + user.getEndereco() +
				"\nAdministrador: " + user.isAdmin()
				);
				
	}
	
}
