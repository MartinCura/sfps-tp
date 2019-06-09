FROM mozilla/sbt:latest

COPY ./http4s-server /http4s-server
COPY ./xgboost-evaluator /xgboost-evaluator
COPY ./scripts/start.sh /scripts/start.sh
RUN chmod 777 /scripts/*

ENTRYPOINT ["/bin/bash", "-c", "/scripts/start.sh"]