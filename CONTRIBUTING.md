# Contribuir a API de tareas

Gracias por contribuir. Usa Issues para proponer mejoras o informar de errores y trabaja siempre mediante Pull Requests (PR).

## Preparar el entorno

Necesitas JDK 21 y Maven 3.9 o posterior. Tras clonar el repositorio, activa el hook que aplica el formato antes de cada commit:

```bash
git config core.hooksPath .githooks
mvn clean package
```

Antes de abrir un PR ejecuta:

```bash
mvn test
mvn spotless:check
```

Puedes aplicar automaticamente el formato con `mvn spotless:apply`.

## Flujo de trabajo

1. Crea o elige un Issue con la etiqueta adecuada (`enhancement` o `bug`).
2. Actualiza tu rama `main` local y crea una rama corta con un nombre descriptivo: `feat/nombre-de-la-mejora` o `fix/descripcion-del-error`.
3. Implementa el cambio y sus pruebas. Los commits deben describir el cambio de forma concisa.
4. Sube la rama y abre un PR usando la plantilla. Enlaza el Issue con `Closes #numero`.
5. Atiende los comentarios de revision y mantén las comprobaciones en verde.

## Politica de fusion

`main` esta protegida: no se permiten pushes directos, force pushes ni su eliminacion. Cada PR necesita una aprobacion, la revision de los propietarios del codigo y todas las conversaciones resueltas.

Se utiliza **squash and merge**. Cada PR se integra como un unico commit en `main`, lo que mantiene un historial conciso y permite relacionar claramente un cambio con su PR.
