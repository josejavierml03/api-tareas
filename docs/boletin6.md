# Boletín 6: Despliegue con Ansible

## Resumen de cambios de esta sesión

| Mensaje previsto | Qué se hizo | Autor | Referencia |
|---|---|---|---|
| `feat(deploy): completa Pages y despliegue Ansible multi-host` | Flota, controlador, Vault, runner y automatización protegida; memorias y generador Pages | José Javier Méndez | Rama `feat/boletines-pages-ansible` (enlace al commit en el PR de esta sesión) |

## Detalle del cambio

La sesión añade los archivos de `deploy/`, amplía Release con `needs: publish`, entorno `produccion` y runner self-hosted, y publica las memorias desde `docs/`. Se revisaron los cambios y se probaron el despliegue y la idempotencia. IA: OpenCode, GPT-6.1-sol, petición «Implementa todo lo que falta de los boletines».

## A — Dependencias reproducibles

Ansible-core 2.16.14 y community.docker 3.7.0 en una imagen controladora Python 3.12, con sshpass. No se necesita instalar herramientas globales en el host. Dependencia declarada en `deploy/requirements.yml`.

## B–C — Flota SSH e inventario

Tres máquinas simuladas con Ubuntu 24.04 y SSH: app1 (2223), app2 (2224), db1 (2225). Se usa Ubuntu actual en lugar de 18.04. Docker se ejecuta dentro de cada máquina privilegiada; el controlador solo conecta por SSH. Los puertos de API son 8081 y 8082. La conexión Ansible devolvió `pong` desde los tres hosts.

## D–E — Playbook y Vault

Se comprueba/arranca Docker sin systemd, se despliega PostgreSQL con volumen y healthcheck y después las dos API desde GHCR. Cada API resuelve db1 mediante su dirección real, compartiendo la red de su máquina simulada. Se esperan salud de base de datos y UP en ambas API. Se rechaza `latest` para desplegar siempre una versión trazable.

`deploy/group_vars/servidores/vault.yml` está cifrado con Ansible Vault (cabecera `$ANSIBLE_VAULT;1.1;AES256`). Contraseña maestra aleatoria local en `deploy/.vault-pass`, ignorada y con permiso 600, y en el secreto del entorno `produccion`. No se pasan credenciales mediante argumentos; las tareas con datos sensibles usan `no_log`.

## F — Ejecución y pruebas

Primera ejecución real de `version_app=1.0.0`:

```text
app1: changed=4 unreachable=0 failed=0
app2: changed=4 unreachable=0 failed=0
db1:  changed=3 unreachable=0 failed=0
```

Segunda ejecución real, misma versión:

```text
app1: changed=0 unreachable=0 failed=0
app2: changed=0 unreachable=0 failed=0
db1:  changed=0 unreachable=0 failed=0
```

`http://localhost:8081/actuator/health` y `http://localhost:8082/actuator/health` devolvieron `{"status":"UP"}`. La primera descarga de `ghcr.io/josejavierml03/api-tareas:1.0.0` se realizó desde los servidores sin autenticación por ser pública.

![Salud real app1](capturas/b6-app1-health.png)

![Salud real app2](capturas/b6-app2-health.png)

Actualización a 1.1.0 y rollback: pendientes de registrar el resultado después de publicar la nueva imagen. No se sustituyen imágenes con la misma etiqueta para simular actualizaciones.

## G — Pipeline y arquitectura

Runner local construido con la distribución oficial Actions Runner 2.338.2, Ansible 2.16.14 y sshpass; registrado como `tareas-local`, estado online. El runner solo recibe el despliegue para tags después de `publish`, condicionado al entorno `produccion`. Se configuró como revisor a `juanlaxO3` y se creó el secreto `VAULT_PASS`. Solo se permiten despliegues de tags `v*`.

![Arquitectura y pasos numerados](arquitectura.svg)

1. El estudiante inicia HTTPS al hacer push de `vX.Y.Z`.
2. GitHub ejecuta publish en un runner alojado y construye la imagen.
3. Publish sube por HTTPS a GHCR con tags trazables y devuelve la versión.
4. GitHub solicita aprobación del entorno antes de entregar el job deploy.
5. El runner del ordenador mantiene una conexión HTTPS saliente a GitHub, recibe el job, descarga el código y descifra Vault temporalmente.
6. Ansible inicia SSH hacia los tres servidores; sus Docker internos descargan imágenes por HTTPS y arrancan PostgreSQL y API.
7. Las API conectan por JDBC/TCP a db1 y Ansible comprueba salud por HTTP en cada máquina.

La prueba automatizada de extremo a extremo necesita el workflow integrado o etiquetado y la aprobación real del revisor. No se simula esa aprobación.
