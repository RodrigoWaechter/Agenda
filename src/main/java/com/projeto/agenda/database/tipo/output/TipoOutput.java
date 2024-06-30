package com.projeto.agenda.database.tipo.output;

import com.projeto.agenda.database.tipo.entity.Tipo;

public class TipoOutput {

	public String retornarDados(Tipo type) {
		return ("\nId: " + type.getId_tipo() +
				"\nDescrição: " + type.getDesc_tipo() +
				"\nValor: " + type.getValor_tipo()
				);
	}
	
}
