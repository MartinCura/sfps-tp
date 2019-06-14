#!/bin/bash
cd spark-docker/
echo
echo "> sbt assembly"
sbt assembly

echo
echo "> building sfps-spark"
docker build -t sfps-spark .

cd db-loader/
echo
echo "> building sfps-dbloader"
docker build -t sfps-dbloader .

cd ../../http4s-server-docker/
echo
#echo "> building sfps-server"
#docker build -t sfps-server .
# ToDo: UNCOMMENT !

cd ..
echo
echo "> Done!"
echo
echo "> Now you can start everything with 'docker-compose up'."
echo "> If you hadn't loaded the database yet, you will need to re-up everything after db-loader done."
