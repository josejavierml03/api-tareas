package es.maxih.tareas.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record TaskDraft(
    @NotBlank(message = "El titulo es obligatorio")
        @Size(max = 120, message = "El titulo admite 120 caracteres como maximo")
        String title,
    @Size(max = 500, message = "La descripcion admite 500 caracteres como maximo")
        String description,
    @NotNull(message = "El estado es obligatorio") TaskStatus status,
    @Min(value = 1, message = "La prioridad debe estar entre 1 y 5")
        @Max(value = 5, message = "La prioridad debe estar entre 1 y 5")
        int priority,
    @NotNull(message = "La fecha limite es obligatoria") LocalDate dueDate) {}
