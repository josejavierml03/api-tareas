package es.maxih.tareas.service;

import es.maxih.tareas.domain.Task;
import es.maxih.tareas.domain.TaskDraft;
import es.maxih.tareas.domain.TaskStatus;
import es.maxih.tareas.repository.TaskRepository;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

  private final TaskRepository repository;

  public TaskService(TaskRepository repository) {
    this.repository = repository;
  }

  public List<Task> findAll(TaskStatus status, String query) {
    String needle = normalize(query);
    return repository.findAll().stream()
        .filter(task -> status == null || task.status() == status)
        .filter(task -> matches(task, needle))
        .sorted(Comparator.comparingInt(Task::priority).reversed().thenComparing(Task::dueDate))
        .toList();
  }

  public Task findById(UUID id) {
    return repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
  }

  public Task create(TaskDraft draft) {
    validate(draft);
    return repository.save(toTask(UUID.randomUUID(), draft));
  }

  public Task update(UUID id, TaskDraft draft) {
    Task current = findById(id);
    validate(draft);
    if (current.status() == TaskStatus.COMPLETED && draft.status() != TaskStatus.COMPLETED) {
      throw new TaskValidationException("Una tarea completada no puede volver a abrirse");
    }
    return repository.save(toTask(id, draft));
  }

  public void delete(UUID id) {
    if (!repository.deleteById(id)) {
      throw new TaskNotFoundException(id);
    }
  }

  private String normalize(String query) {
    return query == null ? "" : query.trim().toLowerCase(Locale.ROOT);
  }

  private boolean matches(Task task, String needle) {
    if (needle.isEmpty()) {
      return true;
    }
    return contains(task.title(), needle) || contains(task.description(), needle);
  }

  private boolean contains(String text, String needle) {
    return text != null && text.toLowerCase(Locale.ROOT).contains(needle);
  }

  private void validate(TaskDraft draft) {
    if (draft.title() == null || draft.title().isBlank()) {
      throw new TaskValidationException("El titulo es obligatorio");
    }
    if (draft.description() != null && draft.description().length() > 500) {
      throw new TaskValidationException("La descripcion admite 500 caracteres como maximo");
    }
    if (draft.status() == null) {
      throw new TaskValidationException("El estado es obligatorio");
    }
    if (draft.priority() < 1 || draft.priority() > 5) {
      throw new TaskValidationException("La prioridad debe estar entre 1 y 5");
    }
    if (draft.dueDate() == null || draft.dueDate().isBefore(LocalDate.now())) {
      throw new TaskValidationException("La fecha limite no puede ser anterior a hoy");
    }
  }

  private Task toTask(UUID id, TaskDraft draft) {
    return new Task(
        id,
        draft.title().trim(),
        draft.description(),
        draft.status(),
        draft.priority(),
        draft.dueDate());
  }
}
