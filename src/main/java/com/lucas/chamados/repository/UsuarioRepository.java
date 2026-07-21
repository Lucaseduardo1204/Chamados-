package com.lucas.chamados.repository;

import com.lucas.chamados.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long> {
    //métodos ja existentes .save(), .findById(), .findAll(), deleteById, .count()
}
