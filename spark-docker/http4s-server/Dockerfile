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


ENV SCALA_VERSION 2.12.8
ENV SCALA_TARBALL https://downloads.lightbend.com/scala/2.12.8/scala-2.12.8.deb
RUN curl -sSL $SCALA_TARBALL -o scala.deb             && \
    dpkg -i scala.deb

COPY ./target/scala-2.12/http4s-server-assembly-1.0.jar /server.jar

EXPOSE 8088

#DOES NOT WORK.
ENTRYPOINT ["scala", "/server.jar"]
