package br.edu.infnet.assessment.repository.elastic;

import br.edu.infnet.assessment.model.elastic.Produto;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoElasticRepository extends ElasticsearchRepository<Produto, String> {

    // 1. Busca por nome (Match Query padrão)
    @Query("{\"match\": {\"nome\": \"?0\"}}")
    List<Produto> buscarPorNome(String termo);

    // 2. Busca por descricao
    @Query("{\"match\": {\"descricao\": \"?0\"}}")
    List<Produto> buscarPorDescricao(String termo);

    // 3. Busca por frase exata (Match Phrase)
    @Query("{\"match_phrase\": {\"descricao\": \"?0\"}}")
    List<Produto> buscarPorFraseExata(String termo);

    // 4. Busca Fuzzy (Tolerante a erros de digitação, ex: 'espdaa')
    @Query("{\"match\": {\"nome\": {\"query\": \"?0\", \"fuzziness\": \"AUTO\"}}}")
    List<Produto> buscarPorNomeFuzzy(String termo);

    // 5. Busca em múltiplos campos simultaneamente (Multi Match)
    @Query("{\"multi_match\": {\"query\": \"?0\", \"fields\": [\"nome\", \"descricao\"]}}")
    List<Produto> buscarEmMultiplosCampos(String termo);

    // --- PARTE B: Buscas com Filtros ---

    // 6. Busca textual + filtro por categoria
    @Query("{\"bool\": {\"must\": [{\"match\": {\"descricao\": \"?0\"}}], \"filter\": [{\"term\": {\"categoria\": \"?1\"}}]}}")
    List<Produto> buscarNaDescricaoComFiltroCategoria(String termo, String categoria);

    // 7. Busca por faixa de preço
    @Query("{\"range\": {\"preco\": {\"gte\": ?0, \"lte\": ?1}}}")
    List<Produto> buscarPorFaixaDePreco(Double min, Double max);

    // 8. Busca combinada (Categoria, Raridade e Faixa de Preço)
    @Query("{\"bool\": {\"filter\": [{\"term\": {\"categoria\": \"?0\"}}, {\"term\": {\"raridade\": \"?1\"}}, {\"range\": {\"preco\": {\"gte\": ?2, \"lte\": ?3}}}]}}")
    List<Produto> buscarAvancada(String categoria, String raridade, Double min, Double max);
}