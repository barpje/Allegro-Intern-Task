# Allegro Intern Task 2021

Task was about creating server application which allows fetching repositories (name, stars) and total number of stars for any GitHub user. 

## Technology stack
- Java 11
- Spring Boot 2.4.5
- Retrofit 2.9.0 - to make connection with GitHub API
- Lombok
## Installation
You need:
- JDK 11 or later
- maven

In order to compile and run the code go to the exercise directory and type:
```bash
mvn clean package
```
then go to the "target" folder and type:
```bash
java -jar AllegroInternTask-1.0.jar
```


To run the tests just type:

```bash
mvn test
```
## Limitations:
- From GitHub API: For unauthenticated requests, the rate limit allows for up to 60 requests per hour. Unauthenticated requests are associated with the originating IP address, and not the user making requests.
- accept-type: application/json 
## List of endpoints:
- GET: /user/{username}/repositories\
Return list of user public repositories with pagination(as default first 30 repositories). Use single synchronous Retrofit call.\
-- parameters: username in path\
-- optional: per_page(default = 30, max = 100) and page in query\
Example of usage: 
/user/allegro/repositories\
/user/allegro/repositories?per_page=60&page=1
---
- GET: /user/{username}/repositories/all\
Return list of all user public repositories. Use multiple asynchronous Retrofit calls to improve speed.\
-- parameters: username in path\
Example of usage: 
/user/allegro/repositories/all
---
- GET: /user/{username}/stars\
Return number of stars in all user public repositories. Use multiple asynchronous Retrofit calls to improve speed.\
-- parameters: username in path\
Example of usage: 
/user/allegro/stars
---
For async calls i used CompletableFuture