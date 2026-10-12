package es.maxih.tareas.repository;

import es.maxih.tareas.domain.Task;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class JpaTaskRepository implements TaskRepository {
  private final SpringDataTaskRepository repository;

  public JpaTaskRepository(SpringDataTaskRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<Task> findAll() {
    return repository.findAll().stream().map(TaskEntity::toTask).toList();
  }

  @Override
  public Optional<Task> findById(UUID id) {
    return repository.findById(id).map(TaskEntity::toTask);
  }

  @Override
  @Transactional
  public Task save(Task task) {
    return repository.save(new TaskEntity(task)).toTask();
  }

  @Override
  @Transactional
  public boolean deleteById(UUID id) {
    Optional<TaskEntity> task = repository.findById(id);
    if (task.isEmpty()) {
      return false;
    }
    repository.delete(task.get());
    return true;
  }
}
