package br.edu.infnet.assessment.dto;

import br.edu.infnet.assessment.model.ParticipacaoMissao;
import br.edu.infnet.assessment.enums.PapelMissao;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class ParticipacaoResponseDTO {
    private String tituloMissao;
    private String nomeAventureiro;
    private PapelMissao papel;
    private OffsetDateTime dataRegistro;

    public ParticipacaoResponseDTO(ParticipacaoMissao p) {
        this.papel = p.getPapel();
        this.dataRegistro = p.getDataRegistro();
        this.tituloMissao = p.getMissao().getTitulo();
        this.nomeAventureiro = p.getAventureiro().getNome();
    }
}