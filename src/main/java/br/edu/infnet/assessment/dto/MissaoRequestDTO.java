package br.edu.infnet.assessment.dto;

import br.edu.infnet.assessment.enums.NivelPerigo;
import lombok.Data;

@Data
public class MissaoRequestDTO {
    private Long organizacaoId;
    private String titulo;
    private NivelPerigo nivelPerigo;
}