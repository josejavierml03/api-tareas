package es.maxih.tareas.api;

import es.maxih.tareas.domain.Task;
import es.maxih.tareas.domain.TaskDraft;
import es.maxih.tareas.service.TaskService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

  private final TaskService service;

  public TaskController(TaskService service) {
    this.service = service;
  }

  @GetMapping
  public List<Task> findAll() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  public Task findById(@PathVariable UUID id) {
    return service.findById(id);
  }

  @PostMapping
  public ResponseEntity<Task> create(@Valid @RequestBody TaskDraft draft) {
    Task task = service.create(draft);
    return ResponseEntity.created(URI.create("/api/tasks/" + task.id())).body(task);
  }

  @PutMapping("/{id}")
  public Task update(@PathVariable UUID id, @Valid @RequestBody TaskDraft draft) {
    return service.update(id, draft);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
