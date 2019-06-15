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

#cd http4s-server/
#echo
#echo "> building sfps-server"
#docker build -t sfps-server .

cd ..
echo
echo "> Done!"
echo
echo "> Now you can start anythin with 'docker-compose up db [service]' (dbloader, spark, or server)."
echo "> Or you can just 'docker-compose up' and see everything go up."
