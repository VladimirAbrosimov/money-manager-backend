# Money Manager Backend

## Introduction
Backend for money-manager-application. Frontend located located in the repository [money-manager-frontend](https://github.com/VladimirAbrosimov/money-manager-frontend)

## Quick Start

### Run Docker
1. Pull postgres image from Docker Hub:
```
docker pull postgres:13.6-alpine
```
When ready, run it:
```
docker run -d -p 5432:5432 postgres:13.6-alpine POSTGRES_USER=postgres POSTGRES_PASSWORD=pass POSTGRES_DB=moneymanager_db
```
Postgres will run by default on port `5432`

2. Pull money-manager-backend image from Docker Hub:
```
docker run -d -p 8080:8080 vabrosimov/money-manager-backend:latest SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/moneymanager_db SPRING_DATASOU
RCE_USERNAME=postgres SPRING_DATASOURCE_PASSWORD=pass
```
When ready, run it:
```
docker run -d -p 8080:8080 vabrosimov/money-manager-backend:latest
```
Money Manager Backend will run by default on port `8080`
