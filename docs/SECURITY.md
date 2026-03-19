# AyuBot Security Policy — One Page

Purpose
- Provide a concise, actionable security policy for AyuBot covering authentication, data protection, deployment, and incident response.

Scope
- Applies to all AyuBot components (backend, frontend, background workers, storage), contributors, CI/CD pipelines, and deployed environments.

Responsibilities
- **Developers**: follow secure coding, use SCA, avoid committing secrets.
- **Security Owner**: maintain policy, run audits, coordinate incidents.

Core Controls
- **Authentication & Authorization**: enforce strong auth (OAuth2/JWT), short-lived access tokens, server-side RBAC checks, token revocation on logout/compromise.
- **Transport Security**: require HTTPS/WSS, HSTS, secure cookie flags (HttpOnly, Secure, SameSite=strict).
- **Data Protection**: encrypt sensitive fields at rest and in backups; use application-level encryption for PHI when needed.
- **Secrets Management**: no secrets in source; use environment variables or centralized vault (HashiCorp/Azure Key Vault). Rotate keys regularly.
- **Input & Output Safety**: validate inputs, use parameterized queries/JPA, sanitize outputs, apply CSP and XSS protections.
- **File Uploads**: validate MIME/type/size, store outside webroot or in object storage with signed URLs, scan for malware before processing.
- **Dependency Hygiene**: run SCA (Dependabot/Snyk) in CI, pin and update dependencies on a scheduled cadence.
- **Infrastructure Hardening**: least-privilege network rules, private subnets for DB/workers, IAM role limitations.

Operational Security
- **CI/CD**: fail builds on critical SCA findings; include container image scanning and DAST (OWASP ZAP) pre-deploy tests.
- **Monitoring & Audit**: structured logs, immutable audit trail for auth/privileged ops, metrics and tracing (Prometheus/Grafana/OpenTelemetry).
- **Backups & Recovery**: automated encrypted backups, regular restore drills, offsite retention.
.

Implementation Notes
- Start enforcement points in AyuBot at: backend/ayubot/src/main/java/com/ayubot/config/SecurityConfig.java and CI pipeline. Replace secrets in application.properties with vault/env references.

I'll create a short todo plan for drafting the real-time and security design, then produce the architecture and mitigation guidance.

Created 4 todos

**Real-Time Considerations**
- **Use cases**: chat messages, live consultations/notifications, appointment updates, presence/typing indicators.
- **Latency budget**: target ~<200ms for chat, <1s for notifications; background tasks asynchronous.
- **Transport choices**: WebSocket (Spring WebSocket / STOMP) for bidirectional chat; SSE for one-way updates; fallback to long-polling for legacy clients.
- **Message backbone**: brokered pub/sub (Redis Pub/Sub or RabbitMQ) to decouple producers/consumers and allow horizontal scaling.
- **Ordering & delivery**: sequence IDs, client acks, idempotent operations, retry with exponential backoff.
- **Scaling**: stateless Spring Boot nodes behind LB, shared Redis for presence/state, sticky sessions avoided by token auth.
- **Backpressure & QoS**: queue limits, per-user rate-limits, circuit breakers for overloaded downstreams.
- **Monitoring & SLOs**: metrics (Prometheus), tracing (OpenTelemetry), alerting on latency/error rates.

**Security Issues & Mitigations**
- **Authentication**: use strong auth (OAuth2/JWT via Spring Security). Store refresh tokens securely (HTTP-only, SameSite cookies) and rotate keys.
- **Authorization**: enforce RBAC and method-level checks; validate on server side (never rely on client).
- **Passwords & credentials**: store passwords with BCrypt; never log secrets; use secret manager for keys.
- **Transport security**: require HTTPS and WSS; HSTS and secure cookies.
- **Data at rest**: encrypt sensitive DB fields and backups; use DB built-in encryption or application-level field encryption for PHI.
- **Input validation & injection**: use parameterized queries/JPA, validate and sanitize inputs, enforce strict JSON schemas.
- **File uploads**: validate types and sizes, store outside webroot (or S3), scan files for malware, serve via signed URLs; keep uploads in [backend/ayubot/src/main/resources/static] out of public scope.
- **XSS/CSRF**: apply CSP, sanitize outputs, use CSRF tokens for forms and secure SameSite cookie settings for auth tokens.
- **Session & token security**: short-lived access tokens, refresh token revocation, token blacklisting on logout/compromise.
- **Rate limiting & brute-force protection**: per-IP & per-account throttling, progressive delays, account lockout policies with admin review.
- **Dependency supply chain**: SCA in CI (Dependabot/Snyk), pinned versions, regular updates.
- **Audit & logging**: immutable audit trail for auth/privileged actions, redact PII in logs, export to centralized SIEM.
- **Network controls**: VPC/subnet isolation, security groups, least-privileged service accounts.

**How the student will avoid these problems (practical steps)**
- **Spring Security integration**: enforce via [backend/ayubot/src/main/java/com/ayubot/config/SecurityConfig.java] and method annotations (`@PreAuthorize`).
- **Auth flow**: JWT access (short) + refresh tokens (secure cookie) with server-side revocation list.
- **Password hashing**: BCrypt; no plaintext.
- **Uploads**: persist to object storage (S3 or local equivalent) with signed URLs; scan on upload; store metadata in DB only.
- **Secrets**: move secrets from `application.properties` to env vars or a vault; never commit creds.
- **Secure defaults**: secure headers, CSP, HTTPS enforced at ingress/load balancer.
- **CI/CD checks**: add static code analysis, dependency scanning, and container image scanning into pipeline before deploy.
- **Ops controls**: firewall rules, DB access limited to app subnet, automated backups and tested restores.
- **Testing**: include automated security tests (OWASP ZAP/DAST), unit tests for auth/authorization paths, regression tests for upload handling.

**Security Policy Plans & Architecture (high-level)**
- **Client → Edge**: HTTPS terminated at load balancer / ingress with WAF.
- **Edge → App**: LB forwards to stateless Spring Boot instances (auth via JWT).
- **Real-time layer**: WebSocket gateway on app nodes connected to message broker (Redis/RabbitMQ) for fan-out.
- **Background processing**: job queue (RabbitMQ/Redis streams) -> worker pool for heavy tasks (OCR, parsing).
- **Persistence**: primary DB (Postgres) with replicas, encrypted storage for sensitive files (S3 or equivalent).
- **Secrets & config**: centralized secret manager (Vault/Azure Key Vault) + config in env.
- **Observability**: Prometheus + Grafana, structured logs to ELK, tracing with OpenTelemetry.
- **Network & infra**: VPC, private subnets for DB/workers, bastion for admin access, least-privilege IAM roles.
- **Data flow safeguards**: gating for PHI access (audit + approval), token-scoped access for APIs.

**Implementation Checklist (next practical items)**
- **Auth & policies**: finalize JWT refresh/revocation and RBAC rules in [backend/ayubot/src/main/java/com/ayubot/config/SecurityConfig.java].
- **Transport**: enable TLS on ingress, enforce HSTS and secure cookies.
- **Uploads**: implement virus-scan + signed-URL storage and size/type validation.
- **Broker & realtime**: provision Redis/RabbitMQ and integrate WebSocket endpoints with brokered pub/sub.
- **Secrets**: replace any secrets in `application.properties` with env/vault references.
- **CI security**: add SCA and DAST to CI pipeline.
- **Monitoring/Audit**: add metrics, tracing, and immutable audit logs for security events.
- **Runbook**: produce incident response steps (token revocation, key rotation, DB restore).
