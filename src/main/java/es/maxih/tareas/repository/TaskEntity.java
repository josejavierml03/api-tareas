package es.maxih.tareas.repository;

import es.maxih.tareas.domain.Task;
import es.maxih.tareas.domain.TaskStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tasks")
public class TaskEntity {
  @Id private UUID id;

  @Column(nullable = false)
  private String title;

  @Column(length = 500)
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TaskStatus status;

  @Column(nullable = false)
  private int priority;

  @Column(nullable = false)
  private LocalDate dueDate;

  protected TaskEntity() {}

  public TaskEntity(Task task) {
    id = task.id();
    title = task.title();
    description = task.description();
    status = task.status();
    priority = task.priority();
    dueDate = task.dueDate();
  }

  public Task toTask() {
    return new Task(id, title, description, status, priority, dueDate);
  }
}
