package es.maxih.tareas.service;

import java.util.UUID;

public class TaskNotFoundException extends RuntimeException {

  public TaskNotFoundException(UUID id) {
    super("No existe la tarea con id " + id);
  }
}
