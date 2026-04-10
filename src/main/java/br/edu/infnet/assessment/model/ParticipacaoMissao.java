package br.edu.infnet.assessment.model;

import br.edu.infnet.assessment.enums.PapelMissao;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "participacao_missao", schema = "operacoes")
@IdClass(ParticipacaoMissaoId.class)
public class ParticipacaoMissao {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "missao_id", nullable = false)
    private Missao missao;

    //Aventureiros e Missões são um relacionamento Muitos-para-Muitos. Porém, na prática deste projeto, era necessário registrar os atributos dessa relação: qual foi o papel assumido, a recompensaOuro ganha e se o aventureiro foi o destaqueMvp daquela incursão. Um simples @ManyToMany não permite salvar dados extras. A solução foi criar essa entidade que recebe dois relacionamentos @ManyToOne.

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "aventureiro_id", nullable = false)
    private Aventureiro aventureiro;

    @Enumerated(EnumType.STRING)
    @Column(name = "papel", nullable = false)
    private PapelMissao papel;

    @Column(name = "recompensa_ouro")
    private BigDecimal recompensaOuro;

    @Column(name = "destaque", nullable = false)
    private Boolean destaqueMvp = false;

    @Column(name = "data_registro", nullable = false, updatable = false)
    private OffsetDateTime dataRegistro;

    @PrePersist
    protected void onCreate() {
        this.dataRegistro = OffsetDateTime.now();
    }
}