package br.unisinos.unirun.runner;

import br.unisinos.unirun.runner.model.Corredor;
import br.unisinos.unirun.runningConsulting.model.AssessoriaCorrida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CorredorRepository extends JpaRepository<Corredor, Long> {
    Optional<Corredor> findByNome(String nome);
    List<Corredor> findByAssessoriaCorrida(AssessoriaCorrida assessoriaCorrida);
    // Corredor does not have direct nutritionist field anymore, relationship is via Dieta or Assessoria.
    // But wait, the diagram says Corredor "1" --> "0..*" Dieta and Nutricionista "1" --> "0..*" Dieta.
    // It does NOT show a direct link between Corredor and Nutricionista except via AssessoriaCorrida?
    // Actually, Corredor belongs to AssessoriaCorrida, and Nutricionista belongs to AssessoriaCorrida.
    // So findByNutritionist might not be directly applicable unless we join tables.
    // However, the previous code had it.
    // I will remove findByNutritionist for now as it's not in the entity.
}
