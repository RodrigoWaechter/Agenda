package com.projeto.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

import com.projeto.agenda.database.usuario.entity.Usuario;
import com.projeto.agenda.database.usuario.services.UsuarioDatabase;
import com.projeto.view.custom.RoundedBorder;
import com.projeto.view.model.TipoTableModel;
import com.projeto.view.model.UsuarioTableModel;

public class UsuarioInterface<Entidade> extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtNome, txtCPF, txtData, txtAltura, txtPeso, txtSenha, txtEndereco, txtEmail, txtTelefone;
	private JComboBox<String> boxAdmin;
	private JTable tabela;
	private UsuarioTableModel modelo;
	private GridBagConstraints gbc;
	private Insets defaultInsets = new Insets(10, 10, 10, 10);
	private int colunaCounter = 0;
	private int linhaCounter = 0;
	private DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	private UsuarioDatabase usuarioDatabase;
	private int linhaEditar = -1;

	public UsuarioInterface() {
		setTitle("Cadastro de usuário");
		setSize(1200, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(true);

		JPanel contentPane = new JPanel(new BorderLayout());
		setContentPane(contentPane);

		JPanel panelTopo = new JPanel(new GridBagLayout());
		panelTopo.setBackground(Color.BLACK);
		gbc = new GridBagConstraints();

		addLabelEtxt(panelTopo, "Nome do cliente:", txtNome = createTextField("Teste"));
		addLabelEtxt(panelTopo, "Altura(opcional):", txtAltura = createTextField("1.90"));
		addLabelEtxt(panelTopo, "CPF do cliente:", txtCPF = createTextField("43212345678"));
		addLabelEtxt(panelTopo, "Peso(opcional):", txtPeso = createTextField("80.0"));
		addLabelEtxt(panelTopo, "Data de nascimento:", txtData = createTextField("10/10/2010"));
		addLabelEtxt(panelTopo, "Email(opcional):", txtEmail = createTextField("teste@gmail.com"));
		addLabelEtxt(panelTopo, "Senha:", txtSenha = createTextField("12345"));
		addLabelEtxt(panelTopo, "Endereço(opcional):", txtEndereco = createTextField("teste teste"));
		addLabelEtxt(panelTopo, "Telefone:", txtTelefone = createTextField("51999543602"));

		JLabel labelAdmin = setConfigLabel("Admin:");
		gbc.gridx = 2;
		gbc.gridy = 4;
		panelTopo.add(labelAdmin, gbc);

		String[] admin = { "False", "True" };
		boxAdmin = new JComboBox<>(admin);
		gbc.gridx = 3;
		gbc.gridy = 4;
		gbc.gridwidth = 4;
		boxAdmin.setPreferredSize(new Dimension(225, 25));
		boxAdmin.setBorder(new LineBorder(Color.BLACK, 2));
		boxAdmin.setRenderer(new DefaultListCellRenderer.UIResource());
		panelTopo.add(boxAdmin, gbc);

		contentPane.add(panelTopo, BorderLayout.NORTH);

		tabela = new JTable();
		tabela.setFont(new Font("Arial", Font.PLAIN, 18));
		tabela.setPreferredSize(new Dimension(300, 200));
		tabela.setBackground(Color.white);
		tabela.setFocusable(false);
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabela.setRowHeight(25);
		tabela.setColumnSelectionAllowed(false);
		tabela.setDefaultEditor(Object.class, null);
		modelo = new UsuarioTableModel();
		tabela.setModel(modelo);

		usuarioDatabase = new UsuarioDatabase();
		List<Usuario> usuariosTeste = usuarioDatabase.getUsuarios();
		for (Usuario usuario : usuariosTeste) {
			modelo.addRow(usuario);
		}
		for (int i = 0; i < tabela.getColumnCount(); i++) {
			int maxWidth = 0;
			for (int linha = 0; linha < tabela.getRowCount(); linha++) {
				maxWidth = Math.max(maxWidth, tabela.getCellRenderer(linha, i)
						.getTableCellRendererComponent(tabela, tabela.getValueAt(linha, i), false, false, linha, i)
						.getPreferredSize().width);
			}
			tabela.getColumnModel().getColumn(i).setPreferredWidth(maxWidth);
			centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
			tabela.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		JScrollPane scrollPane = new JScrollPane(tabela);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		JPanel panelBaixo = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
		panelBaixo.setBackground(Color.BLACK);

		JButton botaoSave = setConfigBotao("Save", e -> botaoSalvarClicado());
		panelBaixo.add(botaoSave);

		JButton botaoEditar = setConfigBotao("Editar", e -> botaoEditarClicado());
		panelBaixo.add(botaoEditar);

		JButton botaoDeletar = setConfigBotao("Deletar", e -> botaoDeletarClicado());
		panelBaixo.add(botaoDeletar);

		JButton botaoCancel = setConfigBotao("Cancel", e -> dispose());
		panelBaixo.add(botaoCancel);

		contentPane.add(panelBaixo, BorderLayout.SOUTH);
	}

	private void botaoSalvarClicado() {
		try {
			if (linhaEditar >= 0) {
				modelo.setValueAt(txtNome.getText(), linhaEditar, 0);
				modelo.setValueAt(txtCPF.getText(), linhaEditar, 1);
				modelo.setValueAt(txtData.getText(), linhaEditar, 2);
				modelo.setValueAt(txtEmail.getText(), linhaEditar, 3);
				modelo.setValueAt(txtTelefone.getText(), linhaEditar, 4);
				modelo.setValueAt(txtAltura.getText(), linhaEditar, 5);
				modelo.setValueAt(txtPeso.getText(), linhaEditar, 6);
				modelo.setValueAt(txtEndereco.getText(), linhaEditar, 7);
				modelo.setValueAt(txtSenha.getText(), linhaEditar, 8);
				linhaEditar = -1;
			} else {
				try {
					Usuario usuario = new Usuario();
					usuario.setNome(txtNome.getText());
					usuario.setCpf(txtCPF.getText());
					usuario.setDataNascFromString(txtData.getText());
					usuario.setEmail(txtEmail.getText());
					usuario.setTelefone(txtTelefone.getText());
					usuario.setAltura(Float.parseFloat(txtAltura.getText()));
					usuario.setPeso(Float.parseFloat(txtPeso.getText()));
					usuario.setEndereco(txtEndereco.getText());
					usuario.setSenha(txtSenha.getText());
					usuario.setAdmin(Boolean.parseBoolean(boxAdmin.getSelectedItem().toString()));
					modelo.addRow(usuario);
				} catch (ParseException e) {
					JOptionPane.showMessageDialog(null, "Data inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "O valor informado não é válido.", "Erro", JOptionPane.ERROR_MESSAGE);
		}
		clearForm();
	}

	private void botaoEditarClicado() {
		int linhaSelecionada = tabela.getSelectedRow();

		if (linhaSelecionada >= 0) {
			String[] valoresLinhaSelecionada = obterValoresLinhaSelecionada();

			Usuario usuario = new Usuario();
			usuario.setNome(valoresLinhaSelecionada[0]);
			usuario.setCpf(valoresLinhaSelecionada[1]);
			usuario.setEmail(valoresLinhaSelecionada[3]);
			usuario.setTelefone(valoresLinhaSelecionada[4]);
			usuario.setAltura(Float.parseFloat(valoresLinhaSelecionada[5]));
			usuario.setPeso(Float.parseFloat(valoresLinhaSelecionada[6]));
			usuario.setEndereco(valoresLinhaSelecionada[7]);
			usuario.setSenha(valoresLinhaSelecionada[8]);
			usuario.setAdmin(Boolean.parseBoolean(valoresLinhaSelecionada[9]));
			try {
				usuario.setDataNascFromString(valoresLinhaSelecionada[2]);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			txtNome.setText(valoresLinhaSelecionada[0]);
			txtCPF.setText(valoresLinhaSelecionada[1]);
			txtData.setText(valoresLinhaSelecionada[2]);
			txtEmail.setText(valoresLinhaSelecionada[3]);
			txtTelefone.setText(valoresLinhaSelecionada[4]);
			txtAltura.setText(valoresLinhaSelecionada[5]);
			txtPeso.setText(valoresLinhaSelecionada[6]);
			txtEndereco.setText(valoresLinhaSelecionada[7]);
			txtSenha.setText(valoresLinhaSelecionada[8]);

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

	private void addLabelEtxt(JPanel panel, String labelText, JTextField textField) {
		gbc.insets = defaultInsets;
		int coluna = colunaCounter * 2;
		int linha = linhaCounter;
		JLabel label = setConfigLabel(labelText);
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = coluna;
		gbc.gridy = linha;
		panel.add(label, gbc);
		gbc.gridx = coluna + 1;
		panel.add(textField, gbc);
		colunaCounter++;
		if (colunaCounter == 2) {
			colunaCounter = 0;
			linhaCounter++;
		}
	}

	private void clearForm() {
		txtNome.setText("");
		txtCPF.setText("");
		txtData.setText("");
		txtEmail.setText("");
		txtTelefone.setText("");
		txtAltura.setText("");
		txtPeso.setText("");
		txtEndereco.setText("");
		txtSenha.setText("");
		boxAdmin.setSelectedIndex(0);
	}

	public void atualizarLinhaSelecionada(Usuario usuario) {
		int linhaSelecionada = tabela.getSelectedRow();
		if (linhaSelecionada >= 0) {
			modelo.setValueAt(usuario.getNome(), linhaSelecionada, 0);
			modelo.setValueAt(usuario.getCpf(), linhaSelecionada, 1);
			modelo.setValueAt(usuario.getDataNascToString(), linhaSelecionada, 2);
			modelo.setValueAt(usuario.getEmail(), linhaSelecionada, 3);
			modelo.setValueAt(usuario.getTelefone(), linhaSelecionada, 4);
			modelo.setValueAt(usuario.getAltura(), linhaSelecionada, 5);
			modelo.setValueAt(usuario.getPeso(), linhaSelecionada, 6);
			modelo.setValueAt(usuario.getEndereco(), linhaSelecionada, 7);
			modelo.setValueAt(usuario.getSenha(), linhaSelecionada, 8);
			modelo.setValueAt(usuario.isAdmin(), linhaSelecionada, 9);
		}
	}

	public String[] obterValoresLinhaSelecionada() {
		int linhaSelecionada = tabela.getSelectedRow();

		if (linhaSelecionada >= 0) {
			String[] valores = new String[tabela.getColumnCount()];

			for (int i = 0; i < tabela.getColumnCount(); i++) {
				valores[i] = modelo.getValueAt(linhaSelecionada, i).toString();
			}
			return valores;
		}
		return null;
	}

	private JLabel setConfigLabel(String nomeLabel) {
		gbc.insets = defaultInsets;
		JLabel label = new JLabel(nomeLabel);
		label.setFont(new Font("Arial", Font.PLAIN, 19));
		label.setForeground(Color.green);
		label.setBackground(Color.BLACK);
		return label;
	}

	private JButton setConfigBotao(String nomeBotao, ActionListener actionListener) {
		gbc.insets = defaultInsets;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JButton botao = new JButton(nomeBotao);
		botao.setFont(new Font("Arial", Font.PLAIN, 16));
		botao.setPreferredSize(new Dimension(130, 40));
		botao.setFocusable(false);
		botao.setBorder(new RoundedBorder(Color.GREEN, 6));
		botao.setBackground(Color.BLACK);
		botao.setForeground(Color.GREEN);
		botao.addActionListener(actionListener);
		return botao;
	}

	private JTextField setConfigTxT(int columns) {
		gbc.insets = defaultInsets;
		JTextField txt = new JTextField(columns);
		txt.setBorder(new LineBorder(Color.BLACK, 1));
		txt.setForeground(Color.BLACK);
		txt.setHorizontalAlignment(JTextField.CENTER);
		txt.setFont(new Font("Arial", Font.PLAIN, 18));
		return txt;
	}

	private JTextField createTextField(String initialText) {
		JTextField textField = setConfigTxT(13);
		textField.setText(initialText);
		return textField;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			UsuarioInterface u = new UsuarioInterface();
			u.setVisible(true);
		});
	}
}
