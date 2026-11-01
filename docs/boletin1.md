# Boletín 1: Git y proyecto Maven

Repositorio local: `api-tareas/`. Repositorio publicado: <https://github.com/josejavierml03/api-tareas>.

Memoria integrada a partir de `memoria-boletin1.md`, aportada por el estudiante.

## Resumen de commits

| Mensaje | Qué se hizo | Autor | Enlace |
|---|---|---|---|
| `chore: proyecto Maven inicial de la API de tareas` | API por capas, CRUD, validación, errores y tests unitarios | José Javier Méndez | [4e6239c](https://github.com/josejavierml03/api-tareas/commit/4e6239c) |
| `chore(editor): añade .editorconfig` | EditorConfig | José Javier Méndez | [1cbf1fc](https://github.com/josejavierml03/api-tareas/commit/1cbf1fc) |
| `feat(tasks): añade filtro por estado` | Filtro por estado en rama feature | José Javier Méndez | [dc067b6](https://github.com/josejavierml03/api-tareas/commit/dc067b6) |
| `merge: integra filtro por estado` | Integración de la feature | José Javier Méndez | [20aec19](https://github.com/josejavierml03/api-tareas/commit/20aec19) |
| `chore(build): añade Spotless para formateo automático` | Spotless | José Javier Méndez | [9716d10](https://github.com/josejavierml03/api-tareas/commit/9716d10) |
| `chore(hooks): añade hook de pre-commit con Spotless` | Hook versionado | José Javier Méndez | [3d99cc5](https://github.com/josejavierml03/api-tareas/commit/3d99cc5) |
| `chore(hooks): hace ejecutable el hook` | Hook ejecutable | José Javier Méndez | [7cd0d8e](https://github.com/josejavierml03/api-tareas/commit/7cd0d8e) |
| `docs(readme): acorta la descripcion de la API` | Primera modificación del README para el conflicto | José Javier Méndez | [37642e4](https://github.com/josejavierml03/api-tareas/commit/37642e4) |
| `docs(readme): destaca el uso practico de la API` | Segunda modificación incompatible | José Javier Méndez | [668ed06](https://github.com/josejavierml03/api-tareas/commit/668ed06) |
| `merge: resuelve conflicto de documentacion` | Resolución del conflicto | José Javier Méndez | [8c0c127](https://github.com/josejavierml03/api-tareas/commit/8c0c127) |
| `chore(style): aplica formato de Spotless` | Formato aplicado | José Javier Méndez | [f162cc6](https://github.com/josejavierml03/api-tareas/commit/f162cc6) |

## Desarrollo y relación con el boletín

La API inicial guardaba tareas en memoria. `4e6239c` cubre las partes B–C: separación controlador/servicio/repositorio, CRUD y reglas de negocio. `1cbf1fc`, `dc067b6` y `20aec19` cubren la configuración de edición y trabajo en ramas de la parte D. `9716d10`, `3d99cc5`, `7cd0d8e` y `f162cc6` automatizan el estilo en la parte E: Google Java Format y hook `.githooks/pre-commit`, activado por clon. Los dos cambios del README y su fusión documentan el conflicto de la parte F.

Se comprobaron `mvn clean package`, `mvn test`, `mvn spotless:check` y el arranque del fat JAR. Las reglas incluían fecha no pasada, título obligatorio, prioridad 1–5, ID estable, no reabrir tareas completadas y filtrado por estado.

## Detalle por commit

### 1. `chore: proyecto Maven inicial de la API de tareas`

- **Qué se hizo:** API REST con `id`, título, descripción, estado, prioridad y fecha límite; CRUD, repositorio en memoria, servicio, validaciones, errores centralizados y ocho tests de negocio.
- **Relación:** partes B y C; Maven/Spring Boot separado en controlador, servicio y repositorio, sin tests de integración que levanten Spring.
- **Revisión:** se revisó el código generado antes de incorporarlo.

### 2. `chore(editor): añade .editorconfig`

- **Qué se hizo:** UTF-8, LF e indentación por espacios compartida entre editores.
- **Relación:** parte D, configuración base del repositorio.
- **Revisión:** se revisó la configuración.

### 3. `feat(tasks): añade filtro por estado`

- **Qué se hizo:** filtro opcional por estado y test asociado en la rama `feat/filtro-por-estado`.
- **Relación:** parte D, cambio pequeño pero no trivial en una rama feature.
- **Revisión:** cambio y test asociado revisados.

### 4. `merge: integra filtro por estado`

- **Qué se hizo:** fusión de `feat/filtro-por-estado` en `main` mediante commit de merge.
- **Relación:** parte D, práctica de ramas y merge.
- **Revisión:** se revisó el historial resultante.

### 5. `chore(build): añade Spotless para formateo automático`

- **Qué se hizo:** plugin Spotless 2.43.0 con la configuración del enunciado.
- **Relación:** parte E, modos de comprobación y aplicación del formato.
- **Revisión:** bloque XML contrastado con el enunciado.

### 6. `chore(hooks): añade hook de pre-commit con Spotless`

- **Qué se hizo:** `.githooks/pre-commit` ejecuta `mvn -q spotless:apply` y vuelve a añadir los archivos modificados.
- **Relación:** parte E, hook compartido en lugar de `.git/hooks`.
- **Revisión:** script revisado antes de incorporarlo.

### 7. `chore(hooks): hace ejecutable el hook`

- **Qué se hizo:** permiso de ejecución guardado en Git.
- **Relación:** parte E; Git solo ejecuta hooks con ese permiso.
- **Revisión:** se comprobó el cambio de modo del archivo.

### 8. `docs(readme): acorta la descripcion de la API`

- **Qué se hizo:** cambio de una línea del README en `conflict/descripcion-corta`.
- **Relación:** parte F, preparación de un conflicto real.
- **Revisión:** modificación revisada.

### 9. `docs(readme): destaca el uso practico de la API`

- **Qué se hizo:** rama `conflict/descripcion-practica` desde `main`, modificando de otra forma la misma línea.
- **Relación:** parte F, modificación incompatible que provoca conflicto al fusionar.
- **Revisión:** modificación revisada.

### 10. `merge: resuelve conflicto de documentacion`

- **Qué se hizo:** eliminación manual de `<<<<<<<`, `=======` y `>>>>>>>`, combinando ambas propuestas.
- **Relación:** parte F, resolución y cierre con `git add` y `git commit`.
- **Revisión:** se comprobó que no quedaran marcadores.

### 11. `chore(style): aplica formato de Spotless`

- **Qué se hizo:** `spotless:check` detectó tres archivos sin formato; `spotless:apply` los corrigió y el hook se ejecutó al crear el commit.
- **Relación:** parte E, demostración de `check`, `apply` y hook.
- **Revisión:** diff de formato revisado.

**Autor de los once commits:** José Javier Méndez. **Uso de IA en cada uno, según la memoria original:** OpenCode, modelo GPT-5.6-terra, bajo la petición común indicada a continuación. Esta declaración corresponde a la sesión original, no al modelo de la sesión de incorporación a Pages.

## Uso de IA y evidencias

Petición original: «Vamos a hacer el boletin1 teniendo en cuenta todo lo que dice el boletin0».

La memoria original registra JDK 21.0.12.1 y Maven 3.9.12, `mvn clean package` correcto, JAR ejecutable con dependencias y ocho tests sin fallos, errores ni omisiones. Spotless falló inicialmente en tres archivos y pasó después de aplicar el formato. El hook se ejecutó al crear `f162cc6`. Se arrancó el fat JAR, se creó una tarea por POST y se recuperó con `GET /api/tasks?status=PENDING`.

Las imágenes referenciadas en el original son `capturas-boletin1/api-filtrada.png` y `capturas-boletin1/repositorio-github.png`. Esas carpetas no se han aportado todavía; quedan pendientes de adjuntar. Se han conservado sus nombres sin publicar enlaces a imágenes inexistentes.
