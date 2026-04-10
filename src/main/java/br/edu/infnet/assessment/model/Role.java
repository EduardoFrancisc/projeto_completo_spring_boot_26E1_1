package br.edu.infnet.assessment.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.OffsetDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "roles", schema = "audit")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Cargos podem ser customizados por organização
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;

    @Column(nullable = false)
    private String nome;

    private String descricao;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    //Agrupa de forma limpa quais permissões do sistema pertencem a qual cargo, cruzando os registros de forma relacional pura.
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_permissions",
            schema = "audit",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions;
}
