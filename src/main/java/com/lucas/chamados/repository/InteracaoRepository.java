package com.lucas.chamados.repository;

import com.lucas.chamados.model.entity.Interacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InteracaoRepository extends JpaRepository<Interacao, Long> {
}
