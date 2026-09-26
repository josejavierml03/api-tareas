package es.maxih.tareas.repository;

import es.maxih.tareas.domain.Task;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository {

  List<Task> findAll();

  Optional<Task> findById(UUID id);

  Task save(Task task);

  boolean deleteById(UUID id);
}
