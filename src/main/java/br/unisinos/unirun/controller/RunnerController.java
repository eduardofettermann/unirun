package br.unisinos.unirun.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/runner")
public class RunnerController {
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        return "runner/dashboard";
    }

    @GetMapping("/workouts/done")
    public String done(Model model) {
        return "runner/workout/done";
    }

    @GetMapping("/workouts/planned")
    public String planned(Model model) {
        return "runner/workout/planned";
    }

    @GetMapping("/workouts/add")
    public String add(Model model) {
        return "runner/workout/add";
    }
}
