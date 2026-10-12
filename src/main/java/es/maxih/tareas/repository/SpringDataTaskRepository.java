package es.maxih.tareas.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataTaskRepository extends JpaRepository<TaskEntity, UUID> {}
