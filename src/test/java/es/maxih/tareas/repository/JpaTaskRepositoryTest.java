package es.maxih.tareas.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import es.maxih.tareas.domain.Task;
import es.maxih.tareas.domain.TaskStatus;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class JpaTaskRepositoryTest {
  private final SpringDataTaskRepository storage = mock(SpringDataTaskRepository.class);
  private final JpaTaskRepository repository = new JpaTaskRepository(storage);

  @Test
  void savesAndRetrievesAllDomainFields() {
    Task task =
        new Task(UUID.randomUUID(), "Revisar", null, TaskStatus.PENDING, 4, LocalDate.now());
    when(storage.save(any(TaskEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
    when(storage.findById(task.id())).thenReturn(Optional.of(new TaskEntity(task)));

    assertEquals(task, repository.save(task));
    assertEquals(Optional.of(task), repository.findById(task.id()));
  }

  @Test
  void missingTaskDoesNotTriggerDeletion() {
    UUID id = UUID.randomUUID();
    when(storage.findById(id)).thenReturn(Optional.empty());

    assertFalse(repository.deleteById(id));
    verify(storage, never()).delete(any(TaskEntity.class));
  }

  @Test
  void deletesTheExistingEntity() {
    TaskEntity entity =
        new TaskEntity(
            new Task(
                UUID.randomUUID(),
                "Borrar",
                "Descripcion",
                TaskStatus.PENDING,
                1,
                LocalDate.now()));
    UUID id = entity.toTask().id();
    when(storage.findById(id)).thenReturn(Optional.of(entity));

    assertTrue(repository.deleteById(id));
    verify(storage).delete(entity);
  }
}
