package br.edu.infnet.assessment.model;

import br.edu.infnet.assessment.enums.EspecieCompanheiro;
import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "companheiro", schema = "operacoes")
public class Companheiro {

    @Id
    @Column(name = "aventureiro_id")
    private Long aventureiroId;

    @Column(nullable = false, length = 120)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EspecieCompanheiro especie;

    @Column(nullable = false)
    private Integer indice_lealdade;
}