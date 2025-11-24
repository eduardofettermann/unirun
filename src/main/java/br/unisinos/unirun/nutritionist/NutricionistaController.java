package br.unisinos.unirun.nutritionist;

import br.unisinos.unirun.meal.DietaService;
import br.unisinos.unirun.meal.model.Dieta;
import br.unisinos.unirun.nutritionist.dto.WeeklyMealDTO;
import br.unisinos.unirun.nutritionist.model.Nutricionista;
import br.unisinos.unirun.nutritionist.model.NutricionistaDTO;
import br.unisinos.unirun.runner.CorredorService;
import br.unisinos.unirun.runner.model.Corredor;
import br.unisinos.unirun.runner.model.CorredorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/nutritionist")
public class NutricionistaController {
    private final NutricionistaService nutricionistaService;
    private final CorredorService corredorService;
    private final DietaService dietaService;

    public NutricionistaController(NutricionistaService nutricionistaService, CorredorService corredorService, DietaService dietaService) {
        this.nutricionistaService = nutricionistaService;
        this.corredorService = corredorService;
        this.dietaService = dietaService;
    }

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam Long nutritionistId, Model model) {
        Nutricionista nutricionista = nutricionistaService.findById(nutritionistId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nutricionista not found"));
        List<Corredor> students = nutricionistaService.findStudents(nutricionista);
        model.addAttribute("nutritionist", new NutricionistaDTO(nutricionista.getNome()));
        model.addAttribute("students", students.stream().map(s -> new CorredorDTO(s.getId(), s.getNome(), s.getAssessoriaCorrida() != null ? s.getAssessoriaCorrida().getNome() : null, null)).toList());
        return "nutritionist/dashboard";
    }
    @GetMapping("/student/{runnerId}/meals")
    public String studentMeals(@PathVariable Long runnerId, Model model) {
        Corredor corredor = corredorService.findById(runnerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Corredor not found"));
        List<Dieta> meals = dietaService.findAllForWeek(corredor, LocalDate.now());
        WeeklyMealDTO weekMeals = nutricionistaService.getWeekMeals(meals);
        model.addAttribute("runnerName", corredor.getNome());
        model.addAttribute("runnerId", corredor.getId());
        model.addAttribute("weekMeals", weekMeals.meals());
        return "nutritionist/student_meals";
    }
    @GetMapping("/plan/add")
    public String planAddForm(@RequestParam Long runnerId, Model model) {
        model.addAttribute("runnerId", runnerId);
        return "nutritionist/plan_add";
    }

    @PostMapping("/plan/add")
    public String createPlan(@RequestParam Long runnerId,
                             @RequestParam String date,
                             @RequestParam String descricao,
                             @RequestParam Long nutricionistaId) { // Need nutritionistId to link
        Corredor corredor = corredorService.findById(runnerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Corredor not found"));
        Nutricionista nutricionista = nutricionistaService.findById(nutricionistaId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nutricionista not found"));

        Dieta dieta = new Dieta();
        dieta.setCorredor(corredor);
        dieta.setNutricionista(nutricionista); // Linked to nutritionist -> Predefined/Proposed
        try {
             java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
             dieta.setData(sdf.parse(date));
        } catch (Exception e) {
            // handle
        }
        dieta.setDescricao(descricao);
        dieta.setConcluido(false);

        dietaService.save(dieta);
        return "redirect:/nutritionist/student/" + corredor.getId() + "/meals";
    }
}
