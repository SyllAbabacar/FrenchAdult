# FrenchAdult

 How to Run the Application

    Spring Boot is compatible with Apache Maven 3.3 or above. If you do not already have Maven installed, you can follow the instructions at maven.apache.org
    
    Open the terminal and go to the project root folder
    run the commande : mvn spring-boot:run


 How to uses the Rest API UserRegisters(French Adult)  
 
    To retrieve all users
      GET: /api/users
      
    To retrieve user with a ID
      GET: /api/users/id 
      
    To register a user
      POST:  /api/users
         Constrains :
             -  name :  without special characters and numerics
                        between 3 and 50 characters
             -  birthdate > 18
             -  country of residence : france
            *Optional
             -  phone number : (+)0033 X XX XX XX XX
             -  gender : M or F
                  
        
    To update user with a ID
       PUT /api/users/id
       
    To Delete user with a ID
      DELETE /api/users/id
      
 H2 database 
 
    Acces h2 console : localhost:8082/h2-console/ 
      
 Logs
 
     All requests and Response are logged 
     Path of Logs /logs/user_request.log
     
 Swagger : 
 
     http://localhost:8082/swagger-ui.html
     
       
