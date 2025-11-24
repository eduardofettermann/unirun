package br.unisinos.unirun.workout;

import br.unisinos.unirun.runner.model.Corredor;
import br.unisinos.unirun.workout.model.Treino;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;

@Service
public class TreinoService {
    private final TreinoRepository treinoRepository;

    public TreinoService(TreinoRepository treinoRepository) {
        this.treinoRepository = treinoRepository;
    }

    public Treino save(Treino treino) {
        return treinoRepository.save(treino);
    }

    public List<Treino> findWorkoutsForWeek(Corredor corredor, LocalDate date) {
        return findByConcluidoForWeek(corredor, date, true);
    }

    public List<Treino> findPlannedWorkoutsForWeek(Corredor corredor, LocalDate date) {
        return findByConcluidoForWeek(corredor, date, false);
    }

    private List<Treino> findByConcluidoForWeek(Corredor corredor, LocalDate date, boolean concluido) {
        LocalDate start = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate end = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        
        Date startDate = Date.from(start.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant());

        if (corredor == null || corredor.getId() == null) {
            return List.of();
        }
        return treinoRepository.findByCorredorIdAndConcluidoAndDataBetween(corredor.getId(), concluido, startDate, endDate);
    }
}
