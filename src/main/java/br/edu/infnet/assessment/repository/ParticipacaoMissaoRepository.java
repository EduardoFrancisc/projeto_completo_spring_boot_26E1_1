package br.edu.infnet.assessment.repository;

import br.edu.infnet.assessment.model.ParticipacaoMissao;
import br.edu.infnet.assessment.model.ParticipacaoMissaoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional; // <-- Não esqueça de importar o Optional!

@Repository
public interface ParticipacaoMissaoRepository extends JpaRepository<ParticipacaoMissao, ParticipacaoMissaoId> {
    List<ParticipacaoMissao> findByMissaoId(Long missaoId);

    Long countByAventureiroId(Long aventureiroId);

    Optional<ParticipacaoMissao> findTopByAventureiroIdOrderByDataRegistroDesc(Long aventureiroId);
}