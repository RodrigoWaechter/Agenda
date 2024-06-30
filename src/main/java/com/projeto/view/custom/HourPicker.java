package com.projeto.view.custom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.projeto.view.custom.RoundedBorder;

public class HourPicker extends JPanel {
	private static final long serialVersionUID = 1L;

	private JTextField campoTexto = new JTextField();
	private Calendar calendario = Calendar.getInstance();
	private Calendar calendarioInicial = Calendar.getInstance();

	public HourPicker() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(100, 10));
		setBackground(Color.white);

		JPanel contentPane = new JPanel(new BorderLayout());
		setOpaque(false);
		add(contentPane);

		JPanel panelTopo = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		panelTopo.setBackground(Color.white);

		JButton aumentarHora10 = setConfigBotao("+ H");
		addFuncionalidadeBotoes(aumentarHora10, true, true, 10);
		panelTopo.add(aumentarHora10);

		JButton aumentarHora1 = setConfigBotao("+ H");
		addFuncionalidadeBotoes(aumentarHora1, true, true, 1);
		panelTopo.add(aumentarHora1);

		JButton aumentarMin10 = setConfigBotao("+ M");
		addFuncionalidadeBotoes(aumentarMin10, true, false, 10);
		panelTopo.add(aumentarMin10);

		JButton aumentarMin1 = setConfigBotao("+ M");
		addFuncionalidadeBotoes(aumentarMin1, true, false, 1);
		panelTopo.add(aumentarMin1);

		JButton botaoSalvar = setConfigBotao("Save");
		botaoSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		panelTopo.add(botaoSalvar);
		contentPane.add(panelTopo, BorderLayout.NORTH);

		//campoTexto.setBorder(new LineBorder(Color.GRAY));
		campoTexto.setBorder(null);
		campoTexto.setPreferredSize(new Dimension(100, 30));
		campoTexto.setHorizontalAlignment(JTextField.CENTER);
		campoTexto.setFont(new Font("Arial", Font.PLAIN, 18));
		contentPane.add(campoTexto, BorderLayout.CENTER);

		JPanel panelBaixo = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		panelBaixo.setBackground(Color.white);

		JButton diminuirHora10 = setConfigBotao("- H");
		addFuncionalidadeBotoes(diminuirHora10, false, true, 10);
		panelBaixo.add(diminuirHora10);

		JButton diminuirHora1 = setConfigBotao("- H");
		addFuncionalidadeBotoes(diminuirHora1, false, true, 1);
		panelBaixo.add(diminuirHora1);

		JButton diminuirMin10 = setConfigBotao("- M");
		addFuncionalidadeBotoes(diminuirMin10, false, false, 10);
		panelBaixo.add(diminuirMin10);

		JButton diminuirMin1 = setConfigBotao("- M");
		addFuncionalidadeBotoes(diminuirMin1, false, false, 1);
		panelBaixo.add(diminuirMin1);

		JButton botaoReset = setConfigBotao("Reset");
		botaoReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calendario.setTime(calendarioInicial.getTime());
				atualizarCampoTexto();
			}
		});
		panelBaixo.add(botaoReset);

		contentPane.add(panelBaixo, BorderLayout.SOUTH);

		calendario.set(Calendar.HOUR_OF_DAY, 0);
		calendario.set(Calendar.MINUTE, 0);
		calendarioInicial.setTime(calendario.getTime());
		atualizarCampoTexto();
	}

	public void atualizarCampoTexto() {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		Date horaAtual = calendario.getTime();
		campoTexto.setText(format.format(horaAtual));
		getCampoTexto();

	}

	public String getCampoTexto() {
		return campoTexto.getText();
	}

	private void addFuncionalidadeBotoes(JButton botao, boolean aumentar, boolean isHora, int valor) {
		botao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// aumentar
				if (aumentar) {
					if (isHora) {
						calendario.add(Calendar.HOUR_OF_DAY, valor);
					} else {
						calendario.add(Calendar.MINUTE, valor);
					}
					// diminuir
				} else {
					if (isHora) {
						calendario.add(Calendar.HOUR_OF_DAY, -valor);
					} else {
						calendario.add(Calendar.MINUTE, -valor);
					}
				}
				atualizarCampoTexto();
			}
		});
	}

	public Calendar getSelectedTime() {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		String selectedTime = campoTexto.getText();

		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(format.parse(selectedTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return calendar;
	}

	public void setSelectedTime(String tempo) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");

		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(format.parse(tempo));
		} catch (ParseException e) {
			e.printStackTrace();
		}		
	}

	private JButton setConfigBotao(String nomeBotao) {
		JButton botao = new JButton(nomeBotao);
		botao.setPreferredSize(new Dimension(40, 20));
		botao.setFont(new Font("Arial", Font.PLAIN, 12));
		botao.setBorder(new RoundedBorder(Color.black, 5));
		//botao.setBorder(new LineBorder(Color.lightGray));
		botao.setForeground(Color.black);
		botao.setBackground(Color.white);
		botao.setFocusable(false);
		return botao;
	}
	/*
	 * public static void main(String[] args) { SwingUtilities.invokeLater(() -> {
	 * JFrame frame = new JFrame("Hour Picker");
	 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); frame.setSize(300,
	 * 200); frame.add(new HourPicker()); frame.setVisible(true); }); }
	 */
}
