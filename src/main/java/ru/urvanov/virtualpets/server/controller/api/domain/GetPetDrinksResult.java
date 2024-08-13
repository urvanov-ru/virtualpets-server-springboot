package ru.urvanov.virtualpets.server.controller.api.domain;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Напитки из машины с напитками.")
public record GetPetDrinksResult(List<Drink> drinks) {};
