package br.unisinos.unirun.runningConsulting;

import br.unisinos.unirun.runningConsulting.model.AssessoriaCorrida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssessoriaCorridaRepository extends JpaRepository<AssessoriaCorrida, Long> {
    Optional<AssessoriaCorrida> findByNome(String nome);
}
