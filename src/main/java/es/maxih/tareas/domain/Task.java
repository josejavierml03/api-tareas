package es.maxih.tareas.domain;

import java.time.LocalDate;
import java.util.UUID;

public record Task(
    UUID id,
    String title,
    String description,
    TaskStatus status,
    int priority,
    LocalDate dueDate) {}
