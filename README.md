# Parking Lot System — Low-Level Design in Java

A parking lot management system designed to demonstrate object-oriented programming, extensible pricing and payment strategies, and core low-level design principles using Java.

The project models the core operations of a parking facility, including vehicle parking, spot allocation, ticket generation, fee calculation, payment processing, and vehicle exit. The focus is on keeping business logic modular and making the system easier to extend as requirements evolve.

## Overview

Parking lot systems involve more than assigning an available spot to a vehicle. A well-structured implementation must handle spot availability, vehicle compatibility, parking duration, pricing policies, payment outcomes, and the lifecycle of a parking ticket.

This project approaches the problem using object-oriented design and established design patterns to separate responsibilities and reduce coupling between components.

### Key capabilities

- **Vehicle management:** Models different vehicle types, including bikes, cars, and trucks.
- **Parking floor management:** Organizes parking spots into floors and supports searching for available spots.
- **Vehicle allocation:** Searches for a compatible spot before generating a parking ticket.
- **Ticket lifecycle:** Tracks active parking sessions using unique ticket identifiers.
- **Flexible pricing:** Supports interchangeable pricing strategies, including time-based and event-based pricing.
- **Payment processing:** Separates payment handling from the core parking workflow.
- **Payment modes and statuses:** Uses enums to represent supported payment modes and payment outcomes.
- **Parking status:** Displays the availability and occupancy status of parking spots.
- **Vehicle exit:** Calculates parking fees, processes payment, releases the occupied spot, and removes the active ticket after successful payment.

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 21 | Core language and object-oriented design |
| Spring Boot | Application bootstrap and project configuration |
| Maven | Dependency management and build lifecycle |
| Lombok | Reduces boilerplate code in model classes |
| Java Collections Framework | Manages floors, spots, and active tickets |
| Java Time API | Represents entry, exit, and parking timestamps |
| UUID | Generates unique ticket identifiers |

## Architecture and Design

The system is organized around a central parking lot service, with dedicated models, factories, and strategy implementations.

```text
                    ParkingLotApplication
                            |
                            v
                       ParkingLot
                            |
             +--------------+--------------+
             |              |              |
             v              v              v
       ParkingFloor    Active Tickets   PricingStrategy
             |                             |
             v                    +--------+--------+
        ParkingSpot                |                 |
                                   v                 v
                            TimeBasedPricing   EventBasedPricing

                       Vehicle Entry / Exit
                               |
                               v
                       PaymentProcessor
                               |
                               v
                      PaymentStrategyFactory
                               |
                               v
                      Selected PaymentStrategy
```

The diagram represents the main conceptual relationships; the exact runtime wiring depends on the implementation.

### 1. Domain models

The model layer represents the main entities involved in the parking workflow.

- `Vehicle`: Base abstraction for vehicles.
- `Bike`, `Car`, `Truck`: Concrete vehicle types.
- `ParkingFloor`: Groups parking spots and finds available spots.
- `ParkingSpot`: Represents an individual spot, its permitted vehicle type, and occupancy state.
- `Ticket`: Represents a parking session and stores its identifier, vehicle, entry time, floor ID, and spot ID.
- `Gate`, `EntryGate`, `ExitGate`: Represent gate-related domain concepts.

This separation keeps vehicle-specific behavior and parking-related state out of the main orchestration logic wherever practical.

### 2. Parking lot service

`ParkingLot` coordinates the main parking operations.

Its responsibilities include:

- Managing parking floors.
- Finding an available spot for a vehicle.
- Creating and registering parking tickets.
- Calculating the applicable parking fee.
- Coordinating payment processing.
- Releasing a spot after successful payment.
- Maintaining the collection of active tickets.

The class currently follows the Singleton pattern, providing a shared parking lot instance through `getInstance()`.

### 3. Pricing strategies

The pricing module supports alternative fee-calculation policies through the `PricingStrategy` abstraction.

Implementations include:

- `TimeBasedPricing`: Calculates fees according to the configured time-based policy.
- `EventBasedPricing`: Provides an alternative pricing policy for event-based scenarios.

The pricing policy can be selected through `PricingStrategyFactory` and changed through the configured strategy reference.

This design avoids embedding every pricing rule directly into the parking workflow.

### 4. Payment processing

Payment handling is separated from parking and pricing responsibilities.

The payment-related components include:

