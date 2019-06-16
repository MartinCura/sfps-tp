#!/bin/bash
cd spark-docker/
echo
echo "> sbt assembly"
sbt assembly

cd xgboost-trainer/
echo
echo "> building sfps-spark"
docker build -t sfps-spark .
cd ..

cd db-loader/
echo
echo "> building sfps-dbloader"
docker build -t sfps-dbloader .
cd ..

echo
echo "> building sfps-server"
docker build -t sfps-server .

cd ..
echo
echo "> Done!"
echo
echo "> Now you can start any service with 'docker-compose up db [service]' (dbloader, spark, or server)."
echo "> Or you can just do 'docker-compose up' and see everything go up."
