package es.maxih.tareas.repository;

import es.maxih.tareas.domain.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryTaskRepository implements TaskRepository {

  private final ConcurrentMap<UUID, Task> tasks = new ConcurrentHashMap<>();

  @Override
  public List<Task> findAll() {
    return new ArrayList<>(tasks.values());
  }

  @Override
  public Optional<Task> findById(UUID id) {
    return Optional.ofNullable(tasks.get(id));
  }

  @Override
  public Task save(Task task) {
    tasks.put(task.id(), task);
    return task;
  }

  @Override
  public boolean deleteById(UUID id) {
    return tasks.remove(id) != null;
  }
}
