# API de tareas

API REST ligera y practica en Java 21 y Spring Boot para gestionar tareas. La persistencia se mantiene en memoria, por lo que los datos se pierden al detener la aplicacion.

## Requisitos

- JDK 21
- Maven 3.9 o posterior

## Puesta en marcha

Clona el repositorio e instala el hook de Git antes de empezar a trabajar:

```bash
git clone https://github.com/josejavierml03/api-tareas.git
cd api-tareas
git config core.hooksPath .githooks
```

Compila, ejecuta los tests y arranca la aplicacion:

```bash
mvn clean package
java -jar target/api-tareas-0.0.1-SNAPSHOT.jar
```

En otra terminal, crea una tarea:

```bash
curl -i -X POST http://localhost:8080/api/tasks \
  -H 'Content-Type: application/json' \
  -d '{"title":"Preparar boletin","description":"Completar Git y Maven","status":"PENDING","priority":3,"dueDate":"2026-10-02"}'
```

Los endpoints disponibles son `GET /api/tasks`, `GET /api/tasks/{id}`, `POST /api/tasks`, `PUT /api/tasks/{id}` y `DELETE /api/tasks/{id}`. Una tarea inexistente devuelve `404`; los datos invalidos devuelven `400` con un detalle del error.

El listado se ordena por prioridad descendente (de 5 a 1) y, si hay empate, por fecha limite ascendente. Admite dos filtros opcionales que pueden combinarse: `status` acepta `PENDING`, `IN_PROGRESS` o `COMPLETED`, y `q` busca texto en el titulo y en la descripcion sin distinguir mayusculas. Si `q` no llega o llega en blanco no se aplica ningun filtro de texto:

```bash
curl "http://localhost:8080/api/tasks?status=PENDING"
curl "http://localhost:8080/api/tasks?q=boletin"
curl "http://localhost:8080/api/tasks?q=boletin&status=PENDING"
```

## Formato y hook de Git

```bash
mvn spotless:check
mvn spotless:apply
git config core.hooksPath .githooks
```

El hook versionado aplica Spotless antes de cada commit y vuelve a anadir los ficheros reformateados. Debe activarse una vez en cada clon; no sustituye la comprobacion de formato que se incorporara a CI.

## Contribuir

Consulta [CONTRIBUTING.md](CONTRIBUTING.md) para conocer el flujo de ramas, las comprobaciones locales y la politica de Pull Requests.
