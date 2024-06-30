package com.projeto.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
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

import org.springframework.context.ConfigurableApplicationContext;

import com.projeto.agenda.database.atendimento.entity.Atendimento;
import com.projeto.agenda.database.atendimento.services.AtendimentoDatabase;
import com.projeto.agenda.database.tipo.entity.Tipo;
import com.projeto.agenda.database.usuario.entity.Usuario;
import com.projeto.view.custom.RoundedBorder;
import com.projeto.view.model.AtendimentoTableModel;

public class MainInterface extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private AtendimentoTableModel modelo;
	private JTable tabela;
	private JTextField txtDia;
	private GridBagConstraints gbc;
	private Insets defaultInsets = new Insets(10, 10, 10, 10);
	private DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	private AtendimentoDatabase atendimentoDatabase;

	public MainInterface(ConfigurableApplicationContext context) {
		setTitle("Tela Teste");
		setVisible(true);
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.black);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.setBackground(Color.black);
		setContentPane(contentPane);
		gbc = new GridBagConstraints();

		JPanel panelTopo = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		panelTopo.setBackground(Color.black);

		addLabelEtxt(panelTopo, "Agenda - Próximos ", txtDia = setConfigTxT(2));
		addLabelEtxt(panelTopo, "dias", null);

		JButton botaoBuscar = setConfigBotao("Buscar", e -> BotaoBuscarClicado());
		panelTopo.add(botaoBuscar);

		contentPane.add(panelTopo, BorderLayout.NORTH);

		JPanel panelBaixo = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
		panelBaixo.setBackground(Color.black);

		JButton botaoNovo = setConfigBotao("Novo", e -> BotaoNovoClicado());
		panelBaixo.add(botaoNovo);

		JButton botaoEditar = setConfigBotao("Editar", e -> BotaoEditarClicado());
		panelBaixo.add(botaoEditar);

		JButton botaoDeletar = setConfigBotao("Deletar", e -> BotaoDeletarClicado());
		panelBaixo.add(botaoDeletar);

		JButton botaoUsuario = setConfigBotao("Usuario", e -> BotaoUsuarioClicado());
		panelBaixo.add(botaoUsuario);

		JButton botaoTipo = setConfigBotao("Tipo", e -> BotaoTipoClicado());
		panelBaixo.add(botaoTipo);

		contentPane.add(panelBaixo, BorderLayout.SOUTH);

		tabela = new JTable();
		tabela.setFont(new Font("Arial", Font.PLAIN, 15));
		tabela.setBackground(Color.white);
		tabela.setRowHeight(35);
		tabela.setDefaultEditor(Object.class, null);
		tabela.setColumnSelectionAllowed(false);
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabela.setFocusable(false);
		modelo = new AtendimentoTableModel();

		tabela.setModel(modelo);
		atendimentoDatabase = new AtendimentoDatabase();
		List<Atendimento> tiposFicticios = atendimentoDatabase.getAtendimentos();
		for (Atendimento atendimento : tiposFicticios) {
			modelo.addRow(atendimento);
		}

		for (int i = 0; i < tabela.getColumnCount(); i++) {
			centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
			tabela.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		contentPane.add(new JScrollPane(tabela), BorderLayout.CENTER);

		pack();
	}

	private int getSelectedRowIndex() {
		return tabela.getSelectedRow();
	}

	private void BotaoBuscarClicado() {
//		modelo.addRow(new Object[] { "Josemar", "24/08/2023", "10:00", "11:00", "Limpeza" });
	}

	private void BotaoNovoClicado() {
		AtendimentoInterface novo = new AtendimentoInterface(this, true, null);
		novo.setVisible(true);
	}

	private void BotaoEditarClicado() {
		int linhaSelecionada = getSelectedRowIndex();
		if (linhaSelecionada >= 0) {
			AtendimentoInterface editar = new AtendimentoInterface(this, false, obterValoresLinhaSelecionada());
			editar.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "Selecione uma linha para editar.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void BotaoDeletarClicado() {
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

	private void BotaoUsuarioClicado() {
		UsuarioInterface<?> u = new UsuarioInterface<>();
		u.setVisible(true);
	}

	private void BotaoTipoClicado() {
		TipoInterface<?> t = new TipoInterface<>();
		t.setVisible(true);
	}

	private void addLabelEtxt(JPanel panel, String labelText, JTextField textField) {
		gbc.insets = defaultInsets;

		JLabel label = setConfigLabel(labelText);
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(label, gbc);

		if (textField != null) {
			gbc.gridx = 1;
			panel.add(textField, gbc);
		}
	}

	public void addAgendamento(Atendimento atendimento) {
		modelo.addRow(atendimento);
	}

	public void atualizarLinhaSelecionada(Usuario usuario, Date novaData, Calendar horaInicio, Calendar horaFim,
			Tipo tipo) {
		int linhaSelecionada = tabela.getSelectedRow();
		if (linhaSelecionada >= 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");

			modelo.setValueAt(usuario.getNome(), linhaSelecionada, 0);
			modelo.setValueAt(sdf.format(novaData), linhaSelecionada, 1);
			modelo.setValueAt(sdfHora.format(horaInicio.getTime()), linhaSelecionada, 2);
			modelo.setValueAt(sdfHora.format(horaFim.getTime()), linhaSelecionada, 3);
			modelo.setValueAt(tipo.getDesc_tipo(), linhaSelecionada, 4);
		}
	}

	public void atualizarLinhaSelecionada2(Atendimento atendimento) {
		int linhaSelecionada = tabela.getSelectedRow();
		if (linhaSelecionada >= 0) {

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");

			modelo.setValueAt(atendimento.getUsuario().getNome(), linhaSelecionada, 0);
			modelo.setValueAt(atendimento.getHorario_inicio().toLocalDate().format(formatter), linhaSelecionada, 1);
			modelo.setValueAt(atendimento.getHorario_inicio().toLocalTime().format(formatter2), linhaSelecionada, 2);
			modelo.setValueAt(atendimento.getHorario_fim().toLocalTime().format(formatter2), linhaSelecionada, 3);
			modelo.setValueAt(atendimento.getTipo().getDesc_tipo(), linhaSelecionada, 4);

		}
	}

	public String[] obterValoresLinhaSelecionada() {
		int linhaSelecionada = tabela.getSelectedRow();

		if (linhaSelecionada >= 0) {
			String[] valores = new String[tabela.getColumnCount()];

			for (int i = 0; i < tabela.getColumnCount(); i++) {
				valores[i] = (String) modelo.getValueAt(linhaSelecionada, i);
			}
			return valores;
		}
		return null;
	}

	private JLabel setConfigLabel(String nomeLabel) {
		gbc.insets = defaultInsets;
		JLabel label = new JLabel(nomeLabel);
		label.setFont(new Font("Arial", Font.PLAIN, 40));
		label.setForeground(Color.GREEN);
		label.setBackground(Color.BLACK);
		return label;
	}

	private JTextField setConfigTxT(int columns) {
		gbc.insets = defaultInsets;
		JTextField txt = new JTextField(columns);
		// txt.setBorder(new RoundedBorder(Color.gray, 5));
		txt.setBorder(new LineBorder(Color.black, 3));
		txt.setHorizontalAlignment(JTextField.CENTER);
		txt.setFont(new Font("Arial", Font.PLAIN, 35));
		txt.setPreferredSize(new Dimension(30, 40));
		return txt;
	}

	private JButton setConfigBotao(String nomeBotao, ActionListener actionListener) {
		JButton botao = new JButton(nomeBotao);
		botao.setFont(new Font("Arial", Font.PLAIN, 20));
		botao.setPreferredSize(new Dimension(120, 50));
		botao.setBorder(new RoundedBorder(Color.green, 5));
		botao.setFocusable(false);
		botao.setBackground(Color.black);
		botao.setForeground(Color.green);
		botao.addActionListener(actionListener);
		return botao;
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			MainInterface tela = new MainInterface(null);
			tela.setVisible(true);
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}
}