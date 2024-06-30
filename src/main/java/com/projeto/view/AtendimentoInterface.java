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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import com.projeto.agenda.database.atendimento.entity.Atendimento;
import com.projeto.agenda.database.tipo.entity.Tipo;
import com.projeto.agenda.database.usuario.entity.Usuario;
import com.projeto.view.custom.DateConverter;
import com.projeto.view.custom.HourPicker;
import com.projeto.view.custom.RoundedBorder;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

public class AtendimentoInterface extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private HourPicker hrInicio;
	private HourPicker hrFim;
	private GridBagConstraints gbc;
	private Insets defaultInsets = new Insets(10, 10, 10, 10);
	private MainInterface mainInterface;
	private JTextField txtUsuarioNome, txtUsuarioID;
	private JComboBox<String> boxTipo;
	private JDateChooser dateChooser;
	private JTextFieldDateEditor editor;
	private String[] tipo = { "Tipo 1", "Tipo 2", "Tipo 3", "Tipo 4", "Tipo 5" };
	private String[] conteudoLinha;
	private boolean novo;

	public AtendimentoInterface(MainInterface mainInterface, boolean novo, String[] conteudoLinha) {
		this.mainInterface = mainInterface;
		this.novo = novo;
		this.conteudoLinha = conteudoLinha;
		setTitle("Novo atendimento");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		contentPane = new JPanel(new GridBagLayout());
		contentPane.setBackground(Color.white);
		setContentPane(contentPane);

		gbc = new GridBagConstraints();
		if (novo == true) {
			novo();
		} else {
			editar();

		}
		JButton botaoSave = setConfigBotao("Save", e -> botaoSaveClicado(), 5);

		contentPane.add(botaoSave, gbc);

		JButton botaoCancel = setConfigBotao("Cancel", e -> {
			dispose();
		}, 6);

		gbc.insets = new Insets(0, 10, 10, 10);
		contentPane.add(botaoCancel, gbc);
		pack();
	}

	private void novo() {
		addLabelAndComponent(contentPane, "Usuário:", txtUsuarioID = setConfigTxT(2), 0, 0);
		addLabelAndComponent(contentPane, "", txtUsuarioNome = setConfigTxT(14), 1, 0);
		addLabelAndComponent(contentPane, "Data:", dateChooser = setConfigData(null), 0, 1);
		addLabelAndComponent(contentPane, "Início:", hrInicio = setConfigHourPicker(2, null), 0, 2);
		addLabelAndComponent(contentPane, "Fim:", hrFim = setConfigHourPicker(3, null), 0, 3);
		addLabelAndComponent(contentPane, "Tipo:", boxTipo = setConfigBox(tipo, null), 0, 4);
	}

	public void editar() {
		if (mainInterface != null) {
			String[] valoresLinhaSelecionada = mainInterface.obterValoresLinhaSelecionada();
			if (valoresLinhaSelecionada != null) {
				addLabelAndComponent(contentPane, "Usuário:", txtUsuarioID = setConfigTxT(2), 0, 0);
				addLabelAndComponent(contentPane, "", txtUsuarioNome = createTextField(14, this.conteudoLinha[0]), 1,
						0);
				addLabelAndComponent(contentPane, "Data:", dateChooser = setConfigData(this.conteudoLinha[1]), 0, 1);
				addLabelAndComponent(contentPane, "Início:", hrInicio = setConfigHourPicker(2, this.conteudoLinha[2]),
						0, 2);
				addLabelAndComponent(contentPane, "Fim:", hrFim = setConfigHourPicker(3, this.conteudoLinha[3]), 0, 3);
				addLabelAndComponent(contentPane, "Tipo:", boxTipo = setConfigBox(tipo, this.conteudoLinha[4]), 0, 4);

			}

		} else {
			JOptionPane.showMessageDialog(null, "Valores nulos", "Aviso", JOptionPane.WARNING_MESSAGE);
		}
	}

	private JTextField createTextField(int tamanho, String initialText) {
		JTextField textField = setConfigTxT(tamanho);
		textField.setText(initialText);
		return textField;
	}

	private void addLabelAndComponent(JPanel panel, String labelText, Component component, int x, int y) {
		gbc.insets = defaultInsets;

		int coluna = x;
		int linha = y;

		JLabel label = setConfigLabel(labelText);
		gbc.anchor = GridBagConstraints.WEST;

		gbc.gridx = coluna;
		gbc.gridy = linha;
		panel.add(label, gbc);

		gbc.gridx = coluna + 1;
		if (component == txtUsuarioNome) {
			gbc.gridwidth = 1;
		} else {
			gbc.gridwidth = 2;
		}
		panel.add(component, gbc);
	}

	private void botaoSaveClicado() {

		Date data = dateChooser.getDate();
		Calendar horaInicio = hrInicio.getSelectedTime();
		Calendar horaFim = hrFim.getSelectedTime();
		DateConverter converter = new DateConverter();
		Atendimento atendimento = new Atendimento();
		Usuario usuario = new Usuario();
		Tipo tipo = new Tipo();
		usuario.setNome(txtUsuarioNome.getText());
		tipo.setDesc_tipo(boxTipo.getSelectedItem().toString());

		atendimento.setUsuario(usuario);
		atendimento.setHorario_inicio(converter.convertToLocalDateTime(data, horaInicio));
		atendimento.setHorario_fim(converter.convertToLocalDateTime(data, horaFim));
		atendimento.setTipo(tipo);
		if (novo) {

			mainInterface.addAgendamento(atendimento);
		} else {
			mainInterface.atualizarLinhaSelecionada2(atendimento);
		}

		dispose();
	}

	private HourPicker setConfigHourPicker(int gridy, String horaInicial) {
		gbc.gridx = 1;
		gbc.gridy = gridy;
		gbc.insets = defaultInsets;
		HourPicker hp = new HourPicker();
		if (horaInicial != null) {
			hp.setSelectedTime(horaInicial);
			hp.setSelectedTime(horaInicial);

		}
		hp.setPreferredSize(new Dimension(215, 75));
		hp.setBorder(new RoundedBorder(Color.black, 5));
		return hp;
	}

	private JComboBox<String> setConfigBox(String[] tipo, String tipoInicial) {

		boxTipo = new JComboBox<String>(tipo);
		boxTipo.setPreferredSize(new Dimension(215, 25));
		if (tipoInicial != null) {
			String tipoSelecionado = tipoInicial;
			boxTipo.setSelectedItem(tipoSelecionado);
		}
		return boxTipo;
	}

	private JDateChooser setConfigData(String dataInicial) {
		JDateChooser dateChooser = new JDateChooser(new Date(), "dd/MM/yyyy");
		editor = (JTextFieldDateEditor) dateChooser.getDateEditor();
		editor.setHorizontalAlignment(JTextField.CENTER);
		dateChooser.setFont(new Font("Arial", Font.PLAIN, 18));
		dateChooser.setPreferredSize(new Dimension(215, 25));
		dateChooser.setBorder(new LineBorder(Color.black, 1));
		if (dataInicial != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			try {
				Date data = sdf.parse(dataInicial);
				dateChooser.setDate(data);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return dateChooser;
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
		gbc.gridwidth = 3;
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

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			AtendimentoInterface tela = new AtendimentoInterface(null, true, null);
			tela.setVisible(true);
		});
	}
}