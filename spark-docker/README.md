# spark-docker
_Docker with Java and spark_
  
Crea una imagen de docker con java y spark y ejecuta un job de scala.  
  
- Build scala job

   ```
    cd spark-sample
    sbt package
    ```
  
- Build & exec docker: 
   ```
    docker build -t spark-docker .
    docker run --name=spark -d -p 8080:8080 spark-docker tail -f /dev/null
    docker exec -it spark bash
    ```
    
- Instrucciones
    - Compilar el job de scala con sbt (esto genera un jar en target)
    - Compilar la imagen de docker
    - Correr la imagen de docker
    - Se puede ver el resultado del job con `docker logs spark`, ahí debería verse el resultado de la ejecución "Pi is roughly..."
    
    
  