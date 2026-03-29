package br.edu.infnet.assessment.dto;

import br.edu.infnet.assessment.model.Aventureiro;
import br.edu.infnet.assessment.enums.ClasseAventureiro;
import lombok.Data;

@Data
public class AventureiroResumoDTO {
    private Long id;
    private String nome;
    private ClasseAventureiro classe;
    private Integer nivel;
    private Boolean ativo;

    public AventureiroResumoDTO(Aventureiro a) {
        this.id = a.getId();
        this.nome = a.getNome();
        this.classe = a.getClasse();
        this.nivel = a.getNivel();
        this.ativo = a.getAtivo();
    }
}