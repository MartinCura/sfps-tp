#!/usr/bin/env bash

HOST=$(cat /etc/hostname)
./sbin/start-master.sh
./bin/spark-class org.apache.spark.deploy.worker.Worker  spark://${HOST}:7077 -c 1 -m 2048M &
./bin/spark-submit --class="SparkPi" --master spark://${HOST}:7077 /jobs/spark-sample.jar