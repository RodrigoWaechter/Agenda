package com.projeto.agenda.database.atendimento.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.projeto.agenda.database.atendimento.entity.Atendimento;
import com.projeto.agenda.database.tipo.entity.Tipo;
import com.projeto.agenda.database.usuario.entity.Usuario;

public class AtendimentoDatabase {
	private List<Atendimento> atendimentos;

	public AtendimentoDatabase() {
		atendimentos = new ArrayList<>();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Usuario usuario1 = new Usuario(1, "Usuario1", "05432859642", dateFormatter.parse("2000-10-15"),
					"usuario1@example.com", "1234567890", 1.75f, 70.0f, "Endereco1", "senha123", true);
			Usuario usuario2 = new Usuario(2, "Usuario2", "02934758192", dateFormatter.parse("2000-10-15"),
					"usuario1@example.com", "1234567890", 1.75f, 70.0f, "Endereco1", "senha123", true);

			LocalDateTime now = LocalDateTime.now().withNano(0).withSecond(0);

			atendimentos.add(new Atendimento(new Tipo("Tipo1", 10.0f), usuario1, now, now.plusHours(1)));

			atendimentos.add(new Atendimento(new Tipo("Tipo2", 20.0f), usuario2, now.plusHours(2), now.plusHours(3)));

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	public List<Atendimento> getAtendimentos() {
		return atendimentos;
	}
}
