4.1 Basic Modules:-

4.1.1 Authentication Module

The Authentication Module manages secure user access by handling user registration and login. It verifies credentials, stores passwords securely (bcrypt), issues sessions or JWTs, and enforces role-based access controls so only authorized users can access protected features such as dataset upload, report export, and admin functions.

4.1.2 User & Profile Management Module

This module handles user profiles and related metadata. It allows creating and updating profiles for patients, doctors, and admins, manages verification flags, contact details, assigned relationships (doctor/admin assignments), and profile assets such as license images and bios.

4.1.3 Patient Profile Module

The Patient Profile Module stores and maintains clinical and demographic data for patients: DOB, gender, contact info, medical history, medications, chronic conditions, vitals (height/weight), and emergency contacts. It provides APIs for viewing and updating these records while keeping audit timestamps.

4.1.4 Appointment Scheduling Module

The Appointment Scheduling Module lets patients request and manage appointments with doctors. It stores appointment times, types, reasons, statuses, and provides scheduling constraints and notifications for confirmations, reschedules, and cancellations.

4.1.5 Consultation & Triage Module

The Consultation Module captures patient-submitted symptoms and triage data, stores doctor notes, AI-assisted diagnosis suggestions, prescriptions, and consultation status. It links consultations to patients and doctors and supports follow-up workflows.

4.1.6 Medical Report Management Module

This module manages uploaded medical reports and extracted information. It stores binary/text report data, extracted text and AI analysis results, metadata (type/name), and created/updated timestamps. It supports downloading and sharing reports.

4.1.7 Report Sharing & Collaboration Module

The Report Sharing Module enables secure sharing of medical reports between users (doctors, specialists) with optional messages. It records who shared the report, the recipient, and when, and supports revocation or audit trails where required.

4.1.8 Dataset Management & Upload Module

The Dataset Management Module handles uploading, validating, and storing structured dataset files (CSV/Excel). It enforces allowed file types, runs basic validation, stores dataset metadata, and exposes datasets for downstream processing and dashboard generation.

4.1.9 Data Processing & AI Module

The Data Processing Module cleans and transforms raw datasets into analysis-ready formats (parsing, type detection, missing-value handling). The AI component provides analyses such as automated insights, natural-language summaries, and assists diagnosis and report extraction.

4.1.10 Dashboard Generation & Visualization Module

This module converts processed datasets into interactive dashboards and visualizations (bar charts, line charts, pie charts, scatter plots). It exposes endpoints for retrieving chart data and supports saving and exporting dashboards as reports.

4.1.11 Report Generation & Export Module

The Report Generation Module builds downloadable reports (PDF) from dashboards and processed datasets. It formats summaries, charts, and key metrics into a readable document and supports exporting and storing those reports.

4.1.12 Notifications & Messaging Module

This module sends user-facing notifications (email, in-app) for events like appointment confirmations, report shares, and verification updates. It centralizes message templates, delivery retry logic, and user preferences.

4.1.13 Admin & Audit Module

Admin features provide user and content moderation tools, assignment management, and audit logs. This module exposes admin dashboards for system health, user activity, and content moderation workflows.

4.1.14 File Storage & Security Module

Handles secure storage of uploaded files (reports, license images). It ensures files are stored safely (local or cloud), enforces access controls, scans or validates file contents when needed, and integrates with backup/retention policies.

4.1.15 Integration & External Services Module

Manages integration points such as external AI services, email/SMS gateways, and third-party authentication providers. It centralizes API clients, keys, rate-limiting, and retry behavior for external calls.


