package ru.urvanov.virtualpets.server.controller.api.domain;

import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Техническая информация о сервере.")
public record ServerTechnicalInfo(Map<String, String> info) {
};
