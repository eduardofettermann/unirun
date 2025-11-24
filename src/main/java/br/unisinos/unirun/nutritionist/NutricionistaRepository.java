package br.unisinos.unirun.nutritionist;

import br.unisinos.unirun.nutritionist.model.Nutricionista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NutricionistaRepository extends JpaRepository<Nutricionista, Long> {
    Optional<Nutricionista> findByNome(String nome);
    java.util.List<Nutricionista> findByAssessoriaCorrida(br.unisinos.unirun.runningConsulting.model.AssessoriaCorrida assessoriaCorrida);
}
