package br.unisinos.unirun.nutritionist.dto;

import java.util.List;

public record WeeklyMealDTO(List<DailyMealDTO> meals) {
}

