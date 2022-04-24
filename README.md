# Generate-Short-Link

This application will generate short link for you

## How to start

1. Install Java 11 and higher and Maven 3, Docker
2. Start Postgres use Docker
```shell
docker run -p 5432:5432 -e POSTGRES_PASSWORD=postgres -it --rm postgres
```
3. Build the project use:
```shell
mvn package
```
4 . Add to env all required variables to connect to the DB:
DB_HOST, DB_PORT, DB_NAME, DB_LOGIN, DB_PASSWORD
5. Start the application:
```shell
java -jar target/shortlinks-1.0.0.jar
```
   