package br.unisinos.unirun.runner.dto;

import java.util.List;

public record RunnerWeeklyWorkoutDTO(List<RunnerDailyWorkoutDTO> workouts) {
}

