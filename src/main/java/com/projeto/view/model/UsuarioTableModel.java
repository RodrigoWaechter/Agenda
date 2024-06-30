package com.projeto.view.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import com.projeto.agenda.database.usuario.entity.Usuario;

public class UsuarioTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private Map<String, List<Usuario>> dataMap = new LinkedHashMap<String, List<Usuario>>();

	public UsuarioTableModel() {
		dataMap.put("Nome", new ArrayList<>());
		dataMap.put("CPF", new ArrayList<>());
		dataMap.put("Data_Nascimento", new ArrayList<>());
		dataMap.put("Senha", new ArrayList<>());
		dataMap.put("Telefone", new ArrayList<>());
		dataMap.put("Altura", new ArrayList<>());
		dataMap.put("Peso", new ArrayList<>());
		dataMap.put("Email", new ArrayList<>());
		dataMap.put("Endereco", new ArrayList<>());
		dataMap.put("Admin", new ArrayList<>());

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
		List<Usuario> listausuario = dataMap.get(nomeColuna);
		Usuario usuario = listausuario.get(linha);

		if ("Nome".equals(nomeColuna))           return usuario.getNome();
		if ("CPF".equals(nomeColuna))            return usuario.getCpf();
		if ("Data_Nascimento".equals(nomeColuna))return usuario.getDataNascToString();
		if ("Senha".equals(nomeColuna))          return usuario.getSenha();
		if ("Telefone".equals(nomeColuna))       return usuario.getTelefone();
		if ("Altura".equals(nomeColuna))         return usuario.getAltura();
		if ("Peso".equals(nomeColuna))           return usuario.getPeso();
		if ("Email".equals(nomeColuna))          return usuario.getEmail();
		if ("Endereco".equals(nomeColuna))       return usuario.getEndereco();
		if ("Admin".equals(nomeColuna))          return usuario.isAdmin();
												 return null;
	}

	public void setValueAt(Object valor, int linha, int coluna) {
		String nomeColuna = getColumnName(coluna);
		List<Usuario> listaUsuario = dataMap.get(nomeColuna);

		if (dataMap.containsKey(nomeColuna)) {
			Usuario usuario = listaUsuario.get(linha);
			
			if ("Nome".equals(nomeColuna))     usuario.setNome((String)valor);
			if ("CPF".equals(nomeColuna))      usuario.setCpf((String)valor);
			if ("Senha".equals(nomeColuna))    usuario.setSenha((String)valor);
			if ("Telefone".equals(nomeColuna)) usuario.setTelefone((String)valor);
			if ("Altura".equals(nomeColuna))   usuario.setAltura((Float)valor);
			if ("Peso".equals(nomeColuna))     usuario.setPeso((Float)valor);
			if ("Email".equals(nomeColuna))    usuario.setEmail((String)valor);
			if ("Endereco".equals(nomeColuna)) usuario.setEndereco((String)valor);
			if ("Admin".equals(nomeColuna))    usuario.setAdmin((Boolean)valor);
			if ("Data_Nascimento".equals(nomeColuna)) {
				try {
					usuario.setDataNascFromString((String)valor);
				} catch (ParseException e) {
					JOptionPane.showMessageDialog(null, "Data inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		}
		fireTableCellUpdated(linha, coluna);
	}

	

	public void addRow(Usuario usuario) {
		for (List<Usuario> usuarioList : dataMap.values()) {
			usuarioList.add(usuario);
		}
		fireTableDataChanged();
	}

	public void removeRow(int linha) {
		for (List<Usuario> usuarioList : dataMap.values()) {
			usuarioList.remove(linha);
		}
		fireTableRowsDeleted(linha, linha);
	}
}
