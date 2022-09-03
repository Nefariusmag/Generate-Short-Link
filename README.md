# Generate-Short-Link

This application will generate short link for you

## How to build

1. Install Java 11 or higher, Maven 3 and Docker
2. Build the project:
```shell
mvn clean verify
```

## How to start

1. Start Postgres 13 use Docker
```shell
docker run -p <your port for postgres>:5432 -e POSTGRES_PASSWORD=<your password for postgres> -it --rm postgres:13
```
2. Add to env all required variables to connect to the DB:
DB_HOST, DB_PORT, DB_NAME, DB_LOGIN, DB_PASSWORD
3. Start the application:
```shell
java -jar target/shortlinks-1.0.0.jar
```
