FROM ubuntu:24.04
RUN apt-get update && DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends openssh-server python3 python3-docker docker.io ca-certificates \
    && rm -rf /var/lib/apt/lists/* && mkdir /run/sshd \
    && echo 'root:root' | chpasswd \
    && printf 'PermitRootLogin yes\nPasswordAuthentication yes\n' > /etc/ssh/sshd_config.d/laboratorio.conf
EXPOSE 22
CMD ["/usr/sbin/sshd", "-D", "-e"]
