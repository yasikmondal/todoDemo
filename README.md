# Sample To Do Demo web application using Spring Boot and PstgresSQL

A simple Todo Demo application using Spring Boot with the following options:

- Spring JPA and postgreSQL for data persistence

To build and run the sample from a fresh clone of this repo:

## Configure postgreSQL

1. Create a database in your postgreSQL instance.
2. Update the application.properties file in the `src/main/resources` folder with the URL, username and password for your postgreSQL instance. The table schema for the Todo objects will be created for you in the database.


## Build and run the sample

1. `mvnw package`
3. `java -jar target/TodoDemo-0.0.1-SNAPSHOT.jar`
3. Open a web browser to http://localhost:8080
