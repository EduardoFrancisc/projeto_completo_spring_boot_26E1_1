package br.edu.infnet.assessment.dto;

import br.edu.infnet.assessment.enums.PapelMissao;
import lombok.Data;

@Data
public class ParticipacaoRequestDTO {
    private Long aventureiroId;
    private PapelMissao papel;
}