package br.edu.infnet.assessment.service;

import br.edu.infnet.assessment.dto.RankingAventureiroDTO;
import br.edu.infnet.assessment.dto.RelatorioMissaoDTO;
import br.edu.infnet.assessment.enums.StatusMissao;
import br.edu.infnet.assessment.repository.AventureiroRepository;
import br.edu.infnet.assessment.repository.MissaoRepository;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class RelatorioService {

    private final AventureiroRepository aventureiroRepository;
    private final MissaoRepository missaoRepository;

    public RelatorioService(AventureiroRepository aventureiroRepository, MissaoRepository missaoRepository) {
        this.aventureiroRepository = aventureiroRepository;
        this.missaoRepository = missaoRepository;
    }

    public List<RankingAventureiroDTO> obterRanking(OffsetDateTime inicio, OffsetDateTime fim, StatusMissao status) {
        return aventureiroRepository.gerarRankingAventureiros(inicio, fim, status);
    }

    public List<RelatorioMissaoDTO> obterRelatorioMissoes(OffsetDateTime inicio, OffsetDateTime fim) {
        return missaoRepository.gerarRelatorioMissoes(inicio, fim);
    }
}