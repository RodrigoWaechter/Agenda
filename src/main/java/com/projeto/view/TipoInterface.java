package com.projeto.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

import com.projeto.agenda.database.tipo.entity.Tipo;
import com.projeto.agenda.database.tipo.services.TipoDatabase;
import com.projeto.view.custom.RoundedBorder;
import com.projeto.view.model.TipoTableModel;

public class TipoInterface<Entidade> extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private TipoTableModel modelo;
	private JTextField txtNome;
	private JTextField txtValor;
	private JPanel panelTopo;
	private GridBagConstraints gbc;
	private Insets defaultInsets = new Insets(10, 10, 10, 10);
	private JTable tabela;
	private DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	private TipoDatabase tipoDatabase;
	private JPanel contentPane;
	private int linhaEditar = -1;

	public TipoInterface() {
		setTitle("Cadastro de tipo");
		setSize(500, 400);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setBackground(Color.white);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		contentPane = new JPanel(new BorderLayout());
		contentPane.setBackground(Color.LIGHT_GRAY);
		setContentPane(contentPane);
		gbc = new GridBagConstraints();

		panelTopo = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		panelTopo.setBackground(Color.black);

		addLabelAndComponent(panelTopo, "Nome", txtNome = setConfigTxT(6), 0, 0);
		addLabelAndComponent(panelTopo, "Valor", txtValor = setConfigTxT(6), 0, 1);

		contentPane.add(panelTopo, BorderLayout.NORTH);

		JPanel panelBaixo = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		panelBaixo.setBackground(Color.black);
		/*
		 * JButton botaoGet = setConfigBotao("Get", e -> { }); panelBaixo.add(botaoGet);
		 */
		JButton botaoNovo = setConfigBotao("Salvar", e -> botaoSalvarClicado());
		panelBaixo.add(botaoNovo);

		JButton botaoEditar = setConfigBotao("Editar", e -> botaoEditarClicado());
		panelBaixo.add(botaoEditar);

		JButton botaoDeletar = setConfigBotao("Deletar", e -> botaoDeletarClicado());
		panelBaixo.add(botaoDeletar);

		contentPane.add(panelBaixo, BorderLayout.SOUTH);

		tabela = new JTable();
		tabela.setFont(new Font("Arial", Font.PLAIN, 18));
		tabela.setPreferredSize(new Dimension(300, 200));
		tabela.setBackground(Color.white);
		tabela.setFocusable(false);
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabela.setRowHeight(30);
		tabela.setDefaultEditor(Object.class, null);
		modelo = new TipoTableModel();
		tabela.setModel(modelo);

		tipoDatabase = new TipoDatabase();
		List<Tipo> tiposFicticios = tipoDatabase.getTipos();
		for (Tipo tipo : tiposFicticios) {
			modelo.addRow(tipo);
		}

		contentPane.add(new JScrollPane(tabela), BorderLayout.CENTER);
		for (int i = 0; i < tabela.getColumnCount(); i++) {
			centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
			tabela.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
	}

	private void botaoSalvarClicado() {
		try {
			if (linhaEditar >= 0) {
				modelo.setValueAt(txtNome.getText(), linhaEditar, 0);
				modelo.setValueAt(txtValor.getText(), linhaEditar, 1);
				linhaEditar = -1;
			} else {
				String nome = txtNome.getText();
				String valor = txtValor.getText();

				if (!nome.isEmpty() && !valor.isEmpty()) {
					Tipo tipo = new Tipo();
			
					tipo.setDesc_tipo(nome);
					tipo.setValor_tipo(Float.parseFloat(valor));
					modelo.addRow(tipo);
				}
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "O valor informado não é válido.", "Erro", JOptionPane.ERROR_MESSAGE);
		}
		txtNome.setText("");
		txtValor.setText("");
	}

	private void botaoEditarClicado() {
		int linhaSelecionada = tabela.getSelectedRow();

		if (linhaSelecionada >= 0) {
			String[] valoresLinhaSelecionada = obterValoresLinhaSelecionada();

			Tipo tipo = new Tipo();
			tipo.setDesc_tipo(valoresLinhaSelecionada[0]);
			tipo.setValor_tipo(Float.parseFloat(valoresLinhaSelecionada[1]));

			txtNome.setText(valoresLinhaSelecionada[0]);
			txtValor.setText(valoresLinhaSelecionada[1]);
			linhaEditar = linhaSelecionada;
		} else {
			JOptionPane.showMessageDialog(null, "Selecione uma linha para editar.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void botaoDeletarClicado() {
		int linhaSelecionada = tabela.getSelectedRow();

		if (linhaSelecionada >= 0) {
			int opcao = JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja deletar?", "Confirmação",
					JOptionPane.YES_NO_OPTION);

			if (opcao == JOptionPane.YES_OPTION) {
				modelo.removeRow(linhaSelecionada);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Selecione uma linha para deletar.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	public void atualizarLinhaSelecionada(Tipo tipo) {
		int linhaSelecionada = tabela.getSelectedRow();
		if (linhaSelecionada >= 0) {
			modelo.setValueAt(tipo.getDesc_tipo(), linhaSelecionada, 0);
			modelo.setValueAt(String.valueOf(tipo.getValor_tipo()), linhaSelecionada, 1);
		} else {
			JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada.", "Aviso", JOptionPane.WARNING_MESSAGE);
		}
	}

	public String[] obterValoresLinhaSelecionada() {
		int linhaSelecionada = tabela.getSelectedRow();

		if (linhaSelecionada >= 0) {
			String[] valores = new String[tabela.getColumnCount()];

			for (int i = 0; i < tabela.getColumnCount(); i++) {
				valores[i] = String.valueOf(modelo.getValueAt(linhaSelecionada, i));
			}
			return valores;
		} else {
			JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return null;
		}
	}

	private void addLabelAndComponent(JPanel panel, String labelText, Component component, int x, int y) {

		int coluna = x;
		int linha = y;

		JLabel label = setConfigLabel(labelText);
		gbc.anchor = GridBagConstraints.EAST;

		gbc.gridx = coluna;
		gbc.gridy = linha;
		gbc.insets = defaultInsets;
		panel.add(label, gbc);

		gbc.gridx = coluna + 1;
		gbc.anchor = GridBagConstraints.WEST;

		panel.add(component, gbc);

	}

	private JLabel setConfigLabel(String nomeLabel) {
		gbc.insets = defaultInsets;
		JLabel label = new JLabel(nomeLabel);
		label.setFont(new Font("Arial", Font.PLAIN, 20));
		label.setForeground(Color.GREEN);
		label.setBackground(Color.BLACK);
		return label;
	}

	private JTextField setConfigTxT(int columns) {
		gbc.insets = defaultInsets;
		JTextField txt = new JTextField(columns);
		// txt.setBorder(new RoundedBorder(Color.gray, 5));
		txt.setBorder(new LineBorder(Color.black, 8));
		txt.setHorizontalAlignment(JTextField.CENTER);
		txt.setFont(new Font("Arial", Font.PLAIN, 20));
		txt.setPreferredSize(new Dimension(30, 40));
		return txt;
	}

	private JButton setConfigBotao(String nomeBotao, ActionListener actionListener) {
		JButton botao = new JButton(nomeBotao);
		botao.setFont(new Font("Arial", Font.PLAIN, 20));
		botao.setPreferredSize(new Dimension(100, 50));
		botao.setBorder(new RoundedBorder(Color.green, 5));
		botao.setFocusable(false);
		botao.setBackground(Color.black);
		botao.setForeground(Color.GREEN);
		botao.addActionListener(actionListener);
		return botao;
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			TipoInterface<?> tela = new TipoInterface<>();
			tela.setVisible(true);
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}