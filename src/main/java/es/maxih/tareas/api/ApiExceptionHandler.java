package es.maxih.tareas.api;

import es.maxih.tareas.service.TaskNotFoundException;
import es.maxih.tareas.service.TaskValidationException;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(TaskNotFoundException.class)
  ProblemDetail handleNotFound(TaskNotFoundException exception) {
    return problem(HttpStatus.NOT_FOUND, "Tarea no encontrada", exception.getMessage());
  }

  @ExceptionHandler({TaskValidationException.class, MethodArgumentNotValidException.class})
  ProblemDetail handleBadRequest(Exception exception) {
    String detail = exception instanceof MethodArgumentNotValidException validationException
        ? validationException.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.joining(", "))
        : exception.getMessage();
    return problem(HttpStatus.BAD_REQUEST, "Peticion no valida", detail);
  }

  private ProblemDetail problem(HttpStatus status, String title, String detail) {
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detail);
    problem.setTitle(title);
    return problem;
  }
}