- `PaymentStrategy`: Abstraction for payment behavior.
- `PaymentStrategyFactory`: Selects a payment strategy for the requested payment mode.
- `PaymentProcessor`: Coordinates payment execution.
- `PaymentMode` and `PaymentStatus`: Enums for representing payment choices and outcomes.

This structure allows new payment methods to be introduced without rewriting the entire parking workflow, provided they follow the existing strategy contract.

## Design Patterns Used

### Singleton Pattern

**Where:** `ParkingLot`

The Singleton pattern provides a shared instance of the parking lot.

```java
private static final ParkingLot INSTANCE = new ParkingLot();

private ParkingLot() {
}

public static ParkingLot getInstance() {
    return INSTANCE;
}
```

**Why:** Multiple independent parking lot instances could otherwise maintain conflicting views of floors and active tickets in a single-lot simulation.

**Trade-off:** A Singleton introduces global state and makes isolated testing more difficult. A dependency-injected instance or explicit lifecycle management may be preferable in a larger application.

### Strategy Pattern

**Where:** Pricing and payment modules

The Strategy pattern encapsulates interchangeable algorithms behind common interfaces.

**Why:** Pricing rules and payment behavior can evolve independently of the main parking workflow.

**Trade-off:** Additional interfaces and implementation classes introduce some complexity, but become useful when multiple policies need to be supported.

### Factory Pattern

**Where:** `PricingStrategyFactory` and `PaymentStrategyFactory`

Factories centralize the selection of an appropriate strategy based on an enum or another selection criterion.

**Why:** The main service does not need to construct every strategy implementation directly.

**Trade-off:** As the number of strategies grows, factory mappings must be maintained. Dependency injection or registry-based selection can become useful at larger scale.

### Object-Oriented Design Principles

The implementation applies several core OOP principles:

- **Encapsulation:** Keeps domain state and related operations within appropriate classes.
- **Abstraction:** Uses interfaces to define pricing and payment contracts.
- **Polymorphism:** Allows different implementations to be used through a common strategy type.
- **Separation of concerns:** Distinguishes parking management, pricing, and payment processing.

The objective is not to apply patterns everywhere, but to use them where they reduce coupling and make changing requirements easier to accommodate.

## Core Workflows

### 1. Parking a vehicle

1. Receive the vehicle and its entry timestamp.
2. Iterate through the configured parking floors.
3. Ask each floor to find a compatible available spot.
4. If a spot is found, generate a unique ticket ID.
5. Create a ticket containing the vehicle, entry time, floor ID, and spot ID.
6. Register the ticket in the active-ticket collection.
7. Return the ticket to the caller.
8. If no compatible spot exists, return `null` and report that parking is unavailable.

A ticket must only be generated after successful spot allocation.

### 2. Calculating the parking fee

1. Retrieve the active ticket.
2. Validate that the ticket exists.
3. Obtain the vehicle type and entry timestamp from the ticket.
4. Use the selected pricing strategy with the entry and exit timestamps.
5. Produce the fee according to the selected pricing policy.

The pricing implementation should define its own rules for duration rounding, minimum charges, and other applicable pricing conditions.

### 3. Processing vehicle exit

1. Receive the ticket ID, exit timestamp, and payment mode.
2. Retrieve the corresponding active ticket.
3. Reject the operation if the ticket does not exist.
4. Calculate the parking fee.
5. Select the payment strategy.
6. Process the payment.
7. If payment fails, preserve the active ticket and occupied spot.
8. If payment succeeds, release the spot and remove the active ticket.
9. Report the completed exit and fee charged.

The key invariant is that a failed payment must not release the spot or invalidate the active parking session.

## Project Structure

