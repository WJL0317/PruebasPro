FROM openjdk:17 
COPY "./target/PruebasPro-0.0.1-SNAPSHOT.jar" "app.jar"
EXPOSE 8045
ENTRYPOINT [ "java", "-jar", "app.jar" ]