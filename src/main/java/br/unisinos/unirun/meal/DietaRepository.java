package br.unisinos.unirun.meal;

import br.unisinos.unirun.meal.model.Dieta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DietaRepository extends JpaRepository<Dieta, Long> {
    List<Dieta> findByCorredorIdAndDataBetween(Long corredorId, Date start, Date end);
    List<Dieta> findByCorredorIdAndNutricionistaIsNotNullAndDataBetween(Long corredorId, Date start, Date end);
    List<Dieta> findByCorredorIdAndNutricionistaIsNullAndDataBetween(Long corredorId, Date start, Date end);
}
