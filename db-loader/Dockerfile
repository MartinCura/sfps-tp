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

COPY ./target/scala-2.12/db-loader-assembly-1.0.jar /db-loader.jar

ENTRYPOINT ["/usr/bin/java", "-jar", "/db-loader.jar"]
