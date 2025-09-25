Food Order System

📌 Project Overview
Food Order System is a backend application designed to simplify restaurant order management.
The system allows customers to browse the menu, place orders, and track their order status.
Restaurant staff can manage orders, update their status, and administrators have full control over all orders.
The project is built with Spring Boot, Spring Security, JWT, and Spring Data JPA.

👥 User Roles

Customer
 2.Registration & login
 3.Browse menu
 4.Create orders
 5.Track order status

Restaurant Staff
 1.Manage and update order statuses (e.g., preparing, delivering, completed)

Administrator
 1.View and manage all orders
 2.Perform administrative operations when necessary

⚙️ Core Features

Authentication
 1.POST /api/auth/register → Customer registration
 2.POST /api/auth/login → Login and get JWT token

Menu
 1.GET /api/menu → Get menu list
 2.GET /api/menu/{id} → Get details of a menu item
(Optional: GET /api/menu?category=Pizza&priceRange=10-20)

Orders
 1.POST /api/orders → Create a new order
 2.GET /api/orders/{id} → Get order details
 3.PUT /api/orders/{id} → Update order status
 4.GET /api/orders → List all orders (staff/admin only)

🗄️ Database Design

Entities:

1.User: id, username, email, password, role
2.Role: id, name
3.MenuItem: id, name, description, price, category
4.Order: id, userId (FK), orderDate, status, totalAmount
5.OrderItem: id, orderId (FK), menuItemId (FK), quantity, price

🚀 Setup & Run
1️⃣ Clone Repository
git clone https://github.com/Esgerkhanova/Online-Food-Order-System.git
cd Online-Food-Order-System

2️⃣ Install Dependencies
mvn clean install

3️⃣ Configure Database

spring.application.name=Food-order-system
spring.datasource.url=jdbc:postgresql://localhost:5432/food-order-system
spring.datasource.username=postgres
spring.datasource.password=aybeniz
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true


4️⃣ Run the Application
mvn spring-boot:run

5️⃣ Swagger Documentation

Once the app is running, open Swagger UI:

http://localhost:8080/swagger-ui/index.html

📬 API Workflow

Customer registers → POST /api/auth/register

Logs in and gets JWT → POST /api/auth/login

Views menu → GET /api/menu

Creates an order → POST /api/orders

Views order details → GET /api/orders/{id}

Staff updates order status → PUT /api/orders/{id}

Admin/staff view all orders → GET /api/orders

🧪 Testing

Use Postman to test endpoints

Write Unit & Integration Tests with JUnit and Mockito

🐳 Docker Deployment

Dockerfile:

FROM openjdk:17
WORKDIR /app
COPY target/food-order-system.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

Build Docker Image
docker build -t food-order-system .

Run Container
docker run -p 8080:8080 food-order-system


👨‍💻 Author

✍️Aybeniz Esgerxanova – Back-End Developer
