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

    List<Task> tasks = service.findAll(TaskStatus.IN_PROGRESS, null);

    assertEquals(1, tasks.size());
    assertEquals("En curso", tasks.getFirst().title());
  }

  @Test
  void findsTasksByTitleIgnoringCase() {
    service.create(draft("Preparar el boletin", TaskStatus.PENDING, 2));
    service.create(draft("Comprar cafe", TaskStatus.PENDING, 3));

    List<Task> tasks = service.findAll(null, "BOLETIN");

    assertEquals(1, tasks.size());
    assertEquals("Preparar el boletin", tasks.getFirst().title());
  }

  @Test
  void findsTasksByDescription() {
    service.create(task("Revisar codigo", "Mirar el apartado de boletin"));
    service.create(task("Comprar cafe", null));

    List<Task> tasks = service.findAll(null, "apartado de boletin");

    assertEquals(1, tasks.size());
    assertEquals("Revisar codigo", tasks.getFirst().title());
  }

  @Test
  void ignoresSurroundingSpacesInQuery() {
    service.create(draft("Preparar el boletin", TaskStatus.PENDING, 2));

    assertEquals(1, service.findAll(null, "  boletin  ").size());
  }

  @Test
  void returnsEveryTaskWhenQueryIsBlank() {
    service.create(draft("Preparar el boletin", TaskStatus.PENDING, 2));
    service.create(draft("Comprar cafe", TaskStatus.PENDING, 3));

    assertEquals(2, service.findAll(null, "   ").size());
    assertEquals(2, service.findAll(null, null).size());
  }

  @Test
  void combinesQueryWithStatus() {
    service.create(draft("Preparar el boletin", TaskStatus.PENDING, 2));
    service.create(draft("Cerrar el boletin", TaskStatus.COMPLETED, 3));

    List<Task> tasks = service.findAll(TaskStatus.COMPLETED, "boletin");

    assertEquals(1, tasks.size());
    assertEquals("Cerrar el boletin", tasks.getFirst().title());
  }

  @Test
  void returnsEmptyListWhenNothingMatches() {
    service.create(draft("Comprar cafe", TaskStatus.PENDING, 2));

    assertEquals(0, service.findAll(null, "boletin").size());
  }

  @Test
  void sortsTasksByPriorityAndDueDate() {
    service.create(taskWithPriorityAndDueDate("Prioridad baja", 1, LocalDate.now().plusDays(1)));
    service.create(taskWithPriorityAndDueDate("Alta tardia", 5, LocalDate.now().plusDays(5)));
    service.create(taskWithPriorityAndDueDate("Alta proxima", 5, LocalDate.now().plusDays(1)));

    List<Task> tasks = service.findAll(null, null);

    assertEquals("Alta proxima", tasks.get(0).title());
    assertEquals("Alta tardia", tasks.get(1).title());
    assertEquals("Prioridad baja", tasks.get(2).title());
  }

  private TaskDraft draft(String title, TaskStatus status, int priority) {
    return new TaskDraft(title, "Descripcion", status, priority, LocalDate.now().plusDays(7));
  }

  private TaskDraft task(String title, String description) {
    return new TaskDraft(title, description, TaskStatus.PENDING, 3, LocalDate.now().plusDays(7));
  }

  private TaskDraft taskWithPriorityAndDueDate(String title, int priority, LocalDate dueDate) {
    return new TaskDraft(title, null, TaskStatus.PENDING, priority, dueDate);
  }
}
