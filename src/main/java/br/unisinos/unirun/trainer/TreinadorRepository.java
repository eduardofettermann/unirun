package br.unisinos.unirun.trainer;

import br.unisinos.unirun.trainer.model.Treinador;
import br.unisinos.unirun.runningConsulting.model.AssessoriaCorrida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TreinadorRepository extends JpaRepository<Treinador, Long> {
    Optional<Treinador> findByNome(String nome);
    List<Treinador> findByAssessoriaCorrida(AssessoriaCorrida assessoriaCorrida);
}
