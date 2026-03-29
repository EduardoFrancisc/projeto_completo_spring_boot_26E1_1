package br.edu.infnet.assessment.service.elastic;

import br.edu.infnet.assessment.model.elastic.Produto;
import br.edu.infnet.assessment.repository.elastic.ProdutoElasticRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoElasticRepository repository;

    public ProdutoService(ProdutoElasticRepository repository) {
        this.repository = repository;
    }

    public List<Produto> buscarPorNome(String termo) { return repository.buscarPorNome(termo); }
    public List<Produto> buscarPorDescricao(String termo) { return repository.buscarPorDescricao(termo); }
    public List<Produto> buscarPorFraseExata(String termo) { return repository.buscarPorFraseExata(termo); }
    public List<Produto> buscarFuzzy(String termo) { return repository.buscarPorNomeFuzzy(termo); }
    public List<Produto> buscarMultiCampos(String termo) { return repository.buscarEmMultiplosCampos(termo); }
    // --- PARTE B ---
    public List<Produto> buscarComFiltro(String termo, String categoria) {
        return repository.buscarNaDescricaoComFiltroCategoria(termo, categoria);
    }
    public List<Produto> buscarPorFaixaDePreco(Double min, Double max) {
        return repository.buscarPorFaixaDePreco(min, max);
    }
    public List<Produto> buscarAvancada(String categoria, String raridade, Double min, Double max) {
        return repository.buscarAvancada(categoria, raridade, min, max);
    }
}