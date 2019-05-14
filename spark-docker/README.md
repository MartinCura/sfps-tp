# spark-docker
_Docker with Java and spark_

Crea una imagen de docker con java y spark y ejecuta un job de scala.
  
- Build scala job

    ```
    cd spark-job-sample
    sbt package
    ```

- Build & exec docker:
    ```
    docker build -t sfps-spark .
    docker rm spark && docker create --name=spark -d -p 8080:8080 sfps-spark tail -f /dev/null
    docker start spark --interactive
    ```

- Instrucciones
    - Compilar el job de scala con sbt (esto genera un jar en target)
    - Compilar la imagen de docker
    - Correr la imagen de docker
    - Se puede ver el resultado del job con `docker logs spark`, ahí debería verse el resultado de la ejecución "Pi is roughly..."
