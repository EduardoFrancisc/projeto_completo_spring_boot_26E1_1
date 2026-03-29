package br.edu.infnet.assessment.service;

import br.edu.infnet.assessment.model.Missao;
import br.edu.infnet.assessment.model.Organizacao;
import br.edu.infnet.assessment.enums.StatusMissao;
import br.edu.infnet.assessment.dto.MissaoRequestDTO;
import br.edu.infnet.assessment.repository.MissaoRepository;
import br.edu.infnet.assessment.repository.OrganizacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MissaoService {

    private final MissaoRepository missaoRepository;
    private final OrganizacaoRepository organizacaoRepository;

    public MissaoService(MissaoRepository missaoRepository, OrganizacaoRepository organizacaoRepository) {
        this.missaoRepository = missaoRepository;
        this.organizacaoRepository = organizacaoRepository;
    }

    @Transactional
    public Missao criarMissao(MissaoRequestDTO dto) {
        // Busca a organização
        Organizacao org = organizacaoRepository.findById(dto.getOrganizacaoId())
                .orElseThrow(() -> new RuntimeException("Organização não encontrada"));

        // Monta a missão nova
        Missao missao = new Missao();
        missao.setOrganizacao(org);
        missao.setTitulo(dto.getTitulo().trim());
        missao.setNivelPerigo(dto.getNivelPerigo());
        missao.setStatus(StatusMissao.PLANEJADA);

        return missaoRepository.save(missao);
    }

    // Listagem Paginada e Filtrada de Missões
    public org.springframework.data.domain.Page<Missao> listarComFiltros(
            br.edu.infnet.assessment.enums.StatusMissao status,
            br.edu.infnet.assessment.enums.NivelPerigo nivelPerigo,
            java.time.OffsetDateTime dataInicio,
            java.time.OffsetDateTime dataFim,
            org.springframework.data.domain.Pageable pageable) {

        return missaoRepository.buscarMissoesComFiltros(status, nivelPerigo, dataInicio, dataFim, pageable);
    }

    // Busca uma missão específica pelo ID
    public Missao buscarPorId(Long id) {
        return missaoRepository.findById(id)
                .orElseThrow(() -> new br.edu.infnet.assessment.exception.RecursoNaoEncontradoException("Missão não encontrada na Guilda."));
    }
}