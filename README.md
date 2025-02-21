# Parspec-OrderProcessing
This is a Spring Boot-based backend system that processes e-commerce orders. It supports:
- Order creation and retrieval via RESTful APIs.
- Asynchronous order processing using a queue.
- Simulating 1,000 or more concurrent orders via a test API.
- Metrics tracking for total orders and processing time.

## Explanation of design decisions and trade-offs :
A backend application where the user is allowed to place concurrent orders and they will be processed asynchronously. To achieve this I have used a Concurrent Queue like BlockingQueue(a thread-safe concurrent queue).
Whenever the application starts, I have spawned off a single thread (can increase the number of threads to process which will increase the throughput) to look into the BlockingQueue to see whether an order has been placed or not.
Now when an order is placed or if N number of orders have been placed concurrently then we have N threads which are accessing the BlockingQueue and are placing their orders. And once this queue has orders, we have a thread which we spawned in the very start of the application startup which will start processing the orders. To simulate the order processing I’m letting the thread sleep for random(between 1 to 10 seconds) time. By using JAVA Futures, I am able to simulate asynchronous thread behavior.
### Design Patterns used :
- Builder Design Pattern : have used this to create orders
- Singleton Pattern : OrderRepository and OrderService managed by spring are by default singleton
- Factory Pattern : Could be used to instantiate different types of orders in a scalable way.
- Observer Pattern(for Future) :  If notifications were needed after an order is processed, an event-driven approach could be implemented.
- Facade Pattern(for future) : To make sure only users with special privileges can access the /metrics endpoint.
### Exception Handling : 
Have used custom exceptions leveraging the RestAdviceController from spring to make sure the exceptions are handled gracefully and can also be viewed in the response.
### Trade-Offs : 
- Simplicity vs. Scalability: Chose an in-memory queue (BlockingQueue) instead of Kafka to keep it lightweight
- Asynchronous Processing : The current order processing happens asynchronously using ExecutorService(ThreadPool) and Futures, but adding distributed messaging (RabbitMQ, Kafka) could improve handling at scale.

## How to Run the Project :
1️) Prerequisites
- Java 17+
- Maven 3+
- PostgreSQL Database

2) Clone and Setup the Project :
    git clone
    cd orderProcessing

3) Import orderProcessing as a existing Maven project

4) Configure Database in application.properties (Let other properties be untouched)
    spring.datasource.url=jdbc:postgresql://localhost:5432/parspec
    spring.datasource.username={your_username}
    spring.datasource.password={your_password}

## API Endpoints :
### 1) Create an Order :
POST http://localhost:8080/api/orders :
    Request Body:
        {
            "userId": "550e8400-e29b-41d4-a716-446655440000",
            "itemIds": "item1,item2",
            "totalAmount": 199.99
        }

        
Response : 

![Image](https://github.com/user-attachments/assets/a411832b-9896-444d-9891-dd98d28d128e)

### 2) Get Order Status : 

 GET /api/orders/{order_id} :

 ![Image](https://github.com/user-attachments/assets/123f80bd-ae68-4bbb-8a79-efd1a8205567)

 Invalid orders : 
 
![Image](https://github.com/user-attachments/assets/5f133a62-7a9c-4192-af2d-2b0cd317f01f)

### 3) Get Metrics : 

 GET http://localhost:8080/api/metrics : 
 
![Image](https://github.com/user-attachments/assets/8858fc85-8c0a-432d-ac60-3d429f07ced1)

### 4) Simulate N number of concurrent orders (have also written Junit to simulate 1000 concurrent orders but have exposed an endpoint for placing N concurrent orders) :

  POST : http//localhost:8080/api/test/concurent-orders?count={N}
  
![Image](https://github.com/user-attachments/assets/d062b830-b9c6-47e3-9750-8c951dbe6cd9)

  Logging on server asynchronously after placing the order :
  
![Image](https://github.com/user-attachments/assets/dd4d2e09-8941-4dff-82a9-87b2008e62ef)




