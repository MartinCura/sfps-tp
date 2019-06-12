# sfps-tp

Build de los proyectos

cd spark-docker/xgboost-trainer
sbt assembly

cd spark-docker/
docker build -t sfps-spark .

cd http4s-server-docker/
docker build -t sfps-server .

