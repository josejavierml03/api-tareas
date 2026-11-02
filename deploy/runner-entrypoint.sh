#!/bin/bash
set -eu
if [ ! -f .runner ]; then
  : "${RUNNER_TOKEN:?Proporciona un token de registro temporal}"
  ./config.sh --unattended --url https://github.com/josejavierml03/api-tareas \
    --token "$RUNNER_TOKEN" --name tareas-local --labels tareas-local --replace
fi
unset RUNNER_TOKEN
exec ./run.sh
