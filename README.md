# bsa-tracking-application
Tracking application is to track skill that people have


BSATrackingApplication has been built using JAVA 8,Spring boot (2.5.2),Spring security, microservices,in-built H2 database as the backend.

The application has 2 Rest APIs

People API
Skills API
People API lets a user to add/update/ delete people to the repository The API also lets a user to manage the skills recorded for the person - skill Id, skill name, skill level To fetch the details of a people - person Id, person name and the skills recorded for the person

- GET request  to fetch details of all people - localhost:8072/people
- GET request  to fetch details of a specific person - localhost:8072/people/{personId} 
- People can be added using a POST request to the URL - localhost:8072/people
- People/person and any field associated with that person (inlcuding skills)can be updated my making a PUT request to the URL-  localhost:8079/people/{personId}
- People can be deleted by making a DELETE request to the URL - localhost:8072/people/{personId}
- GET request  to fetch details of skills recorded for a person - localhost:8072/people/{personId}/skills
- GET request to fetch a specific skill recorded for a person - localhost:8072/people/{personId}/skills/{skillId}
- Skills can be recorded for a person using a POST request to the URL - localhost:8072/people/{personId}/skills
Skills API REST API built which lets a user to add / update / delete a skill.

- To fetch the details of skills - skill name, skill Id and skill Level 
-  GET request to fetch all skills  - localhost:8072/skills
- Skills can be added using a POST request to the URL - localhost:8072/skills
- Skills can be updated my making a PUT request to the URL- localhost:8072/skills/{skillId}
- Skills can be deleted by making a DELETE request to the URL - localhost:8072/skills/{skillId}
- A specific skill details can be fetched using a GET request to URL - localhost:8072/skills/{skillId}

To change the port number (in case 8072 is already used on your machine, kindly change it in application.properties file)
Prerequisites :

JAVA (version 8 or above) should be installed on the system. Maven should be installed IDE (Eclipse / Intelli J) should be installed (in case api should be run using an IDE) Installation TO install the project, follow the steps below - clone / download the git project using git clone and git checkout commands.

USING AN IDE

Import the projects using any IDE (IntelliJ / eclipse). Open the BSATrackingApplication.java and right click and run the class. USING A JAR

The API can also be run using a jar To run the api using jar - browse to the location where the project has been downloaded / cloned Run the command mvn clean install , which would create the jar - tracking-app-0.0.1-SNAPSHOT.jar under target folder 4 browse to target and run the command java -jar tracking-app-0.0.1-SNAPSHOT.jar , which would start the application and can be tested using a browser

Assumptions : The API has been developed based on certain assumptions -

- People refer to more than one person
- Person Id mentioned above refers to the Id generated for the person which is autogenerated by JPA in this application
- Skill Id mentioned above refers to the id generated for the person which is autogenerated by JPA in this application
