Request the below from browser when passenger are less than 4 and greater than 0:

http://localhost:8888/busyflightApi/search/DEL/SYD/23-05-2017/23-06-2017/4


Request from browser when passenger are greater than 4 OR less than 1(as provided in UseCase) : http://localhost:8888/busyflightApi/search/DEL/SYD/23-05-2017/23-06-2017/5
 
 Response:
   {"errorMessage":"Flight search with origin DEL destination SYD departureDate 23-05-2017 returnDate 23-06-2017 passengers limit should be less than 4"}
   
Repository:
  busyflights : Which is used run your http request above. 
  
Main Class for Spring Boot :
  BusyFlightsApplication.java (/busyflights/src/main/java/com/travix/medusa/busyflights/BusyFlightsApplication.java)
   
  
  Steps:
  We need to start busyflights using spring boot from class BusyFlightsApplication.java as Java Application.
  
  What Exactly we had done :
  1) We are using Spring boot Error Classes for Error Handling
  2) We had used JAVA 8 features to sort and iterate
  3) Logging File configured in application.properties using Spring Boot
  4) In application.properties used other features too like server port, Logging, etc
  5) Make external File to configure URL Toughjet and Crazyair  
  6) Based on SOA architecture
  
  
  
  What need to be Done Later.
  1) We had implemented Executor framework of Concurrent API but this can be improved but due to time constraint we had haven't implemented many other features thread.
   
  2) Due to time constraint JUnit is not implemented