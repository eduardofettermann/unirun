package br.unisinos.unirun.runner;

import br.unisinos.unirun.runner.dto.RunnerDailyWorkoutDTO;
import br.unisinos.unirun.runner.dto.RunnerWeeklyWorkoutDTO;
import br.unisinos.unirun.runner.dto.RunnerDailyMealDTO;
import br.unisinos.unirun.runner.dto.RunnerWeeklyMealDTO;
import br.unisinos.unirun.runner.model.Corredor;
import br.unisinos.unirun.workout.model.Treino;
import br.unisinos.unirun.meal.model.Dieta;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CorredorService {
    private final CorredorRepository corredorRepository;

    public CorredorService(CorredorRepository corredorRepository) {
        this.corredorRepository = corredorRepository;
    }

    public Optional<Corredor> findById(Long id) {
        return corredorRepository.findById(id);
    }

    public RunnerWeeklyWorkoutDTO getWeekWorkouts(List<Treino> treinos) {
        LocalDate startOfWeek = getStartOfWeek();
        List<RunnerDailyWorkoutDTO> dailyWorkouts = IntStream.range(0, 7)
                .mapToObj(i -> processDay(startOfWeek.plusDays(i), treinos))
                .collect(Collectors.toList());
        return new RunnerWeeklyWorkoutDTO(dailyWorkouts);
    }

    private LocalDate getStartOfWeek() {
        return LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    private RunnerDailyWorkoutDTO processDay(LocalDate currentDay, List<Treino> treinos) {
        Optional<Treino> workoutForDay = findWorkoutForDay(currentDay, treinos);
        String dayName = getDayDisplayName(currentDay);
        Long id = workoutForDay.map(Treino::getId).orElse(null);
        String description = workoutForDay.map(Treino::getDescricao).orElse("Descanso");
        boolean completed = workoutForDay.map(Treino::isConcluido).orElse(false);
        return new RunnerDailyWorkoutDTO(id, dayName, description, completed);
    }

    private Optional<Treino> findWorkoutForDay(LocalDate currentDay, List<Treino> treinos) {
        return treinos.stream()
                .filter(treino -> treino.getData() != null && new Date(treino.getData().getTime()).toLocalDate().equals(currentDay))
                .findFirst();
    }

    private String getDayDisplayName(LocalDate date) {
        return date.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("pt", "BR"))
                + " (" + date.getDayOfMonth() + "/" + date.getMonthValue() + ")";
    }

    public RunnerWeeklyMealDTO getWeekMeals(List<Dieta> dietas) {
        LocalDate startOfWeek = getStartOfWeek();
        List<RunnerDailyMealDTO> dailyMeals = IntStream.range(0, 7)
                .mapToObj(i -> processMealDay(startOfWeek.plusDays(i), dietas))
                .collect(Collectors.toList());
        return new RunnerWeeklyMealDTO(dailyMeals);
    }

    private RunnerDailyMealDTO processMealDay(LocalDate currentDay, List<Dieta> dietas) {
        Optional<Dieta> mealForDay = findMealForDay(currentDay, dietas);
        String dayName = getDayDisplayName(currentDay);
        Long id = mealForDay.map(Dieta::getId).orElse(null);
        String description = mealForDay.map(Dieta::getDescricao).orElse("Descanso");
        boolean completed = mealForDay.map(Dieta::isConcluido).orElse(false);
        return new RunnerDailyMealDTO(id, dayName, description, completed);
    }

    private Optional<Dieta> findMealForDay(LocalDate currentDay, List<Dieta> dietas) {
        return dietas.stream()
                .filter(dieta -> dieta.getData() != null && new Date(dieta.getData().getTime()).toLocalDate().equals(currentDay))
                .findFirst();
    }
}
