package br.unisinos.unirun.trainer.dto;

import java.util.List;

public record WeeklyWorkoutDTO(List<DailyWorkoutDTO> workouts) {
}

