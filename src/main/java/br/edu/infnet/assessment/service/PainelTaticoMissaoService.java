package br.edu.infnet.assessment.service;

import br.edu.infnet.assessment.model.PainelTaticoMissao;
import br.edu.infnet.assessment.repository.PainelTaticoMissaoRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class PainelTaticoMissaoService {

    private final PainelTaticoMissaoRepository repository;

    public PainelTaticoMissaoService(PainelTaticoMissaoRepository repository) {
        this.repository = repository;
    }

    /* * EXPLICAÇÃO DA ABORDAGEM DE DESEMPENHO (Estratégia de Cache em Memória):
     * Foi aplicada a anotação @Cacheable para salvar o resultado desta consulta pesada
     * na memória RAM da aplicação (sob o nome "rankingTatico").
     * Isso reduz drasticamente as idas ao PostgreSQL, melhorando o tempo de resposta do endpoint.
     */
    @Cacheable(value = "rankingTatico")
    public List<PainelTaticoMissao> obterTop10MissoesUltimos15Dias() {
        OffsetDateTime dataCorte = OffsetDateTime.now().minusDays(15);
        PageRequest limit10 = PageRequest.of(0, 10);

        System.out.println("Buscando no banco de dados... (Se você ver isso 2x seguidas, o cache falhou!)");

        return repository.findByUltimaAtualizacaoGreaterThanEqualOrderByIndiceProntidaoDesc(dataCorte, limit10);
    }

    /*
     * Para manter a consistência e o "nível aceitável de atualização dos dados",
     * o @CacheEvict atrelado ao @Scheduled invalida (limpa) o cache a cada 5 minutos (300000 milissegundos).
     * Assim, a próxima requisição buscará dados frescos na materialized view do banco.
     */
    @CacheEvict(value = "rankingTatico", allEntries = true)
    @Scheduled(fixedRate = 300000)
    public void limparCacheRankingTatico() {
        System.out.println("Limpando o cache das top missões para buscar dados frescos!");
    }
}