# Boletín 1: Git y Maven

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

1. **4e6239c (B–C):** creó Task y sus campos, CRUD en capas, errores 400/404, Maven/Spring Boot y ocho tests de negocio sin contexto Spring ni base de datos.
2. **1cbf1fc (D):** fijó UTF-8, LF e indentación compartida para los editores.
3. **dc067b6 (D):** añadió el filtro `status` en una rama de funcionalidad, con test de selección de tareas.
4. **20aec19 (D):** integró la rama feature, conservando el historial de ramas y fusión.
5. **9716d10 (E):** declaró Spotless 2.43.0, Google Java Format, limpieza de imports y espacios.
6. **3d99cc5 (E):** añadió `.githooks/pre-commit` con Spotless y `git add -u`, compartible entre clones.
7. **7cd0d8e (E):** guardó el permiso ejecutable necesario para que Git ejecute el hook.
8. **37642e4 (F):** creó la primera variante de una línea del README para provocar conflicto.
9. **668ed06 (F):** modificó la misma línea desde otra rama de forma incompatible.
10. **8c0c127 (F):** resolvió los marcadores de conflicto combinando ambas descripciones y cerró el merge.
11. **f162cc6 (E):** aplicó el formato que faltaba y comprobó el funcionamiento del hook.

El autor de estos once cambios es José Javier Méndez. Para todos se utilizó OpenCode bajo la petición de la sección siguiente; el modelo de aquella sesión no se puede recuperar del repositorio.

## Uso de IA y evidencias

Se utilizó OpenCode para generar la API y trabajar el boletín: «Vamos a hacer el boletin1 teniendo en cuenta todo lo que dice el boletin0». Esta memoria se reconstruye del historial porque el documento original de Downloads ya no está disponible. Pendiente recuperar las capturas originales para la entrega; no se inventan imágenes de una sesión pasada.
