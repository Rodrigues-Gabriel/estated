# Welcome to the Estated Engineering Take Home Challenge

The idea of this challenge is to implement a fictional service to enable real estate investors to add, view and remove properties from their real estate portfolios.

## Application
* Tools version
  * Java 11
  * PostgreSQL 11.12
  * Spring Boot 2.5.0
  * Gradle 7.0.2
  
### Prerequisites:
* Define the following fields with a Postgre database credentials on ```src/main/java/resources/application.properties```
  * ```spring.datasource.url=jdbc:postgresql://<ip>:<port>/<database>```
  * ```spring.datasource.username=changeme```
  * ```spring.datasource.password=changeme```

### Run tests & Build the application 
```
gradle build --console plain
```

### Run the application
```
gradle bootRun --console plain
```
After running the application, the endpoints are available at ```http://localhost:8080```

### API Documentation
The API Documentation is available at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html). <br>
*Note: the application must be running in order to the documentation be available.*
