package com.projeto.view.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import com.projeto.agenda.database.tipo.entity.Tipo;

public class TipoTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private Map<String, List<Tipo>> dataMap = new HashMap<String, List<Tipo>>();

	public TipoTableModel() {
		dataMap.put("Desc", new ArrayList<>());
		dataMap.put("Valor", new ArrayList<>());
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
		List<Tipo> listaTipo = dataMap.get(nomeColuna);
		Tipo tipo = listaTipo.get(linha);

		if ("Desc".equals(nomeColuna))
			return tipo.getDesc_tipo();
		if ("Valor".equals(nomeColuna))
			return tipo.getValor_tipo();
		return null;
	}

	public void setValueAt(Object valor, int linha, int coluna) {
		String nomeColuna = getColumnName(coluna);
		List<Tipo> listaTipo = dataMap.get(nomeColuna);

		if (dataMap.containsKey(nomeColuna)) {
			Tipo tipo = listaTipo.get(linha);
			
			if ("Desc".equals(nomeColuna))
				tipo.setDesc_tipo((String) valor);
			if ("Valor".equals(nomeColuna))
				tipo.setValor_tipo(Float.parseFloat((String) valor));
		}
		fireTableCellUpdated(linha, coluna);
	}

	

	public void addRow(Tipo tipo) {
		for (List<Tipo> tipoList : dataMap.values()) {
			tipoList.add(tipo);
		}
		fireTableDataChanged();
	}

	public void removeRow(int linha) {
		for (List<Tipo> tipoList : dataMap.values()) {
			tipoList.remove(linha);
		}
		fireTableRowsDeleted(linha, linha);
	}
}
