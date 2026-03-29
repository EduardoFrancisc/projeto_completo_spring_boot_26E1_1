package br.edu.infnet.assessment.repository;

import br.edu.infnet.assessment.model.Aventureiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface AventureiroRepository extends JpaRepository<Aventureiro, Long> {

    Page<Aventureiro> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    @Query("SELECT new br.edu.infnet.assessment.dto.RankingAventureiroDTO(" +
            "a.id, a.nome, COUNT(p.id), COALESCE(SUM(p.recompensaOuro), 0L), " +
            "SUM(CASE WHEN p.destaqueMvp = true THEN 1L ELSE 0L END)) " +
            "FROM Aventureiro a " +
            "LEFT JOIN ParticipacaoMissao p ON p.aventureiro = a " +
            "LEFT JOIN p.missao m " +
            "WHERE (CAST(:dataInicio AS string) IS NULL OR p.dataRegistro >= :dataInicio) " +
            "AND (CAST(:dataFim AS string) IS NULL OR p.dataRegistro <= :dataFim) " +
            "AND (CAST(:statusMissao AS string) IS NULL OR m.status = :statusMissao) " +
            "GROUP BY a.id, a.nome " +
            "ORDER BY COUNT(p.id) DESC, COALESCE(SUM(p.recompensaOuro), 0L) DESC")
    List<br.edu.infnet.assessment.dto.RankingAventureiroDTO> gerarRankingAventureiros(
            @Param("dataInicio") java.time.OffsetDateTime dataInicio,
            @Param("dataFim") java.time.OffsetDateTime dataFim,
            @Param("statusMissao") br.edu.infnet.assessment.enums.StatusMissao statusMissao);
}