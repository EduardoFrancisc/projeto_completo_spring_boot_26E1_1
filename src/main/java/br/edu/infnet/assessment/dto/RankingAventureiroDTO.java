package br.edu.infnet.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankingAventureiroDTO {
    private Long idAventureiro;
    private String nomeAventureiro;
    private Long totalParticipacoes;
    private BigDecimal somaRecompensas;
    private Long quantidadeDestaques;
}