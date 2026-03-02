# Microservices Lab - SE4010

## Overview
This is a complete microservices system built for the SLIIT SE4010 lab requirements. The system consists of four services that communicate through an API Gateway.

## System Architecture
```
Client (Postman/Browser)
         ↓
   API Gateway (Port 8080)
         ↓
┌────────┴────────┬─────────┐
│                 │         │
Item Service    Order     Payment
(Port 8081)   Service   Service
              (Port 8082) (Port 8083)
```

## Services

### 1. Item Service (Port 8081)
- **Technology**: Spring Boot (Java 17)
- **Endpoints**:
  - `GET /items` - Get all items
  - `POST /items` - Create new item
  - `GET /items/{id}` - Get item by ID

### 2. Order Service (Port 8082)
- **Technology**: Spring Boot (Java 17)
- **Endpoints**:
  - `GET /orders` - Get all orders
  - `POST /orders` - Place new order
  - `GET /orders/{id}` - Get order by ID

### 3. Payment Service (Port 8083)
- **Technology**: Spring Boot (Java 17)
- **Endpoints**:
  - `GET /payments` - Get all payments
  - `POST /payments/process` - Process payment
  - `GET /payments/{id}` - Get payment by ID

### 4. API Gateway (Port 8080)
- **Technology**: Spring Cloud Gateway
- **Routing**:
  - `/items/**` → Item Service
  - `/orders/**` → Order Service
  - `/payments/**` → Payment Service

## Prerequisites
- Docker
- Docker Compose
- Java 17 (for local development)
- Maven (for local development)

## Setup and Running

### Option 1: Using Docker Compose (Recommended)
1. Build and start all services:
```bash
docker-compose build
docker-compose up
```

2. The services will be available at:
   - API Gateway: http://localhost:8080
   - Item Service: http://localhost:8081
   - Order Service: http://localhost:8082
   - Payment Service: http://localhost:8083

### Option 2: Running Locally
1. Build each service:
```bash
cd item-service
mvn clean package
java -jar target/item-service-0.0.1-SNAPSHOT.jar

cd ../order-service
mvn clean package
java -jar target/order-service-0.0.1-SNAPSHOT.jar

cd ../payment-service
mvn clean package
java -jar target/payment-service-0.0.1-SNAPSHOT.jar

cd ../api-gateway
mvn clean package
java -jar target/api-gateway-0.0.1-SNAPSHOT.jar
```

## Testing with Postman

### Import Collection
1. Open Postman
2. Import the collection from `postman/microservices-lab.postman_collection.json`
3. Run the requests through the API Gateway (http://localhost:8080)

### Sample Requests

#### Get all items:
```
GET http://localhost:8080/items
```

#### Create new item:
```
POST http://localhost:8080/items
Content-Type: application/json

{
  "name": "Headphones"
}
```

#### Place order:
```
POST http://localhost:8080/orders
Content-Type: application/json

{
  "item": "Laptop",
  "quantity": 2,
  "customerId": "C001"
}
```

#### Process payment:
```
POST http://localhost:8080/payments/process
Content-Type: application/json

{
  "orderId": 1,
  "amount": 1299.99,
  "method": "CARD"
}
```

## Docker Commands

### Build services:
```bash
docker-compose build
```

### Start all services:
```bash
docker-compose up
```

### Start in background:
```bash
docker-compose up -d
```

### View logs:
```bash
docker-compose logs
docker-compose logs item-service
```

### Stop services:
```bash
docker-compose down
```

## Project Structure
```
microservices-lab/
├── item-service/           # Item microservice
├── order-service/          # Order microservice
├── payment-service/        # Payment microservice
├── api-gateway/            # API Gateway
├── postman/                # Postman collection
├── docker-compose.yml      # Docker orchestration
└── README.md              # This file
```

## Key Features
-✅ All services containerized with Docker
-✅ Communication through API Gateway only
- ✅ No direct service-to-service calls
- ✅ RESTful API endpoints
- ✅ In-memory data storage
- ✅ Comprehensive testing via Postman
-✅ Proper routing configuration

## Learning Outcomes Achieved
- ✅ Understand microservices communication through API Gateway
-✅ Apply containerization with Docker and Docker Compose
-✅ Design and expose REST API endpoints
- ✅ Test multi-service systems using Postman
- ✅ Practice polyglot development concepts

## Submission Requirements
This repository contains:
-✅ Complete working microservices system
- ✅ Docker setup with docker-compose.yml
- ✅ Postman collection for testing
- ✅ Proper documentation (this README)
- ✅ All required API endpoints implemented

## Troubleshooting

### Services not starting
- Check if ports 8080-8083 are available
- Ensure Docker is running
- Check Docker logs: `docker-compose logs`

### Gateway routing issues
- Verify service names in docker-compose.yml match the gateway configuration
- Check that all services are running: `docker-compose ps`

### Connection refused
- Ensure services are started before testing
- Check if the API Gateway is running on port 8080