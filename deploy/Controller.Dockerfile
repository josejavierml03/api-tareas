FROM python:3.12-slim
RUN apt-get update && apt-get install -y --no-install-recommends openssh-client sshpass && rm -rf /var/lib/apt/lists/*
RUN pip install --no-cache-dir ansible-core==2.16.14 requests==2.32.3
COPY requirements.yml /tmp/requirements.yml
RUN ansible-galaxy collection install -r /tmp/requirements.yml
WORKDIR /workspace
