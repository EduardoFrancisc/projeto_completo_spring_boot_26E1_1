package br.edu.infnet.assessment.service;

import br.edu.infnet.assessment.exception.RecursoNaoEncontradoException;
import br.edu.infnet.assessment.model.Aventureiro;
import br.edu.infnet.assessment.model.Missao;
import br.edu.infnet.assessment.model.ParticipacaoMissao;
import br.edu.infnet.assessment.enums.PapelMissao;
import br.edu.infnet.assessment.enums.StatusMissao;
import br.edu.infnet.assessment.repository.AventureiroRepository;
import br.edu.infnet.assessment.repository.MissaoRepository;
import br.edu.infnet.assessment.repository.ParticipacaoMissaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParticipacaoMissaoService {

    private final ParticipacaoMissaoRepository participacaoRepository;
    private final MissaoRepository missaoRepository;
    private final AventureiroRepository aventureiroRepository;

    public ParticipacaoMissaoService(ParticipacaoMissaoRepository participacaoRepository,
                                     MissaoRepository missaoRepository,
                                     AventureiroRepository aventureiroRepository) {
        this.participacaoRepository = participacaoRepository;
        this.missaoRepository = missaoRepository;
        this.aventureiroRepository = aventureiroRepository;
    }

    @Transactional
    public ParticipacaoMissao registrarParticipacao(Long idMissao, Long idAventureiro, PapelMissao papel) {

        // 1. Busca os recursos (Lança exceção se não achar)
        Missao missao = missaoRepository.findById(idMissao)
                .orElseThrow(() -> new RuntimeException("Missão não encontrada."));

        Aventureiro aventureiro = aventureiroRepository.findById(idAventureiro)
                .orElseThrow(() -> new RuntimeException("Aventureiro não encontrado."));

        // 2. REGRA: Apenas aventureiros da mesma organização podem participar.
        if (!missao.getOrganizacao().getId().equals(aventureiro.getOrganizacao().getId())) {
            throw new RecursoNaoEncontradoException("Violação de Integridade: O aventureiro não pertence à organização da missão.");
        }

        // 3. REGRA: Um aventureiro inativo não pode ser associado.
        if (!aventureiro.getAtivo()) {
            throw new RecursoNaoEncontradoException("Aventureiro inativo não pode ser enviado para missões.");
        }

        // 4. REGRA: A missão deve estar em estado compatível.
        if (missao.getStatus() == StatusMissao.CONCLUIDA || missao.getStatus() == StatusMissao.CANCELADA) {
            throw new RecursoNaoEncontradoException("A missão não está em um estado que aceita novos participantes.");
        }

        // 5. REGRA (Do BD): Participação única.
        // Como colocamos o @UniqueConstraint na entidade, se tentar salvar de novo,
        // o JPA/Banco vai estourar uma DataIntegrityViolationException.
        // Estamos protegidos em duas camadas!

        // Se passou em todas as regras, criamos o registro!
        ParticipacaoMissao participacao = new ParticipacaoMissao();
        participacao.setMissao(missao);
        participacao.setAventureiro(aventureiro);
        participacao.setPapel(papel);
        // dataRegistro será preenchida automaticamente pelo @PrePersist que fizemos

        return participacaoRepository.save(participacao);
    }

    public java.util.List<ParticipacaoMissao> buscarPorMissao(Long missaoId) {
        return participacaoRepository.findByMissaoId(missaoId);
    }

}