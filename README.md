# spring-unittest-dbdialect
Doing unit tests in a Spring Boot project with a custom db dialect (PostGIS) and an external database (Postgres).

The default Spring (Boot) unit test setup uses the embedded db (H2) by default.  It works for many (95% ?) cases.  
But, when trying to use a custom db dialect, and an external real db instance, such as Postgres, for doing the unit tests, it's not very clear how to do.  And, I had hard time finding any complete examples.  

Using Spring's @EnableAutoConfiguration or @EnableAutoConfiguration, etc. would only make things worse.  I don't want to use @SpringBootTest either, that'd start the main app and it'd become an integration test.

In this example, I used PostGIS as the special db extension, which required a dedicated dialect.

This project was based on 2 other projects found in github, as stated in the source code.
