# API de tareas

API REST en Java 21 y Spring Boot para gestionar tareas. Los datos se guardan en PostgreSQL mediante Spring Data JPA/Hibernate y sobreviven al reinicio de la aplicacion.

## Requisitos

- JDK 21
- Maven 3.9 o posterior
- Docker y Docker Compose para ejecutar el stack completo

## Puesta en marcha

Clona el repositorio e instala el hook de Git antes de empezar a trabajar:

```bash
git clone https://github.com/josejavierml03/api-tareas.git
cd api-tareas
git config core.hooksPath .githooks
```

## Arranque con Docker Compose

```bash
cp .env.example .env
# Ajusta POSTGRES_PASSWORD en .env antes del primer arranque.
docker compose config --quiet
docker compose up --build -d --wait
curl -f http://localhost:8080/actuator/health
docker compose ps
```

Compose espera a que PostgreSQL este listo antes de arrancar la API. Esta hereda el healthcheck del Dockerfile. Solo se exponen `health` e `info` de Actuator; el endpoint de salud devuelve `{"status":"UP"}` cuando la API y su base de datos estan listas.

`docker compose down` elimina contenedores y red, pero conserva las tareas en el volumen `db-data`. `docker compose down -v` borra tambien el volumen y sus datos. PostgreSQL solo utiliza las credenciales de `.env` al inicializar un volumen nuevo. Para cambiar esas credenciales en desarrollo, elimina el volumen y vuelve a arrancar.

`.env` no se versiona ni se envia al contexto de build. `.env.example` documenta las variables. `docker compose config` muestra la configuracion resuelta, incluida la contraseña; `--quiet` solo la valida.

## Arranque del JAR en local

Los tests unitarios no necesitan una base de datos. Para arrancar el JAR, levanta PostgreSQL y exporta las variables de conexion:

```bash
mvn clean package
docker compose up -d --wait db
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/tareas
export SPRING_DATASOURCE_USERNAME=app
read -rs -p 'Contraseña de PostgreSQL: ' SPRING_DATASOURCE_PASSWORD
export SPRING_DATASOURCE_PASSWORD
java -jar target/api-tareas-0.0.1-SNAPSHOT.jar
```

Si cambiaste el nombre de base de datos o el usuario en `.env`, adapta las variables anteriores. El JAR no lee `.env` automaticamente; Compose si lo hace.

En otra terminal, crea una tarea:

**Git Bash / Linux:**

```bash
curl -i -X POST http://localhost:8080/api/tasks \
  -H 'Content-Type: application/json' \
  -d "{\"title\":\"Preparar boletin\",\"description\":\"Completar Git y Maven\",\"status\":\"PENDING\",\"priority\":3,\"dueDate\":\"$(date -d '+7 days' +%F)\"}"
```

**PowerShell (usa `curl.exe`):**

```powershell
$d = (Get-Date).AddDays(7).ToString("yyyy-MM-dd")
curl.exe -i -X POST http://localhost:8080/api/tasks -H "Content-Type: application/json" -d "{\"title\":\"Preparar boletin\",\"description\":\"Completar Git y Maven\",\"status\":\"PENDING\",\"priority\":3,\"dueDate\":\"$d\"}"
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

## Imagen Docker

```bash
docker build -t tareas-api:0.1 .
docker build --target build -t tareas-api:build .
docker images tareas-api
```

La etapa de build contiene Maven y JDK; la imagen final solo contiene JRE, el JAR y curl para el healthcheck. La API se ejecuta como el usuario `app` (UID 1001). Se copia primero `pom.xml` para reutilizar la descarga de dependencias cuando solo cambia el codigo Java. La imagen necesita una conexion PostgreSQL: usa Compose para arrancarla con la base de datos.

## Dev Container

Instala la extension Dev Containers en VS Code, abre la carpeta del proyecto y ejecuta **Dev Containers: Reopen in Container**. El contenedor incluye Java 21, Maven y Docker mediante Docker-in-Docker. Tras su creacion prepara `.env`, activa los hooks y descarga las dependencias Maven.

Dentro del dev container ajusta `.env` y ejecuta:

```bash
docker compose up --build -d --wait
```

El puerto 8080 se reenvia para consultar `http://127.0.0.1:8080/actuator/health` desde el navegador. El uso del dev container es opcional si ya dispones del entorno local.
