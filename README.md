# 🚆 Feedback-Scoring
There are a multitude of things that influences the daily experience of commuters on
public transport - congestion and disruptions being the major ones. We believe, that
there are other less visible influences that have significant impacts too.
Route Scores strives to “make the invisible visible” through data, with 
a collection of some anonymous route sentiment scores. The application's task is to summarise
these scores and see if they can help in identifying other influences.

## 🤓 Assumptions: 
- You have mysql installed and running
- Postman installed and running
- You have Java 8 or higher installed

# 🚀 Starting the application

- Execute the `schema.sql` file to create the schema
- Change the mysql user and password configuration in the application.yml if need be to suit your configuration
- open the application in your respective IDE for Java. Such as Intellij IDEA, VS Code or Netbeans
- run the application, build will start with all dependencies being downloaded, the service will be start up on port `9081`
- when the service is running import the `Whereismytransport.postman_collection.json` file in Postman
- send the HTTP request in the Postman collection in the following order
- `'Consume Files'` followed by `'Generate Results'`
- With the application still running and `'Consume Files'` Postman post request executed successfully
- The following `localhost:9081/produce-results` in a web browser will download a text file called `results` 
  with the calculated scores for each route should be output, from highest to lowest, along with the day of the week.

The calculated scores for each route should be output, from highest to lowest, along
with the day of the week.

# Unit Tests

## Running unit tests

Run `FileInputServiceTest` file to execute the unit tests which use the H2 database


## 📝 License

Copyright © 2019 [Malesela Sithole](https://github.com/HoodLum-1).<br />
This project is [MIT](https://github.com/HoodLum-1/route-scores/blob/main/LICENSE) licensed.
