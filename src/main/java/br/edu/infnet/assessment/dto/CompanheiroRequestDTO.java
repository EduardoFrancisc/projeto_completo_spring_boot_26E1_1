package br.edu.infnet.assessment.dto;

import br.edu.infnet.assessment.enums.EspecieCompanheiro;
import lombok.Data;

@Data
public class CompanheiroRequestDTO {
    private String nome;
    private EspecieCompanheiro especie;
    private Integer lealdade;
}