package es.maxih.tareas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import es.maxih.tareas.domain.Task;
import es.maxih.tareas.domain.TaskDraft;
import es.maxih.tareas.domain.TaskStatus;
import es.maxih.tareas.repository.InMemoryTaskRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskServiceTest {

  private TaskService service;

  @BeforeEach
  void setUp() {
    service = new TaskService(new InMemoryTaskRepository());
  }

  @Test
  void createsTaskWithGeneratedId() {
    Task task = service.create(draft("Preparar la practica", TaskStatus.PENDING, 3));

    assertNotNull(task.id());
    assertEquals("Preparar la practica", task.title());
  }

  @Test
  void rejectsDueDateInThePast() {
    TaskDraft invalid =
        new TaskDraft("Entregar", null, TaskStatus.PENDING, 2, LocalDate.now().minusDays(1));

    assertThrows(TaskValidationException.class, () -> service.create(invalid));
  }

  @Test
  void rejectsBlankTitle() {
    TaskDraft invalid =
        new TaskDraft("   ", null, TaskStatus.PENDING, 2, LocalDate.now().plusDays(1));

    assertThrows(TaskValidationException.class, () -> service.create(invalid));
  }

  @Test
  void rejectsPriorityOutsideAllowedRange() {
    TaskDraft invalid =
        new TaskDraft("Revisar", null, TaskStatus.PENDING, 6, LocalDate.now().plusDays(1));

    assertThrows(TaskValidationException.class, () -> service.create(invalid));
  }

  @Test
  void updateKeepsTaskIdAndChangesItsFields() {
    Task created = service.create(draft("Inicial", TaskStatus.PENDING, 1));

    Task updated = service.update(created.id(), draft("Actualizada", TaskStatus.IN_PROGRESS, 5));

    assertEquals(created.id(), updated.id());
    assertEquals("Actualizada", updated.title());
    assertEquals(TaskStatus.IN_PROGRESS, updated.status());
  }

  @Test
  void doesNotReopenCompletedTask() {
    Task completed = service.create(draft("Terminar", TaskStatus.COMPLETED, 3));

    assertThrows(
        TaskValidationException.class,
        () -> service.update(completed.id(), draft("Terminar", TaskStatus.PENDING, 3)));
  }

  @Test
  void failsWhenDeletingMissingTask() {
    assertThrows(TaskNotFoundException.class, () -> service.delete(UUID.randomUUID()));
  }

  @Test
  void findsOnlyTasksWithRequestedStatus() {
    service.create(draft("Pendiente", TaskStatus.PENDING, 2));
    service.create(draft("En curso", TaskStatus.IN_PROGRESS, 3));

    List<Task> tasks = service.findByStatus(TaskStatus.IN_PROGRESS);

    assertEquals(1, tasks.size());
    assertEquals("En curso", tasks.getFirst().title());
  }

  @Test
  void sortsByPriorityBeforeDueDate() {
    service.create(
        new TaskDraft("Urgente tardia", null, TaskStatus.PENDING, 5, LocalDate.now().plusDays(5)));
    service.create(
        new TaskDraft("Trivial pronta", null, TaskStatus.PENDING, 1, LocalDate.now().plusDays(1)));

    List<Task> tasks = service.findByStatus(TaskStatus.PENDING);

    assertEquals("Trivial pronta", tasks.getFirst().title());
    assertEquals("Urgente tardia", tasks.getLast().title());
  }

  private TaskDraft draft(String title, TaskStatus status, int priority) {
    return new TaskDraft(title, "Descripcion", status, priority, LocalDate.now().plusDays(7));
  }
}
