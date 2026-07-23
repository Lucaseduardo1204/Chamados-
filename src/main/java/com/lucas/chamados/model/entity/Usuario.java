package com.lucas.chamados.model.entity;

import com.lucas.chamados.model.enums.Fundacao;
import com.lucas.chamados.model.enums.TipoUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    @Email(message = "Digite um email válido!")
    private String email;

    // por padrão, o Java trata de enums como ordinais com o @Enumerated(EnumType.STRING) ele passa a tratar como uma
    // String senão guardaria a posição (0,1,2), e reordenar o enum corromperia os dados históricos
    @Enumerated(EnumType.STRING)
    // no banco = tipo_usuario, na classe tipoUsuario, portanto é necessário mapea-lo com a coluna do banco
    @Column(name = "tipo_usuario", nullable = false)
    private TipoUsuario tipoUsuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Fundacao fundacao;


    // Exigido pelo jpa para executar o sql por traz dos panos, o framework precisa criar instâncias sem conhecer
    // previamente os valores das propriedades, ele depende desse contrutor padrão para inicialiizar a classe antes
    // de preencher seus atributos pode ser public ou protected
    public Usuario(){}

    public Usuario(String nome, String email, TipoUsuario tipoUsuario, Fundacao fundacao){
        this.nome = nome;
        this.email= email;
        this.tipoUsuario = tipoUsuario;
        this.fundacao = fundacao;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public Fundacao getFundacao() {
        return fundacao;
    }


    public void setNome(String nome){
        this.nome = nome;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario){
        this.tipoUsuario = tipoUsuario;
    }

    public void setFundacao(Fundacao fundacao){
        this.fundacao = fundacao;
    }

}


