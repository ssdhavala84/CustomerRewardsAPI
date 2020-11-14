This API proof of concept addresses the below listed requirement
Requirement: Develop rest API using Java, Spring Boot, and REST
§  A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent over $50 in each transaction
(e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).
§  Given a record of every transaction during a three month period, calculate the reward points earned for each customer per month and total.
§  Make up a data set to best demonstrate your solution

Tools and Technologies used: 
IDE:  Spring Tool Suite 4 ,Version: 4.7.0.RELEASE, Build Id: 202006181331
Java:   version:  1.8 
Database: In memory H2 database for persistence
Testing: Postman
In the project directory, you can run:
Running the Project as Stand-Alone jar

1.	Create stand-alone uber jar from project
2.	Command to execute the jar
Testing the API:
1.	Using postman or any web browser call/open the URL http://localhost:8080/customers/rewards?summary=monthly&depth=3
Note: Have used the default port 8080, this can be changed if the port is occupied with a different value in application.properties file server.port
Note: Only monthly data summary being captured, with 3 months summary being the default. However, by changing depth query parameter value from [1 to12] respective depth monthly summary can be obtained
2.	Test data is persisted in H2 database. The data is loaded from data.sql file in the project. url for h2 database console http://localhost:8080/h2

Alternatives:
For this use case CQRS pattern may be a good candidate, where the data model for inserts and the aggregated data we query for reads is different. Implementing a Rest API to query the aggregated data model. 
This solution is for POC purpose only. However, in real time environments, for each transaction inserts or updates, a trigger-based capturing of the aggregates in database.
Then querying already aggregated data from data gives better scalability and performance and control via the Rest API. 
