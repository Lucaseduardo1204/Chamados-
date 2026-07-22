package com.lucas.chamados.model.entity;

import com.lucas.chamados.model.enums.PrioridadeEnum;
import com.lucas.chamados.model.enums.SituacaoEnum;
import com.lucas.chamados.model.enums.TipoEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name = "chamados")
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chamado_id")
    private Long id;

    // Hibernate preenche preenche a data antes de enviar o INSERT, o DEFAULT NOW() fica de "reserva"
    @CreationTimestamp
    @Column(name = "data_hora_criacao", updatable = false)
    private LocalDateTime dataHoraCriacao;

    // Já é automaticamente preenchida
    @UpdateTimestamp
    @Column(name = "data_hora_modificacao")
    private LocalDateTime dataHoraModificacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SituacaoEnum situacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PrioridadeEnum prioridade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private TipoEnum tipo;

    @Column(nullable = false, length = 50)
    private String sistema;

    @Column(nullable = false, length = 100)
    private String resumo;

    @Column(nullable = false, length = 500)
    private String descricao;

    //ManyToONe - muitos para um, muitos chamados para um usuario
    // @Column = mapeia um valor simples enquanto @JoinColumn mapeia uma FK (chave estrangeira) que aponta para outra tabela
    @ManyToOne
    @JoinColumn(name = "solicitante_id", nullable = false)
    // solicitante guarda um objeto inteiro, no banco ele é referenciado por id, aqui é o objeto como todo
    private Usuario solicitante;

    @ManyToOne
    @JoinColumn(name = "responsavel_id")
    private Usuario responsavel;

    public  Chamado (){}

    public Chamado(SituacaoEnum situacao, PrioridadeEnum prioridade, TipoEnum tipo, String sistema, String resumo, String descricao, Usuario solicitanteId, Usuario responsavelId) {
        this.situacao = situacao;
        this.prioridade = prioridade;
        this.tipo = tipo;
        this.sistema = sistema;
        this.resumo = resumo;
        this.descricao = descricao;
        this.solicitante = solicitanteId;
        this.responsavel = responsavelId;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDataHoraCriacao() {
        return dataHoraCriacao;
    }

    public LocalDateTime getDataHoraModificacao() {
        return dataHoraModificacao;
    }

    public SituacaoEnum getSituacao() {
        return situacao;
    }

    public PrioridadeEnum getPrioridade() {
        return prioridade;
    }

    public TipoEnum getTipo() {
        return tipo;
    }

    public String getSistema() {
        return sistema;
    }

    public String getResumo() {
        return resumo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Usuario getSolicitante() {
        return solicitante;
    }

    public Usuario getResponsavel() {
        return responsavel;
    }

    public void setSituacao(SituacaoEnum situacao) {
        this.situacao = situacao;
    }

    public void setPrioridade(PrioridadeEnum prioridade) {
        this.prioridade = prioridade;
    }

    public void setTipo(TipoEnum tipo) {
        this.tipo = tipo;
    }

    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setSolicitante(Usuario solicitanteId) {
        this.solicitante = solicitanteId;
    }

    public void setResponsavel(Usuario responsavelId) {
        this.responsavel = responsavelId;
    }
}
