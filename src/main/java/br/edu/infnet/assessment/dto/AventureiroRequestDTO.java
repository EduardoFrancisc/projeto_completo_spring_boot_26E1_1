package br.edu.infnet.assessment.dto;

import br.edu.infnet.assessment.enums.ClasseAventureiro;
import lombok.Data;

@Data
public class AventureiroRequestDTO {
    private Long organizacaoId;
    private Long usuarioResponsavelId;
    private String nome;
    private ClasseAventureiro classe;
    private Integer nivel;
}
