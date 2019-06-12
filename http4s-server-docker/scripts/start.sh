#!/usr/bin/env bash
cd xgboost-evaluator
sbt compile
sbt publishLocal
cd ../http4s-server
sbt compile
sbt run
