package br.unisinos.unirun.trainer;

import br.unisinos.unirun.runner.CorredorService;
import br.unisinos.unirun.runner.model.Corredor;
import br.unisinos.unirun.trainer.model.Treinador;
import br.unisinos.unirun.workout.TreinoService;
import br.unisinos.unirun.workout.model.Treino;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

        model.addAttribute("trainer", new br.unisinos.unirun.trainer.model.TreinadorDTO(treinador.getNome()));
        model.addAttribute("students",
                students.stream()
                        .map(s -> new br.unisinos.unirun.runner.model.CorredorDTO(s.getId(), s.getNome(),
                                s.getAssessoriaCorrida() != null ? s.getAssessoriaCorrida().getNome() : null, null))
                        .toList());
        return "trainer/dashboard";
    }

    @GetMapping("/student/{runnerId}/workouts")
    public String studentWorkouts(@PathVariable Long runnerId, Model model) {
        Corredor corredor = corredorService.findById(runnerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Corredor not found"));
        List<Treino> treinos = treinoService.findWorkoutsForWeek(corredor, LocalDate.now());

        LocalDate startOfWeek = LocalDate.now()
                .with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        java.util.Map<String, java.util.Map<String, Object>> weekWorkouts = new java.util.LinkedHashMap<>();
        String[] days = { "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado", "Domingo" };

        for (int i = 0; i < 7; i++) {
            LocalDate currentDay = startOfWeek.plusDays(i);
            String dayName = days[i];
            String workoutDesc = "Descanso";
            boolean isConcluido = false;

            for (Treino treino : treinos) {
                if (treino.getData() != null) {
                    LocalDate treinoDate = new java.sql.Date(treino.getData().getTime()).toLocalDate();
                    if (treinoDate.equals(currentDay)) {
                        workoutDesc = treino.getDescricao();
                        isConcluido = treino.isConcluido();
                        break;
                    }
                }
            }

            java.util.Map<String, Object> workoutInfo = new java.util.HashMap<>();
            workoutInfo.put("descricao", workoutDesc);
            workoutInfo.put("concluido", isConcluido);

            weekWorkouts.put(dayName + " (" + currentDay.getDayOfMonth() + "/" + currentDay.getMonthValue() + ")",
                    workoutInfo);
        }

        model.addAttribute("runnerName", corredor.getNome());
        model.addAttribute("runnerId", corredor.getId());
        model.addAttribute("workouts", treinos);
        model.addAttribute("weekWorkouts", weekWorkouts);
        return "trainer/student_workouts";
    }

    @GetMapping("/student/{runnerId}/plan/add")
    public String studentPlanAddForm(@PathVariable Long runnerId, Model model) {
        model.addAttribute("runnerId", runnerId);
        return "trainer/plan_add";
    }

    @PostMapping("/student/{runnerId}/plan/add")
    public String createStudentPlan(@PathVariable Long runnerId,
            @RequestParam String date,
            @RequestParam String descricao,
            jakarta.servlet.http.HttpSession session) {

        Corredor corredor = corredorService.findById(runnerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Corredor not found"));

        Long trainerId = (Long) session.getAttribute("loggedUserId");
        Treinador treinador = treinadorService.findById(trainerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Treinador not found"));

        Treino treino = new Treino();
        treino.setCorredor(corredor);
        treino.setTreinador(treinador);

        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            treino.setData(sdf.parse(date));
        } catch (Exception e) {
            // handle error
        }

        treino.setDescricao(descricao);
        treino.setConcluido(false);

        treinoService.save(treino);
        return "redirect:/trainer/student/" + corredor.getId() + "/workouts";
    }
}
