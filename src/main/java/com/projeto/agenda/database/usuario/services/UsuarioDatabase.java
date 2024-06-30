package com.projeto.agenda.database.usuario.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.projeto.agenda.database.usuario.entity.Usuario;

public class UsuarioDatabase {
	private List<Usuario> usuarios;

	public UsuarioDatabase() {
		usuarios = new ArrayList<>();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			usuarios.add(new Usuario(1, "João", "94327181912", dateFormatter.parse("2000-10-15"), "joao@example.com",
					"999543404", 1.70f, 80.0f, "Rua Exemplo, Cidade", "senha123", false));
			usuarios.add(new Usuario(2, "Maria", "03217593767", dateFormatter.parse("2004-02-03"), "maria@example.com",
					"999644704", 1.80f, 88.0f, "Avenida Teste, Cidade", "senha456", false));
			usuarios.add(new Usuario(3, "Pedro", "04172938536", dateFormatter.parse("1998-05-20"), "pedro@example.com",
					"999128631", 1.87f, 93.0f, "Rua Principal, Cidade", "senha789", true));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

}