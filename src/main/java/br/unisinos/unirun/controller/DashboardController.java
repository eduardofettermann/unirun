package br.unisinos.unirun.controller;

import br.unisinos.unirun.nutritionist.NutricionistaRepository;
import br.unisinos.unirun.runner.CorredorRepository;
import br.unisinos.unirun.trainer.TreinadorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DashboardController {
    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);
    private final CorredorRepository corredorRepository;
    private final TreinadorRepository treinadorRepository;
    private final NutricionistaRepository nutricionistaRepository;
    private final br.unisinos.unirun.runningConsulting.AssessoriaCorridaRepository assessoriaRepository;

    public DashboardController(CorredorRepository corredorRepository,
            TreinadorRepository treinadorRepository,
            NutricionistaRepository nutricionistaRepository,
            br.unisinos.unirun.runningConsulting.AssessoriaCorridaRepository assessoriaRepository) {
        this.corredorRepository = corredorRepository;
        this.treinadorRepository = treinadorRepository;
        this.nutricionistaRepository = nutricionistaRepository;
        this.assessoriaRepository = assessoriaRepository;
    }

    public record UserView(String type, Long id, String name) {
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        List<UserView> users = new ArrayList<>();
        corredorRepository.findAll().forEach(c -> users.add(new UserView("RUNNER", c.getId(), c.getNome())));
        treinadorRepository.findAll().forEach(t -> users.add(new UserView("TRAINER", t.getId(), t.getNome())));
        nutricionistaRepository.findAll().forEach(n -> users.add(new UserView("NUTRITIONIST", n.getId(), n.getNome())));
        model.addAttribute("users", users);
        model.addAttribute("assessorias", assessoriaRepository.findAll());
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam Long userId, @RequestParam String userType, HttpSession session) {
        logger.info("User login attempt - Type: {}, ID: {}", userType, userId);
        session.setAttribute("loggedUserId", userId);

        String redirectUrl = switch (userType) {
            case "RUNNER" -> "redirect:/runner/dashboard?runnerId=" + userId;
            case "TRAINER" -> "redirect:/trainer/dashboard?trainerId=" + userId;
            case "NUTRITIONIST" -> "redirect:/nutritionist/dashboard?nutritionistId=" + userId;
            default -> {
                logger.warn("Invalid user type provided: {}", userType);
                yield "redirect:/login";
            }
        };
        logger.info("User login successful - Type: {}, ID: {}", userType, userId);
        return redirectUrl;
    }

    @PostMapping("/assessorias/create")
    public String createAssessoria(@RequestParam String name) {
        logger.info("Creating new assessoria: {}", name);
        var assessoria = new br.unisinos.unirun.runningConsulting.model.AssessoriaCorrida();
        assessoria.setNome(name);
        assessoriaRepository.save(assessoria);
        logger.info("Assessoria created successfully: {} (ID: {})", name, assessoria.getId());
        return "redirect:/login";
    }

    @PostMapping("/users/create")
    public String createUser(@RequestParam String name, @RequestParam String type, @RequestParam Long assessoriaId) {
        var assessoria = assessoriaRepository.findById(assessoriaId)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "Assessoria not found"));

        switch (type) {
            case "RUNNER" -> {
                var c = new br.unisinos.unirun.runner.model.Corredor();
                c.setNome(name);
                c.setAssessoriaCorrida(assessoria);
                corredorRepository.save(c);
            }
            case "TRAINER" -> {
                var t = new br.unisinos.unirun.trainer.model.Treinador();
                t.setNome(name);
                t.setAssessoriaCorrida(assessoria);
                treinadorRepository.save(t);
            }
            case "NUTRITIONIST" -> {
                var n = new br.unisinos.unirun.nutritionist.model.Nutricionista();
                n.setNome(name);
                n.setAssessoriaCorrida(assessoria);
                nutricionistaRepository.save(n);
            }
        }
        return "redirect:/login";
    }
}
