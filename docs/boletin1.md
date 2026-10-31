# Boletín 1: Git y Maven

## Resumen de commits

| Commit | Qué se hizo | Autor | Enlace |
|---|---|---|---|
| `4e6239c` | API por capas, CRUD, validación, errores y tests unitarios | José Javier Méndez | [commit](https://github.com/josejavierml03/api-tareas/commit/4e6239c) |
| `1cbf1fc` | EditorConfig | José Javier Méndez | [commit](https://github.com/josejavierml03/api-tareas/commit/1cbf1fc) |
| `dc067b6` | Filtro por estado en rama feature | José Javier Méndez | [commit](https://github.com/josejavierml03/api-tareas/commit/dc067b6) |
| `20aec19` | Integración de la feature | José Javier Méndez | [commit](https://github.com/josejavierml03/api-tareas/commit/20aec19) |
| `9716d10` | Spotless | José Javier Méndez | [commit](https://github.com/josejavierml03/api-tareas/commit/9716d10) |
| `3d99cc5` | Hook versionado | José Javier Méndez | [commit](https://github.com/josejavierml03/api-tareas/commit/3d99cc5) |
| `7cd0d8e` | Hook ejecutable | José Javier Méndez | [commit](https://github.com/josejavierml03/api-tareas/commit/7cd0d8e) |
| `37642e4` | Primera modificación del README para el conflicto | José Javier Méndez | [commit](https://github.com/josejavierml03/api-tareas/commit/37642e4) |
| `668ed06` | Segunda modificación incompatible | José Javier Méndez | [commit](https://github.com/josejavierml03/api-tareas/commit/668ed06) |
| `8c0c127` | Resolución del conflicto | José Javier Méndez | [commit](https://github.com/josejavierml03/api-tareas/commit/8c0c127) |
| `f162cc6` | Formato aplicado | José Javier Méndez | [commit](https://github.com/josejavierml03/api-tareas/commit/f162cc6) |

## Desarrollo y relación con el boletín

La API inicial guardaba tareas en memoria. `4e6239c` cubre las partes B–C: separación controlador/servicio/repositorio, CRUD y reglas de negocio. `1cbf1fc`, `dc067b6` y `20aec19` cubren la configuración de edición y trabajo en ramas de la parte D. `9716d10`, `3d99cc5`, `7cd0d8e` y `f162cc6` automatizan el estilo en la parte E: Google Java Format y hook `.githooks/pre-commit`, activado por clon. Los dos cambios del README y su fusión documentan el conflicto de la parte F.

Se comprobaron `mvn clean package`, `mvn test`, `mvn spotless:check` y el arranque del fat JAR. Las reglas incluían fecha no pasada, título obligatorio, prioridad 1–5, ID estable, no reabrir tareas completadas y filtrado por estado.

## Uso de IA y evidencias

Se utilizó OpenCode para generar la API y trabajar el boletín: «Vamos a hacer el boletin1 teniendo en cuenta todo lo que dice el boletin0». Esta memoria se reconstruye del historial porque el documento original de Downloads ya no está disponible. Pendiente recuperar las capturas originales para la entrega; no se inventan imágenes de una sesión pasada.
