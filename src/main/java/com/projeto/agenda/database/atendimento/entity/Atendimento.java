package com.projeto.agenda.database.atendimento.entity;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.projeto.agenda.database.tipo.entity.Tipo;
import com.projeto.agenda.database.usuario.entity.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Atendimento {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id_atendimento;

	@ManyToOne()
	@JoinColumn(nullable = false)
	private Tipo tipo;

	@ManyToOne()
	@JoinColumn(nullable = false)
	private Usuario usuario;

	@Column(nullable = false)
	private LocalDateTime horario_inicio;

	@Column(nullable = false)
	private LocalDateTime horario_fim;

	public Atendimento() {
	}

	public Atendimento(Tipo tipo, Usuario usuario, LocalDateTime horario_inicio, LocalDateTime horario_fim) {
		this.tipo = tipo;
		this.usuario = usuario;
		this.horario_inicio = horario_inicio;
		this.horario_fim = horario_fim;
	}

	public Integer getId_atendimento() {
		return id_atendimento;
	}

	public void setId_atendimento(Integer id_atendimento) {
		this.id_atendimento = id_atendimento;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public LocalDateTime getHorario_inicio() {
		return horario_inicio;
	}

	public void setHorario_inicio(LocalDateTime horario_inicio) {
		this.horario_inicio = horario_inicio;
	}

	public LocalDateTime getHorario_fim() {
		return horario_fim;
	}

	public void setHorario_fim(LocalDateTime horario_fim) {
		this.horario_fim = horario_fim;
	}

	public String getHoraInicioToBD() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return formatter.format(horario_inicio);
	}

	public void setHoraInicioFromBD(String horaInicio) throws ParseException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		this.horario_inicio = LocalDateTime.parse(horaInicio, formatter);
	}

	public String getHoraFimToBD() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return formatter.format(horario_fim);
	}

	public void setHoraFimFromBD(String horaFim) throws ParseException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		this.horario_fim = LocalDateTime.parse(horaFim, formatter);
	}

	public void setHoraInicioFromString(String horaInicio) throws ParseException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		this.horario_inicio = LocalDateTime.parse(horaInicio, formatter);

	}

	public void setHoraFimFromString(String horaFim) throws ParseException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		this.horario_fim = LocalDateTime.parse(horaFim, formatter);
	}

	public String getDate() {
		LocalDate data = horario_inicio.toLocalDate();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return formatter.format(data);
	}

	public void setDate(Date data) {
		LocalDate localDate = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		horario_inicio = horario_inicio.with(localDate);
	}
}
