package br.edu.infnet.assessment.dto;

import br.edu.infnet.assessment.enums.NivelPerigo;
import br.edu.infnet.assessment.enums.StatusMissao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioMissaoDTO {
    private String titulo;
    private StatusMissao status;
    private NivelPerigo nivelPerigo;
    private Long quantidadeParticipantes;
    private Long totalRecompensasDistribuidas;
}