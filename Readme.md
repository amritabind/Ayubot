# AyuBot - AI-Powered Medical Diagnosis System

![AyuBot Logo](https://img.shields.io/badge/AyuBot-AI%20Medical%20Assistant-blueviolet)
![Status](https://img.shields.io/badge/Status-Active-success)
![License](https://img.shields.io/badge/License-MIT-blue)

## 🏥 Overview

**AyuBot** is an innovative AI-powered medical diagnosis system that combines cutting-edge technologies to provide preliminary medical insights through:

- **3D Interactive Symptom Checker** - Click on body parts to describe symptoms
- **AI Chat Doctor** - Natural language medical consultation
- **Medical Report Analyzer** - OCR + AI analysis of blood tests, X-rays, MRI scans, and prescriptions
- **Role-Based Dashboards** - Separate interfaces for patients and doctors
- **Secure Authentication** - JWT-based authentication with BCrypt password encryption

## ✨ Key Features

### For Patients
- 🎯 **3D Symptom Checker** - Interactive 3D human model for symptom identification
- 💬 **AI Chat Doctor** - 24/7 AI medical consultation
- 📄 **Report Upload & Analysis** - AI-powered medical report interpretation
- 📊 **Medical Reports Manager** - Track and manage all medical documents
- 🌓 **Dark/Light Theme** - Customizable theme preference

### For Doctors
- 👥 **Patient Management** - View and manage patient records
- 🧠 **AI Diagnostic Support** - AI-assisted preliminary diagnosis
- 📈 **Dashboard Analytics** - Patient statistics and insights

## 🛠️ Technology Stack

### Frontend
- **HTML5, CSS3, JavaScript (ES6+)**
- **Bootstrap 5.3.3** - Responsive design
- **Three.js** - 3D visualization
- **Font Awesome 6.5.0** - Icons
- **Tesseract.js** - OCR for medical reports

### Backend
- **Java 21** with **Spring Boot 4.0.0**
- **Spring Security** - Authentication & Authorization
- **Spring Data JPA** - Database ORM
- **JWT (JSON Web Tokens)** - Secure token-based auth
- **BCrypt** - Password encryption
- **MySQL** - Database

### AI Integration
- **Groq AI API** - Medical consultation and diagnosis
- **LLaMA 3.1 70B Model** - Advanced language processing

## 📋 Prerequisites

- **Java 21+**
- **Maven 3.6+**
- **MySQL 8.0+**
- **Modern web browser** (Chrome, Firefox, Edge)
- **Groq API Key** ([Get one here](https://console.groq.com))

## 🚀 Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/YOUR_USERNAME/ayubot.git
cd ayubot
```

### 2. Database Setup
```bash
# Create MySQL database
mysql -u root -p

# Run in MySQL console
CREATE DATABASE ayubot;
USE ayubot;
SOURCE database.sql;

# Insert demo users
SOURCE insert-demo-users.sql;
```

### 3. Backend Configuration

**Create `application-secrets.properties`** in `backend/ayubot/src/main/resources/`:
```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/ayubot
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD

# Groq AI API Key
groq.api.key=YOUR_GROQ_API_KEY
```

**Update `application.properties`**:
```properties
server.port=8080
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Load secrets from external file
spring.config.import=optional:file:./src/main/resources/application-secrets.properties
```

### 4. Build & Run Backend
```bash
cd backend/ayubot

# Build with Maven
mvn clean install

# Run Spring Boot application
mvn spring-boot:run

# OR run the JAR file
java -jar target/ayubot-0.0.1-SNAPSHOT.jar
```

Backend will start on `http://localhost:8080`

### 5. Run Frontend

**Option A: Using Live Server (Recommended)**
- Open `frontend` folder in VS Code
- Install "Live Server" extension
- Right-click `index.html` → "Open with Live Server"

**Option B: Using Python HTTP Server**
```bash
cd frontend
python -m http.server 8000
```

**Option C: Using Node.js HTTP Server**
```bash
cd frontend
npx http-server -p 8000
```

Frontend will be available at `http://localhost:8000` (or Live Server port)

## 👥 Demo Accounts

### Patient Account
- **Email:** `patient@ayubot.com`
- **Password:** `123`

### Doctor Account
- **Email:** `doctor@ayubot.com`
- **Password:** `123`

## 📁 Project Structure

```
ayubot/
├── frontend/                    # Frontend application
│   ├── index.html              # Landing page
│   ├── login.html              # Login page
│   ├── register.html           # Registration page
│   ├── patient-dashboard.html  # Patient dashboard
│   ├── doctor-dashboard.html   # Doctor dashboard
│   ├── chatbot.html            # AI Chat Doctor
│   ├── symptom-3d.html         # 3D Symptom Checker
│   ├── upload-report.html      # Report upload
│   ├── patient-reports.html    # Reports manager
│   ├── theme.js                # Theme switcher
│   └── 3d-model/               # 3D human model assets
│
├── backend/ayubot/             # Spring Boot backend
│   ├── src/main/java/com/ayubot/
│   │   ├── controller/         # REST API controllers
│   │   ├── model/              # JPA entities
│   │   ├── repository/         # Data access layer
│   │   ├── service/            # Business logic
│   │   ├── config/             # Security configuration
│   │   └── util/               # JWT utilities
│   ├── src/main/resources/
│   │   └── application.properties
│   └── pom.xml                 # Maven dependencies
│
├── database.sql                # Database schema
├── insert-demo-users.sql       # Demo user data
└── README.md                   # This file
```

## 🔐 Security Features

- ✅ **JWT Authentication** - Secure token-based authentication
- ✅ **BCrypt Password Hashing** - Industry-standard encryption
- ✅ **CORS Configuration** - Cross-origin resource sharing
- ✅ **Input Validation** - Server-side validation
- ✅ **SQL Injection Prevention** - JPA/Hibernate ORM
- ✅ **XSS Protection** - Content security policies

## 🌐 API Endpoints

### Authentication
- `POST /api/register` - User registration
- `POST /api/login` - User login
- `POST /api/verify-token` - Token verification

### AI Services
- `POST /api/chat` - AI medical consultation

### User Management
- `GET /api/users` - Get all users (admin)
- `GET /api/users/{id}` - Get user by ID
- `DELETE /api/users/{id}` - Delete user

## 🎨 Features in Detail

### 3D Symptom Checker
- Interactive 3D human anatomy model
- Click-to-identify body parts
- Smart anatomical naming
- Real-time AI diagnosis

### AI Chat Doctor
- Natural language processing
- Medical terminology understanding
- Instant responses via Groq AI
- Conversation history

### Report Analyzer
- OCR text extraction (Tesseract.js)
- AI interpretation of medical reports
- Support for multiple formats (JPG, PNG, PDF)
- Detailed medical analysis

## ⚠️ Medical Disclaimer

**IMPORTANT:** AyuBot is an AI-assisted tool designed for educational and preliminary guidance purposes only. It is **NOT** a substitute for professional medical advice, diagnosis, or treatment.

- Always consult a qualified healthcare professional for medical concerns
- Do not use this application for emergency medical situations
- AI suggestions are preliminary and require professional verification
- This system is not FDA-approved for clinical diagnosis

## 🧪 Testing

### Frontend Testing
1. Open browser console (F12)
2. Test user registration
3. Test login with demo accounts
4. Navigate through all pages
5. Test theme switching
6. Test 3D interaction
7. Test AI chat
8. Test report upload

### Backend Testing
```bash
cd backend/ayubot
mvn test
```

## 🚢 Deployment

### Frontend (GitHub Pages / Netlify / Vercel)
1. Update API URLs in frontend files to production backend URL
2. Deploy frontend folder to hosting service

### Backend (Heroku / AWS / Railway)
1. Update `application.properties` for production database
2. Set environment variables for sensitive data
3. Build and deploy JAR file

### Environment Variables (Production)
```
DB_URL=your_production_database_url
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password
GROQ_API_KEY=your_groq_api_key
JWT_SECRET=your_jwt_secret_key
```

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 👨‍💻 Authors

**Amrita Bind** - Roll No: 2543003  
**Bhagyashree Kalshetty** - Roll No: 2543020

**Institution:** [Your Institution Name]  
**Project Type:** Final Year Project  
**Year:** 2025

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- **Groq AI** for powerful LLM API
- **Three.js** for 3D visualization
- **Spring Boot** community
- **Bootstrap** for responsive design
- **Tesseract.js** for OCR capabilities

## 📞 Support

For support, email your_email@example.com or create an issue in the GitHub repository.

## 🔮 Future Enhancements

- [ ] Mobile application (React Native)
- [ ] Multi-language support
- [ ] Appointment scheduling
- [ ] Telemedicine video calls
- [ ] Prescription management
- [ ] Health tracking dashboard
- [ ] Integration with wearable devices
- [ ] Email notifications
- [ ] Advanced analytics for doctors
- [ ] Export medical reports as PDF

---

<div align="center">

**Made with ❤️ by Amrita Bind & Bhagyashree Kalshetty**

⭐ Star this repository if you found it helpful!

</div>
