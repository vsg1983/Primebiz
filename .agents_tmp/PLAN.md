# 1. OBJECTIVE
Build a comprehensive end-to-end ecommerce platform for a Madurai-based entrepreneur using a Spring Boot microservices architecture. The platform will support local and pan-India sales, multi-language support (English/Tamil), and regional payment/delivery integrations.

# 2. CONTEXT SUMMARY
- **Seller Location:** Madurai, Tamil Nadu.
- **Target Markets:** Local (Madurai/Tier-2 TN) and Pan-India.
- **Tech Stack:** Spring Boot, Spring Cloud (Gateway, Eureka, Config), PostgreSQL, RabbitMQ/Kafka, Redis.
- **Key Integrations:** Razorpay/Paytm (Payments), Shiprocket/Delhivery (Logistics).
- **Special Requirements:** i18n (English & Tamil), INR currency, Local self-delivery logic.

# 3. APPROACH OVERVIEW
The system will follow the **Database-per-Service** pattern to ensure loose coupling. A **Spring Cloud Gateway** will act as the single entry point. Asynchronous communication via a **Message Broker (RabbitMQ)** will handle event-driven processes (e.g., Order Placed -> Inventory Deduct -> Notification Sent). Localization will be handled via Spring's `MessageSource` and database-driven translation tables for product content.

# 4. IMPLEMENTATION STEPS

### Phase 1: Infrastructure & Foundation
- **Step 1: Service Discovery & Config.** Implement Netflix Eureka Server and Spring Cloud Config Server for centralized configuration.
- **Step 2: API Gateway.** Setup Spring Cloud Gateway for routing, rate limiting, and global CORS configuration.
- **Step 3: Identity & Access Management (IAM).** Create an Auth Service using Spring Security, JWT, and OAuth2 for user authentication and Role-Based Access Control (Admin vs. Customer).

### Phase 2: Core Catalog & Inventory
- **Step 4: Product Catalog Service.** Implement product management, category hierarchies, and i18n support for product names/descriptions (Tamil/English).
- **Step 5: Inventory Service.** Create stock tracking, low-stock alerts, and procurement logging.
- **Step 6: User Profile Service.** Manage customer data, multiple shipping addresses, and language preferences.

### Phase 3: Order & Payment Orchestration
- **Step 7: Order Service.** Implement order creation, status management (State Machine), and cart logic.
- **Step 8: Payment Service.** Integrate Razorpay/Paytm APIs for UPI and Card payments; implement logic for Cash on Delivery (COD).
- **Step 9: Event Integration.** Link Order and Payment services via Message Broker (e.g., `OrderCreated` event triggers payment request).

### Phase 4: Logistics & Communication
- **Step 10: Shipping Service.** 
    - Local delivery module for Madurai (zone-based routing).
    - Third-party API integration for Pan-India (Shiprocket/Delhivery).
- **Step 11: Notification Service.** Implementation of Email, SMS, and WhatsApp alerts for order updates, localized in the user's preferred language.

### Phase 5: Localization & Final Polish
- **Step 12: i18n Integration.** Setup global locale resolvers for English and Tamil across all user-facing APIs.
- **Step 13: Admin Dashboard API.** Aggregated endpoints for the entrepreneur to manage procurement and delivery.

# 5. TESTING AND VALIDATION
- **Unit & Integration Tests:** JUnit 5 and Mockito for each service.
- **End-to-End Flow:** 
    - User (Tamil locale) -> Search Product -> Place Order -> Pay via UPI -> Order Status updated to 'Processing'.
    - Local Order -> Assigned to self-delivery.
    - Pan-India Order -> Shiprocket label generated.
- **API Validation:** Postman/Swagger collections for all microservice endpoints.
- **Load Testing:** Basic stress tests on the Gateway to ensure stability.
