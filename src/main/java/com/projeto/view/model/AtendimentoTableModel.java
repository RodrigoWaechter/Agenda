package com.projeto.view.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import com.projeto.agenda.database.atendimento.entity.Atendimento;
import com.projeto.agenda.database.tipo.entity.Tipo;
import com.projeto.agenda.database.usuario.entity.Usuario;

public class AtendimentoTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private Map<String, List<Atendimento>> dataMap = new LinkedHashMap<String, List<Atendimento>>();

	public AtendimentoTableModel() {
		dataMap.put("Nome", new ArrayList<>());
		dataMap.put("Data", new ArrayList<>());
		dataMap.put("Horario_Inicio", new ArrayList<>());
		dataMap.put("Horario_Fim", new ArrayList<>());
		dataMap.put("Tipo", new ArrayList<>());
	}

	@Override
	public String getColumnName(int coluna) {
		List<String> nomeColunas = new ArrayList<>(dataMap.keySet());
		return nomeColunas.get(coluna);
	}

	@Override
	public int getRowCount() {
		return dataMap.get(getColumnName(0)).size();
	}

	@Override
	public int getColumnCount() {
		return dataMap.size();
	}

	@Override
	public Object getValueAt(int linha, int coluna) {
		String nomeColuna = getColumnName(coluna);
		List<Atendimento> listaatendimento = dataMap.get(nomeColuna);
		Atendimento atendimento = listaatendimento.get(linha);
		if ("Nome".equals(nomeColuna))
			return atendimento.getUsuario().getNome();
		if ("Data".equals(nomeColuna)) {
			return atendimento.getDate();
		}
		if ("Horario_Inicio".equals(nomeColuna)) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
			return atendimento.getHorario_inicio().toLocalTime().format(formatter);
		}

		if ("Horario_Fim".equals(nomeColuna)) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
			return atendimento.getHorario_fim().toLocalTime().format(formatter);
		}

		if ("Tipo".equals(nomeColuna))
			return atendimento.getTipo().getDesc_tipo();
		return null;
	}

	public void setValueAt(Object valor, int linha, int coluna) {
		String nomeColuna = getColumnName(coluna);
		List<Atendimento> listaAtendimento = dataMap.get(nomeColuna);

		if (dataMap.containsKey(nomeColuna)) {
			Atendimento atendimento = listaAtendimento.get(linha);
			if ("Nome".equals(nomeColuna)) {
				Usuario usuario = new Usuario();
				atendimento.setUsuario(usuario);
				atendimento.getUsuario().setNome((String) valor);
			}
			if ("Data".equals(nomeColuna)) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					Date data = sdf.parse((String) valor);
					atendimento.setDate(data);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if ("Horario_Inicio".equals(nomeColuna)) {
				try {
					atendimento.setHoraInicioFromString(atendimento.getDate() + " " + (String) valor);
				} catch (ParseException e) {
					JOptionPane.showMessageDialog(null, "ERRO HORA INICIO.", "Aviso", JOptionPane.WARNING_MESSAGE);
					e.printStackTrace();
				}
			}
			if ("Horario_Fim".equals(nomeColuna)) {
				try {
					atendimento.setHoraFimFromString(atendimento.getDate() + " " + (String) valor);
				} catch (ParseException e) {
					JOptionPane.showMessageDialog(null, "ERRO HORA FIM.", "Aviso", JOptionPane.WARNING_MESSAGE);
					e.printStackTrace();
				}
			}

			if ("Tipo".equals(nomeColuna)) {
				Tipo tipo = new Tipo();
				atendimento.setTipo(tipo);
				atendimento.getTipo().setDesc_tipo((String) valor);
			}
		}
		fireTableCellUpdated(linha, coluna);
	}

	public void addRow(Atendimento atendimento) {
		for (List<Atendimento> atendimentoList : dataMap.values()) {
			atendimentoList.add(atendimento);
		}
		fireTableDataChanged();
	}

	public void removeRow(int linha) {
		for (List<Atendimento> atendimentoList : dataMap.values()) {
			atendimentoList.remove(linha);
		}
		fireTableRowsDeleted(linha, linha);
	}
}
