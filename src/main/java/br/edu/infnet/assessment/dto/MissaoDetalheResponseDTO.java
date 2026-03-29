package br.edu.infnet.assessment.dto;

import br.edu.infnet.assessment.model.Missao;
import br.edu.infnet.assessment.model.ParticipacaoMissao;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class MissaoDetalheResponseDTO extends MissaoResponseDTO {

    private List<ParticipacaoResponseDTO> aventureiros;

    public MissaoDetalheResponseDTO(Missao m, List<ParticipacaoMissao> participacoes) {
        super(m);

        this.aventureiros = participacoes.stream()
                .map(p -> new ParticipacaoResponseDTO(p))
                .toList();
    }
}