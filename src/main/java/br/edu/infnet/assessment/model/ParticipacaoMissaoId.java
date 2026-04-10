package br.edu.infnet.assessment.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class ParticipacaoMissaoId implements Serializable {
    private Long missao;
    private Long aventureiro;
}

/*
Ao anotar as relações estrangeiras (missao e aventureiro) com @Id e referenciar a classe ParticipacaoMissaoId, o código instrui o PostgreSQL a transformar essas duas colunas na Chave Primária da tabela. Isso força uma garantia estrutural onde é impossível inserir o mesmo aventureiro duas vezes na mesma missão por erro do sistema.
*/