```text
parking_lot/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/nailyourinterview/lld/parking_lot/
│   │   │       └── parking_lot/
│   │   │           ├── enums/
│   │   │           │   ├── GateType.java
│   │   │           │   ├── PaymentMode.java
│   │   │           │   ├── PaymentStatus.java
│   │   │           │   ├── PricingStrategyType.java
│   │   │           │   └── VehicleType.java
│   │   │           ├── factory/
│   │   │           │   ├── PaymentStrategyFactory.java
│   │   │           │   ├── PricingStrategyFactory.java
│   │   │           │   └── VehicleFactory.java
│   │   │           ├── model/
│   │   │           │   ├── Bike.java
│   │   │           │   ├── Car.java
│   │   │           │   ├── Truck.java
│   │   │           │   ├── Vehicle.java
│   │   │           │   ├── ParkingFloor.java
│   │   │           │   ├── ParkingSpot.java
│   │   │           │   └── Ticket.java
│   │   │           ├── pricing/
│   │   │           │   ├── PricingStrategy.java
│   │   │           │   ├── TimeBasedPricing.java
│   │   │           │   └── EventBasedPricing.java
│   │   │           ├── service/
│   │   │           │   ├── ParkingLot.java
│   │   │           │   └── PaymentProcessor.java
│   │   │           ├── strategy/
│   │   │           │   └── payment/
│   │   │           ├── utils/
│   │   │           │   └── DateTimeParser.java
│   │   │           └── ParkingLotApplication.java
│   │   └── resources/
│   └── test/
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

Note: The structure above summarizes the main packages and files. Refer to the repository for the complete implementation.

## Getting Started

### Prerequisites

- JDK 21 or a compatible configured Java environment.
- Git.
- IntelliJ IDEA or another Java IDE (optional).

Maven Wrapper is included, so a separate Maven installation is not required.

### Clone the repository

```bash
git clone https://github.com/VISHAL55UIET/ParkingLotLowLevelDesign.git
cd ParkingLotLowLevelDesign
```

If the repository contains an additional nested `parking_lot` directory, enter that directory before executing the Maven commands.

### Build the project

Windows PowerShell:

```powershell
.\mvnw.cmd clean compile
```

Linux or macOS:

```bash
./mvnw clean compile
```

### Run tests

Windows PowerShell:

```powershell
.\mvnw.cmd test
```

Linux or macOS:

```bash
./mvnw test
```

### Run the application

Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

Alternatively, open the project in IntelliJ IDEA and run `ParkingLotApplication.java`.

The application currently demonstrates the parking workflow through its configured application entry point. This README does not assume the existence of REST endpoints or a persistent database.

## Design Considerations and Trade-offs

### In-memory state

The current parking lot maintains floors and active tickets using Java collections.

This keeps the initial implementation simple and makes it suitable for learning and demonstrating LLD concepts.

However, in-memory state is lost when the process terminates. A production implementation would require persistent storage, transaction boundaries, and recovery mechanisms.

### Spot allocation and concurrency

Spot allocation must ensure that two concurrent parking requests cannot reserve the same spot.

A production implementation would need an explicit atomic allocation mechanism, such as synchronized critical sections or suitable database locking and transactional updates.

### Ticket lifecycle

An active ticket links the vehicle to its parking location and entry timestamp. Ticket creation, payment completion, and spot release should be coordinated to prevent inconsistent states.

### Extensibility

The strategy abstractions provide a foundation for adding pricing policies and payment implementations. Factories can be extended as new options become available.

For larger systems, dependency injection and configuration-driven strategy selection may reduce reliance on manually maintained factory logic.

### Testing

The most important behaviors to test include:

- Parking a vehicle when a compatible spot is available.
- Rejecting parking when no compatible spot exists.
- Ensuring a failed parking attempt does not create an active ticket.
- Calculating fees for different parking durations.
- Rejecting invalid ticket IDs during exit.
- Preserving the ticket and occupied spot when payment fails.
- Releasing the spot only after successful payment.
- Handling repeated exit requests safely.

## Potential Improvements

The following are possible future enhancements, not claims about existing functionality.

- Add automated unit tests using JUnit and Mockito.
- Introduce REST APIs for entry, exit, and parking status.
- Persist floors, parking spots, tickets, and payments in a relational database.
- Add atomic spot reservation and concurrent allocation tests.
- Introduce explicit transaction boundaries for payment and ticket lifecycle changes.
- Add configurable pricing rules, grace periods, and fee-rounding policies.
- Improve error handling with typed results or domain-specific exceptions instead of `null`.
- Add structured logging and centralized exception handling.
- Introduce integration tests for end-to-end parking and payment flows.

## Engineering Takeaways

This project is an exercise in translating real-world requirements into a maintainable object-oriented design.

It demonstrates how domain models, strategy abstractions, factories, and a coordinating service can work together to keep business rules understandable and extensible.

The most important design goal is correctness of the parking lifecycle: a vehicle should only receive a valid ticket after successful allocation, and a parking spot should only be released after the exit workflow completes successfully.

## Author

Vishal Singh

B.Tech Computer Science and Engineering | Java Backend Development

- GitHub: https://github.com/VISHAL55UIET
- LinkedIn: https://www.linkedin.com/in/vishal-singh-5b052828a/

---

If you find this project useful for learning object-oriented design and low-level system design, feel free to explore the code and share suggestions for improvement.
