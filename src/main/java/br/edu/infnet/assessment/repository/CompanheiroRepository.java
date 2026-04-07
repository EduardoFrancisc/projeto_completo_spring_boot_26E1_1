package br.edu.infnet.assessment.repository;

import br.edu.infnet.assessment.model.Companheiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanheiroRepository extends JpaRepository<Companheiro, Long> {
}