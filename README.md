# iotDemo

This project is of a microservice that has two parts:
- Component RabbitMQSender that sends to a rabbitMQ queue every 10 seconds a json message based in OpenGate API with random values
- Component RabbitMQReceiver that creates a listener to the rabbitMQ queu that receives and process messages. It calculates a series of statistical data with the       values contained in the message and stores this calculations in a mongo database.

Steps to run this project. This has been tested with IntelliJ IDE with docker plugin and DockerDesktop
- Build jar file (Maven clean and Maven install)
- Run file docker-compose.yml (needs docker plugin for IntelliJ)

This steps should create the container "ampliademo" that is composed of three containers:
  - rabbitMQ - container with the rabbitMQ queue
  - mongo - container with mongoDB database where data is going to be stored
  - micro-streaming-analytics - container with the microservice
  
The data stored by the microservice can be seen with a mongoDB client (like mongoDB Compass) connecting to the URI "mongodb://localhost:27018" to the database "local", collection "ampliaDemo".

Note: The microservice has a test implmented but disabled because if the user doesn't have a mongoDB database and a rabbitMQ to connect to, it wont be able to create the jar file and docker won't start the microservice. To enable just remove the @Disabled anottation in file MicroStreamingAnalyticsApplicationTests
