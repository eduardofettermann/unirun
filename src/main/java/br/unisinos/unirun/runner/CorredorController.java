package br.unisinos.unirun.runner;

import br.unisinos.unirun.meal.DietaService;
import br.unisinos.unirun.meal.model.Dieta;
import br.unisinos.unirun.runner.model.Corredor;
import br.unisinos.unirun.runner.model.CorredorDTO;
import br.unisinos.unirun.trainer.TreinadorService;
import br.unisinos.unirun.trainer.model.Treinador;
import br.unisinos.unirun.trainer.model.TreinadorDTO;
import br.unisinos.unirun.workout.TreinoService;
import br.unisinos.unirun.workout.model.Treino;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/runner")
public class CorredorController {
    private static final Logger logger = LoggerFactory.getLogger(CorredorController.class);
    private final CorredorService corredorService;
    private final TreinadorService treinadorService;
    private final TreinoService treinoService;
    private final DietaService dietaService;
    private final br.unisinos.unirun.nutritionist.NutricionistaService nutricionistaService;

    public CorredorController(CorredorService corredorService,
            TreinadorService treinadorService,
            TreinoService treinoService,
            DietaService dietaService,
            br.unisinos.unirun.nutritionist.NutricionistaService nutricionistaService) {
        this.corredorService = corredorService;
        this.treinadorService = treinadorService;
        this.treinoService = treinoService;
        this.dietaService = dietaService;
        this.nutricionistaService = nutricionistaService;
    }

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam Long runnerId, Model model) {
        Corredor corredor = corredorService.findById(runnerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Corredor not found"));
        Treinador treinador = null;
        br.unisinos.unirun.nutritionist.model.Nutricionista nutricionista = null;

        if (corredor.getAssessoriaCorrida() != null) {
            List<Treinador> treinadores = treinadorService.findByAssessoriaCorrida(corredor.getAssessoriaCorrida());
            if (!treinadores.isEmpty())
                treinador = treinadores.get(0);

            List<br.unisinos.unirun.nutritionist.model.Nutricionista> nutricionistas = nutricionistaService
                    .findByAssessoriaCorrida(corredor.getAssessoriaCorrida());
            if (!nutricionistas.isEmpty())
                nutricionista = nutricionistas.get(0);
        }

        // Manual DTO mapping
        CorredorDTO corredorDTO = new CorredorDTO(corredor.getId(), corredor.getNome(),
                corredor.getAssessoriaCorrida() != null ? corredor.getAssessoriaCorrida().getNome() : null,
                nutricionista != null ? nutricionista.getNome() : null);

        model.addAttribute("runner", corredorDTO);
        model.addAttribute("trainer",
                treinador != null ? new TreinadorDTO(treinador.getNome()) : new TreinadorDTO("-"));
        return "runner/dashboard";
    }

    @GetMapping("/workouts/done")
    public String done(@RequestParam Long runnerId, Model model) {
        Corredor corredor = corredorService.findById(runnerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Corredor not found"));
        List<Treino> treinos = treinoService.findWorkoutsForWeek(corredor, LocalDate.now()); // Need to update this
                                                                                             // method in service
        model.addAttribute("workouts", treinos);
        model.addAttribute("runner", new CorredorDTO(corredor.getId(), corredor.getNome(), null, null));
        return "runner/workout/done";
    }

    @GetMapping("/workouts/planned")
    public String planned(@RequestParam Long runnerId, Model model) {
        Corredor corredor = corredorService.findById(runnerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Corredor not found"));
        List<Treino> treinos = treinoService.findPlannedWorkoutsForWeek(corredor, LocalDate.now());
        model.addAttribute("workouts", treinos);
        model.addAttribute("runner", new CorredorDTO(corredor.getId(), corredor.getNome(), null, null));
        return "runner/workout/planned";
    }

    @GetMapping("/workouts/add")
    public String addForm(@RequestParam Long runnerId, Model model) {
        model.addAttribute("runnerId", runnerId);
        return "runner/workout/add";
    }

    @PostMapping("/workouts/add")
    public String createWorkout(@RequestParam Long runnerId,
            @RequestParam String date,
            @RequestParam String descricao) {
        logger.info("Creating workout for runner: {}, date: {}", runnerId, date);
        Corredor corredor = corredorService.findById(runnerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Corredor not found"));
        Treino treino = new Treino();
        treino.setCorredor(corredor);
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            java.util.Date parsedDate = sdf.parse(date);
            treino.setData(parsedDate);
            logger.info("Workout created successfully - Runner: {}, Date: {}, Description: {}", runnerId, parsedDate,
                    descricao);
        } catch (Exception e) {
            logger.error("Failed to parse date: {} for runner: {}", date, runnerId, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format");
        }
        treino.setDescricao(descricao);
        treino.setConcluido(true); // Done
        treinoService.save(treino);
        return "redirect:/runner/workouts/done?runnerId=" + corredor.getId();
    }

    @GetMapping("/meals/planned")
    public String mealsPlanned(@RequestParam Long runnerId, Model model) {
        Corredor corredor = corredorService.findById(runnerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Corredor not found"));
        List<Dieta> planned = dietaService.findPredefinedMealsForWeek(corredor, LocalDate.now());
        model.addAttribute("meals", planned);
        model.addAttribute("runner", new CorredorDTO(corredor.getId(), corredor.getNome(), null, null));
        return "runner/meals/planned";
    }

    @GetMapping("/meals/done")
    public String mealsDone(@RequestParam Long runnerId, Model model) {
        Corredor corredor = corredorService.findById(runnerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Corredor not found"));
        List<Dieta> done = dietaService.findMealsForWeek(corredor, LocalDate.now());
        model.addAttribute("meals", done);
        model.addAttribute("runner", new CorredorDTO(corredor.getId(), corredor.getNome(), null, null));
        return "runner/meals/done";
    }

    @GetMapping("/meals/add")
    public String mealAddForm(@RequestParam Long runnerId, Model model) {
        model.addAttribute("runnerId", runnerId);
        return "runner/meals/add";
    }

    @PostMapping("/meals/add")
    public String createMeal(@RequestParam Long runnerId,
            @RequestParam String descricao,
            @RequestParam int calorias,
            @RequestParam float proteinas,
            @RequestParam float carboidratos,
            @RequestParam float gorduras) {
        Corredor corredor = corredorService.findById(runnerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Corredor not found"));
        Dieta dieta = new Dieta();
        dieta.setCorredor(corredor);
        dieta.setDescricao(descricao);
        dieta.setCalorias(calorias);
        dieta.setProteinas(proteinas);
        dieta.setCarboidratos(carboidratos);
        dieta.setGorduras(gorduras);
        dietaService.save(dieta);
        return "redirect:/runner/meals/done?runnerId=" + corredor.getId();
    }
}
