package br.edu.infnet.assessment.controller;

import br.edu.infnet.assessment.dto.RankingAventureiroDTO;
import br.edu.infnet.assessment.dto.RelatorioMissaoDTO;
import br.edu.infnet.assessment.enums.StatusMissao;
import br.edu.infnet.assessment.service.RelatorioService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<RankingAventureiroDTO>> getRanking(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime fim,
            @RequestParam(required = false) StatusMissao status) {
        return ResponseEntity.ok(relatorioService.obterRanking(inicio, fim, status));
    }

    @GetMapping("/missoes-metricas")
    public ResponseEntity<List<RelatorioMissaoDTO>> getMetricasMissoes(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime fim) {
        return ResponseEntity.ok(relatorioService.obterRelatorioMissoes(inicio, fim));
    }
}