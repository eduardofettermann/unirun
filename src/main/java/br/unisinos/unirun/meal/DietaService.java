package br.unisinos.unirun.meal;

import br.unisinos.unirun.meal.model.Dieta;
import br.unisinos.unirun.runner.model.Corredor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;

@Service
public class DietaService {
    private final DietaRepository dietaRepository;

    public DietaService(DietaRepository dietaRepository) {
        this.dietaRepository = dietaRepository;
    }

    public Dieta save(Dieta dieta) {
        return dietaRepository.save(dieta);
    }

    public List<Dieta> findMealsForWeek(Corredor corredor, LocalDate date) {
        // Meals done/logged by runner (no nutritionist)
        return findByNutricionistaForWeek(corredor, date, false);
    }

    public List<Dieta> findPredefinedMealsForWeek(Corredor corredor, LocalDate date) {
        // Meals proposed by nutritionist
        return findByNutricionistaForWeek(corredor, date, true);
    }

    private List<Dieta> findByNutricionistaForWeek(Corredor corredor, LocalDate date, boolean hasNutricionista) {
        LocalDate start = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate end = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        
        Date startDate = Date.from(start.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant());

        if (corredor == null || corredor.getId() == null) {
            return List.of();
        }
        
        if (hasNutricionista) {
            return dietaRepository.findByCorredorIdAndNutricionistaIsNotNullAndDataBetween(corredor.getId(), startDate, endDate);
        } else {
            return dietaRepository.findByCorredorIdAndNutricionistaIsNullAndDataBetween(corredor.getId(), startDate, endDate);
        }
    }
}
