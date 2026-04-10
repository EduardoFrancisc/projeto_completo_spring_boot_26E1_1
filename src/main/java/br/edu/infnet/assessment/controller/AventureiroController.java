package br.edu.infnet.assessment.controller;

import br.edu.infnet.assessment.dto.*;
import br.edu.infnet.assessment.enums.ClasseAventureiro;
import br.edu.infnet.assessment.model.Aventureiro;
import br.edu.infnet.assessment.model.Companheiro;
import br.edu.infnet.assessment.service.AventureiroService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aventureiros")
public class AventureiroController {

    private final AventureiroService aventureiroService;

    public AventureiroController(AventureiroService service) {
        this.aventureiroService = service;
    }

    private AventureiroResponseDTO montarResponse(Aventureiro a) {
        Companheiro c = aventureiroService.buscarCompanheiro(a.getId());
        return new AventureiroResponseDTO(a, c);
    }

    @GetMapping
    public ResponseEntity<List<AventureiroResumoDTO>> getAll(
            @RequestParam(required = false) ClasseAventureiro classe,
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) Integer nivelMinimo,
            @RequestHeader(value = "X-Page", defaultValue = "0") int page,
            @RequestHeader(value = "X-Size", defaultValue = "10") int size) {

        List<Aventureiro> todos = aventureiroService.getAll();

        List<AventureiroResumoDTO> filtrados = todos.stream()
                .filter(a -> classe == null || a.getClasse() == classe)
                .filter(a -> ativo == null || a.getAtivo().equals(ativo))
                .filter(a -> nivelMinimo == null || a.getNivel() >= nivelMinimo)
                .sorted(java.util.Comparator.comparing(Aventureiro::getId))
                .map(a -> new AventureiroResumoDTO(a))
                .toList();
        int totalElements = filtrados.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        int pular = page * size;

        List<AventureiroResumoDTO> paginaConteudo = filtrados.stream()
                .skip(pular)
                .limit(size)
                .toList();

        return ResponseEntity.status(HttpStatus.OK)
                .header("X-Total-Count", String.valueOf(totalElements))
                .header("X-Page", String.valueOf(page))
                .header("X-Size", String.valueOf(size))
                .header("X-Total-Pages", String.valueOf(totalPages))
                .body(paginaConteudo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AventureiroResponseDTO> buscarPorId(@PathVariable Long id) {
        Aventureiro aventureiro = aventureiroService.buscarPorId(id);
        return ResponseEntity.ok(montarResponse(aventureiro));
    }

    @PostMapping
    public ResponseEntity<AventureiroResponseDTO> registrar(@RequestBody AventureiroRequestDTO dto) {
        Aventureiro novo = aventureiroService.registrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(montarResponse(novo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AventureiroResponseDTO> atualizar(@PathVariable Long id, @RequestBody AventureiroRequestDTO dto) {
        Aventureiro atualizado = aventureiroService.atualizar(id, dto);
        return ResponseEntity.ok(montarResponse(atualizado));
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<AventureiroResponseDTO> inativar(@PathVariable Long id) {
        Aventureiro inativo = aventureiroService.inativar(id);
        return ResponseEntity.ok(montarResponse(inativo));
    }

    @PatchMapping("/{id}/recrutar")
    public ResponseEntity<AventureiroResponseDTO> recrutar(@PathVariable Long id) {
        Aventureiro ativo = aventureiroService.recrutar(id);
        return ResponseEntity.ok(montarResponse(ativo));
    }

    @PutMapping("/{id}/companheiro")
    public ResponseEntity<AventureiroResponseDTO> salvarCompanheiro(@PathVariable Long id, @RequestBody CompanheiroRequestDTO dto) {
        aventureiroService.salvarCompanheiro(id, dto);
        Aventureiro a = aventureiroService.buscarPorId(id);
        return ResponseEntity.ok(montarResponse(a));
    }

    @DeleteMapping("/{id}/companheiro")
    public ResponseEntity<Void> removerCompanheiro(@PathVariable Long id) {
        aventureiroService.removerCompanheiro(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/busca")
    public ResponseEntity<Page<AventureiroResponseDTO>> buscarPorNome(
            @RequestParam String nome,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {

        var pagina = aventureiroService.buscarPorNome(nome, pageable)
                .map(this::montarResponse); // <-- método auxiliar

        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}/perfil")
    public ResponseEntity<AventureiroPerfilResponseDTO> getPerfilCompleto(@PathVariable Long id) {
        return ResponseEntity.ok(aventureiroService.obterPerfilCompleto(id));
    }
}