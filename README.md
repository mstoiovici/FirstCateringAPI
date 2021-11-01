### **Web service API project** ###

This is a RESTful API built per specifications of a Level 4 Apprenticeship 
Programme's synoptic project.

The API is built for First catering Ltd for use at food kiosks at Bows Formula 
One High Performance Cars locations.
Employees of Bows Formula One High Performance Cars will be able to use their 
existing employee cards on site kiosks to register and top up with money in 
order to buy food/drinks.

Tools used for its implementation:
* Java 
* Spring Initializer
* Spring boot framework
* H2 database
* Postman (desktop version as the service is run on localhost)

Instructions for the API project:
* clone project with:
    * 
* for running the web service:
    * from IDE run: **src/main/java/firstcatering/api/Application.java**
    * from terminal run with the maven tool: **mvn spring-boot:run**
    
* for running the tests:
    * from IDE run: 
        * **src/test/java/firstcatering/api/controller/EmployeeCardControllerTest.java**
        * **src/test/java/firstcatering/api/model/EmployeeCardTest.java**
    * from terminal run with the maven tool: **mvn test**
    
* for manual tests using Postman run :
    * get all employee cards:
      * GET localhost:8080/cards
    * get single employee card: 
      * GET localhost:8080/cards/6h7f9jh6GR879Y7Q
    * register a card: 
      * POST localhost:8080/cards/register?employee-id=123a&name=yohoo&email=me@enterprise.com&mobile=07543765565&pin=1235&card-id=6h7f9jh6GR879aaa 
    * get balance for an employee card: 
      * GET localhost:8080/cards/6h7f9jh6GR879aaa/balance
    * add a top up to an employee card: 
      * PUT localhost:8080/cards/8HK54dr9GTyh7680/top-up?amount=5
    * freeze an employee card: 
      * PUT localhost:8080/cards/6h7f9jh6GR879aaa/freeze
      
    The browser can be used for manual testing using the same URLs as above.

* for interaction with the H2 database:
    * go to [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
    * make sure JDBC URL is: jdbc:h2:mem:testdb
    * enter credentials:
        * username=user
        * password=1234
    * for visualising the EMPLOYEE_CARDS table run *SELECT * FROM EMPLOYEE_CARDS*  





