# Boletín 2: GitHub y colaboración

## Resumen de commits y PR

| Cambio | Descripción y parte | Autor del PR | Enlace |
|---|---|---|---|
| `6819a50` / PR #1 | README, plantillas, CODEOWNERS, CONTRIBUTING; A–B | José Javier Méndez | [PR](https://github.com/josejavierml03/api-tareas/pull/1) |
| `b261910` / PR #3 | Búsqueda por texto; D | juanlaxO3 | [PR](https://github.com/josejavierml03/api-tareas/pull/3) |
| `c9ebc18` / PR #7 | Orden por prioridad; D | José Javier Méndez | [PR](https://github.com/josejavierml03/api-tareas/pull/7) |
| `5914f80` / PR #13 | Conflicto y rebase, desempate por título y test; E | José Javier Méndez | [PR](https://github.com/josejavierml03/api-tareas/pull/13) |
| PR #15 | Validación del título desde fork; F | Ver autor en PR | [PR](https://github.com/josejavierml03/api-tareas/pull/15) |
| `afce9d0` / PR externo #12 | Validar antes de mutar entidad, dos tests; F | José Javier Méndez | [PR externo](https://github.com/SergioAntonLopezumu/PC_26-27/pull/12) |

## Desarrollo por partes

### A–B

Repositorio público y colaborador `juanlaxO3`. Las plantillas de bug/mejora aplican etiquetas y el PR guía descripción, pruebas y revisión. CODEOWNERS asigna la aplicación a ambos compañeros y su análisis devolvió cero errores. Squash merge agrupa cada PR en un commit significativo.

### C

`main` exige PR, una aprobación, Code Owners, conversaciones resueltas, descarta aprobaciones obsoletas y aplica reglas a administradores. No permite force push ni borrado. El commit temporal `3600388` se intentó enviar con `git push origin HEAD:main`; GitHub respondió `GH006` y `Changes must be made through a pull request`. Nunca se integró el comentario de prueba.

### D

Los PR #3 y #7 se aprobaron y fusionaron con intercambio de roles: José Javier revisó la búsqueda de Juan Antonio y Juan Antonio revisó la ordenación de José Javier.

### E

Se creó una rama desde `b261910` con un cambio en el mismo comparador modificado por #7. El PR #13 mostró `CONFLICTING/DIRTY`. `git rebase main` produjo conflicto real; se combinaron prioridad descendente, fecha ascendente y título. Tras pasar 16 tests y `git push --force-with-lease`, pasó a `MERGEABLE`. El PR ya está fusionado.

### F

El Issue #14 y PR #15 aportan validación no trivial desde otro grupo y están cerrados/fusionados. Nuestra contribución no trivial es el PR externo #12: validar antes de mutar una entidad y devolver error de dominio ante entrada nula, con dos tests de regresión (16 tests del proyecto externo correctos). Sigue pendiente de aprobación externa. La anterior corrección de un título no se cuenta como contribución no trivial.

## Uso de IA

OpenCode, GPT-6.1-sol, para gobernanza, pruebas de protección, conflicto y contribución externa bajo «Haz aquello que falte de la 2 y haz el boletin 3». Los prompts de las sesiones de cada compañero deben declararse por su autor. Capturas históricas de conflicto y colaboración: pendientes de recuperar. Las revisiones externas no se pueden sustituir por una aprobación del autor.
