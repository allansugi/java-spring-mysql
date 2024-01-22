# java-spring-mysql
This project uses Java Spring boot and Spring MVC
with MySQL as the database inside docker container

This backend server focuses on REST API with user registration and authentication 
as well as for account and password storage. Additional feature might be added in the future

This app is currently under development and has not been finished.

## Instructions

To start the application
````bash
docker compose up -d --build
````

To stop the application and delete all data in database
```bash
docker compose down -v
```

To stop the application while keeping the data in database, remove `-v` flag
```bash
docker compose down
```

## Diagrams

### UML Diagram
coming soon

### Database Diagram
coming soon
