package br.edu.infnet.assessment.model;

import br.edu.infnet.assessment.enums.NivelPerigo;
import br.edu.infnet.assessment.enums.StatusMissao;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import java.time.OffsetDateTime;

@Data
@Entity
@Immutable
@Subselect("SELECT * FROM operacoes.vw_painel_tatico_missao")
public class PainelTaticoMissao {

    @Id
    @Column(name = "missao_id")
    private Long missaoId;

    private String titulo;

    @Enumerated(EnumType.STRING)
    private StatusMissao status;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_perigo")
    private NivelPerigo nivelPerigo;

    @Column(name = "organizacao_id")
    private Long organizacaoId;

    @Column(name = "total_participantes")
    private Integer totalParticipantes;

    @Column(name = "nivel_medio_equipe")
    private Double nivelMedioEquipe;

    @Column(name = "total_recompensa")
    private Long totalRecompensa;

    @Column(name = "total_mvps")
    private Integer totalMvps;

    @Column(name = "participantes_com_companheiro")
    private Integer participantesComCompanheiro;

    @Column(name = "ultima_atualizacao")
    private OffsetDateTime ultimaAtualizacao;

    @Column(name = "indice_prontidao")
    private Double indiceProntidao;
}