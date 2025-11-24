package br.unisinos.unirun.trainer;

import br.unisinos.unirun.runner.CorredorService;
import br.unisinos.unirun.runner.model.Corredor;
import br.unisinos.unirun.runner.model.CorredorDTO;
import br.unisinos.unirun.trainer.model.Treinador;
import br.unisinos.unirun.trainer.model.TreinadorDTO;
import br.unisinos.unirun.workout.TreinoService;
import br.unisinos.unirun.workout.model.Treino;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import br.unisinos.unirun.trainer.dto.WeeklyWorkoutDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/trainer")
public class TreinadorController {
    private final TreinadorService treinadorService;
    private final CorredorService corredorService;
    private final TreinoService treinoService;

    public TreinadorController(TreinadorService treinadorService,
            CorredorService corredorService,
            TreinoService treinoService) {
        this.treinadorService = treinadorService;
        this.corredorService = corredorService;
        this.treinoService = treinoService;
    }

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam Long trainerId, Model model) {
        Treinador treinador = treinadorService.findById(trainerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Treinador not found"));
        List<Corredor> students = treinadorService.findStudents(treinador);

        model.addAttribute("trainerId", trainerId);
        model.addAttribute("trainer", new TreinadorDTO(treinador.getNome()));
        model.addAttribute("students",
                students.stream()
                        .map(s -> new CorredorDTO(s.getId(), s.getNome(),
                                s.getAssessoriaCorrida() != null ? s.getAssessoriaCorrida().getNome() : null, null))
                        .toList());
        return "trainer/dashboard";
    }

    @GetMapping("{trainerId}/student/{runnerId}/workouts")
    public String studentWorkouts(@PathVariable Long trainerId, @PathVariable Long runnerId, Model model) {
        Corredor corredor = corredorService.findById(runnerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Corredor not found"));
        List<Treino> treinos = treinoService.findWorkoutsForWeek(corredor, LocalDate.now());

        WeeklyWorkoutDTO weekWorkouts = treinadorService.getWeekWorkouts(treinos);

        model.addAttribute("trainerId", trainerId);
        model.addAttribute("runnerName", corredor.getNome());
        model.addAttribute("runnerId", corredor.getId());
        model.addAttribute("weekWorkouts", weekWorkouts.workouts());
        return "trainer/student_workouts";
    }

    @GetMapping("/student/{runnerId}/plan/add")
    public String studentPlanAddForm(@PathVariable Long runnerId, Model model) {
        model.addAttribute("runnerId", runnerId);
        return "trainer/plan_add";
    }

    @PostMapping("/student/{runnerId}/workout-plan/add")
    public String createStudentPlan(@PathVariable Long runnerId,
            @RequestParam String date,
            @RequestParam String descricao,
            HttpSession session) throws ParseException {

        Corredor corredor = corredorService.findById(runnerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Corredor not found"));

        Long trainerId = (Long) session.getAttribute("loggedUserId");
        String userType = (String) session.getAttribute("userType");
        if (!"TRAINER".equals(userType)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuario nao e um treinador");
        }
        Treinador treinador = treinadorService.findById(trainerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Treinador not found"));

        Treino treino = new Treino();
        treino.setCorredor(corredor);
        treino.setTreinador(treinador);

        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        treino.setData(sdf.parse(date));

        treino.setDescricao(descricao);
        treino.setConcluido(false);

        treinoService.save(treino);
        return "redirect:/trainer/" + trainerId + "/student/" + corredor.getId() + "/workouts";
    }
}
