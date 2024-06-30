package com.projeto.agenda.database.tipo.entity;

import java.util.Objects;

import com.projeto.agenda.database.EntidadeEditavel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Tipo implements EntidadeEditavel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id_tipo;

	@Column(length = 80, nullable = false)
	private String desc_tipo;

	@Column(nullable = false)
	private float valor_tipo;

	public Tipo() {
	}

	public Tipo(String desc_tipo, float valor_tipo) {
		this.desc_tipo = desc_tipo;
		this.valor_tipo = valor_tipo;
	}

	public Tipo(Integer id_tipo, String desc_tipo, float valor_tipo) {
		super();
		this.id_tipo = id_tipo;
		this.desc_tipo = desc_tipo;
		this.valor_tipo = valor_tipo;
	}

	public Integer getId_tipo() {
		return id_tipo;
	}

	public void setId_tipo(Integer id_tipo) {
		this.id_tipo = id_tipo;
	}

	public String getDesc_tipo() {
		return desc_tipo;
	}

	public void setDesc_tipo(String desc_tipo) {
		this.desc_tipo = desc_tipo;
	}

	public float getValor_tipo() {
		return valor_tipo;
	}

	public void setValor_tipo(float valor_tipo) {
		this.valor_tipo = valor_tipo;
	}

	public String[] getValores() {
		return new String[] { desc_tipo, String.valueOf(valor_tipo) };
	}

	public void atualizarValores(String[] valores) {
		this.desc_tipo = valores[0];
		this.valor_tipo = Float.parseFloat(valores[1]);
	}

	@Override
	public String toString() {
		return "Tipo [id_tipo=" + id_tipo + ", desc_tipo=" + desc_tipo + ", valor_tipo=" + valor_tipo + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(desc_tipo, id_tipo, valor_tipo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tipo other = (Tipo) obj;
		return Objects.equals(desc_tipo, other.desc_tipo) && Objects.equals(id_tipo, other.id_tipo)
				&& Float.floatToIntBits(valor_tipo) == Float.floatToIntBits(other.valor_tipo);
	}

}
