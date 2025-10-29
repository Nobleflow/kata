# Kata - Order Management System

## Description

Kata is a Spring Boot application that provides an order management system with support for multiple delivery types, reactive endpoints, and event-driven architecture using Kafka. The application includes features for time slot management, HATEOAS support, and real-time order streaming.

## Features

- **Multiple Delivery Types**
  - Drive-through pickup (DRIVE)
  - Standard home delivery (DELIVERY)
  - Same-day delivery (DELIVERY_TODAY) - must be ordered before 4 PM
  - Express ASAP delivery (DELIVERY_ASAP) - within 2 hours

- **Time Slot Management**
  - Configurable delivery time slots per delivery type
  - Automatic validation of selected time slots
  - Flexible configuration via application.yaml

- **Reactive Architecture**
  - Non-blocking endpoints using Project Reactor
  - Reactive streams for real-time data
  - Server-Sent Events support for order streaming

- **HATEOAS Support**
  - Hypermedia links in API responses
  - Self-descriptive REST API

- **Event-Driven Integration**
  - Kafka integration for publishing order events
  - Asynchronous message processing

## Technologies

- **Java 17** (minimum required version)
- **Spring Boot 3.4.1**
  - Spring Web & WebFlux
  - Spring Data JPA
  - Spring Security with OAuth2
  - Spring Kafka
  - Spring Actuator
  - Spring HATEOAS
- **PostgreSQL** (production)
- **H2** (testing)
- **Lombok** for reducing boilerplate
- **MapStruct** for object mapping
- **Maven** for build management

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- PostgreSQL database (for production)
- Kafka (for event streaming)

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/Nobleflow/kata.git
cd kata
```

### Configuration

Edit `src/main/resources/application.yaml` to configure your database and Kafka settings:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/kata
    username: your_username
    password: your_password
```

### Build the Project

```bash
./mvnw clean install
```

### Run the Application

```bash
./mvnw spring-boot:run
```

The application will start on port 8083 by default.

### Using Docker Compose

The project includes a Docker Compose configuration for easy setup:

```bash
docker-compose up
```

## API Endpoints

### Orders

- **GET** `/api/orders` - Retrieve all orders
- **POST** `/api/orders` - Create a new order
- **GET** `/api/orders/{id}` - Get a specific order by ID
- **GET** `/api/orders/stream` - Stream orders in real-time (Server-Sent Events)

### Example: Create an Order

```bash
curl -X POST http://localhost:8083/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "deliveryType": "DELIVERY",
    "timeSlot": "14:00-15:00",
    "orderAt": "2025-10-29T10:00:00",
    "client": {
      "firstName": "John",
      "lastName": "Doe",
      "address": "123 Main St"
    },
    "products": [
      {
        "label": "Product A"
      }
    ]
  }'
```

## Delivery Type Rules

### DRIVE
- Customer picks up at store location
- Available time slots: 09:00-10:00, 10:00-11:00, 11:00-12:00

### DELIVERY
- Standard home delivery
- Available time slots: 14:00-15:00, 15:00-16:00, 16:00-17:00

### DELIVERY_TODAY
- Same-day delivery
- Must be ordered before 4 PM (16:00)
- Available time slots: 17:00-18:00, 18:00-19:00

### DELIVERY_ASAP
- Express delivery within 2 hours
- No specific time slot selection needed
- Delivery window is automatically set

## Testing

Run the test suite:

```bash
./mvnw test
```

The tests use an in-memory H2 database and do not require external dependencies.

## Project Structure

```
src/
├── main/
│   ├── java/com/crafteam/kata/
│   │   ├── conf/              # Configuration classes
│   │   ├── controller/        # REST controllers
│   │   ├── convertor/         # Entity-DTO mappers
│   │   ├── dto/               # Data Transfer Objects
│   │   ├── enums/             # Enumeration types
│   │   ├── kafka/             # Kafka producers/consumers
│   │   ├── model/             # JPA entities
│   │   ├── repository/        # Data access layer
│   │   └── services/          # Business logic layer
│   └── resources/
│       └── application.yaml   # Application configuration
└── test/
    ├── java/                  # Test classes
    └── resources/
        └── application-test.yaml  # Test configuration
```

## Code Improvements Made

1. **Fixed Java version compatibility** - Changed from Java 21 to Java 17
2. **Fixed typo** in application.yaml (postgrese → postgresql)
3. **Fixed critical bug** in OrderMapper.toEntity() method
4. **Added comprehensive JavaDoc comments** to all classes
5. **Fixed naming inconsistency** - Changed Status field to lowercase
6. **Added missing field mappings** in converters
7. **Removed duplicate dependency** in pom.xml
8. **Replaced System.err with proper SLF4J logging**
9. **Added test configuration** for H2 database
10. **Improved error messages** with more context

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the terms specified in the pom.xml file.

## Contact

For questions or support, please open an issue on the GitHub repository.
