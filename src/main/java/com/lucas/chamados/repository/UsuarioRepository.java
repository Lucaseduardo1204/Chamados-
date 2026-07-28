package com.lucas.chamados.repository;

import com.lucas.chamados.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long> {
    //métodos ja existentes .save(), .findById(), .findAll(), deleteById, .count()
    Optional<Usuario> findByEmail(String email);
}
