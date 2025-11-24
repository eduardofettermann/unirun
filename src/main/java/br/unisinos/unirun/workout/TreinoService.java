package br.unisinos.unirun.workout;

import br.unisinos.unirun.runner.model.Corredor;
import br.unisinos.unirun.workout.model.Treino;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

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
        return findAllForWeek(corredor, date);
    }

    private List<Treino> findAllForWeek(Corredor corredor, LocalDate date) {
        LocalDate start = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate end = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        Date startDate = Date.from(start.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant());

        if (corredor == null || corredor.getId() == null) {
            return List.of();
        }
        return treinoRepository.findByCorredorIdAndDataBetween(corredor.getId(), startDate, endDate);
    }

    public void markAsCompleted(Long treinoId) {
        Treino treino = treinoRepository.findById(treinoId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Treino not found"));
        treino.setConcluido(true);
        treinoRepository.save(treino);
    }
}
