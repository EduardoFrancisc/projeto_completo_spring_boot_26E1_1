package br.edu.infnet.assessment.repository;

import br.edu.infnet.assessment.model.Missao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MissaoRepository extends JpaRepository<Missao, Long> {
    @Query("SELECT m FROM Missao m WHERE " +
            "(CAST(:status AS string) IS NULL OR m.status = :status) AND " +
            "(CAST(:nivelPerigo AS string) IS NULL OR m.nivelPerigo = :nivelPerigo) AND " +
            "(CAST(:dataInicio AS string) IS NULL OR m.createdAt >= :dataInicio) AND " +
            "(CAST(:dataFim AS string) IS NULL OR m.createdAt <= :dataFim)")
    org.springframework.data.domain.Page<Missao> buscarMissoesComFiltros(
            @Param("status") br.edu.infnet.assessment.enums.StatusMissao status,
            @Param("nivelPerigo") br.edu.infnet.assessment.enums.NivelPerigo nivelPerigo,
            @Param("dataInicio") java.time.OffsetDateTime dataInicio,
            @Param("dataFim") java.time.OffsetDateTime dataFim,
            org.springframework.data.domain.Pageable pageable);

    @Query("SELECT new br.edu.infnet.assessment.dto.RelatorioMissaoDTO(" +
            "m.titulo, m.status, m.nivelPerigo, COUNT(p.id), COALESCE(SUM(p.recompensaOuro), 0.0)) " +
            "FROM Missao m " +
            "LEFT JOIN ParticipacaoMissao p ON p.missao = m " +
            "WHERE (CAST(:dataInicio AS string) IS NULL OR m.createdAt >= :dataInicio) " +
            "AND (CAST(:dataFim AS string) IS NULL OR m.createdAt <= :dataFim) " +
            "GROUP BY m.id, m.titulo, m.status, m.nivelPerigo " +
            "ORDER BY m.createdAt DESC")
    List<br.edu.infnet.assessment.dto.RelatorioMissaoDTO> gerarRelatorioMissoes(
            @Param("dataInicio") java.time.OffsetDateTime dataInicio,
            @Param("dataFim") java.time.OffsetDateTime dataFim);
}
