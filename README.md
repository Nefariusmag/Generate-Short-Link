# Generate-Short-Link

This application will generate short link for you

## How to start

1. Install Java 11 and higher and Maven 3, Docker
2. Start Postgres 13 use Docker
```shell
docker run -p <your port for postgres>:5432 -e POSTGRES_PASSWORD=<your password for postgres> -it --rm postgres:13
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

# Problems

Don't have time for test, because couldn't mock sql db requests for this time