# api_exercise

This exercise makes use of testng framework so that "dependsOnMethods" annotation parameter can be used.

The GET call is made with the built-in java HTTP Connection client. I researched alternatives such as Apache's lib too but given that I was going to make minimal use of the lib, only to retrieve a JSON response, the existing solution did suffice.

Google's gson library was used to handle/deserialize the incoming JSON response.

Again, maven was used for dependency and build management.

Instructions to run:
mvn test will execute the tests.
mvn surefire-report:report-only will create a surefire report.

  
