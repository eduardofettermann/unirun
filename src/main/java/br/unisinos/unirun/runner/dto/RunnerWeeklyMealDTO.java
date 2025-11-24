package br.unisinos.unirun.runner.dto;

import java.util.List;

public record RunnerWeeklyMealDTO(List<RunnerDailyMealDTO> meals) {
}

