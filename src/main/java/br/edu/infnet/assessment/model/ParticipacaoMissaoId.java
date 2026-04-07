package br.edu.infnet.assessment.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class ParticipacaoMissaoId implements Serializable {
    private Long missao;
    private Long aventureiro;
}