package br.edu.infnet.assessment.controller;

import br.edu.infnet.assessment.enums.NivelPerigo;
import br.edu.infnet.assessment.enums.StatusMissao;
import br.edu.infnet.assessment.model.Missao;
import br.edu.infnet.assessment.model.PainelTaticoMissao;
import br.edu.infnet.assessment.model.ParticipacaoMissao;
import br.edu.infnet.assessment.dto.*;
import br.edu.infnet.assessment.service.MissaoService;
import br.edu.infnet.assessment.service.PainelTaticoMissaoService;
import br.edu.infnet.assessment.service.ParticipacaoMissaoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/missoes")
public class MissaoController {

    private final MissaoService missaoService;
    private final ParticipacaoMissaoService participacaoService;
    private final PainelTaticoMissaoService painelTaticoService;

    public MissaoController(MissaoService missaoService, ParticipacaoMissaoService participacaoService, PainelTaticoMissaoService painelTaticoService) {
        this.missaoService = missaoService;
        this.participacaoService = participacaoService;
        this.painelTaticoService = painelTaticoService;
    }

    @GetMapping
    public ResponseEntity<Page<MissaoResponseDTO>> getAll(
            @RequestParam(required = false) StatusMissao status,
            @RequestParam(required = false) NivelPerigo nivelPerigo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataFim,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Missao> paginaMissoes =
                missaoService.listarComFiltros(status, nivelPerigo, dataInicio, dataFim, pageable);

        Page<br.edu.infnet.assessment.dto.MissaoResponseDTO> paginaDTO =
                paginaMissoes.map(br.edu.infnet.assessment.dto.MissaoResponseDTO::new);

        return ResponseEntity.ok(paginaDTO);
    }


    @GetMapping("/{id}")
    public ResponseEntity<MissaoDetalheResponseDTO> getById(@PathVariable Long id) {

        Missao missao = missaoService.buscarPorId(id);

        List<ParticipacaoMissao> participacoes = participacaoService.buscarPorMissao(id);

        MissaoDetalheResponseDTO dto = new MissaoDetalheResponseDTO(missao, participacoes);

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<MissaoResponseDTO> criarMissao(@RequestBody MissaoRequestDTO dto) {
        Missao novaMissao = missaoService.criarMissao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MissaoResponseDTO(novaMissao));
    }

    @PostMapping("/{id}/participacoes")
    public ResponseEntity<ParticipacaoResponseDTO> inscreverAventureiro(
            @PathVariable Long id,
            @RequestBody ParticipacaoRequestDTO dto) {

        ParticipacaoMissao participacao = participacaoService.registrarParticipacao(id, dto.getAventureiroId(), dto.getPapel());

        return ResponseEntity.status(HttpStatus.CREATED).body(new ParticipacaoResponseDTO(participacao));
    }

    @GetMapping("/top15dias")
    public ResponseEntity<List<PainelTaticoMissao>> getTop15Dias() {

        List<PainelTaticoMissao> topMissoes =
                painelTaticoService.obterTop10MissoesUltimos15Dias();

        return ResponseEntity.ok(topMissoes);
    }
}