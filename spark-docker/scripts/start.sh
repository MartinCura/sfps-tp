#!/usr/bin/env bash
NCORES=4

HOST=$(cat /etc/hostname)
mkdir /tmp/spark-events
./sbin/start-history-server.sh
./sbin/start-master.sh
./bin/spark-class org.apache.spark.deploy.worker.Worker  spark://${HOST}:7077 -c ${NCORES} -m 2048M &
./bin/spark-submit --class="SparkPi" --master spark://${HOST}:7077 /jobs/spark-sample.jar

# Si queremos que solo termine con `docker stop spark`
echo "Spark finished, press Ctrl+C to end its Web UI... " && tail -f /dev/null

./sbin/stop-history-server.sh
