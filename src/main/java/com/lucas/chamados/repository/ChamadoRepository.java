package com.lucas.chamados.repository;

import com.lucas.chamados.model.entity.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChamadoRepository  extends JpaRepository<Chamado, Long> {
}
