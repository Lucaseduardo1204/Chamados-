package com.lucas.chamados.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "interacao")
public class Interacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interacao_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chamado_id", nullable = false)
    private Chamado chamado;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    @CreationTimestamp
    @Column(name = "data_hora", updatable = false)
    private LocalDateTime dataHora;

    @Column(length = 500, nullable = false)
    private String texto;

    public Interacao(){}

    public Interacao(Chamado chamado, Usuario autor, String texto){
        this.chamado = chamado;
        this.autor = autor;
        this.texto = texto;
    }

    public Long getId() {
        return id;
    }

    public Chamado getChamado() {
        return chamado;
    }

    public Usuario getAutor() {
        return autor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getTexto() {
        return texto;
    }
}
