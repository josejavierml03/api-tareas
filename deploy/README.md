# Laboratorio Ansible

Tres contenedores Ubuntu 24.04 simulan servidores. Cada uno tiene SSH y su propio daemon Docker; los datos de estos daemons quedan en volúmenes. SSH solo se publica en localhost, con la contraseña de laboratorio `root`. Se usa Ubuntu actual en lugar de la imagen Ubuntu 18.04 del ejemplo.

## Controlador reproducible (sin instalar Ansible en el host)

```bash
docker build -f deploy/Controller.Dockerfile -t tareas-ansible:2.16 deploy
docker compose -f deploy/servidores/docker-compose.yml up --build -d
docker run --rm --network host -v "$PWD:/workspace" tareas-ansible:2.16 \
  ansible -i deploy/inventory.ini servidores -m ping --vault-password-file deploy/.vault-pass
docker run --rm --network host -v "$PWD:/workspace" tareas-ansible:2.16 \
  ansible-playbook -i deploy/inventory.ini deploy/deploy.yml \
  --vault-password-file deploy/.vault-pass --extra-vars version_app=1.0.0
```

`deploy/.vault-pass` es local, ignorado y con permisos 600. El Vault versionado contiene la contraseña PostgreSQL cifrada. Para editarlo usa `ansible-vault edit` con esa contraseña. Para un registro privado, rellena `ghcr_user`/`ghcr_token` dentro del Vault; para la imagen pública el token queda vacío. Nunca pongas credenciales en `--extra-vars`.

También puedes instalar ansible-core 2.16.x y sshpass en el host y ejecutar los comandos Ansible directamente. Instala la colección con `ansible-galaxy collection install -r deploy/requirements.yml`.

APIs: `http://localhost:8081` y `http://localhost:8082`. Ambas apuntan a PostgreSQL en db1. Se resuelve la IP real de db1 al desplegar y se pasa a la red host del contenedor interno; no se confunde localhost del controlador con localhost de cada servidor.

Ejecuta `python3 deploy/smoke.py` para comprobar salud y persistencia compartida: crea una tarea en app1, la recupera en app2 y la elimina.

## Versiones e idempotencia

Repite el playbook: debe mostrar `changed=0`. Para actualizar usa una etiqueta publicada distinta, por ejemplo `version_app=1.1.0`; para rollback vuelve a `1.0.0`. El playbook rechaza `latest` y espera salud UP en ambas réplicas.

## Runner y aprobación

```bash
docker build -f deploy/Runner.Dockerfile -t tareas-runner:local deploy
RUNNER_TOKEN=$(gh api --method POST repos/josejavierml03/api-tareas/actions/runners/registration-token --jq .token)
export RUNNER_TOKEN
docker run -d --name tareas-actions-runner --network host \
  -e RUNNER_TOKEN tareas-runner:local
unset RUNNER_TOKEN
```

El runner incluye Ansible y sshpass. El job se ejecuta solo para tags de versión, después de publicar la imagen, y espera la aprobación del entorno `produccion`. El secreto `VAULT_PASS` debe coincidir con la contraseña local del Vault. El workflow escribe un fichero temporal 600 y lo elimina al salir.

Al terminar puedes parar el runner con `docker stop tareas-actions-runner` y la flota con `docker compose -f deploy/servidores/docker-compose.yml down`; no uses `-v` si quieres conservar los datos.
