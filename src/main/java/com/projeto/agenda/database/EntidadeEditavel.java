package com.projeto.agenda.database;

public interface EntidadeEditavel {
	String[] getValores();

	void atualizarValores(String[] values);
}