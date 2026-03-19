7.2 Limitations of the System

Although AyuBot provides useful features for uploading, analyzing, and visualizing medical data, the current system has several limitations that should be considered when evaluating production readiness.

First, the platform is optimized for structured, user-uploaded files (CSV and Excel) and for scanned medical reports processed via OCR. Other file formats, streaming data sources, or complex unstructured inputs may not be directly supported without additional preprocessing steps. This means that integrating real-time clinical feeds or bespoke data formats will require extension work.

Second, performance and scalability are constrained in the current design: the system works best for small- to medium-sized datasets. Very large datasets or high volumes of concurrent OCR/AI jobs will require worker scaling, batching, and more robust queue/backpressure handling to avoid slowdowns or failures. Dashboard generation and complex aggregations may need caching and optimized database indices to remain responsive at scale.

Third, the AI and OCR components introduce accuracy and reliability limitations. OCR (Tesseract.js) can misread low-quality scans or handwriting, and AI analysis (Groq/LLaMA) is probabilistic — outputs can be incomplete or incorrect and must be validated by qualified personnel. The system is intended to provide preliminary, assistive insights rather than definitive clinical diagnoses.

Fourth, current visualization options are limited to a set of standard chart types and presets. Advanced, highly customized visualizations, interactive drill-downs, and collaborative editing of dashboards are not fully implemented in this version.

Fifth, the platform does not yet include formal compliance features required for regulated medical deployments (e.g., full HIPAA or GDPR toolsets). Additional controls such as encryption at rest, detailed audit trails, patient consent workflows, and strict data retention/archival policies are required before handling sensitive production data.

Sixth, some operational and security gaps remain: secret management and rotation, multi-factor authentication, file scanning for malware, and automated backups and runbooks are areas that need hardening for production use. The project also depends on external AI and notification services; outages or rate-limits from those providers will directly impact availability.

Despite these limitations, the system provides a strong foundation for AI-assisted medical data workflows. Planned improvements include support for more file formats and real-time connectors, worker autoscaling and job monitoring, stronger security/compliance controls, expanded visualization capabilities, and human-in-the-loop review processes to improve AI result quality.
