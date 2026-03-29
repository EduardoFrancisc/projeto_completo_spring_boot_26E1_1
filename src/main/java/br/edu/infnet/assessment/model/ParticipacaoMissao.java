package br.edu.infnet.assessment.model;

import br.edu.infnet.assessment.enums.PapelMissao;
import jakarta.persistence.*;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(
        name = "participacoes_missao",
        schema = "aventura",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"missao_id", "aventureiro_id"})
        }
)
public class ParticipacaoMissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "missao_id", nullable = false)
    private Missao missao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "aventureiro_id", nullable = false)
    private Aventureiro aventureiro;

    @Enumerated(EnumType.STRING)
    @Column(name = "papel_missao", nullable = false)
    private PapelMissao papel;

    @Column(name = "recompensa_ouro")
    private Integer recompensaOuro;

    @Column(name = "destaque_mvp", nullable = false)
    private Boolean destaqueMvp = false;

    @Column(name = "data_registro", nullable = false, updatable = false)
    private OffsetDateTime dataRegistro;

    @PrePersist
    protected void onCreate() {
        this.dataRegistro = OffsetDateTime.now();
    }
}