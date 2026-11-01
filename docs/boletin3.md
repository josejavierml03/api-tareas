# Boletín 3: Docker

## Resumen de commits

| Commit | Qué se hizo | Autor | Enlace |
|---|---|---|---|
| `f8caeb5` | Actuator, JPA/PostgreSQL, Dockerfile multi-stage, Compose, entorno y tests | José Javier Méndez | [commit](https://github.com/josejavierml03/api-tareas/commit/f8caeb5) |
| `0bdada5` | Puertos locales configurables | José Javier Méndez | [commit](https://github.com/josejavierml03/api-tareas/commit/0bdada5) |

## Detalle y relación con el boletín

`f8caeb5` cubre B–G. La aplicación usaba ConcurrentHashMap, no H2; se añadió JPA y el driver PostgreSQL, conservando memoria para tests unitarios. Actuator publica health/info. El Dockerfile compila con JDK/Maven y ejecuta con JRE y usuario app. Compose espera a PostgreSQL saludable y conserva los datos en volumen. `.env` queda fuera de Git y build; `.env.example` documenta variables. El dev container fija JDK/Maven y Docker-in-Docker, prepara entorno, hooks y dependencias. Su uso visual es opcional y no se ha comprobado en VS Code.

`0bdada5` cubre D–E: permite cambiar puertos del host porque 5432 estaba ocupado, manteniendo 5432/8080 dentro de los contenedores.

## Verificación adicional del dev container

En la sesión de cierre se construyó y arrancó el dev container con `@devcontainers/cli@0.80.1`. Se detectó que la feature Java sin versión explícita instalaba la última versión disponible; se fijaron `version=21`, `jdkDistro=tem` y Maven 3.9.9. Se añadieron los puertos alternativos al reenvío de VS Code.

Se ejecutaron postCreateCommand, Docker-in-Docker y `docker compose -p boletin3-devcontainer up --build -d --wait` dentro del entorno: Java Temurin 21.0.13, Maven 3.9.9, API y PostgreSQL saludables, `curl http://localhost:18080/actuator/health` devolvió UP. La prueba fue por CLI; no se afirma haber abierto VS Code visualmente. Las primeras invocaciones de creación agotaron el timeout tras arrancar el contenedor, y se completó con `--expect-existing-container`.

## Comprobaciones realizadas

- Docker Engine 29.9.0, Compose v5.6.0 y hello-world correctos. Se instaló Engine; Desktop no se ha comprobado.
- API y PostgreSQL saludables; `/actuator/health` devuelve UP; usuario UID 1001.
- POST 201; tarea conservada tras down/up y listado vacío tras down -v/up, en proyecto aislado `boletin3-verificacion`.
- Runtime 571.865.961 bytes; build 1.213.213.866 bytes. Maven, JDK y caché solo permanecen en build.
- Caché medida: 7,72 s copiando pom antes de src frente a 36,94 s invirtiendo el orden; la segunda invalida las dependencias. Se restauró el código temporal.
- Maven: 18 tests correctos en esa sesión, package y Spotless correctos.
- [PR #12](https://github.com/josejavierml03/api-tareas/pull/12) fusionado.

## H — Registro

Imagen [josejavier03ml/tareas-api](https://hub.docker.com/r/josejavier03ml/tareas-api), etiqueta `0.1`. Se ejecutaron login, tag, push y pull. Digest `sha256:5041455bb04bbc62f0e26fca11cde3426c56430a0042ee487ed909b2ee699c57`.

## I — Tests de proyecto externo

Click 8.1.8, commit `934813e4d421071a1b3db3973c02fe2721359a6e`. Requisitos consultados en su pyproject.toml y requirements/tests.in: Python >=3.7, flit_core<4 y pytest. Se montó el código en `/workspace`, instaló editable y ejecutó pytest dentro de Python 3.12 con pytest 8.3.5/flit_core 3.10.1. Resultado: **628 passed, 21 skipped, 1 xfailed**, salida 0. Los artefactos de este ejercicio se mantuvieron fuera del repositorio. El enunciado disponible está truncado al final de esta parte.

## Uso de IA y capturas

OpenCode, GPT-6.1-sol, bajo «Haz aquello que falte de la 2 y haz el boletin 3». Se revisaron los cambios y verificaron Maven y Docker. Pendiente recuperar capturas de la sesión; esta memoria reconstruye los resultados registrados, no simula capturas antiguas.
