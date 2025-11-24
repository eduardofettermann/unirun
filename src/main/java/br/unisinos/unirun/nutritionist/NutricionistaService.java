package br.unisinos.unirun.nutritionist;

import br.unisinos.unirun.meal.model.Dieta;
import br.unisinos.unirun.nutritionist.dto.DailyMealDTO;
import br.unisinos.unirun.nutritionist.dto.WeeklyMealDTO;
import br.unisinos.unirun.nutritionist.model.Nutricionista;
import br.unisinos.unirun.runner.CorredorRepository;
import br.unisinos.unirun.runner.model.Corredor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class NutricionistaService {
    private final NutricionistaRepository nutricionistaRepository;
    private final CorredorRepository corredorRepository;

    public NutricionistaService(NutricionistaRepository nutricionistaRepository, CorredorRepository corredorRepository) {
        this.nutricionistaRepository = nutricionistaRepository;
        this.corredorRepository = corredorRepository;
    }

    public Nutricionista save(Nutricionista nutricionista) {
        return nutricionistaRepository.save(nutricionista);
    }

    public Optional<Nutricionista> findByNome(String nome) {
        return nutricionistaRepository.findByNome(nome);
    }

    public Optional<Nutricionista> findById(Long id) {
        return nutricionistaRepository.findById(id);
    }

    public List<Nutricionista> findByAssessoriaCorrida(br.unisinos.unirun.runningConsulting.model.AssessoriaCorrida assessoriaCorrida) {
        return nutricionistaRepository.findByAssessoriaCorrida(assessoriaCorrida);
    }

    public List<Corredor> findStudents(Nutricionista nutricionista) {
        if (nutricionista == null || nutricionista.getAssessoriaCorrida() == null) {
            return List.of();
        }
        return corredorRepository.findByAssessoriaCorrida(nutricionista.getAssessoriaCorrida());
    }

    public WeeklyMealDTO getWeekMeals(List<Dieta> dietas) {
        LocalDate startOfWeek = getStartOfWeek();
        List<DailyMealDTO> dailyMeals = IntStream.range(0, 7)
                .mapToObj(i -> processDay(startOfWeek.plusDays(i), dietas))
                .collect(Collectors.toList());
        return new WeeklyMealDTO(dailyMeals);
    }

    private LocalDate getStartOfWeek() {
        return LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    private DailyMealDTO processDay(LocalDate currentDay, List<Dieta> dietas) {
        Optional<Dieta> mealForDay = findMealForDay(currentDay, dietas);
        String dayName = getDayDisplayName(currentDay);
        String description = mealForDay.map(Dieta::getDescricao).orElse("Descanso");
        boolean completed = mealForDay.map(Dieta::isConcluido).orElse(false);
        return new DailyMealDTO(dayName, description, completed);
    }

    private Optional<Dieta> findMealForDay(LocalDate currentDay, List<Dieta> dietas) {
        return dietas.stream()
                .filter(dieta -> dieta.getData() != null && new Date(dieta.getData().getTime()).toLocalDate().equals(currentDay))
                .findFirst();
    }

    private String getDayDisplayName(LocalDate date) {
        return date.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("pt", "BR"))
                + " (" + date.getDayOfMonth() + "/" + date.getMonthValue() + ")";
    }
}
