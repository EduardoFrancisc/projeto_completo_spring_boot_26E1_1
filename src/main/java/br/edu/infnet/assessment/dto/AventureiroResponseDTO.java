package br.edu.infnet.assessment.dto;

import br.edu.infnet.assessment.model.Aventureiro;
import br.edu.infnet.assessment.enums.ClasseAventureiro;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class AventureiroResponseDTO {

    private Long id;
    private String nome;
    private ClasseAventureiro classe;
    private Integer nivel;
    private Boolean ativo;
    private OffsetDateTime createdAt;
    private String nomeOrganizacao;
    private String nomeUsuarioResponsavel;
    private CompanheiroResponseDTO companheiro;

    public AventureiroResponseDTO(Aventureiro a, br.edu.infnet.assessment.model.Companheiro c) {
        this.id = a.getId();
        this.nome = a.getNome();
        this.classe = a.getClasse();
        this.nivel = a.getNivel();
        this.ativo = a.getAtivo();
        this.createdAt = a.getCreatedAt();

        if (a.getOrganizacao() != null) {
            this.nomeOrganizacao = a.getOrganizacao().getNome();
        }

        if (a.getUsuarioResponsavel() != null) {
            this.nomeUsuarioResponsavel = a.getUsuarioResponsavel().getNome();
        }

        // Lê a partir do objeto 'c' injetado, em vez do aventureiro
        if (c != null) {
            this.companheiro = new CompanheiroResponseDTO(
                    c.getNome(),
                    c.getEspecie().name(),
                    c.getIndice_lealdade()
            );
        }
    }

    @Data
    public static class CompanheiroResponseDTO {
        private String nome;
        private String especie;
        private Integer lealdade;

        public CompanheiroResponseDTO(String nome, String especie, Integer lealdade) {
            this.nome = nome;
            this.especie = especie;
            this.lealdade = lealdade;
        }
    }
}