package br.edu.infnet.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AventureiroPerfilResponseDTO {
    private Long id;
    private String nome;
    private String classe;
    private Integer nivel;
    private Boolean ativo;
    private String nomeCompanheiro;
    private String especieCompanheiro;
    private Long totalParticipacoes;
    private String tituloUltimaMissao;
}