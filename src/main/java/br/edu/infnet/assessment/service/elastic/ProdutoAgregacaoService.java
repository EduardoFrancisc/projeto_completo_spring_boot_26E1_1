package br.edu.infnet.assessment.service.elastic;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import org.springframework.stereotype.Service;
import co.elastic.clients.elasticsearch._types.aggregations.AggregationRange;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class ProdutoAgregacaoService {

    private final ElasticsearchClient elasticClient;

    public ProdutoAgregacaoService(ElasticsearchClient elasticClient) {
        this.elasticClient = elasticClient;
    }

    public Map<String, Long> agruparPorCategoria() throws IOException {
        SearchResponse<Void> response = elasticClient.search(s -> s
                .index("guilda_loja")
                .size(0)
                .aggregations("agrupamento_categoria", a -> a
                        .terms(t -> t.field("categoria"))
                ), Void.class);

        Map<String, Long> resultado = new LinkedHashMap<>();
        response.aggregations().get("agrupamento_categoria").sterms().buckets().array().forEach(bucket -> {
            resultado.put(bucket.key().stringValue(), bucket.docCount());
        });
        return resultado;
    }

    public Map<String, Long> agruparPorRaridade() throws IOException {
        SearchResponse<Void> response = elasticClient.search(s -> s
                .index("guilda_loja")
                .size(0)
                .aggregations("agrupamento_raridade", a -> a
                        .terms(t -> t.field("raridade"))
                ), Void.class);

        Map<String, Long> resultado = new LinkedHashMap<>();
        response.aggregations().get("agrupamento_raridade").sterms().buckets().array().forEach(bucket -> {
            resultado.put(bucket.key().stringValue(), bucket.docCount());
        });
        return resultado;
    }

    public Double obterPrecoMedio() throws IOException {
        SearchResponse<Void> response = elasticClient.search(s -> s
                .index("guilda_loja")
                .size(0)
                .aggregations("media_preco", a -> a
                        .avg(avg -> avg.field("preco"))
                ), Void.class);

        return response.aggregations().get("media_preco").avg().value();
    }

    public Map<String, Long> agruparPorFaixaDePreco() throws IOException {
        SearchResponse<Void> response = elasticClient.search(s -> s
                .index("guilda_loja")
                .size(0)
                .aggregations("faixas_preco", a -> a
                        .range(r -> r
                                .field("preco")
                                .ranges(
                                        AggregationRange.of(ar -> ar.to(100.0)),
                                        AggregationRange.of(ar -> ar.from(100.0).to(300.0)),
                                        AggregationRange.of(ar -> ar.from(300.0).to(700.0)),
                                        AggregationRange.of(ar -> ar.from(700.0))
                                )
                        )
                ), Void.class);

        Map<String, Long> resultado = new LinkedHashMap<>();
        response.aggregations().get("faixas_preco").range().buckets().array().forEach(bucket -> {
            String chaveBruta = bucket.key();

            String chaveBonita;
            if (chaveBruta.startsWith("*-")) chaveBonita = "Abaixo de 100";
            else if (chaveBruta.equals("100.0-300.0")) chaveBonita = "De 100 a 300";
            else if (chaveBruta.equals("300.0-700.0")) chaveBonita = "De 300 a 700";
            else chaveBonita = "Acima de 700";

            resultado.put(chaveBonita, bucket.docCount());
        });
        return resultado;
    }
}