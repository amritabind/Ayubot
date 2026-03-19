Spiral Model — Ayubot Project

Overview
- The Spiral Model organizes development as repeated cycles (iterations) that each include: Planning → Risk Analysis → Prototyping → Development → Evaluation.
- Each cycle reduces key risks and delivers a working increment.

Iteration planning (suggested 6–8 week cycles)

Iteration 0 — Inception & Foundations (2–3 weeks)
- Objectives: confirm requirements, architecture, CI/CD basics, dev environment, core data model.
- Risk analysis: unclear requirements, environment mismatch, sensitive data handling.
- Prototype: minimal backend scaffold (Spring Boot app), DB schema migration, sample API endpoints.
- Deliverables: repo skeleton, DB migration, README, authentication prototype.

Iteration 1 — Core Authentication & User Management (4 weeks)
- Objectives: implement secure registration/login, user profiles, role-based access, `users` and `patient_profiles` tables.
- Risks: security flaws in auth, password storage, account enumeration.
- Mitigation: use bcrypt, JWT or secure sessions, rate-limiting, automated tests for auth flows.
- Prototype: sign-up/login UI + API, sample protected endpoint.
- Deliverables: auth endpoints, user CRUD, tests, docs.

Iteration 2 — Medical Reports Upload & Storage (4–6 weeks)
- Objectives: file upload flow, secure storage, `medical_reports` entity, basic processing pipeline.
- Risks: large file handling, storage cost, unvalidated files.
- Mitigation: limit file size, validate types, store files outside DB, virus scanning stub.
- Prototype: upload endpoint with temp storage + status polling.
- Deliverables: upload API, background worker stub, storage integration.

Iteration 3 — OCR / AI Extraction & Report Sharing (4–6 weeks)
- Objectives: OCR/extraction, AI analysis pipeline, `report_shares` flows for secure sharing.
- Risks: unreliable AI output, privacy issues, slow processing.
- Mitigation: async jobs, human-review flow, data anonymization options.
- Prototype: small OCR/AI demo pipeline (local or mocked external API).
- Deliverables: processing workers, share API, notification hooks.

Iteration 4 — Consultations & Appointments (3–4 weeks)
- Objectives: create `consultations` and `appointments` flows, doctor/patient interactions, notifications.
- Risks: scheduling conflicts, timezone/format issues.
- Mitigation: simple availability model first, later integrate calendars.
- Deliverables: consultations APIs, appointment scheduler, tests.

Iteration 5 — Dataset Upload, Processing & Dashboards (6–8 weeks)
- Objectives: dataset ingestion (CSV/Excel), data cleaning, dashboard generation, export/reporting.
- Risks: varied dataset schemas, scalability, performance of aggregations.
- Mitigation: schema inference, sample-size processing, caching precomputed aggregates.
- Prototype: processing worker that normalizes columns and produces chart-ready JSON.
- Deliverables: dataset API, processing worker, dashboard endpoints and frontend stubs.

Iteration 6 — Admin, Monitoring, Integrations & Hardening (3–4 weeks)
- Objectives: admin tools, audit logs, metrics, third-party integrations (email, AI service keys), security hardening.
- Risks: operational outages, leakage of secrets.
- Mitigation: monitor, vault/secrets, blue/green deploys for critical integrations.
- Deliverables: admin UI, logging/metrics, secrets management guidelines.

Per-iteration activities (applied every cycle)
- Planning: define scope and acceptance criteria for the cycle.
- Risk analysis: identify new/remaining risks and mitigation plans.
- Prototype: build or extend small prototype to de-risk main technology assumptions.
- Implementation: develop, write tests, and integrate components.
- Evaluation: demo, collect feedback from stakeholders, update backlog and priorities.

Artifacts and checkpoints
- Prototype code + README demonstrating risky features.
- Test reports (unit/integration), security scan summaries.
- Migration scripts and schema snapshots.
- Demo recordings or live demos with acceptance criteria checklist.

Risk management and mitigation strategies
- Security: keep auth and file handling isolated; use tested libraries (bcrypt, secure cookie libs).
- Privacy: store minimal PII where possible; use encrypted storage or secure buckets for files.
- Scalability: make processing async; use queues and scale workers independently.
- External dependency risk: mock external AI/email services in CI and implement retries + circuit breakers.

Roles and responsibilities
- Product owner: prioritizes features and acceptance criteria for each iteration.
- Technical lead/architect: defines architecture and approves prototypes.
- Backend engineers: implement services, controllers, repositories, and background workers.
- Frontend engineer: builds necessary UI prototypes and integrates with APIs.
- QA/DevOps: tests, CI pipelines, deployments, monitoring.

Timeline example (6 iterations):
- Weeks 1–2: Iteration 0 (inception)
- Weeks 3–6: Iteration 1
- Weeks 7–10: Iteration 2
- Weeks 11–14: Iteration 3
- Weeks 15–20: Iteration 4 & 5 (parallel where possible)
- Weeks 21–24: Iteration 6 & stabilization

How to use this doc
- Use `docs/SPIRAL_MODEL.md` as a planning guide; break each iteration into issues/PRs in the repo.
- After each iteration, update risks and next-iteration scope based on stakeholder feedback.

Next steps I can take
- Convert iterations into a GitHub Projects board (issues and milestones).
- Produce checklist templates for iteration planning and demos.
- Draft acceptance criteria for the first three iterations.
