# 1. OBJECTIVE
Build a comprehensive end-to-end ecommerce platform for a Madurai-based entrepreneur using a Spring Boot microservices architecture. The platform will support local and pan-India sales, multi-language support (English/Tamil), and regional payment/delivery integrations, with a custom HTML/JS/CSS frontend.

# 2. CONTEXT SUMMARY
- **Seller Location:** Madurai, Tamil Nadu.
- **Target Markets:** Local (Madurai/Tier-2 TN) and Pan-India.
- **Tech Stack:** Spring Boot, Spring Cloud (Gateway, Eureka, Config), MySQL, RabbitMQ/Kafka, Redis, HTML/JS/CSS.
- **Key Integrations:** Razorpay/Paytm (Payments), Shiprocket/Delhivery (Logistics).
- **Special Requirements:** i18n (English & Tamil), INR currency, Local self-delivery logic, Database DDLs for all entities.

# 3. APPROACH OVERVIEW
The system will follow the **Database-per-Service** pattern using **MySQL**. A **Spring Cloud Gateway** will act as the single entry point for a decoupled **HTML/JS/CSS frontend**. Asynchronous communication via a **Message Broker (RabbitMQ)** will handle event-driven processes. Localization will be handled via Spring's `MessageSource` and database-driven translation tables.

# 4. IMPLEMENTATION STEPS

### Phase 1: Infrastructure & Foundation
- **Step 1: Service Discovery & Config.** Implement Netflix Eureka Server and Spring Cloud Config Server.
- **Step 2: API Gateway.** Setup Spring Cloud Gateway for routing and CORS configuration (essential for HTML frontend).
- **Step 3: Identity & Access Management (IAM).** Create an Auth Service using Spring Security and JWT.

### Phase 2: Core Catalog & Inventory (MySQL)
- **Step 4: Database Design & DDL.** Generate MySQL DDL scripts for Product, Category, and Translation entities.
- **Step 5: Product Catalog Service.** Implement product management with i18n support.
- **Step 6: Inventory Service.** Generate MySQL DDLs for Stock and Procurement; implement stock tracking.
- **Step 7: User Profile Service.** Generate MySQL DDLs for Users and Addresses; manage language preferences.

### Phase 3: Order & Payment Orchestration (MySQL)
- **Step 8: Order Service.** Generate MySQL DDLs for Orders, OrderItems, and Cart; implement order state machine.
- **Step 9: Payment Service.** Generate MySQL DDLs for Payments and Transactions; integrate Razorpay/Paytm/COD.
- **Step 10: Event Integration.** Link Order and Payment services via Message Broker.

### Phase 4: Logistics & Communication (MySQL)
- **Step 11: Shipping Service.** Generate MySQL DDLs for Shipments and LocalZones; implement local vs. pan-India routing.
- **Step 12: Notification Service.** Generate MySQL DDLs for NotificationLogs; implement multi-channel alerts.

### Phase 5: Localization & Final Polish
- **Step 13: i18n Integration.** Setup global locale resolvers for English and Tamil.
- **Step 14: Admin Dashboard API.** Aggregated endpoints for the entrepreneur.

### Phase 6: Frontend Development (HTML/JS/CSS)
- **Step 15: UI Layout Design.** Create responsive HTML/CSS templates for Home, Product Details, Cart, and Checkout.
- **Step 16: API Integration.** Use JavaScript `fetch` or `axios` to consume the API Gateway endpoints.
- **Step 17: State & i18n Management.** Implement client-side language switching (English/Tamil) and local storage for cart persistence.
- **Step 18: Admin Panel.** Build a dedicated HTML interface for the seller to manage orders and inventory.

# 5. TESTING AND VALIDATION
- **Database Validation:** Verify MySQL DDL execution and schema integrity across all services.
- **Unit & Integration Tests:** JUnit 5 and Mockito for each service.
- **Frontend-to-Backend Flow:** 
    - User (HTML UI) -> Select Language (Tamil) -> Order Product -> Payment Gateway Redirect -> Order Confirmation.
- **API Validation:** Postman/Swagger collections.
- **End-to-End Flow:** Verify local delivery routing vs pan-India courier triggers.
