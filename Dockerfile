FROM jenkins:1.642.1
USER root

RUN echo "deb http://apt.dockerproject.org/repo debian-jessie main" \
          > /etc/apt/sources.list.d/docker.list \
      && apt-key adv --keyserver hkp://p80.pool.sks-keyservers.net:80 \
          --recv-keys 58118E89F3A912897C070ADBF76221572C52609D \
      && apt-get update \
      && apt-get install -y apt-transport-https \
      && apt-get install -y sudo \
      && apt-get install -y docker-engine \
      && rm -rf /var/lib/apt/lists/*

RUN echo "jenkins ALL=NOPASSWD: /usr/bin/docker" >> /etc/sudoers
RUN echo "jenkins ALL=NOPASSWD: /usr/local/bin/docker-compose" >> /etc/sudoers

RUN usermod -aG docker jenkins

RUN curl -L https://github.com/docker/compose/releases/download/1.5.2/\
docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose; \
    chmod +x /usr/local/bin/docker-compose

RUN mkdir /jobs && chown -R jenkins /jobs

USER jenkins
COPY plugins.txt /usr/share/jenkins/ref/
RUN /usr/local/bin/plugins.sh /usr/share/jenkins/ref/plugins.txt

COPY add_user.groovy /usr/share/jenkins/ref/init.groovy.d/add_user.groovy

COPY import_jobs.groovy /usr/share/jenkins/ref/init.groovy.d/import_jobs.groovy


ENV JENKINS_USERNAME jenkins
ENV JENKINS_PASSWORD jenkins

ENV PROJECT_REPO https://github.com/BCCVL/jenkinsprojects.git
ENV PROJECT_BRANCH master

ENV DOCKER_PUSH 0
ENV DOCKER_EMAIL nobody@example.com
ENV DOCKER_USERNAME nobody
ENV DOCKER_PASSWORD secret
