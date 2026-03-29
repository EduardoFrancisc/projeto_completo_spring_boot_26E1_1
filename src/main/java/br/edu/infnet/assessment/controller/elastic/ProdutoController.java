package br.edu.infnet.assessment.controller.elastic;

import br.edu.infnet.assessment.model.elastic.Produto;
import br.edu.infnet.assessment.service.elastic.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    // 1. GET /produtos/busca/nome?termo=espada
    @GetMapping("/busca/nome")
    public ResponseEntity<List<Produto>> buscarPorNome(@RequestParam String termo) {
        return ResponseEntity.ok(produtoService.buscarPorNome(termo));
    }

    // 2. GET /produtos/busca/descricao?termo=cura
    @GetMapping("/busca/descricao")
    public ResponseEntity<List<Produto>> buscarPorDescricao(@RequestParam String termo) {
        return ResponseEntity.ok(produtoService.buscarPorDescricao(termo));
    }

    // 3. GET /produtos/busca/frase?termo=cura superior
    @GetMapping("/busca/frase")
    public ResponseEntity<List<Produto>> buscarPorFrase(@RequestParam String termo) {
        return ResponseEntity.ok(produtoService.buscarPorFraseExata(termo));
    }

    // 4. GET /produtos/busca/fuzzy?termo=espdaa
    @GetMapping("/busca/fuzzy")
    public ResponseEntity<List<Produto>> buscarFuzzy(@RequestParam String termo) {
        return ResponseEntity.ok(produtoService.buscarFuzzy(termo));
    }

    // 5. GET /produtos/busca/multicampos?termo=dragão
    @GetMapping("/busca/multicampos")
    public ResponseEntity<List<Produto>> buscarMultiCampos(@RequestParam String termo) {
        return ResponseEntity.ok(produtoService.buscarMultiCampos(termo));
    }

    // --- PARTE B ---

    // 6. GET /produtos/busca/com-filtro?termo=pocao&categoria=pocoes
    @GetMapping("/busca/com-filtro")
    public ResponseEntity<List<Produto>> buscarComFiltro(
            @RequestParam String termo,
            @RequestParam String categoria) {
        return ResponseEntity.ok(produtoService.buscarComFiltro(termo, categoria));
    }

    // 7. GET /produtos/busca/faixa-preco?min=50&max=300
    @GetMapping("/busca/faixa-preco")
    public ResponseEntity<List<Produto>> buscarFaixaPreco(
            @RequestParam Double min,
            @RequestParam Double max) {
        return ResponseEntity.ok(produtoService.buscarPorFaixaDePreco(min, max));
    }

    // 8. GET /produtos/busca/avancada?categoria=armas&raridade=raro&min=200&max=1000
    @GetMapping("/busca/avancada")
    public ResponseEntity<List<Produto>> buscarAvancada(
            @RequestParam String categoria,
            @RequestParam String raridade,
            @RequestParam Double min,
            @RequestParam Double max) {
        return ResponseEntity.ok(produtoService.buscarAvancada(categoria, raridade, min, max));
    }
}