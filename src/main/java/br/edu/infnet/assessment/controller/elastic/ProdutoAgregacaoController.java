package br.edu.infnet.assessment.controller.elastic;

import br.edu.infnet.assessment.service.elastic.ProdutoAgregacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/produtos/agregacoes")
public class ProdutoAgregacaoController {

    private final ProdutoAgregacaoService agregacaoService;

    public ProdutoAgregacaoController(ProdutoAgregacaoService agregacaoService) {
        this.agregacaoService = agregacaoService;
    }

    // 9. GET /produtos/agregacoes/por-categoria
    @GetMapping("/por-categoria")
    public ResponseEntity<Map<String, Long>> getPorCategoria() throws IOException {
        return ResponseEntity.ok(agregacaoService.agruparPorCategoria());
    }

    // 10. GET /produtos/agregacoes/por-raridade
    @GetMapping("/por-raridade")
    public ResponseEntity<Map<String, Long>> getPorRaridade() throws IOException {
        return ResponseEntity.ok(agregacaoService.agruparPorRaridade());
    }

    // 11. GET /produtos/agregacoes/preco-medio
    @GetMapping("/preco-medio")
    public ResponseEntity<Double> getPrecoMedio() throws IOException {
        return ResponseEntity.ok(agregacaoService.obterPrecoMedio());
    }

    // 12. GET /produtos/agregacoes/faixas-preco
    @GetMapping("/faixas-preco")
    public ResponseEntity<Map<String, Long>> getFaixasPreco() throws IOException {
        return ResponseEntity.ok(agregacaoService.agruparPorFaixaDePreco());
    }
}