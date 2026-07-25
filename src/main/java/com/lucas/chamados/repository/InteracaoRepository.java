package com.lucas.chamados.repository;

import com.lucas.chamados.model.entity.Interacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InteracaoRepository extends JpaRepository<Interacao, Long> {

    List<Interacao> findByChamadoIdOrderByDataHoraAsc(Long id);
}

