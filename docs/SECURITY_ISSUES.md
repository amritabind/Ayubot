# 4.5 Security Issues — AyuBot

AyuBot handles sensitive data: user accounts, appointment records, uploaded reports and datasets, and generated analytics. This document lists project-specific security issues, the controls already implemented, and recommended enforcement points targeted to the AyuBot codebase.

## 1. Real-Time Security Considerations (project-specific)

- Real-time features in AyuBot include chat, live consultations, appointment updates, and notifications surfaced in the frontend files (e.g., the chat and consultation pages).
- Authenticate and authorize WebSocket/WSS connections at connect time using the same tokens as REST APIs.
- 
- Validate and sanitize dataset/report uploads (handled by backend controllers) before enqueueing background jobs.

## 2. Potential Security Issues (mapped to AyuBot)

1. Unauthorized access to user accounts (AuthController endpoints and Admin pages)
2. Password theft or exposure (user registration and login flows)
3. Cross-user data leakage (dashboards, reports, and dataset ownership)
4. Uploading malicious or large files (report upload, dataset import in controllers)
5. Unauthenticated API calls to endpoints under `/api/*` (public controllers)
6. Injection attacks via dataset parsing or user inputs (background parsers, templates)

## 3. Security Measures Implemented (how they apply to AyuBot)

### a) Password Handling

### b) Authentication & Tokens

- 

### c) Data Isolation by `user_id`

### d) Upload Validation & Safe Processing


## 4. Security Policy and Architecture (AyuBot mapping)

The security policy follows a layered architecture implemented across AyuBot components. Key deployment flows:

- Edge (HTTPS/WAF) → Ingress → Stateless Spring Boot app nodes (`backend/ayubot`) authenticated via JWT.
- Real-time: WebSocket endpoints on app nodes using brokered pub/sub (Redis/RabbitMQ) for fan-out.
- Background workers: isolated workers for dataset parsing, OCR, report generation with least privilege to storage/DB.
- Persistence: application DB and storage; encrypt sensitive fields and backups.
- Secrets: move credentials/config from `application.properties` to environment variables or secret manager before deployment.

Layered Security Architecture

1. Authentication Layer — User verification using JWT authentication and refresh token controls.
2. Authorization Layer — Role-based access control to ensure users access only permitted resources (ADMIN / DOCTOR / PATIENT).
3. Data Protection Layer — Password hashing (BCrypt), encryption at rest for sensitive fields, and secure backup practices.
4. Validation Layer — Input and file validation to prevent malicious data ingestion (schema checks, MIME/size validation, scanning).

Client‑Server Security Model (AyuBot specifics)

- The frontend (`frontend/` HTML pages and JS) performs initial input validation and holds short-lived tokens for UI calls.
- The backend (`backend/ayubot`) enforces authentication, authorization, fine-grained API protection, and audit logging.
- The database and object storage hold structured data and files; access restricted to app/service accounts in private subnets.
- Real‑time channels (WebSocket/WSS) are authenticated on connect and validated per-message when handling sensitive operations.
- Background workers run in isolated environments with least-privilege access to storage and DB and accept jobs only from trusted queues.



