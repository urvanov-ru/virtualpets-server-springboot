package ru.urvanov.virtualpets.server.controller.api.domain;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Книги питомца в книжном шкафу.")
public record GetPetBooksResult(List<Book> books) {};
