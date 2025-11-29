# Real-Time Payment Architecture – Overview

This document provides a high-level overview of the **Real-Time Payment Architecture and Orchestration** reference implementation. The goal is to illustrate how modern financial institutions can structure real-time payment flows using clean, modular components without tying to any specific vendor or framework.

---

## 1. Objectives

- Demonstrate a **reference architecture** for real-time digital payments.
- Show **orchestration** across validation, enrichment, fraud, routing, posting, and notifications.
- Use **ISO 20022–inspired models** to represent payment data.
- Keep the implementation **framework-agnostic**, using plain Java for clarity.
- Serve as a **teaching and discussion artifact** for architects, engineers, and researchers.

---

## 2. High-Level Architecture

At a high level, the system is composed of:

1. **Channel / API Layer**  
   Entry point for payment initiation requests (e.g., REST/JSON, message bus, UI).

2. **Orchestration Layer**  
   Coordinates the full payment journey using classes such as:
   - `PaymentOrchestrator`
   - `PaymentLifecycleManager`
   - `OrchestrationPipeline`
   - `OrchestrationStep` (enum)

3. **Domain Model & ISO 20022 Layer**  
   Represents payment information using ISO 20022–inspired classes:
   - `Iso20022CreditTransfer`
   - `PartyIdentification`
   - `AccountIdentification`
   - `Amount`
   - `RemittanceInformation`
   - and various helper/value objects

4. **Validation Layer**  
   Ensures that incoming payments are consistent, valid, and compliant:
   - `PaymentValidator`
   - `AmountValidator`
   - `AccountStatusValidator`
   - `CutoffTimeValidator`
   - `SanctionsScreeningValidator`
   - `DuplicatePaymentValidator`
   - `SchemaValidator`

5. **Routing Layer**  
   Determines which scheme, network, or destination path should be used:
   - `PaymentRouter`
   - `SchemeRouter`
   - `BankRouter`
   - `CurrencyRouter`
   - `FallbackRouteStrategy`

6. **Service Layer**  
   Encapsulates key business services and side effects:
   - `FraudCheckService`
   - `RiskScoringService`
   - `PostingService`
   - `NotificationService`
   - `AuditService`
   - `EventPublisher`
   - `LedgerService`
   - `CustomerService`

7. **Infrastructure & Observability (Conceptual)**  
   Although not tied to any specific technology in the code, the architecture assumes:
   - Event streaming (e.g., Kafka topics for payment lifecycle events)
   - Logging and metrics for traceability
   - Health checks, timeouts, and retries for resiliency

---

## 3. Component Responsibilities

### 3.1 Application Entry Point (`app` package)

- `RealTimePaymentApplication`  
  Simple `main` method used to bootstrap the demo and showcase how orchestrators and services can be wired together.

- `ApplicationConfig`  
  Represents configuration placeholders (e.g., endpoints, limits, flags) that can be replaced by real config sources.

- `Bootstrapper`  
  Creates and links instances of orchestrators, validators, routers, and services.

---

### 3.2 Orchestration Layer (`orchestration` package)

Key classes:

- `PaymentOrchestrator`  
  Central coordinator for the payment journey. Calls validators, ISO 20022 transformers, fraud checks, and routing logic.

- `PaymentOrchestrationContext`  
  Holds contextual information about the payment during its journey (IDs, timestamps, decisions, intermediate results).

- `OrchestrationPipeline` and `WorkflowDefinition`  
  Model the sequence of steps to be executed for a given payment type or scheme.

- `OrchestrationStep`  
  Enum listing the canonical steps in the payment lifecycle (RECEIVE_REQUEST, AUTHENTICATE, VALIDATE, ENRICH, etc.).

- `SagaCoordinator` and `CompensationHandler`  
  Concepts for handling long-running workflows and compensating actions in case of partial failure.

---

### 3.3 ISO 20022 Layer (`iso20022` package)

Represents payment data aligned to ISO 20022 concepts:

- `Iso20022CreditTransfer`  
  Core payment message representation (similar in spirit to pacs.008).

- `PartyIdentification`, `AccountIdentification`, `FinancialInstitution`, `PostalAddress`  
  Identify parties, accounts, and institutions in a structured way.

- `Amount`, `ChargesInformation`  
  Capture financial amounts and fee-related details.

- `RemittanceInformation`  
  Supports narrative or structured remittance data.

- `Iso20022MessageFactory`, `Iso20022XmlSerializer`, `Iso20022SchemaValidator`, `Iso20022Constants`  
  Provide helpers for constructing, serializing, and validating ISO 20022 messages.

---

### 3.4 Validation, Routing, and Services

- **Validation**  
  Ensures correctness, limits, sanctions, and data quality before the payment proceeds further.

- **Routing**  
  Resolves the target scheme or destination (e.g., internal, domestic instant, cross-border) using flexible strategies.

- **Services**  
  Represent integration points with:
  - Fraud / risk engines
  - Core banking / ledger systems
  - Notification channels (SMS, email, push, etc.)
  - Audit and event streaming platforms

---

## 4. Non-Functional Considerations

While the code is intentionally lightweight, the architecture is intended to support:

- **Low latency**, suitable for real-time payments.
- **High availability and resiliency**, through retries, isolation of components, and eventual consistency patterns.
- **Auditability and traceability**, using structured events and clear step definitions.
- **Extensibility**, by adding new steps, schemes, or services without large rewrites.

---

## 5. How to Use This Architecture

- As a **starting point** for internal design discussions on real-time payments.
- As a **sandbox** to prototype orchestration logic, ISO 20022 handling, or routing strategies.
- As a **teaching tool** for junior engineers learning about payment system design.
- As a **supporting artifact** in professional/academic portfolios and expert evidence dossiers.
