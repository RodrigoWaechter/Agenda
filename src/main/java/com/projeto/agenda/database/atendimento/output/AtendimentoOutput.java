package com.projeto.agenda.database.atendimento.output;

import com.projeto.agenda.database.atendimento.entity.Atendimento;

public class AtendimentoOutput {

	public String retornarDados(Atendimento appointment) {
		return ("Id: " + appointment.getId_atendimento() +
				"Usuário: " + appointment.getUsuario() +
				"Tipo: " + appointment.getTipo() +
				"Início: " + appointment.getHoraInicioToBD() +
				"Fim: " + appointment.getHoraFimToBD()
				);
	}
	
}
