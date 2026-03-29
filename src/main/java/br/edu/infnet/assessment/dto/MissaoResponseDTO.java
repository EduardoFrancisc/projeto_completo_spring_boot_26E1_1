package br.edu.infnet.assessment.dto;

import br.edu.infnet.assessment.model.Missao;
import br.edu.infnet.assessment.enums.NivelPerigo;
import br.edu.infnet.assessment.enums.StatusMissao;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class MissaoResponseDTO {
    private Long id;
    private String titulo;
    private NivelPerigo nivelPerigo;
    private StatusMissao status;
    private String nomeOrganizacao;
    private OffsetDateTime createdAt;

    public MissaoResponseDTO(Missao m) {
        this.id = m.getId();
        this.titulo = m.getTitulo();
        this.nivelPerigo = m.getNivelPerigo();
        this.status = m.getStatus();
        this.createdAt = m.getCreatedAt();
        if (m.getOrganizacao() != null) {
            this.nomeOrganizacao = m.getOrganizacao().getNome();
        }
    }
}