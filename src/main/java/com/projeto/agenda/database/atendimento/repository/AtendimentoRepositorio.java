package com.projeto.agenda.database.atendimento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.agenda.database.atendimento.entity.Atendimento;

@Repository
public interface AtendimentoRepositorio extends JpaRepository<Atendimento, Integer> {

}
