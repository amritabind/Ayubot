4.2 Data Design:

4.2.1 Schema Design:

- Users Table

| Variable             | Type     |
|----------------------|----------|
| user_id (PK)         | Integer  |
| name                 | String   |
| email                | String   |
| password             | String   |
| role                 | String   |
| assigned_doctor_id   | Integer  |
| assigned_admin_id    | Integer  |
| specialty            | String   |
| license_image        | LongText |
| bio                  | Text     |
| qualifications       | String   |
| clinic_address       | Text     |
| phone_number         | String   |
| verified             | Boolean  |

- Patient Profiles Table

| Variable                   | Type     |
|----------------------------|----------|
| id (PK)                    | Integer  |
| user_id (FK, unique)       | Integer  |
| date_of_birth              | Date     |
| gender                     | String   |
| phone_number               | String   |
| address                    | Text     |
| blood_group                | String   |
| height                     | Double   |
| weight                     | Double   |
| chronic_conditions         | Text     |
| current_medications        | Text     |
| medical_history            | Text     |
| smoking_status             | String   |
| alcohol_consumption        | String   |
| exercise_frequency         | String   |
| emergency_contact_name     | String   |
| emergency_contact_phone    | String   |
| created_at                 | DateTime |
| updated_at                 | DateTime |

- Consultations Table

| Variable            | Type     |
|---------------------|----------|
| id (PK)             | Integer  |
| patient_id (FK)     | Integer  |
| doctor_id           | Integer  |
| symptoms            | Text     |
| affected_area       | String   |
| ai_diagnosis        | Text     |
| doctor_notes        | Text     |
| prescription        | Text     |
| status              | String   |
| created_at          | DateTime |
| updated_at          | DateTime |

- Appointments Table

| Variable            | Type     |
|---------------------|----------|
| id (PK)             | Integer  |
| patient_id (FK)     | Integer  |
| doctor_id (FK)      | Integer  |
| appointment_date    | DateTime |
| appointment_type    | String   |
| reason              | Text     |
| status              | String   |
| created_at          | DateTime |
| updated_at          | DateTime |

- Medical Reports Table

| Variable            | Type     |
|---------------------|----------|
| id (PK)             | Integer  |
| patient_id (FK)     | Integer  |
| report_type         | String   |
| report_name         | String   |
| extracted_text      | Text     |
| ai_analysis         | Text     |
| file_data           | LongText |
| created_at          | DateTime |
| updated_at          | DateTime |

- Report Shares Table

| Variable            | Type     |
|---------------------|----------|
| id (PK)             | Integer  |
| report_id (FK)      | Integer  |
| doctor_id (FK)      | Integer  |
| shared_by_id (FK)   | Integer  |
| message             | Text     |
| created_at          | DateTime |

Notes:
- Types map from JPA model field types in `backend/ayubot/src/main/java/com/ayubot/model`.
- Primary keys are `id`/`user_id` fields (Long in Java → Integer in this doc).
- Foreign keys reference the `users` or `medical_reports` tables as appropriate.

If you want this exact format exported to a PDF, converted into HTML for the frontend, or augmented with an ER diagram (Mermaid), tell me which one and I will add it.
