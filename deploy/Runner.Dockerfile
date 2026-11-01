FROM ubuntu:24.04
RUN apt-get update && DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends curl ca-certificates git jq tar gzip libicu74 libssl3t64 libkrb5-3 zlib1g python3 python3-venv sshpass openssh-client \
    && rm -rf /var/lib/apt/lists/* && useradd -m -u 1001 runner
RUN python3 -m venv /opt/ansible && /opt/ansible/bin/pip install --no-cache-dir ansible-core==2.16.14
ENV PATH="/opt/ansible/bin:${PATH}"
USER runner
WORKDIR /home/runner
ARG RUNNER_VERSION=2.338.2
RUN curl -fL --retry 3 "https://github.com/actions/runner/releases/download/v${RUNNER_VERSION}/actions-runner-linux-x64-${RUNNER_VERSION}.tar.gz" | tar xz
COPY --chown=runner:runner runner-entrypoint.sh /home/runner/entrypoint.sh
ENTRYPOINT ["bash", "/home/runner/entrypoint.sh"]
