package br.edu.infnet.assessment.repository;

import br.edu.infnet.assessment.model.PainelTaticoMissao;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface PainelTaticoMissaoRepository extends JpaRepository<PainelTaticoMissao, Long> {

    List<PainelTaticoMissao> findByUltimaAtualizacaoGreaterThanEqualOrderByIndiceProntidaoDesc(
            OffsetDateTime dataCorte,
            Pageable pageable
    );
}