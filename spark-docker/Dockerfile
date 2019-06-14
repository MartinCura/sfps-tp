FROM ubuntu:16.04

RUN apt-get -y update
RUN apt-get -y install curl
RUN apt-get -y install software-properties-common

# JAVA
RUN apt-get update && \
    DEBIAN_FRONTEND=noninteractive \
    apt-get -y install default-jre-headless && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64
ENV PATH $PATH:$JAVA_HOME/bin

#Solución muy lenta (al menos por ahora que estoy construyendo la imagen a cada rato. L.)
# SBT
#ENV SBT_VERSION 1.2.8
#RUN \
#  curl -L -o sbt-$SBT_VERSION.deb https://dl.bintray.com/sbt/debian/sbt-$SBT_VERSION.deb && \
#  dpkg -i sbt-$SBT_VERSION.deb && \
#  rm sbt-$SBT_VERSION.deb && \
#  apt-get update && \
#  apt-get install sbt && \
#  sbt sbtVersion


# SPARK
ARG SPARK_ARCHIVE=https://archive.apache.org/dist/spark/spark-2.4.2/spark-2.4.2-bin-hadoop2.6.tgz
ENV SPARK_HOME /usr/local/spark-2.4.2-bin-hadoop2.6

ENV PATH $PATH:${SPARK_HOME}/bin
RUN curl -s ${SPARK_ARCHIVE} | tar -xz -C /usr/local/

WORKDIR $SPARK_HOME

COPY ./xgboost-trainer/target/scala-2.12/trainer-assembly-1.0.jar /jobs/spark-trainer.jar
COPY ./scripts/start.sh /scripts/start.sh
RUN chmod 777 /scripts/*

# Spark Web UI and history monitoring
EXPOSE 8080
EXPOSE 18080

ENTRYPOINT ["/bin/bash", "-c", "/scripts/start.sh"]
