# server

`server` is a backend application written in Kotlin.

## Constitution
  - Framework
    - SpringBoot
  - Language
    - Kotlin
  - Test Tools
    - Junit5
  - DataBase
    - PostgreSQL
        
## Prerequisites
- Launch an instance of [**PostgreSQL**](https://www.postgresql.org/) database on your machine.

## Create Databases
in the project root directory
```bash
make create-bds
```

## Run
```bash
./gradlew bootrun
```

## Test
```bash
./gradlew test
```