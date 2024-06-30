package com.projeto.agenda.database.usuario.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.projeto.agenda.database.EntidadeEditavel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Usuario implements EntidadeEditavel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id_usuario;

	@Column(length = 80, nullable = false)
	private String nome;

	@Column(length = 11, nullable = false, unique = true)
	private String cpf;

	@Column(nullable = false)
	private Date data_nascimento;

	@Column(length = 60, nullable = false)
	private String senha;

	@Column()
	private float altura;

	@Column()
	private float peso;

	@Column(length = 80)
	private String endereco;

	@Column(nullable = false)
	private boolean isAdmin;

	@Column(length = 11)
	private String telefone;

	@Column(length = 40)
	private String email;

	public Usuario() {
	}

	public Usuario(String nome, String cpf, Date data_nascimento, String senha, float altura, float peso,
			String endereco, boolean isAdmin, String telefone, String email) {
		this.nome = nome;
		this.cpf = cpf;
		this.data_nascimento = data_nascimento;
		this.senha = senha;
		this.altura = altura;
		this.peso = peso;
		this.endereco = endereco;
		this.isAdmin = isAdmin;
		this.telefone = telefone;
		this.email = email;
	}

	public Usuario(Integer id_usuario, String nome, String cpf, Date data_nascimento, String email, String telefone,
			float altura, float peso, String endereco, String senha, boolean isAdmin) {
		super();
		this.id_usuario = id_usuario;
		this.nome = nome;
		this.cpf = cpf;
		this.data_nascimento = data_nascimento;
		this.email = email;
		this.telefone = telefone;
		this.altura = altura;
		this.peso = peso;
		this.endereco = endereco;
		this.senha = senha;
		this.isAdmin = isAdmin;
	}

	public Integer getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Integer id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(Date data_nascimento) {
		this.data_nascimento = data_nascimento;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public float getAltura() {
		return altura;
	}

	public void setAltura(float altura) {
		this.altura = altura;
	}

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDataNascToString() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		return formatter.format(data_nascimento);
	}

	public void setDataNascFromString(String dataNascAsString) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		this.data_nascimento = formatter.parse(dataNascAsString);
	}

	public String getDataNascToBD() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		return formatter.format(data_nascimento);
	}

	public void setDataNascFromBD(String string) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		this.data_nascimento = formatter.parse(string);
	}

	public String[] getValores() {
		return new String[] { nome, cpf, getDataNascToString(), email, telefone, String.valueOf(altura),
				String.valueOf(peso), endereco, senha, String.valueOf(isAdmin) };
	}

	public void atualizarValores(String[] valores) {
		this.nome = valores[0];
		this.cpf = valores[1];
		this.endereco = valores[3];
		this.telefone = valores[4];
		this.altura = Float.parseFloat(valores[5]);
		this.peso = Float.parseFloat(valores[6]);
		this.email = valores[7];
		this.senha = valores[8];
		this.isAdmin = Boolean.parseBoolean(valores[9]);
		try {
			this.setDataNascFromString(valores[2]);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
}
