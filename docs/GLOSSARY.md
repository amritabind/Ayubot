Glossary — AyuBot

API (Application Programming Interface): A set of endpoints that allow clients and services to communicate.

Authentication: The process of verifying user identity (e.g., login). Often implemented with JWT or sessions.

Authorization: Rules that control what authenticated users are allowed to do (role-based access).

Backend: Server-side application (Spring Boot) that implements business logic, APIs, and persistence.

Controller: A web-layer class that exposes HTTP endpoints and delegates to services.

Service: Business-logic component that orchestrates operations and transactions.

Repository: Data-access component (JPA) that reads/writes entities to the database.

Entity / Model: A domain object persisted to the database (e.g., `User`, `MedicalReport`).

DTO (Data Transfer Object): Lightweight structures used to exchange data between client and server.

Worker / Background Job: Asynchronous process (queue consumer) that handles long-running tasks (OCR, AI analysis).

Queue: Message queue used to decouple producers (services) from consumers (workers) for async processing.

Dataset: Structured data uploaded by users (CSV, Excel) for processing and visualization.

OCR (Optical Character Recognition): Technique to extract text from images or PDFs (Tesseract.js used here).

AI Analysis: Model-driven processing (Groq/LLaMA) that interprets extracted text and provides insights.

Report: Medical document uploaded by users (PDF, image) analyzed and stored by the system.

Report Share: Record that grants another user access to a report with optional message and audit info.

Consultation: Patient-submitted symptoms and doctor notes; stored as part of clinical workflow.

Appointment: Scheduled meeting between patient and doctor with date, type, and status.

Dashboard: Aggregated visual representation of dataset metrics and charts.

JWT (JSON Web Token): A compact token format used for stateless authentication.

BCrypt: Password hashing algorithm used to securely store user passwords.

Encryption at rest: Storing data in encrypted form on disk or storage services.


MFA (Multi-Factor Authentication): Security mechanism requiring multiple proofs of identity.

CI/CD: Continuous Integration/Continuous Delivery — automated build, test, and deploy pipelines.


Audit Trail: Logged record of actions (who did what and when) for compliance and debugging.



Presigned URL: Time-limited URL granting temporary access to a protected file in storage.


