package br.unisinos.unirun.trainer;

import br.unisinos.unirun.runner.CorredorRepository;
import br.unisinos.unirun.runner.model.Corredor;
import br.unisinos.unirun.runningConsulting.model.AssessoriaCorrida;
import br.unisinos.unirun.trainer.dto.DailyWorkoutDTO;
import br.unisinos.unirun.trainer.dto.WeeklyWorkoutDTO;
import br.unisinos.unirun.trainer.model.Treinador;
import br.unisinos.unirun.workout.model.Treino;
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
public class TreinadorService {
    private final TreinadorRepository treinadorRepository;
    private final CorredorRepository corredorRepository;

    public TreinadorService(TreinadorRepository treinadorRepository, CorredorRepository corredorRepository) {
        this.treinadorRepository = treinadorRepository;
        this.corredorRepository = corredorRepository;
    }

    public Optional<Treinador> findById(Long id) {
        return treinadorRepository.findById(id);
    }

    public List<Treinador> findByAssessoriaCorrida(AssessoriaCorrida assessoriaCorrida) {
        return treinadorRepository.findByAssessoriaCorrida(assessoriaCorrida);
    }

    public List<Corredor> findStudents(Treinador treinador) {
        if (treinador == null || treinador.getAssessoriaCorrida() == null) {
            return List.of();
        }
        return corredorRepository.findByAssessoriaCorrida(treinador.getAssessoriaCorrida());
    }

    public WeeklyWorkoutDTO getWeekWorkouts(List<Treino> treinos) {
        LocalDate startOfWeek = getStartOfWeek();
        List<DailyWorkoutDTO> dailyWorkouts = IntStream.range(0, 7).mapToObj(i -> processDay(startOfWeek.plusDays(i), treinos)).collect(Collectors.toList());
        return new WeeklyWorkoutDTO(dailyWorkouts);
    }

    private LocalDate getStartOfWeek() {
        return LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    private DailyWorkoutDTO processDay(LocalDate currentDay, List<Treino> treinos) {
        Optional<Treino> workoutForDay = findWorkoutForDay(currentDay, treinos);
        String dayName = getDayDisplayName(currentDay);
        String description = workoutForDay.map(Treino::getDescricao).orElse("Descanso");
        boolean completed = workoutForDay.map(Treino::isConcluido).orElse(false);

        return new DailyWorkoutDTO(dayName, description, completed);
    }

    private Optional<Treino> findWorkoutForDay(LocalDate currentDay, List<Treino> treinos) {
        return treinos.stream().filter(treino -> treino.getData() != null && new Date(treino.getData().getTime()).toLocalDate().equals(currentDay)).findFirst();
    }

    private String getDayDisplayName(LocalDate date) {
        return date.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("pt", "BR")) + " (" + date.getDayOfMonth() + "/" + date.getMonthValue() + ")";
    }
}
