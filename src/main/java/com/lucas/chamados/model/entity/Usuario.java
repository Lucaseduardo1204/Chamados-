package com.lucas.chamados.model.entity;

import com.lucas.chamados.model.enums.Fundacao;
import com.lucas.chamados.model.enums.TipoUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
// AO implementar um userDetails, a entity ganha outro papel, dados e credenciais. Os mpetodos chamves: getUsername()
// getPassword(), getAuthorities(). é onde a identidade vira permissão, (conveniência, em outros prjetos é comum a separação)
public class Usuario implements UserDetails {

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

    @Column(nullable = false)
    private String senha;


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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String getUsername(){
        return email;
    }

    @Override
    public String getPassword(){
        return senha;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority("ROLE_" + tipoUsuario.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}


