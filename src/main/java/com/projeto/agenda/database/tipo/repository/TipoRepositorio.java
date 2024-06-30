package com.projeto.agenda.database.tipo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.agenda.database.tipo.entity.Tipo;

@Repository
public interface TipoRepositorio extends JpaRepository<Tipo, Integer> {

}
