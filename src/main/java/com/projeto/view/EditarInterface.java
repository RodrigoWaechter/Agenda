package com.projeto.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.projeto.agenda.database.EntidadeEditavel;
import com.projeto.agenda.database.tipo.entity.Tipo;
import com.projeto.agenda.database.usuario.entity.Usuario;
import com.projeto.view.custom.RoundedBorder;
//asdwasd
//dawdwadaw
//asdwa
//dwads/
//dwaw
public class EditarInterface<Entidade extends EntidadeEditavel> extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private GridBagConstraints gbc;
	private Insets defaultInsets = new Insets(10, 10, 10, 10);
	private JTextField txtDesc, txtValor, txtNome, txtAltura, txtCPF, txtPeso, txtData, txtEmail, txtSenha, txtEndereco,
			txtTelefone;
	private JComboBox<String> boxAdmin;
	private TipoInterface<?> tipoInterface;
	private UsuarioInterface<?> usuarioInterface;
	private Entidade entidade;

	public EditarInterface(Entidade entidade) throws ParseException {
		this.entidade = entidade;
		setTitle("Editar");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(true);

		contentPane = new JPanel(new GridBagLayout());
		contentPane.setBackground(Color.white);
		setContentPane(contentPane);

		gbc = new GridBagConstraints();
		try {
			editar(this.entidade.getValores());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		JButton botaoSave = setConfigBotao("Save", e -> botaoSaveClicado(), 20);

		contentPane.add(botaoSave, gbc);

		JButton botaoCancel = setConfigBotao("Cancel", e -> {
			dispose();
		}, 21);

		gbc.insets = new Insets(0, 10, 10, 10);
		contentPane.add(botaoCancel, gbc);
		pack();

	}

	private JTextField createTextField(String initialText) {
		JTextField textField = setConfigTxT(13);
		textField.setText(initialText);
		return textField;
	}

	public void editar(String[] conteudoLinha) throws ParseException {
		if (entidade instanceof Tipo) {
			addLabelAndComponent(contentPane, "Desc:", txtDesc = createTextField(conteudoLinha[0]), 2, 0);
			addLabelAndComponent(contentPane, "Valor: ", txtValor = createTextField(conteudoLinha[1]), 2, 2);
		} else if (entidade instanceof Usuario) {
			addLabelAndComponent(contentPane, "Nome do cliente:", txtNome = createTextField(conteudoLinha[0]), 2, 0);
			addLabelAndComponent(contentPane, "CPF do cliente:", txtCPF = createTextField(conteudoLinha[1]), 2, 2);
			addLabelAndComponent(contentPane, "Data de nascimento:", txtData = createTextField(conteudoLinha[2]), 2, 4);
			addLabelAndComponent(contentPane, "Email(opcional):", txtEmail = createTextField(conteudoLinha[3]), 2, 6);
			addLabelAndComponent(contentPane, "Telefone:", txtTelefone = createTextField(conteudoLinha[4]), 2, 8);
			addLabelAndComponent(contentPane, "Altura(opcional):", txtAltura = createTextField(conteudoLinha[5]), 2,10);
			addLabelAndComponent(contentPane, "Peso(opcional):", txtPeso = createTextField(conteudoLinha[6]), 2, 12);
			addLabelAndComponent(contentPane, "Endereço(opcional):", txtEndereco = createTextField(conteudoLinha[7]), 2,14);
			addLabelAndComponent(contentPane, "Senha:", txtSenha = createTextField(conteudoLinha[8]), 2, 16);

			JLabel labelAdmin = setConfigLabel("Admin:");
			gbc.gridx = 2;
			gbc.gridy = 18;
			gbc.anchor = GridBagConstraints.EAST;
			contentPane.add(labelAdmin, gbc);

			String[] admin = { "False", "True" };
			boxAdmin = new JComboBox<>(admin);
			gbc.gridx = 3;
			gbc.gridy = 18;
			gbc.gridwidth = 2;
			boxAdmin.setPreferredSize(new Dimension(200, 25));
			// boxAdmin.setBorder(new LineBorder(Color.BLACK, 2));
			contentPane.add(boxAdmin, gbc);
		} else {
			JOptionPane.showMessageDialog(null, "TipoInterface Nula 1.", "Aviso", JOptionPane.WARNING_MESSAGE);
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

	private void botaoSaveClicado() {
		if (entidade instanceof Tipo) {
			Tipo tipo = new Tipo();
			tipo.setDesc_tipo(txtDesc.getText());
			tipo.setValor_tipo(Float.parseFloat(txtValor.getText()));

			this.tipoInterface.atualizarLinhaSelecionada(tipo);

		} else if (entidade instanceof Usuario) {
			Usuario usuario = new Usuario();
			usuario.setNome(txtNome.getText());
			usuario.setCpf(txtCPF.getText());
			try {
				usuario.setDataNascFromString(txtData.getText());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			usuario.setEmail(txtEmail.getText());
			usuario.setTelefone(txtTelefone.getText());
			usuario.setAltura(Float.parseFloat(txtAltura.getText()));
			usuario.setPeso(Float.parseFloat(txtPeso.getText()));
			usuario.setEndereco(txtEndereco.getText());
			usuario.setSenha(txtSenha.getText());
			usuario.setAdmin(Boolean.parseBoolean(boxAdmin.getSelectedItem().toString()));

			this.usuarioInterface.atualizarLinhaSelecionada(usuario);
		} else {
			JOptionPane.showMessageDialog(null, "TipoInterface Nula 2.", "Aviso", JOptionPane.WARNING_MESSAGE);
		}
		dispose();
	}

	private JTextField setConfigTxT(int columns) {
		gbc.gridy = 0;
		gbc.insets = defaultInsets;

		JTextField txt = new JTextField(columns);
		txt.setBorder(new LineBorder(Color.black, 1));
		// txt.setBorder(new RoundedBorder(Color.gray, 5));
		txt.setHorizontalAlignment(JTextField.CENTER);
		txt.setFont(new Font("Arial", Font.PLAIN, 18));
		return txt;
	}

	private JLabel setConfigLabel(String nomeLabel) {
		gbc.gridx = 0;
		gbc.insets = defaultInsets;
		gbc.anchor = GridBagConstraints.WEST;

		JLabel label = new JLabel(nomeLabel);
		label.setFont(new Font("Arial", Font.PLAIN, 18));
		label.setForeground(Color.black);
		return label;
	}

	private JButton setConfigBotao(String nomeBotao, ActionListener actionListener, int gridy) {
		gbc.gridx = 0;
		gbc.gridy = gridy;
		gbc.gridwidth = 5;
		gbc.insets = defaultInsets;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JButton botao = new JButton(nomeBotao);
		botao.setFont(new Font("Arial", Font.PLAIN, 15));
		botao.setPreferredSize(new Dimension(25, 25));
		botao.setFocusable(false);
		botao.setBorder(new RoundedBorder(Color.black, 5));
		botao.setBackground(Color.white);
		botao.setForeground(Color.black);
		botao.addActionListener(actionListener);
		return botao;
	}

	public void setTipoInterface(TipoInterface<?> tipoInterface) {
		this.tipoInterface = tipoInterface;
	}

	public void setUsuarioInterface(UsuarioInterface<?> usuarioInterface) {
		this.usuarioInterface = usuarioInterface;
	}
}