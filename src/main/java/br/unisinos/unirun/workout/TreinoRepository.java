package br.unisinos.unirun.workout;

import br.unisinos.unirun.workout.model.Treino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TreinoRepository extends JpaRepository<Treino, Long> {
    List<Treino> findByCorredorIdAndConcluidoAndDataBetween(Long corredorId, boolean concluido, Date start, Date end);
}
