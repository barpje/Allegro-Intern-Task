# Allegro Intern Task 2021

Task was about creating server application which allows fetching repositories (name, stars) and total number of stars for any GitHub user. 

## Technology stack
- Java 11
- Spring Boot
- Retrofit - to make connection with GitHub API
- Lombok
## Installation
You need:
- JDK 13 or later
- maven

In order to compile and run the code go to the exercise directory and type:
```bash
mvn clean package
```
then go to the "target" folder and type:
```bash
java - jar Exercise_no-1.0-SNAPSHOT-jar-with-dependencies.jar
```


To run the tests just type:

```bash
mvn test
```
## List of endpoints:
- GET: /user/{username}/repositories
- GET: /user/{username}/repositories/all
- GET: /user/{username}/stars
