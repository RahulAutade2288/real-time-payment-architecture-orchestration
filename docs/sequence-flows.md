# Real-Time Payment Sequence Flows

This document describes the logical sequence of steps that a payment goes through in the **Real-Time Payment Architecture and Orchestration** model.

The flows are illustrative and can be adapted to specific schemes such as Zelle, RTP®, FedNow®, or SEPA Instant.

---

## 1. Core Payment Flow (Happy Path)

**Actors / Components:**

- Channel / API Client  
- `PaymentOrchestrator`  
- Validation layer (`PaymentValidator`, etc.)  
- ISO 20022 layer (`Iso20022CreditTransfer`, factories, validators)  
- `FraudCheckService` / `RiskScoringService`  
- `PaymentRouter` and scheme-specific routing strategies  
- `PostingService` / `LedgerService`  
- `NotificationService` / `EventPublisher`  

**Steps:**

1. **Initiation**
   - A client or channel calls the payment initiation endpoint.
   - A payment request is created in the `PaymentOrchestrationContext`.

2. **Authentication & Basic Checks**
   - The caller is authenticated (conceptually).
   - Basic request integrity checks are performed.

3. **Validation**
   - `PaymentValidator` triggers specific validators:
     - `AmountValidator` checks amount ranges and limits.
     - `AccountStatusValidator` ensures the debtor account is active.
     - `CutoffTimeValidator` ensures the request is within acceptable processing windows.
     - `SanctionsScreeningValidator` and `DuplicatePaymentValidator` perform additional compliance checks.

4. **Enrichment & ISO 20022 Mapping**
   - Party and account information may be enriched using `CustomerService` or reference data.
   - The payment is mapped into an `Iso20022CreditTransfer`.
   - `Iso20022SchemaValidator` and `Iso20022XmlSerializer` can validate or serialize the message if required.

5. **Fraud and Risk Checks**
   - `FraudCheckService` and/or `RiskScoringService` evaluate the payment.
   - A decision is made: approve, decline, or request additional actions.

6. **Routing**
   - `PaymentRouter` assesses the routing strategy.
   - `SchemeRouter`, `BankRouter`, and `CurrencyRouter` may contribute to selecting the target rail or endpoint.
   - `FallbackRouteStrategy` provides alternative paths when the primary route is unavailable.

7. **Posting and Settlement**
   - `PostingService` and `LedgerService` handle the posting to internal or core systems.
   - Balances are adjusted and confirmation is recorded.

8. **Notification and Events**
   - `NotificationService` informs the payer/payee (e.g., SMS, email, push).
   - `EventPublisher` emits lifecycle events for monitoring, analytics, and audit.

9. **Completion**
   - `PaymentOrchestrator` marks the flow as completed in the `PaymentOrchestrationContext`.
   - Final status is returned to the caller (success, rejected, or pending).

---

## 2. Error and Compensation Flow (Conceptual)

Not all payments follow the happy path. Errors can occur at multiple stages.

**Examples:**

- Validation failure (invalid account, insufficient data)
- Risk or fraud rejection
- Routing failure (destination unreachable)
- Posting error (core system timeout)

**High-level behavior:**

1. The failing step is recorded in the `PaymentOrchestrationContext`.
2. `SagaCoordinator` may trigger compensating actions if intermediate steps have already executed (e.g., reverse reservations, cancel pending messages).
3. `CompensationHandler` encapsulates the logic to safely unwind or mitigate partial processing.
4. `NotificationService` may send failure notifications.
5. `AuditService` and `EventPublisher` record error events for investigation.

---

## 3. Inquiry / Status Flow (Optional Extension)

Although not implemented in detail, the same architecture can support:

1. **Status inquiry** by payment reference or internal ID.
2. `PaymentLifecycleManager` retrieves the current state and history.
3. The system returns a consolidated view of:
   - Payment status (e.g., received, processing, sent, completed, rejected).
   - Timestamps for major orchestration steps.
   - Any relevant failure or exception details.

---

## 4. Relationship to Diagrams

The sequence flows described here are visually represented in:

- `diagrams/high-level-architecture.puml`  
- `diagrams/payment-sequence-flow.puml`  

These diagrams can be rendered using PlantUML and adapted for presentations, design reviews, or documentation packages.
