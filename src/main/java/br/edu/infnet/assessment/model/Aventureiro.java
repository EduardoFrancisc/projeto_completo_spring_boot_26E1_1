package br.edu.infnet.assessment.model;

import br.edu.infnet.assessment.enums.ClasseAventureiro;
import jakarta.persistence.*;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "aventureiro", schema = "operacoes")
public class Aventureiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Vários aventureiros pertencem à mesma guilda (Organização)
    //optional = false - ele é obrigado a ter uma organização atrelada para garantir o isolamento das informações.
    //fetch = FetchType.LAZY foi usada para não sobrecarregar a memória da aplicação carregando os dados completos da organização toda vez que você busca o nome de um aventureiro.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;

    //Vários aventureiros são cadastrados pelo mesmo recrutador (Usuário).
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_cadastro_id", nullable = false)
    private Usuario usuarioResponsavel;

    @Column(nullable = false, length = 120)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClasseAventureiro classe;

    @Column(nullable = false)
    private Integer nivel;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "data_atualizacao", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }
}
