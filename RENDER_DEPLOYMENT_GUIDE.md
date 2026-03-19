# Render.com Deployment Guide

## Overview
This guide explains how to deploy the AyuBot application on Render.com for free.

## Prerequisites
1. GitHub account with your repository pushed
2. Render.com account (https://render.com)
3. MySQL database running somewhere (Render PostgreSQL recommended)

## Step 1: Set Up Database on Render

### Option A: Using Render PostgreSQL (Recommended)
1. Go to https://dashboard.render.com
2. Click "New +"
3. Select "PostgreSQL"
4. Fill in details:
   - Name: `ayubot-db`
   - Database: `ayubot`
   - User: `ayubot`
   - Plan: Free
5. Click "Create Database"
6. Copy the connection string (you'll need it later)

### Option B: Use External MySQL Database
If you prefer MySQL, use services like:
- AWS RDS free tier
- PlanetScale (MySQL-compatible)
- Railway.app
- Keep the connection string for Step 3

## Step 2: Import Database Schema

If using Render PostgreSQL:
1. Go to your database dashboard
2. Click "Connect"
3. Use a PostgreSQL client or run:
```sql
-- Update your tables for PostgreSQL if needed
CREATE TABLE IF NOT EXISTS users (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(20) NOT NULL,
  assigned_doctor_id BIGINT NULL REFERENCES users(id),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

Or import the SQL from your `database.sql` file (may need PostgreSQL syntax adjustments).

## Step 3: Deploy Backend

1. Go to https://dashboard.render.com
2. Click "New +"
3. Select "Web Service"
4. Connect your GitHub repository
5. Fill in details:
   - **Name**: `ayubot-backend`
   - **Runtime**: Java
   - **Build Command**: `cd backend/ayubot && mvn clean package -DskipTests`
   - **Start Command**: `java -jar target/ayubot-0.0.1-SNAPSHOT.jar`
   - **Plan**: Free
   - **Root Directory**: `.` (or leave empty)

6. Click "Advanced"
7. Add Environment Variables:
   ```
   JAVA_VERSION=21
   PORT=10000
   SPRING_DATASOURCE_URL=<your_database_connection_string>
   SPRING_DATASOURCE_USERNAME=<your_db_username>
   SPRING_DATASOURCE_PASSWORD=<your_db_password>
   GROQ_API_KEY=<your_groq_api_key>
   SPRING_PROFILES_ACTIVE=render
   ```

8. Click "Create Web Service"
9. Wait for deployment (10-15 minutes)
10. Copy the deployed URL (e.g., `https://ayubot.onrender.com/`)

## Step 4: Deploy Frontend

### Option A: Deploy Separately on Vercel (Recommended)

1. Go to https://vercel.com
2. Click "New Project"
3. Connect GitHub repository
4. Configure:
   - Framework: Other (static)
   - Root Directory: `frontend`
   - Build Command: (leave empty)
5. Deploy

### Option B: Serve from Same Domain (Advanced)
Configure nginx or use Render's multi-service deployment.

## Step 5: Update Frontend API URLs

### For Separate Frontend Deployment:

Edit each HTML file and replace `http://localhost:8080/api` with your backend URL:

Example files to update:
- `frontend/doctor-patients.html`
- `frontend/doctor-consultation.html`
- `frontend/admin-dashboard.html`
- `frontend/chatbot.html`
- And all other HTML files with API calls

Replace:
```javascript
const API_BASE_URL = 'http://localhost:8080/api';
```

With:
```javascript
const API_BASE_URL = 'https://https://ayubot.onrender.com/api';
```

Or use the dynamic config included:
```html
<!-- Add to <head> section -->
<script src="config.js"></script>
```

Then use:
```javascript
const API_BASE_URL = API_CONFIG.getApiUrl();
```

## Step 6: Final Configuration

### Backend Application Properties
The backend needs to know the frontend URL for CORS. Update in Render environment variables or Edit `application.properties`:

```properties
# CORS
spring.web.cors.allowed-origins=https://your-frontend-url.vercel.app
```

## Testing

1. Go to your frontend URL
2. Try login with test credentials
3. Check browser console for any errors
4. Monitor backend logs on Render dashboard

## Common Issues

### Issue: Database connection fails
**Solution**: Verify environment variables are correct in Render dashboard

### Issue: Frontend can't reach backend
**Solution**: 
- Check CORS settings in backend
- Verify backend URL is accessible
- Check firewall/security group settings

### Issue: Free tier goes to sleep
**Solution**: 
- Render free tier spins down after 15 min of inactivity
- Upgrade to paid plan for continuous availability
- Or implement a periodic wake-up request

### Issue: Cold start is slow
**Solution**: Java apps have slow cold starts. This is normal on free tier.

## Alternative: Use Railway.app

Railway.app is also free and may have better free tier limits:

1. Go to https://railway.app
2. Connect GitHub
3. Create new project
4. Add services:
   - Backend (Java)
   - PostgreSQL Database
   - Frontend (Static)

5. Set environment variables same as above
6. Deploy

## Monitoring

- Access Render logs at: https://dashboard.render.com/
- Check logs for errors and debug
- Set up email alerts for failures

## Costs After Free Tier

**Free tier includes**:
- 750 compute-hours/month
- 5GB bandwidth/month
- 1 free database

**When you upgrade**:
- Web services: $7/month
- Database: $7/month
- Bandwidth: $0.1/GB after 5GB

## Next Steps

1. Set up custom domain (optional)
2. Implement SSL/TLS certificates (auto-included on Render)
3. Add backup strategies for database
4. Implement monitoring and alerts
5. Add authentication and security improvements

---

**Need Help?**
- Render Support: https://support.render.com
- Check deployment logs in Render dashboard
- Contact your team for debugging
