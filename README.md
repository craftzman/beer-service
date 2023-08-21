# beer-service
Demo new features of spring boot 3
* Spring Data JDBC
* Spring Boot Docker compose
* Spring Boot Test Container
## Requirement
* Java version 17.x.x or higher 
## Installation
Use maven to build, install and run
### Install
```bash
mvn clean install
```
### Run
```
mvn spring-boot:run
```
### Run from Jar file
#### Jar file usage
After executing maven build command, the jar file can be found under
```
<location>/loan-request-service/target/
```
Copy to location you desire or just run it directly from target folder
```
java -jar loan-request-service-0.0.1-SNAPSHOT.jar
```
### Run from Docker image
#### image usage

Run this command to build Docker image (Require docker running on your system)
```
mvn spring-boot:build-image
```
After the build process is done, then start an application image with this command
```
docker run --rm -p 8080:8080 loan-request-service:0.0.1-SNAPSHOT
```

#### Usage

* After the project started the main url is [loan-request-service](http://localhost:8080/)

* Project also provides [swagger page](http://localhost:8080/api/swagger-ui/index.html) here
